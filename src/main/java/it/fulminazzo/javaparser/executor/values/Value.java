package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.javaparser.executor.values.primitive.BooleanValue;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a general value.
 */
public interface Value {

    /*
        BINARY COMPARISONS
     */

    /**
     * Executes and comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue and(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes or comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue or(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes equal comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue equal(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes not equal comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue notEqual(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes less than comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue lessThan(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes less than equal comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue lessThanEqual(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes greater than comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue greaterThan(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes greater than equal comparison.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue greaterThanEqual(final @NotNull Value other) {
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
    default @NotNull Value bitAnd(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes bit or operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value bitOr(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes bit xor operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value bitXor(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes lshift operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value lshift(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes rshift operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value rshift(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes urshift operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value urshift(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }


    /**
     * Executes add operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value add(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes subtract operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value subtract(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes multiply operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value multiply(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes divide operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value divide(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes modulo operation.
     *
     * @param other the other value
     * @return the value
     */
    default @NotNull Value modulo(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /**
     * Executes not operation.
     *
     * @param other the other value
     * @return the boolean value
     */
    default @NotNull BooleanValue not(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }
    
}
