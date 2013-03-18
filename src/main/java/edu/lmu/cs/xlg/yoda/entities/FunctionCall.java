package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

import edu.lmu.cs.xlg.util.Log;

public class FunctionCall extends Expression {

    private Expression function;
    private List<Expression> args;

    public FunctionCall(Expression function, List<Expression> args) {
        this.function = function;
        this.args = args;
    }

    public Expression getFunction() {
        return function;
    }

    public List<Expression> getArgs() {
        return args;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {

        // Analyze all the arguments
        for (Expression a: args) {
            a.analyze(log, table, owner, inLoop);
        }

        // Make sure we really have a function.  If not, just forget it.
        function.analyze(log, table, owner, inLoop);
        if (function.type != Type.FUNCTION) {
            log.error("not.a.function");
            type = Type.ARBITRARY;
            return;
        }

        // In this version of Manatee, functions can only be referenced from IdentifierExpressions.
        // In the future we'll support arbitrary function expressions, but not now.
        if (!(function instanceof IdentifierExpression)) {
            log.error("no.complex.functions");
            type = Type.ARBITRARY;
            return;
        }

        Function f = (Function)((IdentifierExpression)function).getReferent();
        type = f.getReturnType();
        f.assertCanBeCalledWith(args, log);
    }
}
