package edu.lmu.cs.xlg.iki.entities;

import java.util.HashSet;
import java.util.Set;

import edu.lmu.cs.xlg.util.Log;

/**
 * An Iki program.
 */
public class Program extends Entity {

    private Block block;

    public Program(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    @Override
    public void analyze(SymbolTable table, Log log) {
        block.analyze(table, log);
    }

    /**
     * Optimizes the program.
     */
    public void optimize() {

        // Local optimizations
        block.optimize();

        // Global optimizations.  Only one so far is removing unused variables.  To do this, we
        // traverse the entity graph, collecting the unused variables.  We do this by adding
        // variables to the set when they are declared, and removing them when used.  When we
        // exit a block, we can removed the unused ones.
        final Set<Variable> variables = new HashSet<Variable>();
        Visitor unusedVariableDetector = new Visitor() {
            public void onEntry(Entity e) {
                if (e instanceof Variable) {
                    variables.add(Variable.class.cast(e));
                } else if (e instanceof VariableReference) {
                    variables.remove(VariableReference.class.cast(e).getReferent());
                }
            }
            public void onExit(Entity e) {
                if (e instanceof Block) {
                    Block.class.cast(e).getDeclarations().removeAll(variables);
                }
            }
        };
        traverse(unusedVariableDetector, new HashSet<Entity>());
    }
}
