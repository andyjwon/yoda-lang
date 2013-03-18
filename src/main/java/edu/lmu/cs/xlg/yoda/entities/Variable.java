package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

public class Variable extends Declaration {

    private String typename;
    private Expression initializer;
    private Type type;
    private boolean constant;

    /**
     * An arbitrary variable, useful in semantic analysis to take the place of a variable that has
     * not been declared.  This variable is type-compatible with everything, so its use serves to
     * prevent a flood of spurious error messages.
     */
    public static final Variable ARBITRARY = new Variable("<arbitrary>", Type.ARBITRARY);

    /**
     * Constructs a variable.
     */
    public Variable(String name, String typename, Expression initializer, boolean constant) {
        super(name);
        this.typename = typename;
        this.initializer = initializer;
        this.constant = constant;
    }

    /**
     * Special constructor for variables created during semantic analysis (not known while parsing).
     * Note that this takes in a real type, rather than just a type name, because these variables
     * aren't part of a user's code and don't have to get analyzed.
     */
    public Variable(String name, Type type) {
        super(name);
        this.typename = type.getName();
        this.initializer = null;
        this.type = type;
    }

    public Expression getInitializer() {
        return initializer;
    }

    public String getTypename() {
        return typename;
    }

    public Type getType() {
        return type;
    }

    public boolean isConstant() {
        return constant;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {

        // If initializer is not present, then there had better be a type.
        if (initializer == null && typename == null) {
            log.error("intializer.or.type.required");
            type = Type.ARBITRARY;
        }

        // The declaration may or may not have a type name.  Look it up if it does.
        if (typename != null) {
            type = table.lookupType(typename, log);
        }

        // If an initializer is present, analyze it and check types.
        if (initializer != null) {
            initializer.analyze(log, table, owner, inLoop);
            if (typename == null) {
                // Here is the type inference part
                type = initializer.type;
            } else {
                initializer.assertAssignableTo(type, log, "initializer.type.mismatch");
            }
        }
    }
}

