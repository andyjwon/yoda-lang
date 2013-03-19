package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * An assignment statement.
 */
public class AssignmentStatement extends Statement {

    private List<Expression> target;
    private List<Expression> source;

    public AssignmentStatement(Expression target, Expression source) {
        this.target = target;
        this.source = source;
    }

    public Expression getTarget() {
        return target;
    }

    public Expression getSource() {
        return source;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        target.analyze(log, table, owner, inLoop);
        source.analyze(log, table, owner, inLoop);
        if (!target.isWritableLValue()) {
            log.error("non.writable.in.assignment.statement");
        }
        source.assertAssignableTo(target.getType(), log, "assignment.type.mismatch");
    }
}
