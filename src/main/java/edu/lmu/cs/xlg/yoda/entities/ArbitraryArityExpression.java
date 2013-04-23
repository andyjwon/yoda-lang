package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

import edu.lmu.cs.xlg.util.Log;

/**
 * An expression made up of an operator and two operands.
 */
public class ArbitraryArityExpression extends Expression {

    private String op;
    private List<Expression> operands;

    /**
     * Creates a binary expression for the given operator and operands.
     */
    public ArbitraryArityExpression(String op, List<Expression> operands) {
        this.op = op;
        this.operands= operands;
    }
    
    /**
     * Returns the operator as a string.
     */
    public String getOp() {
        return op;
    }

    /**
     * Returns the list of operands
     */
    public List<Expression> getOperands() {
        return operands;
    }


    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        for (int i = 0; i < operands.size(); i++) {
            operands.get(i).analyze(log, table, owner, inLoop);
            if (op.matches("<|<=|>|>=|=|!=")) {
                break;
            // ( + [string | num]  [string | num]* )
            }
            else if (op.equals("+")) {
                int getType = operands.get(i).assertArithmeticOrString("+", log);
                if ( getType == 1){
                    type = Type.NUMBER;
                }
                else if (getType == 2) {
                    type = Type.STRING;
                }

            // ( [-*/^] num num* )
            } else if (op.matches("[-*/^]")) {
                operands.get(i).assertArithmetic(op, log);
                type = operands.get(i).type == Type.NUMBER ? Type.NUMBER : Type.WHOLE_NUMBER;

            // (% int int+ ) returning int (for mod)
            } else if (op.matches("%")) {
                operands.get(i).assertInteger(op, log);
                type = Type.WHOLE_NUMBER;

            // ( & bool bool)
            // ( | bool bool)
            } else if (op.matches("[|&]")) {
                operands.get(i).assertBoolean(op, log);
                type = Type.TRUTH_VALUE;
            }
            else {
                throw new RuntimeException("Internal error in binary expression analysis");
            }
        }
        // ( op [char|num]  [char|num] ) (for greater/less inequalities)
        if (op.matches("<|<=|>|>=")) {
            if (operands.get(0).type == Type.CHARACTER) {
                operands.get(1).assertChar(op, log);
            } else if (operands.get(0).type.isArithmetic()){
                operands.get(0).assertArithmetic(op, log);
                operands.get(1).assertArithmetic(op, log);
            }
            type = Type.TRUTH_VALUE;
    
        // equals or not equals on primitives
        } else if (op.matches("=|!=")) {
            if (!(operands.get(0).type.isPrimitive() &&
                    (operands.get(0).isCompatibleWith(operands.get(1).type) ||
                        operands.get(1).isCompatibleWith(operands.get(0).type)))) {
                log.error("eq.type.error", op, operands.get(0).type, operands.get(1).type);
            }
            type = Type.TRUTH_VALUE;
        }
    }
}
