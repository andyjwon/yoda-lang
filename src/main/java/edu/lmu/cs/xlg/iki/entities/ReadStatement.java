package edu.lmu.cs.xlg.iki.entities;

import java.util.List;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki read statement.
 */
public class ReadStatement extends Statement {

    private List<VariableReference> references;

    public ReadStatement(List<VariableReference> references) {
        this.references = references;
    }

    public List<VariableReference> getReferences() {
        return references;
    }

    @Override
    public void analyze(SymbolTable table, Log log) {
        for (VariableReference v: references) {
            v.analyze(table, log);
        }
    }

    @Override
    public Statement optimize() {
        // Nothing to do, really, as variable references don't need to be optimized.
        return this;
    }
}
