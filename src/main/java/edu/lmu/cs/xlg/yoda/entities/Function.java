package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

import edu.lmu.cs.xlg.util.Log;

/**
 * A subroutine called from an expression.  It therefore has a return type.
 */
public class Function extends Subroutine {

    private String returnTypeName;
    private Type returnType;

    public Function(String returnTypeName, String name, List<Variable> parameters, Block body) {
        super(name, parameters, body);
        this.returnTypeName = returnTypeName;
    }

    public String getReturnTypeName() {
        return returnTypeName;
    }

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public void analyzeSignature(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        returnType = table.lookupType(returnTypeName, log);
        super.analyzeSignature(log, table, owner, inLoop);
    }
}
