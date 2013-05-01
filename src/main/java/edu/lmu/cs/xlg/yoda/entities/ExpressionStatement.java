package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

public class ExpressionStatement extends Statement{
	private Expression e;
	
	public ExpressionStatement(Expression e) {
		this.e = e;
	}
	
	public Expression getE() {
		return e;
	}
	
	@Override
	public void analyze(Log log, SymbolTable table, Subroutine owner,
			boolean inLoop) {
		e.analyze(log, table, owner, inLoop);
		
	}

}
