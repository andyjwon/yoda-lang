package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

/**
 * A subroutine called from a statement.  It therefore has no return type.
 */
public class Procedure extends Subroutine {

    public Procedure(String name, List<Variable> parameters, Block body) {
        super(name, parameters, body);
    }
}
