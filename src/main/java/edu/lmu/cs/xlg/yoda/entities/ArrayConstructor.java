package edu.lmu.cs.xlg.yoda.entities;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.lmu.cs.xlg.util.Log;

/**
 * An array expression, such as [3.50, 9, -6], or [].  The type of the array expression is
 * computed at semantic analysis time as the type of arrays of its most general element.
 */
public class ArrayConstructor extends Expression {

    private List<Expression> expressions;

    public ArrayConstructor(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {

        // First analyze the subexpressions
        for (Expression e: expressions) {
            e.analyze(log, table, owner, inLoop);
        }

        // Now let's compute the type of the array expression. Begin by creating a set of all of
        // the types of each of the expressions in the array itself.
        Set<Type> types = new HashSet<Type>();
        for (Expression e: expressions) {
            types.add(e.getType());
        }

        // Now see if we can make any sense out of the set of types we've just built.
        if (types.isEmpty()) {
            type = Type.ARBITRARY_ARRAY;

        } else {
            type = null;
            for (Type t: types) {
                if (type == null) {
                    // First type, tentatively assign it as the expression's type
                    type = t;
                } else if (type.canBeAssignedTo(t)) {
                    // The new type is (the same or) more general, take this one
                    type = t;
                } else if (t.canBeAssignedTo(type)) {
                    // We're good, the type we have so far is the most general, keep it.
                } else {
                    // Conflict among the different types, no good.  Log the error and assign
                    // the arbitrary type to prevent spurious errors.
                    log.error("bad.array.expression");
                    type = Type.ARBITRARY;
                    return;
                }
            }
            type = type.array();
        }
    }
}
