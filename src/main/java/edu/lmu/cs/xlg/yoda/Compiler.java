package edu.lmu.cs.xlg.yoda;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import edu.lmu.cs.xlg.util.Log;
import edu.lmu.cs.xlg.yoda.entities.Script;
import edu.lmu.cs.xlg.yoda.entities.SymbolTable;
import edu.lmu.cs.xlg.yoda.generators.Generator;
import edu.lmu.cs.xlg.yoda.syntax.Parser;

/**
 * A compiler for Yoda.
 *
 * <p>This class contains a static <code>main</code> method allowing you to run the compiler
 * from the command line, as well as a few methods to compile, or even run specific phases of
 * the compiler, from any other application.</p>
 */
public class Compiler {

    /**
     * A logger for logging messages (both regular and error messages). The base properties file
     * is called <code>Yoda.properties</code>.
     */
    private Log log = new Log("Yoda", new PrintWriter(System.out, true));

    /**
     * Runs the compiler as an application.
     *
     * Syntax:
     * <pre>
     *     java Compiler [-x|-m|-js] filename
     * </pre>
     * where:
     * <pre>
     *     -x   generates and prints the syntax tree only (the default)
     *     -m   generates and prints the semantic graph only
     *     -js  translates to JavaScript
     * </pre>
     *
     * @param args
     *     the commandline arguments.
     */
    public static void main(String[] args) throws Exception {

        String option = "-x";
        String filename = "";

        if (args.length < 1) {
            abortWithUsageError();
        } else if (!args[0].startsWith("-")) {
            filename = args[0];
        } else if (!args[0].matches("-(?:x|m|js)") || args.length < 2) {
            abortWithUsageError();
        } else {
            option = args[0];
            filename = args[1];
        }

        Compiler compiler = new Compiler();
        Reader reader = new FileReader(filename);
        PrintWriter writer = new PrintWriter(System.out, true);

        if ("-x".equals(option)) {
            Script script = compiler.checkSyntax(reader);
            script.printSyntaxTree("", "", writer);
        } else if ("-m".equals(option)) {
            Script script = compiler.checkSemantics(reader);
            script.printEntities(writer);
        } else {
            compiler.translate(reader, writer);
        }
    }

    /**
     * Checks the syntax of a Yoda Script read from a given reader object.
     *
     * @param reader
     *     the source
     * @return the abstract syntax tree if successful, or null if there were any syntax errors
     */
    public Script checkSyntax(Reader reader) throws IOException {
        log.clearErrors();

        Parser parser = new Parser(reader);
        try {
            log.message("syntax.checking");
            return parser.parse(log);
        } finally {
            reader.close();
        }
    }

    /**
     * Checks the static semantics of a Yoda script object, generally one already produced from
     * a parse.  This method is useful for testing or in cases where you want to embed an Yoda
     * compiler in a larger application.
     *
     * @param script
     *     the script object to analyze
     * @return the (checked) semantic graph if successful, or null if there were any syntax or
     *     static semantic errors
     */
    public Script checkSemantics(Script script) {
        log.message("semantics.checking");
        script.analyze(log, new SymbolTable(null), null, false);
        return script;
    }

    /**
     * Checks the syntax and static semantics of a Yoda Script from a reader.
     *
     * @param reader
     *     the source
     * @return the (checked) semantic graph if successful, or null if there were any syntax or
     *     static semantic errors
     */
    public Script checkSemantics(Reader reader) throws IOException {
        Script script = checkSyntax(reader);
        if (log.getErrorCount() > 0) {
            return null;
        }
        return checkSemantics(script);
    }

    /**
     * Reads a Yoda script from the given reader and outputs a equivalent script to the
     * given writer.
     *
     * @param reader
     *     the source
     * @param writer
     *     a writer to output the translation
     */
    public void translate(Reader reader, PrintWriter writer) throws IOException {
        Script script = checkSemantics(reader);
        if (log.getErrorCount() == 0) {
            script.optimize();
            Generator.getGenerator("js").translateScript(script, writer);
        }
    }

    /**
     * Returns the number of errors logged so far.  This can be used to determine whether or not
     * to go on to the next compilation phase, as well as for the obvious end-of-compilation
     * reporting task.
     */
    public int getErrorCount() {
        return log.getErrorCount();
    }

    /**
     * Puts the compiler in and out of quiet mode.  In quiet mode, the compiler does no logging
     * at all.
     */
    public void setQuiet(boolean quiet) {
        log.setQuiet(quiet);
    }

    /**
     * Writes the usage message to stderr and exits with status 1.
     */
    private static void abortWithUsageError() {
        System.err.println("Usage: java Compiler [-x|-m|-js] filename");
        System.err.println("");
        System.err.println("    -x    generates and prints the syntax tree only");
        System.err.println("    -m    generates and prints the semantic graph only");
        System.err.println("    -js   translates to JavaScript");
        System.exit(1);
    }
}
