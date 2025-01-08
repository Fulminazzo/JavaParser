package it.fulminazzo.mojito.executor.values.primitivevalue;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Integer} {@link PrimitiveValue}.
 */
final class IntValue extends NumberValue<Integer> {

    /**
     * Instantiates a new Int value.
     *
     * @param value the value
     */
    public IntValue(@NotNull Integer value) {
        super(value);
    }

}
