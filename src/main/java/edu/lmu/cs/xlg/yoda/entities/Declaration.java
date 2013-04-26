package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A statement that declares an entity, such as a variable, procedure, function, or type.
 */
public abstract class Declaration extends Statement {

    private String name;

    public Declaration(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    void assertInitialized(Expression initializer, Log log) {
        if (initializer == null) {
            log.error("mismatched.assignment", initializer);
        }
    }
}
