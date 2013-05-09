package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

public class SubscriptExpression extends Expression {

    private IdentifierExpression collection;
    private Expression index;

    public SubscriptExpression(IdentifierExpression base, Expression index) {
        this.collection = base;
        this.index = index;
    }

    public Expression getBase() {
        return collection;
    }

    public Expression getIndex() {
        return index;
    }

    public String getName() {
        return collection.getName();
    }

    @Override
    public boolean isWritableValue() {
        return collection.isWritableValue();
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        collection.analyze(log, table, owner, inLoop);
        index.analyze(log, table, owner, inLoop);
    }
}
