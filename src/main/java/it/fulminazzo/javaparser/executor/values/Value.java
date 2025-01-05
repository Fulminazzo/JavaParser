package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
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

    /**
     * Checks if the current value is primitive.
     *
     * @return true if it is
     */
    default boolean isPrimitive() {
        return this instanceof PrimitiveValue;
    }

    @Override
    default @NotNull Tuple<ClassValue<V>, Value<V>> getField(final @NotNull Field field) {
        Refl<?> refl = new Refl<>(getValue());
        Object object = refl.getFieldObject(field);
        ClassValue<V> classValue = ClassValue.of((Class<V>) field.getType());
        Value<V> value = (Value<V>) of(object);
        if (classValue.isPrimitive()) value = value.toPrimitive();
        return new Tuple<>(classValue, value);
    }

    /**
     * Executes the method associated with the given name with {@link ParameterValues} as parameters.
     *
     * @param <T>             the type of the returned value
     * @param methodName      the method name
     * @param parameterValues the parameter values
     * @return the returned value
     */
    default <T> @NotNull Value<T> invokeMethod(final @NotNull String methodName,
                                               final @NotNull ParameterValues parameterValues) {
        V value = getValue();
        Class<?> javaClass = is(ClassValue.class) ? (Class<?>) value : value.getClass();
        // Lookup methods from name and parameters count
        @NotNull List<Method> methods = ReflectionUtils.getMethods(javaClass, m ->
                m.getName().equals(methodName) && ValueUtils.verifyExecutable(parameterValues, m));

        Refl<?> refl = new Refl<>(ReflectionUtils.class);
        Class<?> @NotNull [] parametersTypes = parameterValues.getValue().stream()
                .map(Value::toClass)
                .map(ClassValue::getValue)
                .toArray(Class[]::new);

        for (Method method : methods) {
            // For each one, validate its parameters
            if (Boolean.TRUE.equals(refl.invokeMethod("validateParameters",
                    new Class[]{Class[].class, Executable.class},
                    parametersTypes, method))) {
                //TODO: reworkd
                Refl<V> executor = new Refl<>(getValue());
                List<Object> parametersList = new ArrayList<>();
                parametersTypes = method.getParameterTypes();
                List<Value<?>> parametersValues = parameterValues.getValue();
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
                Object returned = executor.invokeMethod(returnType, methodName, parametersList.toArray());
                final Value<?> returnedValue;
                if (Void.TYPE.equals(returnType)) returnedValue = Values.NO_VALUE;
                else if (returnType.isPrimitive()) returnedValue = PrimitiveValue.of(returned);
                else returnedValue = of(returned);
                return (Value<T>) returnedValue;
            }
        }
        throw new IllegalStateException("Cannot find method " + methodName + " in " + javaClass);
    }

    @Override
    default @NotNull Value<V> toPrimitive() {
        throw ValueRuntimeException.noPrimitive(getValue());
    }

    @Override
    default @NotNull Value<V> toPrimitive() {
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
