package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A simple typsafe enum of the two boolean literals, yes and no.
 */
public class BooleanLiteral extends Literal {

    public static final BooleanLiteral TRUE = new BooleanLiteral("true");
    public static final BooleanLiteral FALSE = new BooleanLiteral("false");

    /**
     * Constructs a boolean literal.  The constructor is private because the only two instances
     * of this class are defined as public static members.
     */
    private BooleanLiteral(String lexeme) {
        super(lexeme);
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        this.type = Type.TRUTH_VALUE;
    }
}
