package it.fulminazzo.javaparser.executor.values.primitive;

import it.fulminazzo.javaparser.executor.values.Value;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Character} {@link PrimitiveValue}.
 */
class CharacterValue extends PrimitiveValue<Character> {

    /**
     * Instantiates a new Character value.
     *
     * @param value the value
     */
    public CharacterValue(@NotNull Character value) {
        super(value);
    }

    @Override
    public @NotNull BooleanValue lessThan(@NotNull Value other) {
        return asInteger().lessThan(other);
    }

    @Override
    public @NotNull BooleanValue lessThanEqual(@NotNull Value other) {
        return asInteger().lessThanEqual(other);
    }

    @Override
    public @NotNull BooleanValue greaterThan(@NotNull Value other) {
        return asInteger().greaterThan(other);
    }

    @Override
    public @NotNull BooleanValue greaterThanEqual(@NotNull Value other) {
        return asInteger().greaterThanEqual(other);
    }


    @Override
    public @NotNull Value bitAnd(@NotNull Value other) {
        return asInteger().bitAnd(other);
    }

    @Override
    public @NotNull Value bitOr(@NotNull Value other) {
        return asInteger().bitOr(other);
    }

    @Override
    public @NotNull Value bitXor(@NotNull Value other) {
        return asInteger().bitXor(other);
    }

    @Override
    public @NotNull Value lshift(@NotNull Value other) {
        return asInteger().lshift(other);
    }

    @Override
    public @NotNull Value rshift(@NotNull Value other) {
        return asInteger().rshift(other);
    }

    @Override
    public @NotNull Value urshift(@NotNull Value other) {
        return asInteger().urshift(other);
    }

    @Override
    public @NotNull Value add(@NotNull Value other) {
        return asInteger().add(other);
    }

    @Override
    public @NotNull Value subtract(@NotNull Value other) {
        return asInteger().subtract(other);
    }

    @Override
    public @NotNull Value multiply(@NotNull Value other) {
        return asInteger().multiply(other);
    }

    @Override
    public @NotNull Value divide(@NotNull Value other) {
        return asInteger().divide(other);
    }

    @Override
    public @NotNull Value modulo(@NotNull Value other) {
        return asInteger().modulo(other);
    }

    /**
     * Converts the current value to a {@link IntegerValue}.
     *
     * @return the integer value
     */
    public @NotNull IntegerValue asInteger() {
        return new IntegerValue((int) this.object);
    }

}
