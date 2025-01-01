package it.fulminazzo.javaparser.executor.values.primitive;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Integer} {@link PrimitiveValue}.
 */
class IntegerValue extends NumberValue<Integer> {

    /**
     * Instantiates a new Integer value.
     *
     * @param value the value
     */
    public IntegerValue(@NotNull Integer value) {
        super(value);
    }

}
