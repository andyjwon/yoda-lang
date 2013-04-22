package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A loop that executes at most a fixed number of times.
 */
public class TimesLoop extends Statement {

    private Expression count;
    private Block body;

    public TimesLoop(Expression count, Block body) {
        this.count = count;
        this.body = body;
    }

    public Expression getCount() {
        return count;
    }

    public Block getBody() {
        return body;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        count.analyze(log, table, owner, inLoop);
        body.analyze(log, table, owner, true);
    }
}
