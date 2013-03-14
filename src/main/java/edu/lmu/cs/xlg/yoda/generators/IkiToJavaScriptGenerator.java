package edu.lmu.cs.xlg.yoda.generators;

import java.io.PrintWriter;

import edu.lmu.cs.xlg.yoda.entities.AssignmentStatement;
import edu.lmu.cs.xlg.yoda.entities.BinaryExpression;
import edu.lmu.cs.xlg.yoda.entities.Block;
import edu.lmu.cs.xlg.yoda.entities.Declaration;
import edu.lmu.cs.xlg.yoda.entities.Expression;
import edu.lmu.cs.xlg.yoda.entities.Number;
import edu.lmu.cs.xlg.yoda.entities.Script;
import edu.lmu.cs.xlg.yoda.entities.ReadStatement;
import edu.lmu.cs.xlg.yoda.entities.Statement;
import edu.lmu.cs.xlg.yoda.entities.VariableReference;
import edu.lmu.cs.xlg.yoda.entities.WhileStatement;
import edu.lmu.cs.xlg.yoda.entities.WriteStatement;

/**
 * A generator that translates an Iki program into JavaScript.
 */
public class IkiToJavaScriptGenerator extends Generator {

    @Override
    public void generate(Script script, PrintWriter writer) {
        this.writer = writer;

        emit("(function () {");
        generateBlock(script.getBlock());
        emit("}());");
    }

    /**
     * Emits JavaScript code for the given Iki block.
     */
    private void generateBlock(Block block) {
        indentLevel++;
        for (Declaration d: block.getDeclarations()) {
            generateDeclaration(d);
        }
        for (Statement s: block.getStatements()) {
            generateStatement(s);
        }
        indentLevel--;
    }

    /**
     * Emits JavaScript code for the given Iki declaration.
     */
    private void generateDeclaration(Declaration d) {
        // The only kind of declaration there is in Iki is the variable.
        emit("var " + id(d) + " = 0;");
    }

    /**
     * Emits JavaScript code for the given Iki statement.
     */
    private void generateStatement(Statement s) {
        if (s instanceof AssignmentStatement) {
            AssignmentStatement a = AssignmentStatement.class.cast(s);
            emit(String.format("%s = %s;", generateExpression(a.getVariableReference()),
                    generateExpression(a.getExpression())));
        } else if (s instanceof ReadStatement) {
            for (VariableReference v: ReadStatement.class.cast(s).getReferences()) {
                emit(id(v.getReferent()) + " = +prompt();");
            }
        } else if (s instanceof WriteStatement) {
            for (Expression e: WriteStatement.class.cast(s).getExpressions()) {
                emit("alert(" + generateExpression(e) + ");");
            }
        } else if (s instanceof WhileStatement) {
            WhileStatement w = WhileStatement.class.cast(s);
            emit("while (" + generateExpression(w.getCondition()) + ") {");
            generateBlock(w.getBody());
            emit("}");
        }
    }

    /**
     * Produces the JavaScript string for the given expression.  All expressions in Iki can fit
     * on one line when translated to JavaScript.
     */
    private String generateExpression(Expression e) {
        if (e instanceof Number) {
            return Number.class.cast(e).getValue() + "";
        } else if (e instanceof VariableReference) {
            return id(VariableReference.class.cast(e).getReferent());
        } else if (e instanceof BinaryExpression) {
            BinaryExpression b = BinaryExpression.class.cast(e);
            return String.format("(%s %s %s)", generateExpression(b.getLeft()), b.getOperator(),
                    generateExpression(b.getRight()));
        } else {
            throw new RuntimeException("Internal Compiler Error");
        }
    }
}
