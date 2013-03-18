package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * An unqualified loop statement.
 */
public class PlainLoop extends Statement {

    private Block body;

    public PlainLoop(Block body) {
        this.body = body;
    }

    public Block getBody() {
        return body;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        body.analyze(log, table, owner, true);
    }
}
