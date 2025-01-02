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
