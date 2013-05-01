package edu.lmu.cs.xlg.yoda.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.lmu.cs.xlg.util.Log;

/**
 * A trivial symbol table for a block-structured language.
 */
public class SymbolTable extends Entity {

    // The actual contents of the symbol table.  Names map to entities.
    Map<String, Entity> map = new HashMap<String, Entity>();

    // The table to look in if you can't find what you want here.
    SymbolTable parent;

    /**
     * Creates a symbol table with the given parent.
     */
    public SymbolTable(SymbolTable parent) {
        this.map = new HashMap<String, Entity>();
        this.parent = parent;
    }

    /**
     * Inserts an item into the table, rejecting duplicates.
     */
    public void insert(Declaration d, Log log) {
        Object oldValue = map.put(d.getName(), d);

        if (oldValue != null) {
            log.error("identifier.redeclared", d.getName());
        }
    }

    /**
     * Looks up a type in this table, or if not found, searches along its ancestor chain.
     *
     * @param name the name of the type being searched for.
     * @return the innermost visible type with that name.  If not found, or if the value found is
     * not a type object, logs an error message and returns Type.ANY.
     */


    /**
     * Looks up an entity in this table, or if not found, searches along its ancestor chain.
     *
     * @param name the name of the entity being searched for.
     * @return the innermost visible entity with that name.  If not found, returns the value
     *         Variable.ARBITRARY.
     */
    public Entity lookup(String name, Log log) {
        Entity value = map.get(name);
        if (value != null) {
            return value;
        } else if (parent == null) {
            log.error("not.found", name);
            return Variable.ARBITRARY;
        } else {
            return parent.lookup(name, log);
        }
    }

    /**
     * Looks up a procedure in this table, or if not found, searches along its ancestor chain.
     *
     * @param name the name of the procedure to search for.
     * @return the innermost visible procedure with that name.  If not found, or if the value found
     * is not a procedure object, logs an error message and returns null.
     */
    public Procedure lookupProcedure(String name, Log log) {
        Object value = map.get(name);

        if (value == null) {
            if (parent == null) {
                log.error("procedure.not.found", name);
                return null;
            } else {
                return parent.lookupProcedure(name, log);
            }
        } else if (value instanceof Procedure) {
            return (Procedure)value;
        } else {
            log.error("not.a.procedure", name);
            return null;
        }
    }

    /**
     * Returns all the entities in this symbol table that are instances of class c or any descendant
     * of c.  To get all the entities in the table, pass in class Entity (or java.lang.Object).
     */
    public Set<Object> getEntitiesByClass(Class<?> c) {
        Set<Object> result = new HashSet<Object>();
        for (Object value: map.values()) {
            if (c.isInstance(value)) {
                result.add(value);
            }
        }
        return result;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        // Intentionally empty
    }
}
