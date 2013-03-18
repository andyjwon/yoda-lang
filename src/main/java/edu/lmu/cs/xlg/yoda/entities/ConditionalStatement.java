package edu.lmu.cs.xlg.yoda.entities;

import java.util.List;

import edu.lmu.cs.xlg.util.Log;

public class ConditionalStatement extends Statement {

    public static class Arm extends Entity {
        Expression condition;
        Block block;

        public Arm(Expression condition, Block block) {
            this.condition = condition;
            this.block = block;
        }

        public Expression getCondition() {
            return condition;
        }

        public Block getBlock() {
            return block;
        }

        @Override
        public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
            condition.analyze(log, table, owner, inLoop);
            condition.assertBoolean("condition", log);
            block.analyze(log, table, owner, inLoop);
        }
    }

    private List<Arm> arms;
    private Block elsePart;

    public ConditionalStatement(List<Arm> arms, Block elsePart) {
        this.arms = arms;
        this.elsePart = elsePart;
    }

    public List<Arm> getArms() {
        return arms;
    }

    public Block getElsePart() {
        return elsePart;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {
        for (Arm arm: arms) {
            arm.analyze(log, table, owner, inLoop);
        }
        if (elsePart != null) {
            elsePart.analyze(log, table, owner, inLoop);
        }
    }
}
