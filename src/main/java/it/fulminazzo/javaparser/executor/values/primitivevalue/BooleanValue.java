package it.fulminazzo.javaparser.executor.values.primitivevalue;

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

    @Override
    public @NotNull String toString() {
        return this.object ? "TRUE" : "FALSE";
    }

    /**
     * Gets the {@link BooleanValue} from the given boolean.
     *
     * @param value the boolean
     * @return the boolean value
     */
    public static @NotNull BooleanValue of(boolean value) {
        return value ? TRUE : FALSE;
    }

}
