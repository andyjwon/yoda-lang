package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

public abstract class VariableExpression extends Expression{
	
	public abstract boolean isWritableValue();
	
	public void assertWritable(Log log) {
		if (!isWritableValue()) {
			log.error("read_only_error");
		}
	}
}
