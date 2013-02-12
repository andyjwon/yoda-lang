package edu.lmu.cs.xlg.iki;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import edu.lmu.cs.xlg.iki.entities.Program;

/**
 * A unit test for the front end of the Iki compiler. It reads all the ".iki" files in the test
 * resources directory. Whenever a filename starts with "synerror" the tester asserts that the
 * compiler will detect a syntax error. Whenever a name starts with "semerror" the tester asserts
 * that the compiler successfully parses the program, then asserts that a semantic error is
 * present. Whenever a file name starts with anything else, the tester checks that the compiler
 * can successfully parse and perform static analysis without finding any errors.
 */
@RunWith(Parameterized.class)
public class FrontEndTest {

    private static final String TEST_DIRECTORY = "src/test/iki";
    private static final String EXTENSION = ".iki";

    private String filename;

    public FrontEndTest(String filename) {
        this.filename = filename;
    }

    @Parameters
    public static Collection<Object[]> getFiles() {

        String[] filenames = new File(TEST_DIRECTORY).list(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(EXTENSION);
            }
        });

        Collection<Object[]> params = new ArrayList<Object[]>();
        for (String name : filenames) {
            params.add(new Object[] { name });
        }
        return params;
    }

    /**
     * Tests that the current file does what it is supposed to when compiled.
     */
    @Test
    public void testFrontEnd() throws IOException {
        try {
            System.out.print("Testing " + filename + "... ");
            Reader reader = new FileReader(TEST_DIRECTORY + "/" + filename);

            Compiler compiler = new Compiler();
            compiler.setQuiet(true);

            if (filename.startsWith("synerror")) {
                // Expect at least one error during syntax checking
                compiler.checkSyntax(reader);
                assertTrue("Supposed to have syntax errors", compiler.getErrorCount() != 0);

            } else if (filename.startsWith("semerror")) {
                // Expect no syntax errors, but one or more semantic errors
                Program program = compiler.checkSyntax(reader);
                assertTrue("Supposed to have NO syntax errors", compiler.getErrorCount() == 0);
                compiler.checkSemantics(program);
                assertTrue("Supposed to have semantic errors", compiler.getErrorCount() != 0);

            } else {
                // Expect no errors even after all semantic checks
                compiler.checkSemantics(reader);
                assertTrue("Supposed to be error free", compiler.getErrorCount() == 0);
            }
            System.out.println("PASS");
        } catch (AssertionError e) {
            System.out.println("FAIL: " + e.getMessage());
            throw e;
        }
    }
}
