package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

public class Range extends Expression {

	private Expression low;
	private Expression high;

	public Range(Expression low, Expression hai) {
		this.low = low;
		this.high = hai;
	}

	public Expression getLow() {
		return low;
	}

	public Expression getHigh() {
		return high;
	}

	@Override
	public void analyze(Log log, SymbolTable table, Subroutine owner,
			boolean inLoop) {
		// TODO Auto-generated method stub		
	}
}
