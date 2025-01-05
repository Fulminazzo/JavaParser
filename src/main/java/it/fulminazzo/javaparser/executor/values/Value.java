package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.executor.ExecutorException;
import it.fulminazzo.javaparser.executor.values.arrays.ArrayValue;
import it.fulminazzo.javaparser.executor.values.objects.ObjectValue;
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a general value.
 *
 * @param <V> the type of the value
 */
@SuppressWarnings("unchecked")
public interface Value<V> {

    /**
     * Checks if the current value is character.
     *
     * @return true if it is
     */
    default boolean isCharacter() {
        return false;
    }

    /**
     * Checks if the current value is integer.
     *
     * @return true if it is
     */
    default boolean isInteger() {
        return false;
    }

    /**
     * Checks if the current value is long.
     *
     * @return true if it is
     */
    default boolean isLong() {
        return false;
    }

    /**
     * Checks if the current value is float.
     *
     * @return true if it is
     */
    default boolean isFloat() {
        return false;
    }

    /**
     * Checks if the current value is double.
     *
     * @return true if it is
     */
    default boolean isDouble() {
        return false;
    }

    /**
     * Checks if the current value is boolean.
     *
     * @return true if it is
     */
    default boolean isBoolean() {
        return false;
    }

    /**
     * Checks if the current value is string.
     *
     * @return true if it is
     */
    default boolean isString() {
        return false;
    }

    @Override
    default boolean isNull() {
        return is(Values.NULL_VALUE);
    }

    /**
     * Checks if the current value is primitive.
     *
     * @return true if it is
     */
    default boolean isPrimitive() {
        return this instanceof PrimitiveValue;
    }

    @Override
    default <T extends VisitorObject<ClassValue<?>, Value<?>, ParameterValues> @NotNull T check(final @NotNull Class<T> classValue) {
        if (is(classValue)) return classValue.cast(this);
        else throw ExecutorException.invalidValue(classValue, this);
    }

    @Override
    default @NotNull ClassValue<V> checkClass() {
        return (ClassValue<V>) check(ClassValue.class);
    }

    @Override
    default @NotNull Tuple<ClassValue<?>, Value<?>> getField(final @NotNull Field field) {
        Refl<?> refl = new Refl<>(getValue());
        Object object = refl.getFieldObject(field);
        ClassValue<?> classValue = ClassValue.of(field.getType());
        Value<?> value = of(object);
        if (classValue.isPrimitive()) value = value.toPrimitive();
        return new Tuple<>(classValue, value);
    }

    @Override
    default @NotNull Value<?> invokeMethod(final @NotNull Method method,
                                           final @NotNull ParameterValues parameters) throws ValueException {
        Refl<V> executor = new Refl<>(getValue());
        List<Object> parametersList = new ArrayList<>();
        Class<?>[] parametersTypes = method.getParameterTypes();
        List<Value<?>> parametersValues = parameters.getValue();
        for (int i = 0; i < parametersTypes.length; i++) {
            if (i == parametersTypes.length - 1) {
                if (parametersValues.size() == i + 1)
                    parametersList.add(parametersValues.get(i).getValue());
                else parametersList.add(parametersValues
                        .subList(i, parametersValues.size())
                        .stream()
                        .map(Value::getValue)
                        .toArray());
            } else parametersList.add(parametersValues.get(i).getValue());
        }
        Class<?> returnType = method.getReturnType();
        Object returned = executor.invokeMethod(returnType, method.getName(), parametersList.toArray());
        final Value<?> returnedValue;
        if (Void.TYPE.equals(returnType)) returnedValue = Values.NO_VALUE;
        else if (returnType.isPrimitive()) returnedValue = PrimitiveValue.of(returned);
        else returnedValue = of(returned);
        return returnedValue;
    }

    @Override
    default @NotNull Value<?> toPrimitive() {
        throw ValueRuntimeException.noPrimitive(getValue());
    }

    @Override
    default @NotNull Value<?> toWrapper() {
        throw ValueRuntimeException.noWrapper(getValue());
    }

    /**
     * Converts the current value is of the specified one.
     * This operation is unchecked.
     *
     * @param <T>   the class of the value
     * @param value the expected value
     * @return the current value cast to the expected one
     */
    default <T extends Value<?>> @NotNull T to(final @NotNull Class<T> value) {
        return value.cast(this);
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    V getValue();

    /**
     * Converts the given byte to a {@link Value}.
     *
     * @param value the value
     * @return the associated value
     */
    static @NotNull Value<Byte> of(final byte value) {
        return PrimitiveValue.of(value);
    }

    /**
     * Converts the given short to a {@link Value}.
     *
     * @param value the value
     * @return the associated value
     */
    static @NotNull Value<Short> of(final short value) {
        return PrimitiveValue.of(value);
    }

    /**
     * Converts the given char to a {@link Value}.
     *
     * @param value the value
     * @return the associated value
     */
    static @NotNull Value<Character> of(final char value) {
        return PrimitiveValue.of(value);
    }

    /**
     * Converts the given int to a {@link Value}.
     *
     * @param value the value
     * @return the associated value
     */
    static @NotNull Value<Integer> of(final int value) {
        return PrimitiveValue.of(value);
    }

    /**
     * Converts the given long to a {@link Value}.
     *
     * @param value the value
     * @return the associated value
     */
    static @NotNull Value<Long> of(final long value) {
        return PrimitiveValue.of(value);
    }

    /**
     * Converts the given float to a {@link Value}.
     *
     * @param value the value
     * @return the associated value
     */
    static @NotNull Value<Float> of(final float value) {
        return PrimitiveValue.of(value);
    }

    /**
     * Converts the given double to a {@link Value}.
     *
     * @param value the value
     * @return the associated value
     */
    static @NotNull Value<Double> of(final double value) {
        return PrimitiveValue.of(value);
    }

    /**
     * Converts the given boolean to a {@link Value}.
     *
     * @param value the value
     * @return the associated value
     */
    static @NotNull Value<Boolean> of(final boolean value) {
        return PrimitiveValue.of(value);
    }

    /**
     * Converts the given object to a {@link Value}.
     *
     * @param <T>   the type of the value
     * @param value the value
     * @return the associated value
     */
    @SuppressWarnings("unchecked")
    static <T> @NotNull Value<T> of(final @Nullable T value) {
        if (value == null) return (Value<T>) Values.NULL_VALUE;
        else if (value.getClass().isArray()) return (Value<T>) ArrayValue.of(value);
        else return ObjectValue.of(value);
    }

    /*
        EXCEPTIONS
     */

    @Override
    default @NotNull ValueException fieldNotFound(final @NotNull ClassValue<?> classVisitorObject,
                                                  final @NotNull String field) {
        return ValueException.fieldNotFound(classVisitorObject, field);
    }

    @Override
    default @NotNull ValueException methodNotFound(final @NotNull ClassValue<?> classObject,
                                                   final @NotNull String method,
                                                   final @NotNull ParameterValues parameters) {
        return ValueException.methodNotFound(classObject, method, parameters);
    }

    @Override
    default @NotNull ValueException typesMismatch(final @NotNull ClassValue<?> classObject,
                                                  final @NotNull Executable method,
                                                  final @NotNull ParameterValues parameters) {
        return ValueException.typesMismatch(classObject, method, parameters);
    }

    @Override
    default @NotNull RuntimeException noClassType(final @NotNull Class<?> type) {
        return ExecutorException.noClassType(type);
    }

    @Override
    default @NotNull RuntimeException unsupportedOperation(final @NotNull TokenType operator,
                                                           final @NotNull VisitorObject<ClassValue<?>, Value<?>, ParameterValues> left,
                                                           final @NotNull VisitorObject<ClassValue<?>, Value<?>, ParameterValues> right) {
        return ExecutorException.unsupportedOperation(operator, (Value<?>) left, (Value<?>) right);
    }

    @Override
    default @NotNull RuntimeException unsupportedOperation(final @NotNull TokenType operator,
                                                           final @NotNull VisitorObject<ClassValue<?>, Value<?>, ParameterValues> operand) {
        return ExecutorException.unsupportedOperation(operator, (Value<?>) operand);
    }

    /*
        OPERATIONS
     */
    @Override
    default @NotNull Value<?> and(final @NotNull Value<?> other) {
        return OperationUtils.executeBooleanComparison(this, other,
                (a, b) -> a && b
        );
    }

    @Override
    default @NotNull Value<?> or(final @NotNull Value<?> other) {
        return OperationUtils.executeBooleanComparison(this, other,
                (a, b) -> a || b
        );
    }

    @Override
    default @NotNull Value<?> equal(final @NotNull Value<?> other) {
        return OperationUtils.executeObjectComparison(this, other, (a, b) -> {
            if (a instanceof Number && b instanceof Number)
                return (Boolean) OperationUtils.executeBinaryComparison(this, other,
                        (f, s) -> f.compareTo(s) == 0)
                        .getValue();
            else return Objects.equals(a, b);
        });
    }

    @Override
    default @NotNull Value<?> notEqual(final @NotNull Value<?> other) {
        return equal(other).not();
    }

    @Override
    default @NotNull Value<?> lessThan(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryComparison(this, other,
                (a, b) -> a.compareTo(b) < 0
        );
    }

    @Override
    default @NotNull Value<?> lessThanEqual(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryComparison(this, other,
                (a, b) -> a.compareTo(b) <= 0
        );
    }

    @Override
    default @NotNull Value<?> greaterThan(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryComparison(this, other,
                (a, b) -> a.compareTo(b) > 0
        );
    }

    @Override
    default @NotNull Value<?> greaterThanEqual(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryComparison(this, other,
                (a, b) -> a.compareTo(b) >= 0
        );
    }

    @Override
    default @NotNull Value<?> bitAnd(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryBitOperation(this, other,
                (a, b) -> a & b,
                (a, b) -> a & b, (a, b) -> a & b
        );
    }

    @Override
    default @NotNull Value<?> bitOr(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryBitOperation(this, other,
                (a, b) -> a | b,
                (a, b) -> a | b, (a, b) -> a | b
        );
    }

    @Override
    default @NotNull Value<?> bitXor(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryBitOperation(this, other,
                (a, b) -> a ^ b,
                (a, b) -> a ^ b, (a, b) -> a ^ b
        );
    }

    @Override
    default @NotNull Value<?> lshift(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryOperation(this, other,
                (a, b) -> a << b, (a, b) -> a << b
        );
    }

    @Override
    default @NotNull Value<?> rshift(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryOperation(this, other,
                (a, b) -> a >> b, (a, b) -> a >> b
        );
    }

    @Override
    default @NotNull Value<?> urshift(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryOperation(this, other,
                (a, b) -> a >>> b, (a, b) -> a >>> b
        );
    }


    @Override
    default @NotNull Value<?> add(final @NotNull Value<?> other) {
        //TODO: String concatenation
        return OperationUtils.executeBinaryOperationDecimal(this, other,
                Double::sum, Float::sum,
                Long::sum, Integer::sum
        );
    }

    @Override
    default @NotNull Value<?> subtract(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryOperationDecimal(this, other,
                (a, b) -> a - b, (a, b) -> a - b,
                (a, b) -> a - b, (a, b) -> a - b
        );
    }

    @Override
    default @NotNull Value<?> multiply(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryOperationDecimal(this, other,
                (a, b) -> a * b, (a, b) -> a * b,
                (a, b) -> a * b, (a, b) -> a * b
        );
    }

    @Override
    default @NotNull Value<?> divide(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryOperationDecimal(this, other,
                (a, b) -> a / b, (a, b) -> a / b,
                (a, b) -> a / b, (a, b) -> a / b
        );
    }

    @Override
    default @NotNull Value<?> modulo(final @NotNull Value<?> other) {
        return OperationUtils.executeBinaryOperationDecimal(this, other,
                (a, b) -> a % b, (a, b) -> a % b,
                (a, b) -> a % b, (a, b) -> a % b
        );
    }

    @Override
    default @NotNull Value<?> minus() {
        return OperationUtils.executeUnaryOperationDecimal(this, d -> -d, f -> -f, l -> -l, i -> -i);
    }

    @Override
    default @NotNull Value<?> not() {
        return OperationUtils.executeUnaryOperationBoolean(this, b -> !b);
    }

}
