package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

import edu.lmu.cs.xlg.util.Log;

/**
 * A statement that declares a list of entities, such as a variables, procedures, functions, or types.
 */
public class DecStatement extends Statement {

    private List<Variable> variables;

    public DecStatement(List<Variable> variables) {
        this.variables = variables;
    }

    public List<Variable> getNames() {
        return variables;
    }

    
	@Override
	public void analyze(Log log, SymbolTable table, Subroutine owner,
			boolean inLoop) {
	    for (Variable variable : variables) {
	        table.isDeclared(variable.getName(), log);
	        variable.analyze(log, table, owner, inLoop);
	    }
	}
}
