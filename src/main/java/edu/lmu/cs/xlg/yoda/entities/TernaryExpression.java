package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

public class TernaryExpression extends Expression {

    private Expression condition;
    private Expression trueExpression;
    private Expression falseExpression;

    public TernaryExpression(Expression condition, Expression trueExpression, Expression falseExpression) {
        this.condition = condition;
        this.trueExpression = trueExpression;
        this.falseExpression = falseExpression;
    }

    public Expression getCondition() {
        return condition;
    }

    public Expression getTrueExpression() {
        return trueExpression;
    }

    public Expression getFalseExpression() {
        return falseExpression;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        condition.analyze(log, table, owner, inLoop);
        trueExpression.analyze(log, table, owner, inLoop);
        falseExpression.analyze(log, table, owner, inLoop);

    }
}
