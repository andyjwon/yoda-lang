package edu.lmu.cs.xlg.iki.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki variable reference.  A variable reference is syntactically just an identifier.  During
 * semantic analysis we figure out the referenced variable.
 */
public class VariableReference extends Expression {

    private String name;
    private Variable referent;

    public VariableReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Variable getReferent() {
        return referent;
    }

    @Override
    public void analyze(SymbolTable table, Log log) {
        referent = table.lookupVariable(name, log);
    }
}
