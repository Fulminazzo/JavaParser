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
     * Checks whether the given operands are eligible for comparison (i.e. are {@link #DECIMAL_TYPES}).
     * Throws {@link TypeCheckerException} in case of an invalid type received as operand.
     *
     * @param left  the left operand
     * @param right the right operand
     * @return {@link ValueType#BOOLEAN}
     */
    public static @NotNull Type executeBinaryComparison(final @NotNull Type left,
                                                        final @NotNull Type right) {
        left.check(DECIMAL_TYPES);
        right.check(DECIMAL_TYPES);
        return ValueType.BOOLEAN;
    }

    /**
     * Computes the returned {@link Type} for a binary operation that supports <b>NON-decimal</b> types.
     * Throws {@link TypeCheckerException} in case of an invalid type received as operand.
     *
     * @param left  the left operand
     * @param right the right operand
     * @return the computed type
     */
    public static @NotNull Type executeBinaryOperation(final @NotNull Type left,
                                                       final @NotNull Type right) {
        return executeBinaryOperationDecimal(left.check(NON_DECIMAL_TYPES), right.check(NON_DECIMAL_TYPES));
    }

    /**
     * Computes the returned {@link Type} for a binary operation that supports <b>decimal</b> types.
     * Throws {@link TypeCheckerException} in case of an invalid type received as operand.
     *
     * @param left  the left operand
     * @param right the right operand
     * @return the computed type
     */
    public static @NotNull Type executeBinaryOperationDecimal(final @NotNull Type left,
                                                              final @NotNull Type right) {
        if (left.check(DECIMAL_TYPES).equals(ValueType.DOUBLE) || right.check(DECIMAL_TYPES).equals(ValueType.DOUBLE))
            return ValueType.DOUBLE;
        else if (left.equals(ValueType.FLOAT) || right.equals(ValueType.FLOAT))
            return ValueType.FLOAT;
        else if (left.equals(ValueType.LONG) || right.equals(ValueType.LONG))
            return ValueType.LONG;
        else return ValueType.NUMBER;
    }

}
