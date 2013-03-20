package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A loop that takes an iterator from one value to another by some step value.
 */
public class ConditionalLoop extends Statement {

    private Variable iterator;
    private Expression condition;
    private Expression step;
    private Block body;

    public ConditionalLoop(Variable iterator, Expression condition, Expression step,
            Block body) {
        this.condition = condition;
        this.step = step;
        this.body = body;
    }

    public Variable getIterator() {
        return iterator;
    }

    public Expression getCondition() {
        return condition;
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
