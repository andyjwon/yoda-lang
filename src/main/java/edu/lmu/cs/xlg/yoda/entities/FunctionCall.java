package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

import edu.lmu.cs.xlg.util.Log;

public class FunctionCall extends Expression {

    private Expression function;
    private List<Expression> args;
    private Entity referent;
    private String functionName;

    public FunctionCall(Expression function, List<Expression> args) {
        this.function = function;
        this.args = args;
        functionName = IdentifierExpression.class.cast(function).getName();
    }

    public Expression getFunction() {
        return function;
    }

    public List<Expression> getArgs() {
        return args;
    }
    
    public Entity getReferent() {
    	return referent;
    }
    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {

        for (Expression a: args) {
            a.analyze(log, table, owner, inLoop);
        }
        referent = table.lookup(functionName, log);
        // Make sure we really have a function.  If not, just forget it.
        function.analyze(log, table, owner, inLoop);
        if (!(function instanceof IdentifierExpression)) {
            log.error("no.complex.functions");
            return;
        }

    }
}
