package edu.lmu.cs.xlg.iki;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

import edu.lmu.cs.xlg.iki.entities.Entity;
import edu.lmu.cs.xlg.iki.entities.Program;
import edu.lmu.cs.xlg.iki.entities.SymbolTable;
import edu.lmu.cs.xlg.iki.generators.Generator;
import edu.lmu.cs.xlg.iki.syntax.Parser;
import edu.lmu.cs.xlg.util.Log;

/**
 * A compiler for Iki.
 *
 * <p>This class contains a static <code>main</code> method allowing you to run the compiler
 * from the command line, as well as a few methods to compile, or even run specific phases of
 * the compiler, programmatically.</p>
 */
public class Compiler {

    /**
     * A logger for logging messages (both regular and error messages). The base properties file
     * is called <code>Iki.properties</code>.
     */
    private Log log = new Log("Iki", new PrintWriter(System.out, true));

    /**
     * Runs the compiler as an application.
     *
     * Syntax:
     * <pre>
     *     java Compiler [-x|-m|-c|-js|-86] filename
     * </pre>
     * where:
     * <pre>
     *     -x   generates and prints the syntax tree only
     *     -m   generates and dumps the semantic graph only
     *     -c   translates to C
     *     -js  translates to JavaScript
     *     -86  translates to gas x86-64 assembly language (default)
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
        } else if (!args[0].matches("-(?:x|m|c|js|86)") || args.length < 2) {
            abortWithUsageError();
        } else {
            option = args[0];
            filename = args[1];
        }

        Compiler compiler = new Compiler();
        Reader reader = new FileReader(filename);
        PrintWriter writer = new PrintWriter(System.out, true);

        if ("-x".equals(option)) {
            Program program = compiler.checkSyntax(reader);
            Entity.printSyntaxTree("", "", program, writer);
        } else if ("-m".equals(option)) {
            Program program = compiler.checkSemantics(reader);
            Entity.dump(program, writer);
        } else {
            compiler.translate(option.substring(1), reader, writer);
        }
    }

    /**
     * Checks the syntax of a Iki program read from a given reader object.
     *
     * @param reader
     *     the source
     * @return the abstract syntax tree if successful, or null if there were any syntax errors
     */
    public Program checkSyntax(Reader reader) throws IOException {
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
     * Checks the static semantics of a Iki program object, generally one already produced from
     * a parse.  This method is useful for testing or in cases where you want to embed an Iki
     * compiler in a larger application.
     *
     * @param program
     *     the program object to analyze
     * @return the (checked) semantic graph if successful, or null if there were any syntax or
     *     static semantic errors
     */
    public Program checkSemantics(Program program) {
        log.message("semantics.checking");
        program.analyze(new SymbolTable(), log);
        return program;
    }

    /**
     * Checks the syntax and static semantics of a Iki program from a reader.
     *
     * @param reader
     *     the source
     * @return the (checked) semantic graph if successful, or null if there were any syntax or
     *     static semantic errors
     */
    public Program checkSemantics(Reader reader) throws IOException {
        Program program = checkSyntax(reader);
        if (log.getErrorCount() > 0) {
            return null;
        }
        return checkSemantics(program);
    }

    /**
     * Reads an Iki program from the given reader and outputs an equivalent program to the
     * given writer.
     *
     * @param name
     *     the name of the generator to use, e.g. "c", "js", or "86".
     * @param reader
     *     the source
     * @param writer
     *     a writer to output the translation
     */
    public void translate(String name, Reader reader, PrintWriter writer) throws IOException {
        Program program = checkSemantics(reader);
        if (log.getErrorCount() == 0) {
            program.optimize();
            Generator.getGenerator(name).generate(program, writer);
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
     * Puts the compiler in an out of quiet mode.  In quiet mode, the compiler doe no logging
     * at all.
     */
    public void setQuiet(boolean quiet) {
        log.setQuiet(quiet);
    }

    /**
     * Writes the usage message to stderr and exits with status 1.
     */
    private static void abortWithUsageError() {
        System.err.println("Usage: java Compiler [-x|-m|-c|-js|-86] filename");
        System.err.println("");
        System.err.println("    -x    generates and prints the syntax tree only");
        System.err.println("    -m    generates and prints the semantic graph only");
        System.err.println("    -c    translates to C");
        System.err.println("    -js   translates to JavaScript");
        System.err.println("    -86   translates to gas x86-64 assembly language");
        System.exit(1);
    }
}
