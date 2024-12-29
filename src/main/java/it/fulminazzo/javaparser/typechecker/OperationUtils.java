package it.fulminazzo.javaparser.typechecker;

import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.ValueType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * A collection of utility functions for handling operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OperationUtils {
    private static final Type[] NON_DECIMAL_TYPES = new Type[]{
            ValueType.NUMBER, ValueType.CHAR, ValueType.LONG
    };
    private static final Type[] DECIMAL_TYPES = new Type[]{
            ValueType.NUMBER, ValueType.CHAR, ValueType.LONG, ValueType.FLOAT, ValueType.DOUBLE
    };

    /**
     * Computes the returned {@link Type} for a binary operation that supports <b>NON-decimal</b> types.
     *
     * @param left  the left operand
     * @param right the right operand
     * @return the computed type
     */
    public static @NotNull Type executeBinaryOperation(final @NotNull Type left,
                                                       final @NotNull Type right) {
        return executeBinaryOperationDecimal(left, right);
    }

    /**
     * Computes the returned {@link Type} for a binary operation that supports <b>decimal</b> types.
     *
     * @param left  the left operand
     * @param right the right operand
     * @return the computed type
     */
    public static @NotNull Type executeBinaryOperationDecimal(final @NotNull Type left,
                                                              final @NotNull Type right) {
        if (left.equals(ValueType.DOUBLE) || right.equals(ValueType.DOUBLE))
            return ValueType.DOUBLE;
        else if (left.equals(ValueType.FLOAT) || right.equals(ValueType.FLOAT))
            return ValueType.FLOAT;
        else if (left.equals(ValueType.LONG) || right.equals(ValueType.LONG))
            return ValueType.LONG;
        else return ValueType.NUMBER;
    }

    /**
     * Checks whether the given type is equal to one of the expected ones.
     * Throws a {@link TypeCheckerException} in case no match is found.
     *
     * @param type          the type
     * @param expectedTypes the expected types
     */
    public static void checkType(final @NotNull Type type,
                                 final Type @NotNull ... expectedTypes) {
        if (expectedTypes.length == 0)
            throw new IllegalArgumentException(String.format("Cannot compare type %s with no types", type));
        for (Type expectedType : expectedTypes)
            if (type.equals(expectedType)) return;
        throw TypeCheckerException.invalidType(expectedTypes[0], type);
    }

}
