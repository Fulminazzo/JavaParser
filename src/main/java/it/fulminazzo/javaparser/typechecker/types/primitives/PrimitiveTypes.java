package it.fulminazzo.javaparser.typechecker.types.primitives;

import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.values.ValueTypes;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PrimitiveTypes {
    public static final Type BYTE = new PrimitiveType<>(ValueTypes.NUMBER_TYPE);
    public static final Type CHAR = new PrimitiveType<>(ValueTypes.CHAR_TYPE);
    public static final Type SHORT = new PrimitiveType<>(ValueTypes.NUMBER_TYPE);
    public static final Type INT = new PrimitiveType<>(ValueTypes.NUMBER_TYPE);
    public static final Type LONG = new PrimitiveType<>(ValueTypes.LONG_TYPE);
    public static final Type FLOAT = new PrimitiveType<>(ValueTypes.FLOAT_TYPE);
    public static final Type DOUBLE = new PrimitiveType<>(ValueTypes.DOUBLE_TYPE);
    public static final Type BOOLEAN = new PrimitiveType<>(ValueTypes.BOOLEAN_TYPE);

}
