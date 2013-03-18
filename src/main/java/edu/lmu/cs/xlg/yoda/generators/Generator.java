package edu.lmu.cs.xlg.yoda.generators;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import edu.lmu.cs.xlg.yoda.entities.Declaration;
import edu.lmu.cs.xlg.yoda.entities.Entity;
import edu.lmu.cs.xlg.yoda.entities.Script;

/**
 * An generator that translates a Yoda script into some other form.  The result of the
 * translation is written to a writer.
 */
public abstract class Generator {

    // The writer to use
    PrintWriter writer;

    // Helps to ensure all entities get a unique id if they need one.
    Map<Entity, Integer> idMap = new HashMap<Entity, Integer>();

    // The last id generated.  Updated in <code>id()</code>
    private int lastId = 0;

    // For writing clean output.  May be overridden in subclasses.
    int indentPadding = 4;

    // The current indent level
    int indentLevel = 0;

    /**
     * A factory for retrieving a specific generator based on a name.  Currently only "js"
     * for JavaScript is supported.
     */
    public static Generator getGenerator(String name) {
        if ("js".equals(name)) {
            return new YodaToJavaScriptGenerator();
        } else {
            return null;
        }
    }

    /**
     * Generates a target script for the given Yoda script.
     *
     * @param script
     *     The Yoda program (source).
     * @param writer
     *     Writer for the target program.
     */
    public abstract void generate(Script script, PrintWriter writer);

    /**
     * Returns the id for the given entity, creating the id if the entity doesn't already
     * have one.
     */
    synchronized String id(Entity e) {
        Integer suffix = idMap.get(e);
        if (suffix == null) {
            suffix = ++lastId;
            idMap.put(e, suffix);
        }
        String name = e instanceof Declaration ? Declaration.class.cast(e).getName() : "";
        if (StringUtils.isBlank(name)) {
            name = "$";
        }
        return name + "_" + suffix;
    }

    /**
     * Write a line of target code to the output.
     */
    void emit(String line) {
        int pad = indentPadding * indentLevel;

        // printf does not allow "%0s" as a format specifier, darn it.
        if (pad == 0) {
            writer.println(line);
        } else {
            writer.printf("%" + pad + "s%s\n", "", line);
        }
    }
}
