package edu.lmu.cs.xlg.iki.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki while statement.
 */
public class WhileStatement extends Statement {

    private Expression condition;
    private Block body;

    public WhileStatement(Expression condition, Block body) {
        this.condition = condition;
        this.body = body;
    }

    public Expression getCondition() {
        return condition;
    }

    public Block getBody() {
        return body;
    }

    @Override
    public void analyze(SymbolTable table, Log log) {
        condition.analyze(table, log);
        body.analyze(table, log);
    }

    @Override
    public Statement optimize() {
        condition = condition.optimize();
        body.optimize();
        if  (condition.isZero()) {
            // "while 0" is a no-op
            return null;
        }
        return this;
    }
}
