package edu.lmu.cs.xlg.iki.generators;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.lmu.cs.xlg.iki.entities.AssignmentStatement;
import edu.lmu.cs.xlg.iki.entities.BinaryExpression;
import edu.lmu.cs.xlg.iki.entities.Block;
import edu.lmu.cs.xlg.iki.entities.Expression;
import edu.lmu.cs.xlg.iki.entities.Number;
import edu.lmu.cs.xlg.iki.entities.Program;
import edu.lmu.cs.xlg.iki.entities.ReadStatement;
import edu.lmu.cs.xlg.iki.entities.Statement;
import edu.lmu.cs.xlg.iki.entities.VariableReference;
import edu.lmu.cs.xlg.iki.entities.WhileStatement;
import edu.lmu.cs.xlg.iki.entities.WriteStatement;

/**
 * A generator that translates an Iki program into assembly language for the x86-84, good
 * only for Linux.  Doesn't work on the Mac, since it doesn't do that %rip-relative stuff.
 */
public class IkiToX86Generator extends Generator {

    private RegisterAllocator allocator = new RegisterAllocator();
    private Set<String> usedVariables = new HashSet<String>();
    private static int numberOfLabelsGenerated = 0;

    @Override
    public void generate(Program program, PrintWriter writer) {
        this.writer = writer;

        emit("\t.globl\tmain");
        emit("\t.text");
        emit("main:");
        generateBlock(program.getBlock());
        emit("\tret");
        emit("\t.data");
        emit("READ:\t.ascii\t\"%d\\0\\0\""); // extra 0 for alignment
        emit("WRITE:\t.ascii\t\"%d \\0\"");
        for (String s: usedVariables) {
            emit(s + ":\t.quad\t0");
        }
    }

    // Translation Functions - one for each entity.

    private void generateBlock(Block block) {
        for (Statement s : block.getStatements()) {
            generateStatement(s);
            allocator.freeAllRegisters();
        }
    }

    private void generateStatement(Statement s) {
        if (s instanceof AssignmentStatement) {
            generateAssignmentStatement((AssignmentStatement) s);
        } else if (s instanceof ReadStatement) {
            generateReadStatement((ReadStatement) s);
        } else if (s instanceof WriteStatement) {
            generateWriteStatement((WriteStatement) s);
        } else if (s instanceof WhileStatement) {
            generateWhileStatement((WhileStatement) s);
        } else {
            throw new RuntimeException("Bug inside compiler");
        }
    }

    private void generateAssignmentStatement(AssignmentStatement s) {
        Operand source = generateExpression(s.getExpression());
        Operand destination = generateVariableReference(s.getVariableReference());
        if (source instanceof MemoryOperand && destination instanceof MemoryOperand) {
            Operand oldSource = source;
            source = allocator.makeRegisterOperand();
            emitMove(oldSource, source);
        }
        emitMove(source, destination);
    }

    private void generateReadStatement(ReadStatement s) {
        for (VariableReference r : s.getReferences()) {
            emitRead(generateVariableReference(r));
        }
    }

    private void generateWriteStatement(WriteStatement s) {
        for (Expression e : s.getExpressions()) {
            emitWrite(generateExpression(e));
        }
    }

    private void generateWhileStatement(WhileStatement s) {
        Label top = new Label();
        Label bottom = new Label();
        emitLabel(top);
        Operand condition = generateExpression(s.getCondition());
        emitJumpIfFalse(condition, bottom);
        allocator.freeAllRegisters();
        generateBlock(s.getBody());
        emitJump(top);
        emitLabel(bottom);
    }

    private Operand generateExpression(Expression e) {
        if (e instanceof Number) {
            return generateNumber((Number) e);
        } else if (e instanceof VariableReference) {
            return generateVariableReference((VariableReference) e);
        } else if (e instanceof BinaryExpression) {
            return generateBinaryExpression((BinaryExpression) e);
        } else {
            throw new RuntimeException("Bug inside compiler");
        }
    }

    private Operand generateNumber(Number number) {
        return new ImmediateOperand(number.getValue());
    }

    private MemoryOperand generateVariableReference(VariableReference variable) {
        String name = id(variable.getReferent());
        usedVariables.add(name);
        return new MemoryOperand(name);
    }

    private Operand generateBinaryExpression(BinaryExpression e) {
        Operand left = generateExpression(e.getLeft());
        Operand right = generateExpression(e.getRight());
        Operand result;
        if (e.getOperator() != BinaryExpression.Operator.DIVIDE) {
            if (left instanceof RegisterOperand) {
                result = left;
            } else {
                result = allocator.makeRegisterOperand();
                emitMove(left, result);
            }
            switch (e.getOperator()) {
            case PLUS: emitBinary("addq", right, result); break;
            case MINUS: emitBinary("subq", right, result); break;
            case TIMES: emitBinary("mulq", right, result); break;
            }
        } else {
            result = allocator.makeRegisterOperandFor("rax");
            emit("\tmovq\t" + left + ", " + result);
            emit("\tcqto");
            emit("\tidivq\t" + allocator.nonImmediate(right));
        }
        return result;
    }

    /**
     * Emits a label on its own line.
     */
    protected void emitLabel(Label label) {
        emit(label + ":");
    }

    /**
     * Emits a single move instruction.
     */
    protected void emitMove(Operand source, Operand destination) {
        emit("\tmovq\t" + source + ", " + destination);
    }

    /**
     * Emits a single unconditional jump instruction.
     */
    protected void emitJump(Label label) {
        emit("\tjmp\t" + label);
    }

    /**
     * Emits code to jump to a label if a given expression is 0. On the x86 this is done with a
     * comparison instruction and then a je instruction. The cmp instruction cannot compare two
     * immediate values, so if the operand is immediate we have to get a new register for it.
     */
    protected void emitJumpIfFalse(Operand operand, Label label) {
        emit("\tcmpq\t$0, " + allocator.nonImmediate(operand));
        emit("\tje\t" + label);
    }

    /**
     * Emits a binary instruction.
     */
    protected void emitBinary(String instruction, Operand source, Operand destination) {
        emit("\t" + instruction + "\t" + source + ", " + destination);
    }

    /**
     * Generates code to call scanf() from the C library for the given operand. Requires the
     * operand to be in %rsi and the format string in %rdi.
     */
    protected void emitRead(MemoryOperand operand) {
        emit("\tmovq\t" + operand.address() + ", %rsi");
        emit("\tmovq\t$READ, %rdi");
        emit("\txorq\t%rax, %rax");
        emit("\tcall\tscanf");
    }

    /**
     * Generates code to call printf() from the C library for the given operand. Requires the
     * operand to be in %rsi and the format string in %rdi.
     */
    protected void emitWrite(Operand operand) {
        emit("\tmovq\t" + operand + ", %rsi");
        emit("\tmovq\t$WRITE, %rdi");
        emit("\txorq\t%rax, %rax");
        emit("\tcall\tprintf");
    }

    /**
     * A ridiculously simple register allocator. It thrown an exception thrown there are no free
     * registers available.  Also, it never allocates %rdx, since that is used for division.
     * You can't mark individual registers free; you can only call freeAllRegisters().
     */
    private class RegisterAllocator {
        public Map<String, RegisterOperand> used = new HashMap<String, RegisterOperand>();

        // TODO replace names with enum
        private final String[] names = { "rax", "rcx", "r8", "r9", "r10", "r11"};

        /**
         * Returns a brand new register operand bound to a specific register.  If something is
         * already in that register, generates code to move it out and rebind to a new register.
         */
        public RegisterOperand makeRegisterOperandFor(String registerName) {
            RegisterOperand whatsAlreadyThere = used.get(registerName);
            if (whatsAlreadyThere != null) {
                assignRegister(whatsAlreadyThere);
                emit("\tmovq\t%" + registerName + ", " + whatsAlreadyThere);
            }
            RegisterOperand operand = new RegisterOperand(registerName);
            used.put(registerName, operand);
            return operand;
        }

        /**
         * Returns a brand new register operand bound to the first available free register.
         */
        public RegisterOperand makeRegisterOperand() {
            RegisterOperand operand = new RegisterOperand("");
            assignRegister(operand);
            return operand;
        }

        /**
         * If the operand is already non-immediate, return it, otherwise generate a new register
         * operand containing this value.
         */
        public Operand nonImmediate(Operand operand) {
            if (operand instanceof ImmediateOperand) {
                RegisterOperand newOperand = allocator.makeRegisterOperand();
                emit("\tmovq\t" + operand + ", " + newOperand);
                return newOperand;
            }
            return operand;
        }

        /**
         * Changes the register value of an existing register operand to the first available
         * register.
         */
        private void assignRegister(RegisterOperand operand) {
            for (String register: names) {
                if (!used.containsKey(register)) {
                    used.put(register, operand);
                    operand.register = register;
                    return;
                }
            }
            throw new RuntimeException("No more registers available");
        }

        public void freeAllRegisters() {
            used.clear();
        }
    }

    /**
     * Assembly language labels.
     */
    private class Label {

        private int labelNumber;

        public Label() {
            labelNumber = ++numberOfLabelsGenerated;
        }

        public String toString() {
            return "L" + labelNumber;
        }
    }

    /**
     * Assembly language operands.
     */
    private abstract class Operand {
    }

    /**
     * Assembly language immediate operands, e.g. 4.
     */
    private class ImmediateOperand extends Operand {
        private int value;

        public ImmediateOperand(int value) {
            this.value = value;
        }

        public String toString() {
            return "$" + value;
        }
    }

    /**
     * Assembly language register operands, e.g. esi.
     */
    private class RegisterOperand extends Operand {
        private String register;

        public RegisterOperand(String register) {
            this.register = register;
        }

        public String toString() {
            return "%" + register;
        }
    }

    /**
     * Assembly language memory operands. Although the x86 has a variety of addressing modes, Iki
     * programs require only direct operands. All Iki variables will be stored in a data section.
     * The assembly language name of a variable is its Iki name suffixed with a '$' (to prevent
     * clashes with assembly language reserved words).
     */
    private class MemoryOperand extends Operand {
        private String variable;

        public MemoryOperand(String variable) {
            this.variable = variable;
        }

        public String address() {
            return "$" + variable;
        }

        public String toString() {
            return variable;
        }
    }
}
