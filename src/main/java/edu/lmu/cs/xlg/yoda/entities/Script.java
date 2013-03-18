package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

/**
 * A Manatee script. A script is actually just a top-level block.
 */
public class Script extends Block {

    public Script(List<Statement> statements) {
        super(statements);
    }

    /**
     * Optimizes this script.
     */
    public void optimize() {
        // TODO
    }
}
