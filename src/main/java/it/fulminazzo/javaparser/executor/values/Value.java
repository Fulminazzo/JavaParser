package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.javaparser.executor.values.primitivevalue.BooleanValue;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents a general value.
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
        throw new UnsupportedOperationException();
    }

    /**
     * Executes or comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue or(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    /**
     * Executes less than equal comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue lessThanEqual(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes greater than comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue greaterThan(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes greater than equal comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue greaterThanEqual(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
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
        throw new UnsupportedOperationException();
    }

    /**
     * Executes bit or operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> bitOr(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes bit xor operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> bitXor(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes lshift operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> lshift(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes rshift operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> rshift(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes urshift operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> urshift(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }


    /**
     * Executes add operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> add(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes subtract operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> subtract(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes multiply operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> multiply(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes divide operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> divide(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes modulo operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value<?> modulo(final @NotNull Value<?> other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes not operation.
     *
     * @return the boolean value
     */
    default @NotNull BooleanValue not() {
        throw new UnsupportedOperationException();
    }
    
}
