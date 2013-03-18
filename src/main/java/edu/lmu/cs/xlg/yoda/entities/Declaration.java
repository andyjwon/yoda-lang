package edu.lmu.cs.xlg.yoda.entities;

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
}
