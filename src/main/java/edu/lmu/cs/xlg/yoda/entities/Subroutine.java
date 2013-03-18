package edu.lmu.cs.xlg.yoda.entities;

import java.util.Iterator;
import java.util.List;

import edu.lmu.cs.xlg.util.Log;

/**
 * The superclass for procedures and functions.
 */
public abstract class Subroutine extends Declaration {

    private List<Variable> parameters;
    protected Block body;

    public Subroutine(String name, List<Variable> parameters, Block body) {
        super(name);
        this.parameters = parameters;
        this.body = body;
    }

    public List<Variable> getParameters() {
        return parameters;
    }

    public Block getBody() {
        return body;
    }

    /**
     * Performs semantic analysis on the function's signature and return type, but not the body.
     */
    public void analyzeSignature(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        SymbolTable tableForParameters;
        body.createTable(table);
        tableForParameters = body.getTable();
        for (Variable parameter: parameters) {
            tableForParameters.insert(parameter, log);
            parameter.analyze(log, tableForParameters, owner, inLoop);
        }
    }

    /**
     * Asserts that this routine can be called with the given list of arguments. There have to be
     * the same number of arguments as parameters, and each argument must be type-compatible with
     * the type of the corresponding parameter.
     */
    public void assertCanBeCalledWith(List<Expression> args, Log log) {

        if (args.size() != parameters.size()) {
            log.error("argument.count.mismatch", args.size(), parameters.size());
            System.err.println(log.getErrorCount());
            return;
        }

        // Check each parameter against the corresponding argument.
        Iterator<Expression> ai = args.iterator();
        Iterator<Variable> pi = parameters.iterator();
        while (pi.hasNext()) {
            Expression arg = ai.next();
            Variable parameter = pi.next();
            if (!arg.isCompatibleWith(parameter.getType())) {
                log.error("parameter.type.mismatch", parameter.getName());
            }
        }
    }

    /**
     * Performs semantics analysis on the body.  This is done after the signature has been analyzed,
     * so the body's symbol table has already been constructed and the parameters have already been
     * loaded.
     */
    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        body.analyze(log, table, this, false);
    }
}
