package it.fulminazzo.javaparser.executor.values.primitive;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Boolean} {@link PrimitiveValue}.
 */
public final class BooleanValue extends PrimitiveValue<Boolean> {
    public static final BooleanValue TRUE = new BooleanValue(true);
    public static final BooleanValue FALSE = new BooleanValue(false);

    /**
     * Instantiates a new Boolean value.
     *
     * @param value the value
     */
    private BooleanValue(@NotNull Boolean value) {
        super(value);
    }

}
