package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * A statement for immediately breaking out of a loop.
 */
public class ExitStatement extends Statement {

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        if (!inLoop) {
            log.error("exit.not.in.loop");
        }
    }
}
