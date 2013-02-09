package edu.lmu.cs.xlg.iki.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki binary expression.
 */
public class BinaryExpression extends Expression {

    public static enum Operator {
        PLUS("+"), MINUS("-"), TIMES("*"), DIVIDE("/");

        private String text;

        private Operator(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private Operator operator;
    private Expression left;
    private Expression right;

    public BinaryExpression(Operator op, Expression x, Expression y) {
        this.operator = op;
        this.left = x;
        this.right = y;
    }

    public Operator getOperator() {
        return operator;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public void analyze(SymbolTable table, Log log) {
        left.analyze(table, log);
        right.analyze(table, log);
    }

    @Override
    public Expression optimize() {
        left = left.optimize();
        right = right.optimize();
        if (left instanceof Number && right instanceof Number) {
            int x = Number.class.cast(left).getValue();
            int y = Number.class.cast(right).getValue();
            switch (operator) {
            case PLUS: return new Number(x + y);
            case MINUS: return new Number(x - y);
            case TIMES: return new Number(x * y);
            case DIVIDE: if (y != 0) return new Number(x / y);
            }
        } else {
            switch (operator) {
            case PLUS:
                if (right.isZero()) return left;
                if (left.isZero()) return right;
            case MINUS:
                if (right.isZero()) return left;
                if (left.sameVariableAs(right)) return new Number(0);
            case TIMES:
                if (right.isOne()) return left;
                if (left.isOne()) return right;
                if (right.isZero()) return new Number(0);
                if (left.isZero()) return new Number(0);
            case DIVIDE:
                if (right.isOne()) return left;
                if (left.sameVariableAs(right)) return new Number(1);
            }
        }

        // Could not find any optimizations
        return this;
    }
}
