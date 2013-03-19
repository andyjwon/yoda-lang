package edu.lmu.cs.xlg.yoda.entities;

import java.util.ArrayList;
import java.util.List;

import edu.lmu.cs.xlg.util.Log;


public class Block extends Entity {

    private List<Statement> statements = new ArrayList<Statement>();
    private SymbolTable table = null;

    public Block(List<Statement> statements) {
        this.statements = statements;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public SymbolTable getTable() {
        return table;
    }

    /**
     * Creates the symbol table for this block, only if it has not already been created.
     */
    public void createTable(SymbolTable parent) {
        if (table == null) {
            table = new SymbolTable(parent);
        }
    }

    /**
     * Returns the list of all the functions declared immediately within this block, in the order
     * they are declared.
     */
    public List<Subroutine> getSubroutines() {
        List<Subroutine> result = new ArrayList<Subroutine>();
        for (Statement s: statements) {
            if (s instanceof Subroutine) {
                result.add((Subroutine)s);
            }
        }
        return result;
    }

    @Override
    public void analyze(Log log, SymbolTable outer, Subroutine owner, boolean inLoop) {

        List<Subroutine> subroutines = getSubroutines();

        // Create the table if it hasn't already been created.  For blocks that are bodies of
        // functions or loops, the analyze() method of the function or loop will have created this
        // table already, since it is the table in which the parameters or loop indices belong.
        // For blocks that are entire scripts, the table will have already been created, too.
        // All other blocks will need their tables created here.
        if (table == null) {
            table = new SymbolTable(outer);
        }

        // Insert the routines into the table, but analyze ONLY the parameters and return types.
        // We can't analyze the function bodies until all the functions have been put into the
        // table (with analyzed parameters) because within any function body there can be a call
        // to any other function, and we have to be able to analyze the call.  Notice also that the
        // functions are going in before any variables are being looked at since variables can call
        // any function in their initializing expressions.
        for (Subroutine subroutine: subroutines) {
            subroutine.analyzeSignature(log, table, owner, inLoop);
            table.insert(subroutine, log);
        }

        // Now just go through all the items in order and analyze everything, making sure to
        // insert variables because they have not yet been inserted.
        for (Statement s: statements) {
            if (s instanceof Variable) {
                table.insert((Variable)s, log);
            }
            s.analyze(log, table, owner, inLoop);
        }
    }
}
