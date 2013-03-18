package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A character literal.
 */
public class CharacterLiteral extends Literal {

    /**
     * Creates a character literal given its lexeme. The lexeme does contain the single quote
     * delimiters.
     */
    public CharacterLiteral(String lexeme) {
        super(lexeme);
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        this.type = Type.CHARACTER;
    }
}
