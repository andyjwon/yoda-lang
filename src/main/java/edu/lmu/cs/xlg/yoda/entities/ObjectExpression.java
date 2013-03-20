package edu.lmu.cs.xlg.yoda.entities;

import java.util.HashMap;
import java.util.Map;

import edu.lmu.cs.xlg.util.Log;

public class ObjectExpression extends Expression {

	private Map<Variable, Expression> objectValues;

	public ObjectExpression () {
		this.objectValues = new HashMap<Variable, Expression>();
	}

	public ObjectExpression (Map<Variable, Expression> objectValues) {
		this.objectValues = objectValues;
	}

	public void add (Variable v, Expression e) {
		Map<Variable, Expression> m = new HashMap<Variable, Expression>();
		m.put(v, e);
	}

	public Map<Variable, Expression> getObjectValues() {
		return objectValues;
	}

	@Override
	public void analyze(Log log, SymbolTable table, Subroutine owner,
			boolean inLoop) {
		// TODO Auto-generated method stub
	}
}
