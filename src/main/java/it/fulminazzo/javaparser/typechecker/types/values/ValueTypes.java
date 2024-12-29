package it.fulminazzo.javaparser.typechecker.types.values;

import it.fulminazzo.javaparser.typechecker.types.EnumType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Contains all the {@link ValueType}s representing the values accepted by the language.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValueTypes {
    public static final Type BOOLEAN_TYPE = new BooleanValueType();
    public static final Type CHAR_TYPE = new CharValueType();
    public static final Type DOUBLE_TYPE = new DoubleValueType();
    public static final Type FLOAT_TYPE = new FloatValueType();
    public static final Type LONG_TYPE = new LongValueType();
    public static final Type NUMBER_TYPE = new NumberValueType();
    public static final Type STRING_TYPE = new StringValueType();

    /**
     * Gets all the types in this class.
     *
     * @return the types
     */
    public static Type @NotNull [] values() {
        return EnumType.values(ValueTypes.class, ValueType.class);
    }

    /**
     * Gets the corresponding type from the given name.
     * Throws {@link IllegalArgumentException} in case of error.
     *
     * @param name the name
     * @return the type
     */
    public static @NotNull Type valueOf(final @NotNull String name) {
        return EnumType.valueOf(ValueTypes.class, ValueType.class, name);
    }

}
