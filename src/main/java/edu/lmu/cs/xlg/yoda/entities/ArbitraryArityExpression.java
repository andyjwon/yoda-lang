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
        }
    }
}
