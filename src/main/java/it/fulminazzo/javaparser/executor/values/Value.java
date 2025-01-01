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

    default @NotNull BooleanValue and(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull BooleanValue or(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull BooleanValue equal(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull BooleanValue notEqual(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull BooleanValue lessThan(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull BooleanValue lessThanEqual(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull BooleanValue greaterThan(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull BooleanValue greaterThanEqual(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    /*
        BINARY OPERATIONS
     */

    default @NotNull Value bitAnd(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull Value bitOr(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull Value bitXor(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull Value lshift(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull Value rshift(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull Value urshift(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }


    default @NotNull Value add(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull Value subtract(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull Value multiply(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull Value divide(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull Value modulo(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }

    default @NotNull BooleanValue not(final @NotNull Value other) {
        throw new UnsupportedOperationException();
    }
    
}
