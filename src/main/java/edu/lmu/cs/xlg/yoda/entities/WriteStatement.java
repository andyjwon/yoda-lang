package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A statement for writing a single expression to standard output.
 */
public class WriteStatement extends Statement {

    private Expression expression;

    public WriteStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        expression.analyze(log, table, owner, inLoop);
    }
}
