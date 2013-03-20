package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

import edu.lmu.cs.xlg.util.Log;

public class CallStatement extends Statement {

    private String procedureName;
    private List<Expression> args;
    private Procedure procedure;
    private String objectName;

    public CallStatement(String procedureName, List<Expression> args, String objectName) {
        this.procedureName = procedureName;
        this.args = args;
        this.objectName = objectName;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public List<Expression> getArgs() {
        return args;
    }

    public Procedure getProcedure() {
        return procedure;
    }
    
    public String getObjectName() {
    	return objectName;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {

        // Analyze arguments first.
        for (Expression a: args) {
            a.analyze(log, table, owner, inLoop);
        }

        // Find out which procedure we're referring to.
        procedure = table.lookupProcedure(procedureName, log);

        // If there's no such procedure, just bail on the rest of the analysis because we don't
        // want to generate spurious errors.
        if (procedure == null) {
            return;
        }

        // Now check all the arguments against all the parameters.
        procedure.assertCanBeCalledWith(args, log);
    }
}
