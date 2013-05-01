package edu.lmu.cs.xlg.yoda.entities;

import java.util.ArrayList;
import java.util.List;

import edu.lmu.cs.xlg.util.Log;

/**
 * A statement that declares a list of entities, such as a variables, procedures, functions, or types.
 */
public class DecStatement extends Statement {

    private List<String> targets;
    private List<Expression> sources;
    private List<Variable> variables;
    private boolean constant;

    public DecStatement(String target, Expression source, boolean constant) {
        this.targets = new ArrayList<String>();
        this.sources = new ArrayList<Expression>();
        this.targets.add(target);
        this.sources.add(source);
        this.constant = constant;
    }

    public DecStatement(List<String> targets, List<Expression> sources) {
        this.targets = targets;
        this.sources = sources;
        this.constant = false;
    }

    public List<String> getNames() {
        return this.targets;
    }
    
    public List<Variable> getVariables() {
    	return this.variables;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        if (targets.size() != sources.size() || targets.size() <= 0) {
            log.error("bad_parallel_declaration");
            return;
        }
        variables = new ArrayList<Variable>();
        for (int i = 0; i < targets.size(); i++) {
            variables.add(new Variable(targets.get(i), sources.get(i), constant));
        }
        for (Variable variable : variables) {
            variable.analyze(log, table, owner, inLoop);
        }
    }
}
