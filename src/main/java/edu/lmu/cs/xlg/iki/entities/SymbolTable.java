package edu.lmu.cs.xlg.iki.entities;

import java.util.HashMap;
import java.util.Map;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki symbol table.  Iki is a dreadfully simple language, so its symbol tables are dreadfully
 * simple.  A table contains a mapping from names to variables, and a parent table, since Iki is
 * block-structured and lexically scoped.
 */
public class SymbolTable {

    private Map<String, Variable> map = new HashMap<String, Variable>();
    private SymbolTable parent;

    /**
     * Creates a symbol table with the given parent.
     */
    public SymbolTable(SymbolTable parent) {
        this.parent = parent;
    }

    /**
     * Creates a root symbol table.
     */
    public SymbolTable() {
        this(null);
    }

    /**
     * Looks for the variable with the given name in this symbol table.  If not there, the search
     * will continue in the parent table.  We assume the chain of parent tables is null-terminated.
     * If no variable with the given name is found in any table on the choin, an error is logged
     * and the "fake variable" is returned.
     */
    public Variable lookupVariable(String name, Log log) {
        Variable variable = null;
        for (SymbolTable table = this; table != null; table = table.getParent()) {
            variable = table.map.get(name);
            if (variable != null) {
                return variable;
            }
        }
        log.error("variable.not.found");
        return Variable.FAKE_VARIABLE;
    }

    /**
     * Adds a variable to the symbol table.  If a variable with the same name already exists in
     * the table, logs a redeclared identifier error and enters nothing.
     */
    public void addVariable(Variable variable, Log log) {
        if (map.containsKey(variable.getName())){
            log.error("identifer.redeclared");
        } else {
            map.put(variable.getName(), variable);
        }
    }

    /**
     * Returns the parent table.
     */
    public SymbolTable getParent() {
        return parent;
    }
}
