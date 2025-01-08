package it.fulminazzo.mojito.executor.values.primitivevalue;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Float} {@link PrimitiveValue}.
 */
final class FloatValue extends NumberValue<Float> {

    /**
     * Instantiates a new Float value.
     *
     * @param value the value
     */
    public FloatValue(@NotNull Float value) {
        super(value);
    }

}
