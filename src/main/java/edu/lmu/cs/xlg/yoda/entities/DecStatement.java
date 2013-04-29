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
        targets = new ArrayList<String>();
        sources = new ArrayList<Expression>();
        targets.add(target);
        sources.add(source);
        this.constant = constant;
    }

    public DecStatement(List<String> targets, List<Expression> sources) {
        this.targets = targets;
        this.sources = sources;
        this.constant = false;
    }

    public List<String> getNames() {
        return targets;
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
            table.isDeclared(variable.getName(), log);
            variable.analyze(log, table, owner, inLoop);
        }
    }
}
