package it.fulminazzo.javaparser.executor.values.primitivevalue;

import it.fulminazzo.javaparser.executor.values.Value;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Character} {@link PrimitiveValue}.
 */
public final class CharValue extends PrimitiveValue<Character> {

    /**
     * Instantiates a new Character value.
     *
     * @param value the value
     */
    public CharValue(@NotNull Character value) {
        super(value);
    }

    @Override
    public @NotNull Value<?> equal(@NotNull Value<?> other) {
        if (other.is(NumberValue.class)) return asInteger().equal(other);
        else return super.equal(other);
    }

    @Override
    public @NotNull Value<?> lessThan(@NotNull Value<?> other) {
        return asInteger().lessThan(other);
    }

    @Override
    public @NotNull Value<?> lessThanEqual(@NotNull Value<?> other) {
        return asInteger().lessThanEqual(other);
    }

    @Override
    public @NotNull Value<?> greaterThan(@NotNull Value<?> other) {
        return asInteger().greaterThan(other);
    }

    @Override
    public @NotNull Value<?> greaterThanEqual(@NotNull Value<?> other) {
        return asInteger().greaterThanEqual(other);
    }


    @Override
    public @NotNull Value<?> bitAnd(@NotNull Value<?> other) {
        return asInteger().bitAnd(other);
    }

    @Override
    public @NotNull Value<?> bitOr(@NotNull Value<?> other) {
        return asInteger().bitOr(other);
    }

    @Override
    public @NotNull Value<?> bitXor(@NotNull Value<?> other) {
        return asInteger().bitXor(other);
    }

    @Override
    public @NotNull Value<?> lshift(@NotNull Value<?> other) {
        return asInteger().lshift(other);
    }

    @Override
    public @NotNull Value<?> rshift(@NotNull Value<?> other) {
        return asInteger().rshift(other);
    }

    @Override
    public @NotNull Value<?> urshift(@NotNull Value<?> other) {
        return asInteger().urshift(other);
    }

    @Override
    public @NotNull Value<?> add(@NotNull Value<?> other) {
        return asInteger().add(other);
    }

    @Override
    public @NotNull Value<?> subtract(@NotNull Value<?> other) {
        return asInteger().subtract(other);
    }

    @Override
    public @NotNull Value<?> multiply(@NotNull Value<?> other) {
        return asInteger().multiply(other);
    }

    @Override
    public @NotNull Value<?> divide(@NotNull Value<?> other) {
        return asInteger().divide(other);
    }

    @Override
    public @NotNull Value<?> modulo(@NotNull Value<?> other) {
        return asInteger().modulo(other);
    }

    @Override
    public @NotNull Value<?> minus() {
        return asInteger().minus();
    }

    /**
     * Converts the current value to a {@link IntValue}.
     *
     * @return the integer value
     */
    public @NotNull Value<?> asInteger() {
        return new IntValue((int) this.object);
    }

}
