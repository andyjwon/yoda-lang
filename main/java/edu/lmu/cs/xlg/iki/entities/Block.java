package edu.lmu.cs.xlg.iki.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki block.
 */
public class Block extends Entity {

    private List<Declaration> declarations = new ArrayList<Declaration>();
    private List<Statement> statements = new ArrayList<Statement>();

    public Block(List<Declaration> declarations, List<Statement> statements) {
        this.declarations = declarations;
        this.statements = statements;
    }

    public List<Declaration> getDeclarations() {
        return declarations;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    @Override
    public void analyze(SymbolTable outer, Log log) {
        SymbolTable table = new SymbolTable(outer);
        for (Declaration d: declarations) {
            d.analyze(table, log);
        }
        for (Statement s: statements) {
            s.analyze(table, log);
        }
    }

    /**
     * Performs local optimizations on this block.  In particular we ask each statement in the
     * block to optimize itself.  In cases where the optimization of a statement detects dead
     * or unreachable code, we remove that statement.
     */
    public void optimize() {
        for (ListIterator<Statement> it = statements.listIterator(); it.hasNext();) {
            Statement original = it.next();
            Statement optimized = original.optimize();
            if (optimized == null) {
                it.remove();
            } else if (optimized != original) {
                it.set(optimized);
            }
        }
    }
}
