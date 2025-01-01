package it.fulminazzo.javaparser.executor.values.primitive;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Long} {@link PrimitiveValue}.
 */
public class LongValue extends NumberValue<Long> {

    /**
     * Instantiates a new Long value.
     *
     * @param value the value
     */
    public LongValue(@NotNull Long value) {
        super(value);
    }

}
