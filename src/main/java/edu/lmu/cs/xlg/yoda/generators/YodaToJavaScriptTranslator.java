package edu.lmu.cs.xlg.yoda.generators;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

import edu.lmu.cs.xlg.yoda.entities.*;

/**
 * A translator from yoda semantic graphs to JavaScript.
 */
public class YodaToJavaScriptTranslator extends Generator {

    private PrintWriter writer;
    private int indentPadding = 4;
    private int indentLevel = 0;

    public void translateScript(Script script, PrintWriter writer) {
        this.writer = writer;
        emit("(function () {");
        translateBlock(script);
        emit("}());");
    }

    private void translateBlock(Block block) {
        indentLevel++;
        for (Statement s : block.getStatements()) {
            translateStatement(s);
        }
        indentLevel--;
    }

    private void translateStatement(Statement s) {

        if (s instanceof WhileLoop) {
            translateWhileLoop(WhileLoop.class.cast(s));

        } else if (s instanceof ConditionalLoop) {
            translateConditionalLoop(ConditionalLoop.class.cast(s));

        } else if (s instanceof RangeLoop) {
            translateRangeLoop(RangeLoop.class.cast(s));

        } else if (s instanceof ConditionalStatement) {
            translateConditionalStatement(ConditionalStatement.class.cast(s));

        } else if (s instanceof TimesLoop) {
            translateTimesLoop(TimesLoop.class.cast(s));

        } else if (s instanceof Function) {
            translateFunctionDeclaration(Function.class.cast(s));

        } else if (s instanceof PrintStatement) {
            translatePrintStatement(PrintStatement.class.cast(s));

        } else if (s instanceof DecStatement) {
            translateVariableDeclaration(DecStatement.class.cast(s));

        } else if (s instanceof AssignmentStatement) {
            translateAssignmentStatement(AssignmentStatement.class.cast(s));

        } else if (s instanceof ReturnStatement) {
            translateReturnStatement(ReturnStatement.class.cast(s));

        } else if (s instanceof ExpressionStatement) {
            Expression e = ExpressionStatement.class.cast(s).getE();
            emit("%s;", translateExpression(e));

        } else {
            throw new RuntimeException("Unknown statement class: " + s.getClass().getName());
        }
    }

    private void translateVariableDeclaration(DecStatement d) {
        for (Variable v : d.getVariables()) {
            emit("var %s = %s;", variable(v), translateExpression(v.getInitializer()));
        }
    }

    private void translateFunctionDeclaration(Function f) {
        emit("function %s(%s) {", variable(f), translateParameters(f.getParameters()));
        translateBlock(f.getBody());
        emit("}");
    }

    private void translateAssignmentStatement(AssignmentStatement s) {
        List<Expression> targets = s.getTarget();
        List<Expression> sources = s.getSource();
        for (int i = 0; i < targets.size(); i++) {
            emit("%s = %s;", translateExpression(targets.get(i)), translateExpression(sources.get(i)));
        }
    }

    private void translateCallStatement(CallStatement s) {
        emit("%s(%s);", variable(s.getProcedure()), translateExpressionList(s.getArgs()));
    }

    private void translateReturnStatement(ReturnStatement s) {
        if (s.getExpression() == null) {
            emit("return;");
        } else {
            emit("return %s;", translateExpression(s.getExpression()));
        }
    }

    private void translatePrintStatement(PrintStatement s) {
        emit("console.log(%s);", translateExpression(s.getExpression()));
    }

    private void translateConditionalStatement(ConditionalStatement s) {
        String lead = "if";
        for (ConditionalStatement.Arm a : s.getArms()) {
            emit("%s (%s) {", lead, translateExpression(a.getCondition()));
            translateBlock(a.getBlock());
            lead = "} else if";
        }
        if (s.getElsePart() != null) {
            emit("} else {");
            translateBlock(s.getElsePart());

        }
        emit("}");
    }

    private void translateRangeLoop(RangeLoop r) {
        Range range = r.getRange();
        String init = "", test = "", each = "";
        if (r.getIterator() != null) {
            init = String.format("var %s = %s", r.getIterator(), range.getLow());
            test = String.format("%s <= %s", r.getIterator(), range.getHigh());
        }
        if (r.getStep() != null) {
            each = translateExpression(r.getStep());
        }
        emit("for (%s; %s; %s) {", init, test, each);
        translateBlock(r.getBody());
        emit("}");
    }

    private void translateTimesLoop(TimesLoop t) {
        Expression count = t.getCount();
        Variable constructor = new Variable("Gerald");
        String init = "", test = "", each = "";

        init = String.format("%s = 0", constructor);
        test = String.format("%s < %s", constructor, count);
        each = String.format("%s++", constructor);

        emit("for (%s; %s; %s) {", init, test, each);
        translateBlock(t.getBody());
        emit("}");
    }

    private void translateWhileLoop(WhileLoop s) {
        emit("while (%s) {", translateExpression(s.getCondition()));
        translateBlock(s.getBody());
        emit("}");
    }

    private void translateConditionalLoop(ConditionalLoop s) {
        String init = "", test = "", each = "";
        if (s.getIterator() != null) {
            init = String.format("%s", s.getIterator());
        }
        if (s.getCondition() != null) {
            test = translateExpression(s.getCondition());
        }
        if (s.getStep() != null) {
            each = translateExpression(s.getStep());
        }
        emit("for (%s; %s; %s) {", init, test, each);
        translateBlock(s.getBody());
        emit("}");
    }

    private String translateExpression(Expression e) {
        if (e instanceof NumberLiteral) {
            return NumberLiteral.class.cast(e).getValue().toString();
        } else if (e instanceof StringLiteral) {
            return StringLiteral.class.cast(e).getLexeme().toString();
        } else if (e == BooleanLiteral.TRUE) {
            return "true";
        } else if (e == BooleanLiteral.FALSE) {
            return "false";
        } else if (e instanceof StringLiteral) {
            return translateStringLiteral(StringLiteral.class.cast(e));
        } else if (e instanceof ArrayConstructor) {
            return translateArrayConstructor(ArrayConstructor.class.cast(e));
        } else if (e instanceof UnaryExpression) {
            return translateUnaryExpression(UnaryExpression.class.cast(e));
        } else if (e instanceof ArbitraryArityExpression) {
            return translateArbitraryArityExpression(ArbitraryArityExpression.class.cast(e));
        } else if (e instanceof RelationalExpression) {
            return translateRelationalExpression(RelationalExpression.class.cast(e));
        } else if (e instanceof IdentifierExpression) {
            return translateIdentifierExpression(IdentifierExpression.class.cast(e));
        } else if (e instanceof SubscriptExpression) {
            return translateSubscriptExpression(SubscriptExpression.class.cast(e));
        } else if (e instanceof TernaryExpression) {
            return translateTernaryExpression(TernaryExpression.class.cast(e));
        } else if (e instanceof FunctionCall) {
            return translateFunctionCall(FunctionCall.class.cast(e));
        } else {
            throw new RuntimeException("Unknown entity class: " + e.getClass().getName());
        }
    }

    private String translateStringLiteral(StringLiteral s) {
        StringBuilder result = new StringBuilder("\"");
        for (int codepoint : s.getValues()) {
            if (isDisplayable(codepoint)) {
                result.append((char) codepoint);
            } else {
                for (char c : Character.toChars(codepoint)) {
                    result.append(String.format("\\u%04x", (int) c));
                }
            }
        }
        result.append("\"");
        return result.toString();
    }

    private String translateUnaryExpression(UnaryExpression e) {
        String op = e.getOp();
        String operand = translateExpression(e.getOperand());
        if ("!".equals(op)) {
            return String.format("%s%s", op, operand);
        } else {
            throw new RuntimeException("Unknown prefix operator: " + e.getOp());
        }
    }

    private String translateArbitraryArityExpression(ArbitraryArityExpression e) {
        String op = e.getOp();
        String current;
        List<Expression> operands = e.getOperands();
        StringBuilder result = new StringBuilder("");
        result.append(String.format("(%s ", translateExpression(operands.get(0))));
        for (int i = 1; i < operands.size(); i += 1) {
            current = translateExpression(operands.get(i));
            result.append(String.format("%s %s", op, current));
        }
        result.append(")");
        return result.toString();
    }

    private String translateRelationalExpression(RelationalExpression e) {
        // All yoda binary operators look exactly the same as their JavaScript counterparts!
        String op;
        if (e.getOp().equals("=")) {
            op = "===";
        } else {
            op = e.getOp();
        }
        String left = translateExpression(e.getLeft());
        String right = translateExpression(e.getRight());
        return String.format("(%s %s %s)", right, op, left);
    }

    private String translateArrayConstructor(ArrayConstructor e) {
        List<String> expressions = new ArrayList<String>();
        for (Expression arg : e.getExpressions()) {
            expressions.add(translateExpression(arg));
        }
        return "[" + Joiner.on(", ").join(expressions) + "]";
    }

    private String translateTernaryExpression(TernaryExpression t) {
        String condition = translateExpression(t.getCondition());
        String trueExpression = translateExpression(t.getTrueExpression());
        String falseExpression = translateExpression(t.getFalseExpression());
        return String.format("(%s ? %s : %s);", condition, trueExpression, falseExpression);
    }

    private String translateIdentifierExpression(IdentifierExpression v) {
        if (v instanceof IdentifierExpression) {
            return variable(IdentifierExpression.class.cast(v).getReferent());
        } else {
            throw new RuntimeException("Unknown variable expression class: " + v.getClass().getName());
        }
    }

    private String translateSubscriptExpression(SubscriptExpression v) {
        String collection = translateExpression(v.getBase());
        String index = translateExpression(v.getIndex());
        return String.format("%s[%s]", collection, index);
    }

    private String translateFunctionCall(FunctionCall f) {

        String function = variable(f.getReferent());
        String args = translateExpressionList(f.getArgs());

        return String.format("%s(%s)", function, args);
    }

    /*
     * private String translateCallExpression(CallExpression e) {
     *
     * if (Function.PI.equals(e.getFunction())) { return "Math.PI"; } else if
     * (Function.SUBSTRING.equals(e.getFunction())) { return String.format("(%s).substring(%s, %s)",
     * translateExpression(e.getArgs().get(0)), translateExpression(e.getArgs().get(1)),
     * translateExpression(e.getArgs().get(2))); } else if (Function.GET_STRING.equals(e.getFunction())) { return
     * "fs.readFileSync('/dev/stdin')"; }
     *
     * String function = variable(e.getFunction()); String args = translateExpressionList(e.getArgs()); if
     * (builtIns.containsKey(e.getFunction())) { function = builtIns.get(e.getFunction()); } return
     * String.format("%s(%s)", function, args); }
     */
    private String translateExpressionList(List<Expression> list) {
        List<String> expressions = new ArrayList<String>();
        for (Expression e : list) {
            expressions.add(translateExpression(e));
        }
        return Joiner.on(", ").join(expressions);
    }

    private String translateParameters(List<Variable> list) {
        List<String> names = new ArrayList<String>();
        for (Variable v : list) {
            names.add(variable(v));
        }
        return Joiner.on(", ").join(names);
    }

    private String property(String s) {
        StringBuilder result = new StringBuilder("\"");

        // Both Java and JavaScript use UTF-16, so this is pretty easy
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (isDisplayable(c)) {
                result.append(c);
            } else {
                result.append(String.format("\\u%04x", (int) c));
            }
        }
        result.append("\"");
        return result.toString();
    }

    private String variable(Entity e) {
        return String.format("_v%d", e.getId());
    }

    /**
     * Returns whether or not we should show a particular character in the JavaScript output. We only show characters we
     * are guaranteed to see, that is, the non-control ASCII characters, except the double quote character itself, since
     * we are always going to render string literals and property names inside double quotes.
     */
    private boolean isDisplayable(int c) {
        return 20 <= c && c <= 126 && c != '"';
    }

    private void emit(String line, Object... args) {
        int pad = indentPadding * indentLevel;

        if (args.length != 0) {
            line = String.format(line, args);
        }

        // printf does not allow "%0s" as a format specifier, darn it.
        if (pad == 0) {
            writer.println(line);
        } else {
            writer.printf("%" + pad + "s%s\n", "", line);
        }
    }
}
