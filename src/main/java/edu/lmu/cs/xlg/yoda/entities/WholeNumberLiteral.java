package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A literal for 32-bit integers.
 */
public class WholeNumberLiteral extends Literal {

    // Semantic value, not part of syntax analysis.
    private Integer value;

    public WholeNumberLiteral(String lexeme) {
        super(lexeme);
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        type = Type.WHOLE_NUMBER;

        // Let Java do the work of determining whether the lexeme describes a valid 2s-complement
        // integer literal; after all, whole number literals in Yoda are exactly like integer
        // literals in Java.
        try {
            value = Integer.valueOf(getLexeme());
        } catch (NumberFormatException e) {
            log.error("bad.int", getLexeme());
        }
    }
}
