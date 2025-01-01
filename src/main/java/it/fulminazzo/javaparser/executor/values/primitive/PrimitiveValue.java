package it.fulminazzo.javaparser.executor.values.primitive;

import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.ValueException;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a primitive {@link Value}.
 *
 * @param <V> the type of the value
 */
public abstract class PrimitiveValue<V> extends ObjectWrapper<V> implements Value {

    /**
     * Instantiates a new Primitive value.
     *
     * @param value the value
     */
    PrimitiveValue(@NotNull V value) {
        super(value);
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public @NotNull V getValue() {
        return this.object;
    }

    /**
     * Gets the most appropriate {@link PrimitiveValue} from the given value.
     *
     * @param <V>   the type of the value
     * @param value the value
     * @return the primitive value
     */
    @SuppressWarnings("unchecked")
    public static @NotNull <V> PrimitiveValue<V> of(@NotNull V value) {
        Value primitiveValue;
        if (value instanceof Double) primitiveValue = new DoubleValue((Double) value);
        else if (value instanceof Float) primitiveValue = new FloatValue((Float) value);
        else if (value instanceof Long) primitiveValue = new LongValue((Long) value);
        else if (value instanceof Boolean) primitiveValue = new BooleanValue((Boolean) value);
        else if (value instanceof String) primitiveValue = new StringValue((String) value);
        else if (value instanceof Character) primitiveValue = new CharacterValue((Character) value);
        else if (value instanceof Byte || value instanceof Short || value instanceof Integer)
            primitiveValue = new IntegerValue(Integer.parseInt(value.toString()));
        else throw ValueException.invalidPrimitiveValue(value);
        return (PrimitiveValue<V>) primitiveValue;
    }

}
