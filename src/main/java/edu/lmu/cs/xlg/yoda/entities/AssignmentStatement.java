package edu.lmu.cs.xlg.yoda.entities;

import java.util.ArrayList;
import java.util.List;

import edu.lmu.cs.xlg.util.Log;

/**
 * An assignment statement.
 */
public class AssignmentStatement extends Statement {

    private List<Expression> targets;
    private List<Expression> sources;

    public AssignmentStatement(Expression target, Expression source) {
        this.targets = new ArrayList<Expression>();
        this.sources = new ArrayList<Expression>();
        this.targets.add(target);
        this.sources.add(source);
    }

    public AssignmentStatement(List<Expression> targets, List<Expression> sources) {
        this.targets = targets;
        this.sources = sources;
    }

    public List<Expression> getTarget() {
        return targets;
    }

    public List<Expression> getSource() {
        return sources;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        for (Expression t : targets) {
            t.analyze(log, table, owner, inLoop);
            
            if(t instanceof IdentifierExpression) {
            	System.out.println("YO " + IdentifierExpression.class.cast(t).isWritableValue());
            }
            if (!t.isWritableValue()) {
                log.error("non.writable.in.assignment.statement");
            }
            for (Expression s : sources) {
                s.analyze(log, table, owner, inLoop);
            }
        }
    }
}
