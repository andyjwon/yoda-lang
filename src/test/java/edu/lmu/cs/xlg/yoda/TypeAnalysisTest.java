package edu.lmu.cs.xlg.yoda;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import edu.lmu.cs.xlg.util.Log;
import edu.lmu.cs.xlg.yoda.entities.ArrayConstructor;
import edu.lmu.cs.xlg.yoda.entities.BooleanLiteral;
import edu.lmu.cs.xlg.yoda.entities.CharacterLiteral;
import edu.lmu.cs.xlg.yoda.entities.Expression;
import edu.lmu.cs.xlg.yoda.entities.NullLiteral;
import edu.lmu.cs.xlg.yoda.entities.NumberLiteral;
import edu.lmu.cs.xlg.yoda.entities.StringLiteral;
import edu.lmu.cs.xlg.yoda.entities.Type;
import edu.lmu.cs.xlg.yoda.entities.WholeNumberLiteral;

/**
 * Unit tests for analyzing types and testing type compatibility.
 */
public class TypeAnalysisTest {

    private Log log = new Log("Yoda", new PrintWriter(System.out, true));

    Expression nullLiteral = NullLiteral.INSTANCE;
    Expression no = BooleanLiteral.FALSE;
    Expression yes = BooleanLiteral.TRUE;
    Expression seven = new WholeNumberLiteral("7");
    Expression eight = new WholeNumberLiteral("8");
    Expression half = new NumberLiteral("0.5");
    Expression dollar = new CharacterLiteral("'$'");
    Expression dog = new StringLiteral("\"dog\"");
    Expression rat = new StringLiteral("\"rat\"");
    Expression emptyArray = new ArrayConstructor(new ArrayList<Expression>());
    Expression arrayOfOneNull = new ArrayConstructor(Arrays.asList(nullLiteral));
    Expression arrayOfTwoNulls = new ArrayConstructor(Arrays.asList(nullLiteral, nullLiteral));
    Expression arrayOfOneSeven = new ArrayConstructor(Arrays.asList(seven));

    private void check(Expression e, Type t) {
        e.analyze(log, null, null, false);
        assertEquals(e.getType(), t);
    }

    private void checkArray(List<Expression> list, Type t) {
        check(new ArrayConstructor(list), t);
    }

    @Test
    public void testSimpleExpressions() {
        check(new WholeNumberLiteral("8"), Type.WHOLE_NUMBER);
        check(new CharacterLiteral("'Z'"), Type.CHARACTER);
        check(new StringLiteral("\"Zyxwvut\""), Type.STRING);
        check(BooleanLiteral.FALSE, Type.TRUTH_VALUE);
        check(BooleanLiteral.TRUE, Type.TRUTH_VALUE);
    }

    @Test
    public void testEmptyArrayExpression() {
        check(emptyArray, Type.ARBITRARY_ARRAY);
    }

    @Test
    public void testSingletonArrayExpressions() {
        checkArray(Arrays.asList(nullLiteral), Type.NULL_TYPE.array());
        checkArray(Arrays.asList(no), Type.TRUTH_VALUE.array());
        checkArray(Arrays.asList(seven), Type.WHOLE_NUMBER.array());
        checkArray(Arrays.asList(half), Type.NUMBER.array());
        checkArray(Arrays.asList(dollar), Type.CHARACTER.array());
        checkArray(Arrays.asList(dog), Type.STRING.array());
        checkArray(Arrays.asList(emptyArray), Type.ARBITRARY_ARRAY.array());
        check(arrayOfOneNull, Type.NULL_TYPE.array());
        check(arrayOfOneSeven, Type.WHOLE_NUMBER.array());
        checkArray(Arrays.asList(arrayOfOneNull), Type.NULL_TYPE.array().array());
        checkArray(Arrays.asList(arrayOfOneSeven), Type.WHOLE_NUMBER.array().array());
    }

    @Test
    public void testHomogeneousArrayExpressions() {
        checkArray(Arrays.asList(nullLiteral, nullLiteral), Type.NULL_TYPE.array());
        checkArray(Arrays.asList(no, no, yes), Type.TRUTH_VALUE.array());
        checkArray(Arrays.asList(seven, eight), Type.WHOLE_NUMBER.array());
        checkArray(Arrays.asList(half, half), Type.NUMBER.array());
        checkArray(Arrays.asList(dollar, dollar), Type.CHARACTER.array());
        checkArray(Arrays.asList(dog, dog, rat), Type.STRING.array());
        checkArray(Arrays.asList(emptyArray, emptyArray), Type.ARBITRARY_ARRAY.array());
    }

    @Test
    public void testMixedArrayExpressions() {
        checkArray(Arrays.asList(seven, half), Type.NUMBER.array());
        checkArray(Arrays.asList(half, seven), Type.NUMBER.array());
        checkArray(Arrays.asList(half, seven, half), Type.NUMBER.array());
        checkArray(Arrays.asList(seven, half, eight), Type.NUMBER.array());
        checkArray(Arrays.asList(arrayOfOneSeven, nullLiteral), Type.WHOLE_NUMBER.array().array());
    }

    @Test
    public void testIncompatibles() {
        checkArray(Arrays.asList(arrayOfOneNull, arrayOfOneSeven), Type.ARBITRARY);
    }
}
