package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * An expression made up of an operator and two operands.
 */
public class RelationalExpression extends Expression {

    private String op;
    private Expression left;
    private Expression right;

    /**
     * Creates a binary expression for the given operator and operands.
     */
    public RelationalExpression(Expression left, String op, Expression right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the operator as a string.
     */
    public String getOp() {
        return op;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }


    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        left.analyze(log, table, owner, inLoop);
        right.analyze(log, table, owner, inLoop);

    }
}
