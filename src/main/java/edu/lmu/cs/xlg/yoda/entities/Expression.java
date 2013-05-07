package edu.lmu.cs.xlg.yoda.entities;

/**
 * An expression.
 */
public abstract class Expression extends Entity {

    /**
     * Returns whether this expression can be assigned to.  False by default; it will be
     * overwritten only in those particular cases where it should return true.
     */
    boolean isWritableValue() {
        return false;
    }

}