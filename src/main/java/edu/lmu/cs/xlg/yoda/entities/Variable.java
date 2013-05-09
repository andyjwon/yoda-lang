package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

public class Variable extends Declaration {

    private Expression initializer;
    private boolean constant;
    private boolean derived;

    /**
     * An arbitrary variable, useful in semantic analysis to take the place of a variable that has
     * not been declared.  This variable is type-compatible with everything, so its use serves to
     * prevent a flood of spurious error messages.
     */
    public static final Variable ARBITRARY = new Variable("<arbitrary>");

    /**
     * Constructs a variable.
     */
    public Variable(String name, Expression initializer, boolean constant, boolean derived) {
        super(name);
        this.initializer = initializer;
        this.constant = constant;
        this.derived = derived;
    }

    public Variable(String name, Expression initializer, boolean constant) {
        this(name, initializer, constant, false);
    }

    public Variable(String name, Expression initializer) {
        this(name, initializer, false, false);
    }

    /**
     * Special constructor for variables created during semantic analysis (not known while parsing).
     * Note that this takes in a real type, rather than just a type name, because these variables
     * aren't part of a user's code and don't have to get analyzed.
     */
    public Variable(String name) {
        super(name);
        this.initializer = null;
    }

    public Expression getInitializer() {
        return initializer;
    }

    public boolean isConstant() {
    	System.out.println("Is constant? " + constant);
        return constant;
    }

    public boolean isDerived() {
        return derived;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        // If an initializer is present and analyze it.
        if (initializer != null) {
            initializer.analyze(log, table, owner, inLoop);
        }

    }
}

