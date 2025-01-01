package it.fulminazzo.javaparser.executor.values.primitive;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Float} {@link PrimitiveValue}.
 */
public class FloatValue extends NumberValue<Float> {

    /**
     * Instantiates a new Float value.
     *
     * @param value the value
     */
    public FloatValue(@NotNull Float value) {
        super(value);
    }

}
