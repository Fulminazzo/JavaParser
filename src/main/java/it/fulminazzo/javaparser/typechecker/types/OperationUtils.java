package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
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
            PrimitiveType.NUMBER, PrimitiveType.BYTE, PrimitiveType.SHORT,
            PrimitiveType.CHAR, PrimitiveType.LONG,
            ObjectType.INTEGER, ObjectType.BYTE, ObjectType.SHORT,
            ObjectType.CHARACTER, ObjectType.LONG
    };
    private static final Type[] DECIMAL_TYPES = new Type[]{
            PrimitiveType.DOUBLE, PrimitiveType.FLOAT,
            ObjectType.DOUBLE, ObjectType.FLOAT,
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
     * If they are both {@link PrimitiveType}, then they are checked to verify if they are compatible.
     * If only one of them is {@link PrimitiveType}, or they are not compatible,
     * a {@link TypeCheckerException} is thrown.
     *
     * @param operator the operator of the comparison
     * @param left     the left operand
     * @param right    the right operand
     * @return {@link PrimitiveType#BOOLEAN}
     */
    public static @NotNull Type executeObjectComparison(final @NotNull TokenType operator,
                                                        final @NotNull Type left,
                                                        final @NotNull Type right) {
        if (left.isPrimitive() || right.isPrimitive()) {
            if (isBoolean(left)) checkBooleanType(operator, left, right);
            else return executeBinaryComparison(operator, left, right);
        }
        return PrimitiveType.BOOLEAN;
    }

    /**
     * Simulates a direct comparison between two boolean operands.
     *
     * @param operator the operator of the comparison
     * @param left     the left operand
     * @param right    the right operand
     * @return {@link PrimitiveType#BOOLEAN}
     */
    public static @NotNull Type executeBooleanComparison(final @NotNull TokenType operator,
                                                         final @NotNull Type left,
                                                         final @NotNull Type right) {
        checkBooleanType(operator, left, right);
        return PrimitiveType.BOOLEAN;
    }

    /**
     * Checks whether the given operands are eligible for comparison (i.e. are in {@link #getDecimalTypes()}).
     * Throws {@link TypeCheckerException} in case of an invalid type received as operand.
     *
     * @param operator the operator of the comparison
     * @param left     the left operand
     * @param right    the right operand
     * @return {@link PrimitiveType#BOOLEAN}
     */
    public static @NotNull Type executeBinaryComparison(final @NotNull TokenType operator,
                                                        final @NotNull Type left,
                                                        final @NotNull Type right) {
        checkDecimalType(operator, left, right);
        return PrimitiveType.BOOLEAN;
    }

    /**
     * Computes the returned {@link Type} for a binary bit operation that supports <b>NON-decimal</b> types.
     * If both {@link Type}s are {@link PrimitiveType#BOOLEAN}, then {@link PrimitiveType#BOOLEAN} is returned.
     * Throws {@link TypeCheckerException} in case of an invalid type received as operand.
     *
     * @param operator the operator of the operation
     * @param left     the left operand
     * @param right    the right operand
     * @return the computed type
     */
    public static @NotNull Type executeBinaryBitOperation(final @NotNull TokenType operator,
                                                          final @NotNull Type left,
                                                          final @NotNull Type right) {
        if (isBoolean(left) && isBoolean(right)) return PrimitiveType.BOOLEAN;
        else return executeBinaryOperation(operator, left, right);
    }

    /**
     * Computes the returned {@link Type} for a binary operation that supports <b>NON-decimal</b> types.
     * Throws {@link TypeCheckerException} in case of an invalid type received as operand.
     *
     * @param operator the operator of the operation
     * @param left     the left operand
     * @param right    the right operand
     * @return the computed type
     */
    public static @NotNull Type executeBinaryOperation(final @NotNull TokenType operator,
                                                       final @NotNull Type left,
                                                       final @NotNull Type right) {
        checkNumericType(operator, left, right);
        return executeBinaryOperationDecimal(operator, left, right);
    }

    /**
     * Computes the returned {@link Type} for a binary operation that supports <b>decimal</b> types.
     * Throws {@link TypeCheckerException} in case of an invalid type received as operand.
     *
     * @param operator the operator of the operation
     * @param left     the left operand
     * @param right    the right operand
     * @return the computed type
     */
    public static @NotNull Type executeBinaryOperationDecimal(final @NotNull TokenType operator,
                                                              final @NotNull Type left,
                                                              final @NotNull Type right) {
        checkDecimalType(operator, left, right);
        if (isDouble(left) || isDouble(right)) return PrimitiveType.DOUBLE;
        else if (isFloat(left) || isFloat(right)) return PrimitiveType.FLOAT;
        else if (isLong(left) || isLong(right)) return PrimitiveType.LONG;
        else return PrimitiveType.NUMBER;
    }

    /**
     * Checks whether the given operand is of types {@link PrimitiveType#BOOLEAN}, then {@link PrimitiveType#BOOLEAN}.
     * If not, throws a {@link TypeCheckerException#unsupportedOperation(TokenType, Object)}.
     * Otherwise, returns the operand itself.
     *
     * @param operator the operator of the operation
     * @param operand  the operand
     * @return the type
     */
    public static @NotNull Type executeUnaryOperationBoolean(final @NotNull TokenType operator,
                                                             final @NotNull Type operand) {
        if (!isBoolean(operand))
            throw TypeCheckerException.unsupportedOperation(operator, operand);
        return operand;
    }

    /**
     * Checks whether the given operand is of types {@link #getDecimalTypes()}.
     * If not, throws a {@link TypeCheckerException#unsupportedOperation(TokenType, Object)}.
     * Otherwise, returns the operand itself.
     *
     * @param operator the operator of the operation
     * @param operand  the operand
     * @return the type
     */
    public static @NotNull Type executeUnaryOperationDecimal(final @NotNull TokenType operator,
                                                             final @NotNull Type operand) {
        if (!operand.is(getDecimalTypes()))
            throw TypeCheckerException.unsupportedOperation(operator, operand);
        return operand;
    }

    /**
     * Checks whether the given types are of type {@link PrimitiveType#BOOLEAN} or {@link ObjectType#BOOLEAN}.
     * If not, throws a {@link TypeCheckerException#unsupportedOperation(TokenType, Object, Object)}.
     *
     * @param operator the operator
     * @param left     the left operand
     * @param right    the right operand
     */
    static void checkBooleanType(@NotNull TokenType operator, @NotNull Type left, @NotNull Type right) {
        if (!isBoolean(left) || !isBoolean(right))
            throw TypeCheckerException.unsupportedOperation(operator, left, right);
    }

    /**
     * Checks whether the given types are of type {@link #getNumericTypes()}.
     * If not, throws a {@link TypeCheckerException#unsupportedOperation(TokenType, Object, Object)}.
     *
     * @param operator the operator
     * @param left     the left operand
     * @param right    the right operand
     */
    static void checkNumericType(@NotNull TokenType operator, @NotNull Type left, @NotNull Type right) {
        if (!left.is(getNumericTypes()) || !right.is(getNumericTypes()))
            throw TypeCheckerException.unsupportedOperation(operator, left, right);
    }

    /**
     * Checks whether the given types are of type {@link #getDecimalTypes()}.
     * If not, throws a {@link TypeCheckerException#unsupportedOperation(TokenType, Object, Object)}.
     *
     * @param operator the operator
     * @param left     the left operand
     * @param right    the right operand
     */
    static void checkDecimalType(@NotNull TokenType operator, @NotNull Type left, @NotNull Type right) {
        if (!left.is(getDecimalTypes()) || !right.is(getDecimalTypes()))
            throw TypeCheckerException.unsupportedOperation(operator, left, right);
    }

    /**
     * Checks if the given type is {@link PrimitiveType#LONG} or {@link ObjectType#LONG}.
     *
     * @param type the type
     * @return true if it is
     */
    public static boolean isLong(final Type type) {
        return type.is(PrimitiveType.LONG, ObjectType.LONG);
    }

    /**
     * Checks if the given type is {@link PrimitiveType#FLOAT} or {@link ObjectType#FLOAT}.
     *
     * @param type the type
     * @return true if it is
     */
    public static boolean isFloat(final Type type) {
        return type.is(PrimitiveType.FLOAT, ObjectType.FLOAT);
    }

    /**
     * Checks if the given type is {@link PrimitiveType#DOUBLE} or {@link ObjectType#DOUBLE}.
     *
     * @param type the type
     * @return true if it is
     */
    public static boolean isDouble(final Type type) {
        return type.is(PrimitiveType.DOUBLE, ObjectType.DOUBLE);
    }

    /**
     * Checks if the given type is {@link PrimitiveType#BOOLEAN} or {@link ObjectType#BOOLEAN}.
     *
     * @param type the type
     * @return true if it is
     */
    public static boolean isBoolean(final Type type) {
        return type.is(PrimitiveType.BOOLEAN, ObjectType.BOOLEAN);
    }

}
