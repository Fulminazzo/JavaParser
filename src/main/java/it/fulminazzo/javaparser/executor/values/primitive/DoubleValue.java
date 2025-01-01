package it.fulminazzo.javaparser.executor.values.primitive;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Double} {@link PrimitiveValue}.
 */
public class DoubleValue extends NumberValue<Double> {

    /**
     * Instantiates a new Double value.
     *
     * @param value the value
     */
    public DoubleValue(@NotNull Double value) {
        super(value);
    }

}
