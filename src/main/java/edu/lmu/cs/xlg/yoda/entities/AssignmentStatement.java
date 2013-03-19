package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

import edu.lmu.cs.xlg.util.Log;

/**
 * An assignment statement.
 */
public class AssignmentStatement extends Statement {

    private List<Expression> target;
    private List<Expression> source;

    public AssignmentStatement(List<Expression> target, List<Expression> source) {
        this.target = target;
        this.source = source;
    }

    public List<Expression> getTarget() {
        return target;
    }

    public List<Expression> getSource() {
        return source;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        for (Expression t : target) {
            t.analyze(log, table, owner, inLoop);

            if (!t.isWritableLValue()) {
                log.error("non.writable.in.assignment.statement");
            }
            for (Expression s : source) {
                s.analyze(log, table, owner, inLoop);
                s.assertAssignableTo(t.getType(), log, "assignment.type.mismatch");
            }
        }
    }
}
