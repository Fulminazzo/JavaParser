package it.fulminazzo.javaparser.executor.values.primitive;

import it.fulminazzo.javaparser.executor.values.Value;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.function.BiFunction;

/**
 * Represents a general {@link Number} value.
 *
 * @param <N> the type of the value
 */
abstract class NumberValue<N extends Number> extends PrimitiveValue<N> {

    /**
     * Instantiates a new Number value.
     *
     * @param value the value
     */
    public NumberValue(@NotNull N value) {
        super(value);
    }

    @Override
    public @NotNull BooleanValue equal(@NotNull Value<?> other) {
        if (other.isCharacter()) other = ((CharacterValue) other).asInteger();
        if (other instanceof NumberValue)
            return BooleanValue.of(executeBinaryComparison(other) == 0);
        else return super.equal(other);
    }

    @Override
    public @NotNull BooleanValue lessThan(@NotNull Value<?> other) {
        return BooleanValue.of(executeBinaryComparison(other) < 0);
    }

    @Override
    public @NotNull BooleanValue lessThanEqual(@NotNull Value<?> other) {
        return BooleanValue.of(executeBinaryComparison(other) <= 0);
    }

    @Override
    public @NotNull BooleanValue greaterThan(@NotNull Value<?> other) {
        return BooleanValue.of(executeBinaryComparison(other) > 0);
    }

    @Override
    public @NotNull BooleanValue greaterThanEqual(@NotNull Value<?> other) {
        return BooleanValue.of(executeBinaryComparison(other) >= 0);
    }


    @Override
    public @NotNull Value<?> bitAnd(@NotNull Value<?> other) {
        return executeBinaryOperation(other, (a, b) -> a & b, (a, b) -> a & b);
    }

    @Override
    public @NotNull Value<?> bitOr(@NotNull Value<?> other) {
        return executeBinaryOperation(other, (a, b) -> a | b, (a, b) -> a | b);
    }

    @Override
    public @NotNull Value<?> bitXor(@NotNull Value<?> other) {
        return executeBinaryOperation(other, (a, b) -> a ^ b, (a, b) -> a ^ b);
    }

    @Override
    public @NotNull Value<?> lshift(@NotNull Value<?> other) {
        return executeBinaryOperation(other, (a, b) -> a << b, (a, b) -> a << b);
    }

    @Override
    public @NotNull Value<?> rshift(@NotNull Value<?> other) {
        return executeBinaryOperation(other, (a, b) -> a >> b, (a, b) -> a >> b);
    }

    @Override
    public @NotNull Value<?> urshift(@NotNull Value<?> other) {
        return executeBinaryOperation(other, (a, b) -> a >>> b, (a, b) -> a >>> b);
    }

    @Override
    public @NotNull Value<?> add(@NotNull Value<?> other) {
        return executeBinaryOperationDecimal(other, Double::sum, Float::sum, Long::sum, Integer::sum);
    }

    @Override
    public @NotNull Value<?> subtract(@NotNull Value<?> other) {
        return executeBinaryOperationDecimal(other, (a, b) -> a - b, (a, b) -> a - b,
                (a, b) -> a - b, (a, b) -> a - b);
    }

    @Override
    public @NotNull Value<?> multiply(@NotNull Value<?> other) {
        return executeBinaryOperationDecimal(other, (a, b) -> a * b, (a, b) -> a * b,
                (a, b) -> a * b, (a, b) -> a * b);
    }

    @Override
    public @NotNull Value<?> divide(@NotNull Value<?> other) {
        return executeBinaryOperationDecimal(other, (a, b) -> a / b, (a, b) -> a / b,
                (a, b) -> a / b, (a, b) -> a / b);
    }

    @Override
    public @NotNull Value<?> modulo(@NotNull Value<?> other) {
        return executeBinaryOperationDecimal(other, (a, b) -> a % b, (a, b) -> a % b,
                (a, b) -> a % b, (a, b) -> a % b);
    }

    /**
     * Checks if the other value is a {@link NumberValue}
     * and compares it with the current one.
     *
     * @param other the other value
     * @return the result of the comparison
     */
    int executeBinaryComparison(@NotNull Value<?> other) {
        if (other.isCharacter()) other = ((CharacterValue) other).asInteger();
        BigDecimal first = new BigDecimal(this.object.toString());
        BigDecimal second = new BigDecimal(other.to(NumberValue.class).object.toString());
        return first.compareTo(second);
    }

    /**
     * Checks if the other value is a {@link NumberValue}
     * and executes the most appropriate operation.
     *
     * @param other            the other value
     * @param doubleOperation  the operation executed if one of the two values is {@link DoubleValue}
     * @param floatOperation   the operation executed if one of the two values is {@link FloatValue}
     * @param longOperation    the operation executed if one of the two values is {@link LongValue}
     * @param integerOperation the operation executed if one of the two values is {@link IntegerValue}
     * @return the result value
     */
    Value<?> executeBinaryOperationDecimal(@NotNull Value<?> other,
                                           @NotNull BiFunction<Double, Double, Object> doubleOperation,
                                           @NotNull BiFunction<Float, Float, Object> floatOperation,
                                           @NotNull BiFunction<Long, Long, Object> longOperation,
                                           @NotNull BiFunction<Integer, Integer, Object> integerOperation) {
        if (other.isCharacter()) other = ((CharacterValue) other).asInteger();
        Number first = this.object;
        Number second = (Number) other.to(NumberValue.class).object;
        final Object obj;
        if (first instanceof Double || second instanceof Double)
            obj = doubleOperation.apply(first.doubleValue(), second.doubleValue());
        else if (first instanceof Float || second instanceof Float)
            obj = floatOperation.apply(first.floatValue(), second.floatValue());
        else return executeBinaryOperation(other, longOperation, integerOperation);
        return PrimitiveValue.of(obj);
    }

    /**
     * Checks if the other value is a {@link IntegerValue} or {@link LongValue}
     * and executes the most appropriate operation.
     *
     * @param other            the other value
     * @param longOperation    the operation executed if one of the two values is {@link LongValue}
     * @param integerOperation the operation executed if one of the two values is {@link IntegerValue}
     * @return the result value
     */
    Value<?> executeBinaryOperation(@NotNull Value<?> other,
                                    @NotNull BiFunction<Long, Long, Object> longOperation,
                                    @NotNull BiFunction<Integer, Integer, Object> integerOperation) {
        if (other.isCharacter()) other = ((CharacterValue) other).asInteger();
        Number first = this.object;
        Number second = (Number) other.check(IntegerValue.class, LongValue.class).check(NumberValue.class).object;
        final Object obj;
        if (first instanceof Long || second instanceof Long)
            obj = longOperation.apply(first.longValue(), second.longValue());
        else obj = integerOperation.apply(first.intValue(), second.intValue());
        return PrimitiveValue.of(obj);
    }

}
