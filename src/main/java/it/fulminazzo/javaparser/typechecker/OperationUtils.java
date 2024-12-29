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
            ValueType.DOUBLE, ValueType.FLOAT, ValueType.NUMBER, ValueType.CHAR, ValueType.LONG
    };

    /**
     * Simulates a direct comparison between the two given operands.
     * If they are both {@link ValueType}, then they are checked to verify if they are compatible.
     * If only one of them is {@link ValueType}, or they are not compatible,
     * a {@link TypeCheckerException} is thrown.
     *
     * @param left  the left operand
     * @param right the right operand
     * @return {@link ValueType#BOOLEAN}
     */
    public static @NotNull Type executeObjectComparison(final @NotNull Type left,
                                                        final @NotNull Type right) {
        if (left.isValue() && right.isValue()) {
            if (left.is(ValueType.STRING)) right.check(ValueType.STRING);
            else if (left.is(ValueType.BOOLEAN)) right.check(ValueType.BOOLEAN);
            else return executeBinaryComparison(left, right);
        }
        return ValueType.BOOLEAN;
    }

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
     * Computes the returned {@link Type} for a binary bit operation that supports <b>NON-decimal</b> types.
     * If both {@link Type}s are {@link ValueType#BOOLEAN}, then {@link ValueType#BOOLEAN} is returned.
     * Throws {@link TypeCheckerException} in case of an invalid type received as operand.
     *
     * @param left  the left operand
     * @param right the right operand
     * @return the computed type
     */
    public static @NotNull Type executeBinaryBitOperation(final @NotNull Type left,
                                                          final @NotNull Type right) {
        if (left.equals(ValueType.BOOLEAN) && right.equals(ValueType.BOOLEAN)) return ValueType.BOOLEAN;
        else return executeBinaryOperationDecimal(left.check(NON_DECIMAL_TYPES), right.check(NON_DECIMAL_TYPES));
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
