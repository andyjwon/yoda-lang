package edu.lmu.cs.xlg.iki.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki numeric literal expression.
 */
public class Number extends Expression {

    private int value;

    public Number(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void analyze(SymbolTable table, Log log) {
        // TODO - Nothing for now, but we might want later to check for the literal being
        // too large.  Something to think about.
    }
}
