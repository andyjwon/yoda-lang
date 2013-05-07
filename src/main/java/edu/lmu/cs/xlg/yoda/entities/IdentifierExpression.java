package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;

/**
 * An expression consisting of a single identifier, a referencing occurrence to a variable or a
 * function.
 */
public class IdentifierExpression extends VariableExpression {

    private String name;
    private Entity referent;

    public IdentifierExpression(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Entity getReferent() {
        return referent;
    }

    /**
     * Returns whether the simple variable expression is writable, which it will be if and only
     * if its referent is a variable and not marked constant.
     */
    @Override
    public boolean isWritableValue() {
       return referent instanceof Variable && !Variable.class.cast(referent).isConstant();
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        referent = table.lookup(name, log);
        if (!((referent instanceof Variable) || (referent instanceof Subroutine))) {
            log.error("bad.expression");
        }
    }
}
