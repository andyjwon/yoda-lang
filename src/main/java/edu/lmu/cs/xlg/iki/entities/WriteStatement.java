package edu.lmu.cs.xlg.iki.entities;

import java.util.List;
import java.util.ListIterator;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki write statement.
 */
public class WriteStatement extends Statement {

    private List<Expression> expressions;

    public WriteStatement(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public void analyze(SymbolTable table, Log log) {
        for (Expression expression: expressions) {
            expression.analyze(table, log);
        }
    }

    @Override
    public Statement optimize() {
        for (ListIterator<Expression> it = expressions.listIterator(); it.hasNext();) {
            it.set(it.next().optimize());
        }
        return this;
    }
}
