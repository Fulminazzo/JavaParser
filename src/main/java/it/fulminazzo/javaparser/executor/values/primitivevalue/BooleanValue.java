package it.fulminazzo.javaparser.executor.values.primitivevalue;

import it.fulminazzo.javaparser.executor.values.Value;
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
    public @NotNull BooleanValue and(@NotNull Value<?> other) {
        return of(this.object && other.to(BooleanValue.class).object);
    }

    @Override
    public @NotNull BooleanValue or(@NotNull Value<?> other) {
        return of(this.object || other.to(BooleanValue.class).object);
    }


    @Override
    public @NotNull Value<?> bitAnd(@NotNull Value<?> other) {
        return of(this.object & other.to(BooleanValue.class).object);
    }

    @Override
    public @NotNull Value<?> bitOr(@NotNull Value<?> other) {
        return of(this.object | other.to(BooleanValue.class).object);
    }

    @Override
    public @NotNull Value<?> bitXor(@NotNull Value<?> other) {
        return of(this.object ^ other.to(BooleanValue.class).object);
    }

    @Override
    public @NotNull BooleanValue not() {
        return of(!this.object);
    }

    @Override
    public String toString() {
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
