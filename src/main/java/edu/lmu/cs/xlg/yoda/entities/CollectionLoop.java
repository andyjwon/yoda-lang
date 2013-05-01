package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A loop that runs an iterator through the values in an array or string.
 */
public class CollectionLoop extends Statement {

    private String iteratorName;
    private Variable iterator;
    private Expression collection;
    private Block body;

    public CollectionLoop(String iteratorName, Expression collection, Block body) {
        this.iteratorName = iteratorName;
        this.collection = collection;
        this.body = body;
    }

    public String getIteratorName() {
        return iteratorName;
    }

    public Variable getIterator() {
        return iterator;
    }

    public Expression getCollection() {
        return collection;
    }

    public Block getBody() {
        return body;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        collection.analyze(log, table, owner, inLoop);
        body.createTable(table);
        iterator = new Variable(iteratorName);
        body.getTable().insert(iterator, log);
        body.analyze(log, table, owner, true);
    }
}
