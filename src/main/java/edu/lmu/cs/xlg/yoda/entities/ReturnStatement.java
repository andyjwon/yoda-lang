package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A statement indicating a return from a procedure or function.
 */
public class ReturnStatement extends Statement {

    private Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        if (owner == null) {
            // At top-level, not inside any function
            log.error("return.outside.subroutine");

        } else if (owner instanceof Procedure) {
            // Inside a procedure, we can't have a return expression
            if (expression != null) {
                log.error("return.value.not.allowed");
            }

        } else /* owner instanceof Function */ {
            // Returning something from a function, so typecheck
            expression.analyze(log, table, owner, inLoop);
            //expression.assertAssignableTo(Function.class.cast(owner).getReturnType(),
              //      log, "return.type.error");
        }
    }
}
