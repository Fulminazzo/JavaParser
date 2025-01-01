package it.fulminazzo.javaparser.executor.values.primitive;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Boolean} {@link PrimitiveValue}.
 */
public class BooleanValue extends PrimitiveValue<Boolean> {

    /**
     * Instantiates a new Boolean value.
     *
     * @param value the value
     */
    public BooleanValue(@NotNull Boolean value) {
        super(value);
    }

}
