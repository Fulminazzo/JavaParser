package it.fulminazzo.javaparser.typechecker;

import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.ValueType;
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * A collection of utility functions for handling operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OperationUtils {
    private static final Type[] NON_DECIMAL_TYPES = new Type[]{
            ValueType.NUMBER, ValueType.BYTE, ValueType.SHORT,
            ValueType.CHAR, ValueType.LONG
    };
    private static final Type[] DECIMAL_TYPES = new Type[]{
            ValueType.DOUBLE, ValueType.FLOAT
    };

    /**
     * Get the numeric types (non-decimals).
     *
     * @return the types
     */
    public static Type @NotNull [] getNumericTypes() {
        return NON_DECIMAL_TYPES.clone();
    }

    /**
     * Get the numeric types (with decimals).
     *
     * @return the types
     */
    public static Type @NotNull [] getDecimalTypes() {
        return Stream.concat(Arrays.stream(DECIMAL_TYPES), Arrays.stream(NON_DECIMAL_TYPES)).toArray(Type[]::new);
    }

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
            if (isString(left)) right.check(ValueType.STRING, ObjectType.STRING);
            else if (isBoolean(left)) right.check(ValueType.BOOLEAN, ObjectType.BOOLEAN);
            else return executeBinaryComparison(left, right);
        }
        return ValueType.BOOLEAN;
    }

    /**
     * Checks whether the given operands are eligible for comparison (i.e. are in {@link #getDecimalTypes()}).
     * Throws {@link TypeCheckerException} in case of an invalid type received as operand.
     *
     * @param left  the left operand
     * @param right the right operand
     * @return {@link ValueType#BOOLEAN}
     */
    public static @NotNull Type executeBinaryComparison(final @NotNull Type left,
                                                        final @NotNull Type right) {
        left.check(getDecimalTypes());
        right.check(getDecimalTypes());
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
        if (isBoolean(left) && isBoolean(right)) return ValueType.BOOLEAN;
        else return executeBinaryOperationDecimal(left.check(getNumericTypes()), right.check(getNumericTypes()));
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
        return executeBinaryOperationDecimal(left.check(getNumericTypes()), right.check(getNumericTypes()));
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
        if (isDouble(left.check(getDecimalTypes())) || isDouble(right.check(getDecimalTypes())))
            return ValueType.DOUBLE;
        else if (isFloat(left) || isFloat(right)) return ValueType.FLOAT;
        else if (isLong(left) || isLong(right)) return ValueType.LONG;
        else return ValueType.NUMBER;
    }

    /**
     * Checks if the given type is {@link ValueType#LONG} or {@link ObjectType#LONG}.
     *
     * @param type the type
     * @return true if it is
     */
    public static boolean isLong(final Type type) {
        return type.is(ValueType.LONG, ObjectType.LONG);
    }

    /**
     * Checks if the given type is {@link ValueType#FLOAT} or {@link ObjectType#FLOAT}.
     *
     * @param type the type
     * @return true if it is
     */
    public static boolean isFloat(final Type type) {
        return type.is(ValueType.FLOAT, ObjectType.FLOAT);
    }

    /**
     * Checks if the given type is {@link ValueType#DOUBLE} or {@link ObjectType#DOUBLE}.
     *
     * @param type the type
     * @return true if it is
     */
    public static boolean isDouble(final Type type) {
        return type.is(ValueType.DOUBLE, ObjectType.DOUBLE);
    }

    /**
     * Checks if the given type is {@link ValueType#BOOLEAN} or {@link ObjectType#BOOLEAN}.
     *
     * @param type the type
     * @return true if it is
     */
    public static boolean isBoolean(final Type type) {
        return type.is(ValueType.BOOLEAN, ObjectType.BOOLEAN);
    }

    /**
     * Checks if the given type is {@link ValueType#STRING} or {@link ObjectType#STRING}.
     *
     * @param type the type
     * @return true if it is
     */
    public static boolean isString(final Type type) {
        return type.is(ValueType.STRING, ObjectType.STRING);
    }

}
