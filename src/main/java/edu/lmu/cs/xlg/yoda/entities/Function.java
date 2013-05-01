package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

import edu.lmu.cs.xlg.util.Log;

/**
 * A subroutine called from an expression.  It therefore has a return type.
 */
public class Function extends Subroutine {

    public Function(String name, List<Variable> parameters, Block body) {
        super(name, parameters, body);
    }
    
    @Override
    public void analyzeSignature(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        super.analyzeSignature(log, table, owner, inLoop);
    }
}
