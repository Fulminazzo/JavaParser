package it.fulminazzo.javaparser.executor.values.primitivevalue;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Integer} {@link PrimitiveValue}.
 */
class IntValue extends NumberValue<Integer> {

    /**
     * Instantiates a new Integer value.
     *
     * @param value the value
     */
    public IntValue(@NotNull Integer value) {
        super(value);
    }

}
