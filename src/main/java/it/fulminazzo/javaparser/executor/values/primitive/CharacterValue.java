package it.fulminazzo.javaparser.executor.values.primitive;

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

    /**
     * Converts the current value to a {@link IntegerValue}.
     *
     * @return the integer value
     */
    public @NotNull IntegerValue asInteger() {
        return new IntegerValue((int) this.object);
    }

}
