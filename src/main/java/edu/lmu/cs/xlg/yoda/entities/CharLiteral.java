package edu.lmu.cs.xlg.yoda.entities;

import edu.lmu.cs.xlg.util.Log;
import java.util.ArrayList;
import java.util.List;

public class CharLiteral extends Literal{

    private Integer value;

    public CharLiteral(String lexeme) {
        super(lexeme);
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public void analyze(Log log, SymbolTable table, Subroutine owner, boolean inLoop) {

        // Don't consider the first and last characters (the single quotes).
        List<Integer> values = codepoints(getLexeme(), 1, getLexeme().length() - 1, log);
        this.value = ((Integer)values.get(0)).intValue();
    }

    /**
* Returns a list of the codepoints of the characters in the given
* string from position start (inclusive) to position end (exclusive).
*/
    public static List<Integer> codepoints(String s, int start, int end, Log log) {
        List<Integer> result = new ArrayList<Integer>(end - start);
        for (int pos = start; pos < end; pos++) {
            char c = s.charAt(pos);
            if (c == '\\') {
                c = s.charAt(++pos);
                if (c == 'n') result.add(10);
                else if (c == 't') result.add(9);
                else if (c == '\"') result.add(34);
                else if (c == '\'') result.add(39);
                else if (c == '\\') result.add(92);
                else if ("0123456789abcdefABCDEF".indexOf(c) != -1) {
                    int value = 0;
                    for (; c != ';'; c = s.charAt(++pos)) {
                        value = 16 * value + Character.digit(c, 16);
                    }
                    result.add(new Integer(value));
                }
                else {
                    log.error("illegal_escape", c);
                }
            } else {
                result.add((int)c);
            }
        }
        return result;
    }
}
