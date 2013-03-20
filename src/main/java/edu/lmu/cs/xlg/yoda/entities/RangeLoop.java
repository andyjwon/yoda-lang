package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A loop that takes an iterator from one value to another by some step value.
 */
public class RangeLoop extends Statement {

    private Variable iterator;
    private Range range;
    private Expression step;
    private Block body;

    public RangeLoop(Range range, Expression step,
            Block body) {
        this.range = range;
        this.step = step;
        this.body = body;
    }

    public Variable getIterator() {
        return iterator;
    }

    public Expression getRange() {
        return range;
    }

    public Expression getStep() {
        return step;
    }

    public Block getBody() {
        return body;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        if (step != null) {
            step.analyze(log, table, owner, inLoop);
        }
        if (step != null) {
            step.assertInteger("range loop", log);
        }
        body.createTable(table);
        body.getTable().insert(iterator, log);
        body.analyze(log, table, owner, true);
    }
}
