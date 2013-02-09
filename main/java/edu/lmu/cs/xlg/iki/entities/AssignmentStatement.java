package edu.lmu.cs.xlg.iki.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki assignment statement.
 */
public class AssignmentStatement extends Statement {

    private VariableReference variableReference;
    private Expression expression;

    public AssignmentStatement(VariableReference v, Expression e) {
        this.variableReference = v;
        this.expression = e;
    }

    public VariableReference getVariableReference() {
        return variableReference;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void analyze(SymbolTable table, Log log) {
        variableReference.analyze(table, log);
        expression.analyze(table, log);
    }

    @Override
    public Statement optimize() {
        expression = expression.optimize();
        if (variableReference.sameVariableAs(expression)) {
            // Assignment to self is a no-op
            return null;
        }
        return this;
    }

}
