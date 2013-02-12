package edu.lmu.cs.xlg.iki.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki variable.  Variables are distinct from variable references.  A variable is something
 * that is declared.  A variable reference is a kind of expression.  Iki variable declarations
 * are very simple.  They don't have types nor initial values.  They are a rather boring kind
 * of declaration.
 */
public class Variable extends Declaration {

    /**
     * A fake variable used as a place holder during semantic analysis when an undeclared
     * variable is referenced.  The use of a fake variable in this context is an alternative
     * to stopping the compilation on the first error.
     */
    public static Variable FAKE_VARIABLE = new Variable("$$FAKE$$");

    public Variable(String name) {
        super(name);
    }

    @Override
    public void analyze(SymbolTable table, Log log) {
        table.addVariable(this, log);
    }
}
