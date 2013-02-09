package edu.lmu.cs.xlg.iki.entities;

/**
 * An Iki expression.
 */
public abstract class Expression extends Entity {

    /**
     * Optimizes this expression, returning an optimized version if possible, otherwise returns
     * the expression itself.  This method is intended to be overridden in subclasses; however,
     * since most forms of expressions require no optimization, a default implementation is
     * provided here.
     */
    public Expression optimize() {
        return this;
    }

    // Utility used in optimization
    boolean isZero() {
        return this instanceof Number && Number.class.cast(this).getValue() == 0;
    }

    // Utility used in optimization
    boolean isOne() {
        return this instanceof Number && Number.class.cast(this).getValue() == 1;
    }

    // Utility used in optimization
    boolean sameVariableAs(Expression that) {
        return this instanceof VariableReference && that instanceof VariableReference &&
            VariableReference.class.cast(this).getReferent() ==
                VariableReference.class.cast(that).getReferent();
    }
}
