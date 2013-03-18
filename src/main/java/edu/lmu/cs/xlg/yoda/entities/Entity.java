package edu.lmu.cs.xlg.yoda.entities;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import edu.lmu.cs.xlg.util.Log;

/**
 * A Manatee entity.
 *
 * <p>The front end of the compiler produces an intermediate representation in the form of a graph.
 * Each entity in the graph has some syntactic and semantic content.  The syntactic content is
 * filled in by the entity's constructor, and the semantic content is filled in by its
 * <code>analyze</code> method.  Entities also have an <code>optimize</code> method for
 * simple local improvements.</p>
 *
 * <p>The entities are naturally grouped into a hierarchy of classes.  Properties set
 * during semantic analysis are marked with an asterisk:</p>
 *
 * <pre>
 * Entity
 *     Block (statements, table*)
 *         Script
 *     Type
 *         ArrayType (baseType*)
 *     Statement
 *         Declaration (name)
 *             Variable (typeName, initializer, constant, type*)
 *             Subroutine (parameters, body)
 *                 Procedure
 *                 Function (returnTypeName, returnType*)
 *         DoNothingStatement
 *         AssignmentStatement (target, source)
 *         ReadStatement (expression)
 *         WriteStatement (expression)
 *         ExitStatement
 *         ReturnStatement (expression)
 *         CallStatement (procedureName, args, procedure*)
 *         ModifiedStatement (modifier, statement)
 *         ConditionalStatement (arms, elsePart)
 *         PlainLoop (body)
 *         TimesLoop (count, body)
 *         CollectionLoop (iteratorName, collection, body)
 *         RangeLoop (iteratorName, low, high, step, body)
 *         WhileLoop (condition, body)
 *     Expression (type*)
 *         Literal (lexeme)
 *             BooleanLiteral
 *             CharacterLiteral
 *             NumberLiteral (value*)
 *             WholeNumberLiteral (value*)
 *             StringLiteral
 *             NullLiteral
 *         IdentifierExpression (name, referent*)
 *         UnaryExpression (op, operand)
 *         BinaryExpression (op, left, right)
 *         ArrayConstructor (expressions)
 *         SubscriptExpression (collection, index)
 *         FunctionCall (function, args)
 *     ModifiedStatement.Modifier (modifierType, condition)
 *     ConditionalStatement.Arm (condition, block)
 * </pre>
 */
public abstract class Entity {

    /**
     * Collection of all entities that have ever been created, as a map with the entities as keys
     * and their ids as values.
     */
    private static Map<Entity, Integer> all = new LinkedHashMap<Entity, Integer>();

    /**
     * Creates an entity, "assigning" it a new unique id by placing it in a global map mapping the
     * entity to its id.
     */
    public Entity() {
        synchronized (all) {
            all.put(this, all.size());
        }
    }

    /**
     * Returns the integer id of this entity.
     */
    public Integer getId() {
        return all.get(this);
    }

    /**
     * Returns a short string containing this entity's id.
     */
    @Override
    public String toString() {
        return "#" + getId();
    }

    /**
     * Writes a simple, indented, syntax tree rooted at the given entity to the given print
     * writer.  Each level is indented two spaces.
     */
    public final void printSyntaxTree(String indent, String prefix, PrintWriter out) {

        // Prepare the line to be written
        String className = getClass().getName();
        String line = indent + prefix + className.substring(className.lastIndexOf('.') + 1);

        // Process the fields, adding plain attributes to the line, but storing all the entity
        // children in a linked hash map to be processed after the line is written.  We use a
        // linked hash map because the order of output is important.
        Map<String, Entity> children = new LinkedHashMap<String, Entity>();
        for (Map.Entry<String, Object> entry: attributes().entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            } else if (value instanceof Entity) {
                children.put(name, Entity.class.cast(value));
            } else if (value instanceof Iterable<?>) {
                try {
                    int index = 0;
                    for (Object child : (Iterable<?>) value) {
                        children.put(name + "[" + (index++) + "]", (Entity) child);
                    }
                } catch (ClassCastException cce) {
                    // Special case for non-entity collections
                    line += " " + name + "=\"" + value + "\"";
                }
            } else {
                // Simple attribute, attach description to node name
                line += " " + name + "=\"" + value + "\"";
            }
        }
        out.println(line);

        // Now we can go through all the entity children that were saved up earlier
        for (Map.Entry<String, Entity> child: children.entrySet()) {
            child.getValue().printSyntaxTree(indent + "  ", child.getKey() + ": ", out);
        }
    }

    /**
     * Traverses the semantic graph starting at this entity, applying visitor v to each entity.
     */
    public void traverse(Visitor v, Set<Entity> visited) {

        // The graph may have cycles, so skip this entity if we have seen it before.  If we
        // haven't, mark it seen.
        if (visited.contains(this)) {
            return;
        }
        visited.add(this);

        v.onEntry(this);
        for (Map.Entry<String, Object> entry: attributes().entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Entity) {
                Entity.class.cast(value).traverse(v, visited);
            } else if (value instanceof Iterable<?>) {
                for (Object child : (Iterable<?>) value) {
                    Entity.class.cast(child).traverse(v, visited);
                }
            }
        }
        v.onExit(this);
    }

    public static interface Visitor {
        void onEntry(Entity e);
        void onExit(Entity e);
    }

    /**
     * Writes a concise line with the entity's id number, class, and non-null properties.  For any
     * property that is itself an entity, or a collection of entities, only the entity id is
     * written.
     */
    private void writeDetailLine(PrintWriter writer) {
        String classname = getClass().getName();
        String kind = classname.substring(classname.lastIndexOf('.') + 1);
        writer.print(this + "\t(" + kind + ")");

        for (Map.Entry<String, Object> entry: attributes().entrySet()) {
            String name = entry.getKey();
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            if (value.getClass().isArray()) {
                value = Arrays.asList((Object[]) value);
            }
            writer.print(" " + name + "=" + value);
        }
        writer.println();
    }

    public final void printEntities(final PrintWriter writer) {
        traverse(new Visitor() {
            public void onEntry(Entity e) {
                e.writeDetailLine(writer);
            }
            public void onExit(Entity e) {
                // Intentionally empty
            }
        }, new HashSet<Entity>());
    }

    /**
     * Returns a map of name-value pairs for the given entity's fields and their values.  The
     * set of fields computed here are the non-static declared fields of its class, together with
     * the relevant fields of its ancestor classes, up to but not including the class Entity
     * itself.
     */
    private Map<String, Object> attributes() {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        for (Class<?> c = getClass(); c != Entity.class; c = c.getSuperclass()) {
            for (Field field: c.getDeclaredFields()) {
                if ((field.getModifiers() & Modifier.STATIC) == 0) {
                    try {
                        field.setAccessible(true);
                        result.put(field.getName(), field.get(this));
                    } catch (IllegalAccessException cannotHappen) {
                    }
                }
            }
        }
        return result;
    }

    /**
     * Performs semantic analysis on this entity, and (necessarily) on its descendants.
     * @param log
     *            the destination for info and error messages
     * @param table
     *            the current symbol table in which we are to analyze this entity
     * @param owner
     *            the innermost enclosing procedure or function, used to analyze return statements
     * @param inLoop
     *            whether this analysis is taking place within a loop, needed in order to analyze
     *            exit statements.
     */
    public abstract void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop);
}
