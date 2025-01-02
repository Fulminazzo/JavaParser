package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.javaparser.executor.values.primitivevalue.BooleanValue;
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a general value.
 *
 * @param <V> the type of the value
 */
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

    /**
     * Checks whether the current value is of the one specified.
     *
     * @param <T>   the type of the value
     * @param value the class of the value
     * @return true if it is
     */
    default <T extends Value<?>> boolean is(final Class<T> value) {
        return value.isAssignableFrom(getClass());
    }

    /**
     * Converts the current value to an instance of {@link PrimitiveValue}.
     * Throws {@link ValueRuntimeException} in case of no associated primitive type.
     *
     * @return the primitive value
     */
    default @NotNull PrimitiveValue<V> toPrimitive() {
        throw ValueRuntimeException.invalidPrimitiveValue(getValue());
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
     * Gets the class value associated with the current value.
     *
     * @return the class value
     */
    @NotNull ClassValue<V> toClassValue();

    /**
     * Gets value.
     *
     * @return the value
     */
    V getValue();

    /*
        BINARY COMPARISONS
     */

    /**
     * Executes and comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue and(final @NotNull Value<?> other) {
        return toPrimitive().and(other);
    }

    /**
     * Executes or comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue or(final @NotNull Value<?> other) {
        return toPrimitive().or(other);
    }

    /**
     * Executes equal comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue equal(final @NotNull Value<?> other) {
        return BooleanValue.of(Objects.equals(getValue(), other.getValue()));
    }

    /**
     * Executes not equal comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue notEqual(final @NotNull Value<?> other) {
        return equal(other).not();
    }

    /**
     * Executes less than comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue lessThan(final @NotNull Value<?> other) {
        return toPrimitive().lessThan(other);
    }

    /**
     * Executes less than equal comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue lessThanEqual(final @NotNull Value<?> other) {
        return toPrimitive().lessThanEqual(other);
    }

    /**
     * Executes greater than comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue greaterThan(final @NotNull Value<?> other) {
        return toPrimitive().greaterThan(other);
    }

    /**
     * Executes greater than equal comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue greaterThanEqual(final @NotNull Value<?> other) {
        return toPrimitive().greaterThanEqual(other);
    }

    /*
        BINARY OPERATIONS
     */

    /**
     * Executes bit and operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> bitAnd(final @NotNull Value<?> other) {
        return toPrimitive().bitAnd(other);
    }

    /**
     * Executes bit or operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> bitOr(final @NotNull Value<?> other) {
        return toPrimitive().bitOr(other);
    }

    /**
     * Executes bit xor operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> bitXor(final @NotNull Value<?> other) {
        return toPrimitive().bitXor(other);
    }

    /**
     * Executes lshift operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> lshift(final @NotNull Value<?> other) {
        return toPrimitive().lshift(other);
    }

    /**
     * Executes rshift operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> rshift(final @NotNull Value<?> other) {
        return toPrimitive().rshift(other);
    }

    /**
     * Executes urshift operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> urshift(final @NotNull Value<?> other) {
        return toPrimitive().urshift(other);
    }


    /**
     * Executes add operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> add(final @NotNull Value<?> other) {
        return toPrimitive().add(other);
    }

    /**
     * Executes subtract operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> subtract(final @NotNull Value<?> other) {
        return toPrimitive().subtract(other);
    }

    /**
     * Executes multiply operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> multiply(final @NotNull Value<?> other) {
        return toPrimitive().multiply(other);
    }

    /**
     * Executes divide operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> divide(final @NotNull Value<?> other) {
        return toPrimitive().divide(other);
    }

    /**
     * Executes modulo operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> modulo(final @NotNull Value<?> other) {
        return toPrimitive().modulo(other);
    }

    /**
     * Executes minus operation.
     *
     * @return the boolean value
     */
    default @NotNull Value<?> minus() {
        return toPrimitive().minus();
    }

    /**
     * Executes not operation.
     *
     * @return the boolean value
     */
    default @NotNull BooleanValue not() {
        return toPrimitive().not();
    }
    
}
