package edu.lmu.cs.xlg.iki.entities;

import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki entity.
 *
 * The front end of the compiler produces an intermediate representation in the form of a graph.
 * Each entity in the graph has some syntactic and semantic content.  The syntactic content is
 * filled in by the entity's constructor, and the semantic content is filled in by its analyze
 * method.
 *
 * The entities are naturally grouped into a hierarchy of classes:
 *
 * <pre>
 *     Program (block)
 *     Block (declarations, statements)
 *     Declaration
 *         Variable (name)
 *     Statement
 *         AssignmentStatement (variableReference, expression)
 *         ReadStatement (variableReferences)
 *         WriteStatement (expressions)
 *         WhileStatement (condition, body)
 *     Expression
 *         Number (value)
 *         VariableReference (name)
 *         BinaryExpression (operator, left, right)
 * </pre>
 */
public abstract class Entity {

    /**
     * Writes a simple, indented, syntax tree rooted at the given entity to the given print
     * writer.  Each level is indented two spaces.
     */
    public static void printSyntaxTree(String indent, String prefix, Entity e, PrintWriter out) {

        // Prepare the line to be written
        String className = e.getClass().getName();
        String line = indent + prefix + className.substring(className.lastIndexOf('.') + 1);

        // Process the fields, adding plain attributes to the line, but storing all the entity
        // children in a linked hash map to be processed after the line is written.  We use a
        // linked hash map because the order of output is important.
        Map<String, Entity> children = new LinkedHashMap<String, Entity>();
        for (Map.Entry<String, Object> entry: e.attributes().entrySet()) {
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
            printSyntaxTree(indent + "  ", child.getKey() + ": ", child.getValue(), out);
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

    public static void dump(Entity root, PrintWriter writer) {
        writer.println("Semantic dump not implemented");
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
     * Performs semantic analysis on this entity.  Generally this operation updates fields in
     * the entity.  Sometimes it does nothing.  Sometimes it detects and logs errors.
     */
    public abstract void analyze(SymbolTable table, Log log);
}
