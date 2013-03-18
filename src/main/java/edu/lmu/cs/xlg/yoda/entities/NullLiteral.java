package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A singleton class for an object representing the literal <code>nothing</code>.
 */
public class NullLiteral extends Literal {

    public static NullLiteral INSTANCE = new NullLiteral();

    private NullLiteral() {
        super("nothing");
    }

    /**
     * Analyzes this literal.
     */
    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        type = Type.NULL_TYPE;
    }
}
