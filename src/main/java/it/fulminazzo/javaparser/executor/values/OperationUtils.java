package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.javaparser.executor.values.primitivevalue.CharValue;
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A collection of utility functions for handling operations.
 * <b>No value in this class is checked</b>,
 * as that role is delegated to the TypeChecker.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OperationUtils {

    /**
     * Executes an object comparison on the two operands.
     *
     * @param left      the left operand
     * @param right     the right operand
     * @param operation the operation
     * @return the computed value (either true or false)
     */
    public static @NotNull Value<?> executeObjectComparison(final @NotNull Value<?> left,
                                                            final @NotNull Value<?> right,
                                                            final @NotNull BiFunction<Object, Object, Boolean> operation) {
        return PrimitiveValue.of(operation.apply(convertValue(left).getValue(), convertValue(right).getValue()));
    }

    /**
     * Executes a boolean binary comparison on the two operands.
     *
     * @param left            the left operand
     * @param right           the right operand
     * @param numberOperation the operation ran in case one of the two types is a number
     * @return the computed value (either true or false)
     */
    public static @NotNull Value<?> executeBooleanComparison(final @NotNull Value<?> left,
                                                             final @NotNull Value<?> right,
                                                             final @NotNull BiFunction<Boolean, Boolean, Boolean> numberOperation) {
        Boolean first = (Boolean) left.getValue();
        Boolean second = (Boolean) right.getValue();
        return PrimitiveValue.of(numberOperation.apply(first, second));
    }

    /**
     * Executes a numeric comparison between the two operands.
     *
     * @param left            the left operand
     * @param right           the right operand
     * @param numberOperation the operation ran in case one of the two types is a number
     * @return the computed value (either true or false)
     */
    public static @NotNull Value<?> executeBinaryComparison(final @NotNull Value<?> left,
                                                            final @NotNull Value<?> right,
                                                            final @NotNull BiFunction<BigDecimal, BigDecimal, Boolean> numberOperation) {
        BigDecimal first = new BigDecimal(convertValue(left).getValue().toString());
        BigDecimal second = new BigDecimal(convertValue(right).getValue().toString());
        return PrimitiveValue.of(numberOperation.apply(first, second));
    }

    /**
     * Checks if the given operands are boolean.
     * If so, executes booleanOperation,
     * otherwise calls {@link #executeBinaryOperation(Value, Value, BiFunction, BiFunction)}.
     *
     * @param left             the left operand
     * @param right            the right operand
     * @param booleanOperation the operation ran in case one of the two types is a boolean
     * @param longOperation    the operation ran in case one of the two types is a long
     * @param integerOperation the operation ran in case one of the two types is an integer
     * @return the computed value
     */
    public static @NotNull Value<?> executeBinaryBitOperation(final @NotNull Value<?> left,
                                                              final @NotNull Value<?> right,
                                                              @NotNull BiFunction<Boolean, Boolean, Boolean> booleanOperation,
                                                              @NotNull BiFunction<Long, Long, Long> longOperation,
                                                              @NotNull BiFunction<Integer, Integer, Integer> integerOperation) {
        Object first = left.getValue();
        final Object obj;
        if (first instanceof Boolean) obj = booleanOperation.apply((Boolean) first, (Boolean) right.getValue());
        else obj = executeBinaryOperation(left, right, longOperation, integerOperation).getValue();
        return PrimitiveValue.of(obj);
    }

    /**
     * Executes a non-decimal binary operation on the two operands.
     *
     * @param left             the left operand
     * @param right            the right operand
     * @param longOperation    the operation ran in case one of the two types is a long
     * @param integerOperation the operation ran in case one of the two types is an integer
     * @return the computed value
     */
    public static @NotNull Value<?> executeBinaryOperation(final @NotNull Value<?> left,
                                                           final @NotNull Value<?> right,
                                                           @NotNull BiFunction<Long, Long, Long> longOperation,
                                                           @NotNull BiFunction<Integer, Integer, Integer> integerOperation) {
        return executeBinaryOperationDecimal(left, right, (d1, d2) -> {
            throw new UnsupportedOperationException();
        }, (f1, f2) -> {
            throw new UnsupportedOperationException();
        }, longOperation, integerOperation);
    }

    /**
     * Executes a decimal binary operation on the two operands.
     *
     * @param left             the left operand
     * @param right            the right operand
     * @param doubleOperation  the operation ran in case one of the two types is a double
     * @param floatOperation   the operation ran in case one of the two types is a float
     * @param longOperation    the operation ran in case one of the two types is a long
     * @param integerOperation the operation ran in case one of the two types is a integer
     * @return the computer value
     */
    public static @NotNull Value<?> executeBinaryOperationDecimal(final @NotNull Value<?> left,
                                                                  final @NotNull Value<?> right,
                                                                  final @NotNull BiFunction<Double, Double, Double> doubleOperation,
                                                                  final @NotNull BiFunction<Float, Float, Float> floatOperation,
                                                                  final @NotNull BiFunction<Long, Long, Long> longOperation,
                                                                  final @NotNull BiFunction<Integer, Integer, Integer> integerOperation) {
        Number first = (Number) convertValue(left).getValue();
        Number second = (Number) convertValue(right).getValue();
        final Object obj;
        if (first instanceof Double || second instanceof Double)
            obj = doubleOperation.apply(first.doubleValue(), second.doubleValue());
        else if (first instanceof Float || second instanceof Float) obj = floatOperation.apply(first.floatValue(), second.floatValue());
        else if (first instanceof Long || second instanceof Long) obj = longOperation.apply(first.longValue(), second.longValue());
        else obj = integerOperation.apply(first.intValue(), second.intValue());
        return PrimitiveValue.of(obj);
    }

    /**
     * Executes a boolean unary operation on the given operand.
     *
     * @param operand          the operand
     * @param booleanOperation the operation ran in case one of the two types is a boolean
     * @return the computed type
     */
    public static @NotNull Value<?> executeUnaryOperationBoolean(final @NotNull Value<?> operand,
                                                                 final @NotNull Function<Boolean, Boolean> booleanOperation) {
        return Value.of(booleanOperation.apply(Boolean.valueOf(operand.getValue().toString())));
    }

    /**
     * Executes a decimal unary operation on the given operand.
     *
     * @param operand          the operand
     * @param doubleOperation  the operation ran in case one of the two types is a double
     * @param floatOperation   the operation ran in case one of the two types is a float
     * @param longOperation    the operation ran in case one of the two types is a long
     * @param integerOperation the operation ran in case one of the two types is an integer
     * @return the computed type
     */
    public static @NotNull Value<?> executeUnaryOperationDecimal(final @NotNull Value<?> operand,
                                                                 final @NotNull Function<Double, Double> doubleOperation,
                                                                 final @NotNull Function<Float, Float> floatOperation,
                                                                 final @NotNull Function<Long, Long> longOperation,
                                                                 final @NotNull Function<Integer, Integer> integerOperation) {
        Number value = (Number) convertValue(operand).getValue();
        final Object obj;
        if (value instanceof Double) obj = doubleOperation.apply(value.doubleValue());
        else if (value instanceof Float) obj = floatOperation.apply(value.floatValue());
        else if (value instanceof Long) obj = longOperation.apply(value.longValue());
        else obj = integerOperation.apply(value.intValue());
        return PrimitiveValue.of(obj);
    }

    /**
     * Checks if the given value is a {@link CharValue}.
     * If it is, it converts it using {@link CharValue#asInteger()}.
     * Otherwise, it is returned as it is.
     *
     * @param value the value
     * @return the new value
     */
    static @NotNull Value<?> convertValue(final @NotNull Value<?> value) {
        if (value.is(CharValue.class)) return ((CharValue) value).asInteger();
        else return value;
    }

}
