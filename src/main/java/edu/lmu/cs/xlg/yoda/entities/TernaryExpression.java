package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

public class TernaryExpression extends Expression {

	Expression condition;
	Expression name;

	@Override
	public void analyze(Log log, SymbolTable table, Subroutine owner,
			boolean inLoop) {
		// TODO Auto-generated method stub
		
	}
}
