package edu.lmu.cs.xlg.iki.entities;

/**
 * An Iki declaration.
 */
public abstract class Declaration extends Entity {

    private String name;

    public Declaration(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
