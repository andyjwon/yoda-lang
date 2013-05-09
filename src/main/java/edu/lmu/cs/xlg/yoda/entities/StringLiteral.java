package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;
import java.util.List;

/**
 * A string literal.
 */
public class StringLiteral extends Literal {

	private List<Integer> values;
	
    public StringLiteral(String lexeme) {
        super(lexeme);
    }

    public List<Integer> getValues() {
        return values;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        values = CharLiteral.codepoints(getLexeme(), 1, getLexeme().length() - 1, log);
    }
}
