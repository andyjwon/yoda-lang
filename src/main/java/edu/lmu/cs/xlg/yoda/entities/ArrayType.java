package edu.lmu.cs.xlg.yoda.entities;

/**
 * A type which is an array. Array types objects are never constructed during syntax analysis.
 * Instead, each construct that references an array type stores only a typename of the form
 * "t list" or "t list list", for example. It is only during semantic analysis that array type
 * objects are created.
 */
public class ArrayType extends Type {

    private Type baseType;

    /**
     * Constructs an array type for arrays with the given base type.
     */
    public ArrayType(Type baseType) {
        super(baseType.getName() + " list");
        this.baseType = baseType;
    }

    /**
     * Returns the type of the elements of this array type.
     */
    public Type getBaseType() {
        return baseType;
    }
}
