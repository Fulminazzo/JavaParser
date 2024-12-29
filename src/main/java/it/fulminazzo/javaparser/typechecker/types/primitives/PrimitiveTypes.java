package it.fulminazzo.javaparser.typechecker.types.primitives;

import it.fulminazzo.javaparser.typechecker.types.EnumType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.values.ValueTypes;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

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

    /**
     * Gets all the types in this class.
     *
     * @return the types
     */
    public static Type @NotNull [] values() {
        return EnumType.values(PrimitiveTypes.class, PrimitiveType.class);
    }

    /**
     * Gets the corresponding type from the given name.
     * Throws {@link IllegalArgumentException} in case of error.
     *
     * @param name the name
     * @return the type
     */
    public static @NotNull Type valueOf(final @NotNull String name) {
        return EnumType.valueOf(PrimitiveTypes.class, PrimitiveType.class, name);
    }

}
