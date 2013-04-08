package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

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

        // string * int
        if (op.equals("*") && left.getType() == Type.STRING) {
            right.assertInteger("*", log);
            type = Type.STRING;

        // num op num (for arithmetic op)
        } else if (op.matches("[-+*/]")) {
            left.assertArithmetic(op, log);
            right.assertArithmetic(op, log);
            type = (left.type == Type.NUMBER || right.type == Type.NUMBER)
                ? Type.NUMBER : Type.WHOLE_NUMBER;

        // int op int returning int (for shifts and mod)
        } else if (op.matches("<<|>>")) {
            left.assertInteger(op, log);
            right.assertInteger(op, log);
            type = Type.WHOLE_NUMBER;

        // char/num/str op char/num/str (for greater/less inequalities)
        } else if (op.matches("<|<=|>|>=")) {
            if (left.type == Type.CHARACTER) {
                right.assertChar(op, log);
            } else if (left.type == Type.STRING) {
                right.assertString(op, log);
            } else if (left.type.isArithmetic()){
                left.assertArithmetic(op, log);
                right.assertArithmetic(op, log);
            }
            type = Type.TRUTH_VALUE;

        // equals or not equals on primitives
        } else if (op.matches("=|≠")) {
            if (!(left.type.isPrimitive() &&
                    (left.isCompatibleWith(right.type) || right.isCompatibleWith(left.type)))) {
                log.error("eq.type.error", op, left.type, right.type);
            }
            type = Type.TRUTH_VALUE;

        // bool and bool
        // bool or bool
        } else if (op.matches("and|or")) {
            left.assertBoolean(op, log);
            right.assertBoolean(op, log);
            type = Type.TRUTH_VALUE;

        // char in string
        // t in t list
        } else if (op.matches("in")) {
            right.assertArrayOrString("in", log);
            if (right.getType() == Type.STRING) {
                left.assertChar("in", log);
            } else {
                assert(left.getType().canBeAssignedTo(
                    ArrayType.class.cast(right.getType()).getBaseType()));
            }
            type = Type.TRUTH_VALUE;

        // ref is ref
        // ref is not ref
        } else if (op.matches("is") || op.matches("is not")) {
            if (!left.getType().isReference() && !right.getType().isReference()) {
                log.error("non.reference", op);
            }
            if (left.getType() != right.getType()) {
                log.error("type.mismatch", op);
            }
            type = Type.TRUTH_VALUE;

        } else {
            throw new RuntimeException("Internal error in binary expression analysis");
        }
    }
}
