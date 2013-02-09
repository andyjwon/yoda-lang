package edu.lmu.cs.xlg.iki.entities;

/**
 * An Iki statement.
 */
public abstract class Statement extends Entity {

    /**
     * Optimizes this statement, returning either (1) null, if the statement if found to be dead
     * or unreachable code, (2) an optimized equivalent statement, or (3) the statement itself.
     */
    public abstract Statement optimize();
}
