package it.fulminazzo.mojito.executor.values.primitivevalue;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Long} {@link PrimitiveValue}.
 */
final class LongValue extends NumberValue<Long> {

    /**
     * Instantiates a new Long value.
     *
     * @param value the value
     */
    public LongValue(@NotNull Long value) {
        super(value);
    }

}
