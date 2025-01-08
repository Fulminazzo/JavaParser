package it.fulminazzo.mojito.executor.values.primitivevalue;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Double} {@link PrimitiveValue}.
 */
final class DoubleValue extends NumberValue<Double> {

    /**
     * Instantiates a new Double value.
     *
     * @param value the value
     */
    public DoubleValue(@NotNull Double value) {
        super(value);
    }

}
