package it.fulminazzo.mojito.executor.values.primitivevalue;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.mojito.executor.ExecutorException;
import it.fulminazzo.mojito.executor.values.ClassValue;
import it.fulminazzo.mojito.executor.values.PrimitiveClassValue;
import it.fulminazzo.mojito.executor.values.Value;
import it.fulminazzo.mojito.executor.values.objects.ObjectValue;
import it.fulminazzo.mojito.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a primitive {@link Value} in Java.
 *
 * @param <V> the type of the value
 */
public abstract class PrimitiveValue<V> extends ObjectWrapper<V> implements Value<V> {

    /**
     * Instantiates a new Primitive value.
     *
     * @param value the value
     */
    PrimitiveValue(@NotNull V value) {
        super(value);
    }

    @Override
    public boolean isCharacter() {
        return is(CharValue.class);
    }

    @Override
    public boolean isInteger() {
        return is(IntValue.class);
    }

    @Override
    public boolean isLong() {
        return is(LongValue.class);
    }

    @Override
    public boolean isFloat() {
        return is(FloatValue.class);
    }

    @Override
    public boolean isDouble() {
        return is(DoubleValue.class);
    }

    @Override
    public boolean isBoolean() {
        return is(BooleanValue.class);
    }

    @Override
    public @NotNull PrimitiveValue<V> toPrimitive() {
        return this;
    }

    @Override
    public @NotNull Value<?> toWrapper() {
        return ObjectValue.of(getValue());
    }

    @Override
    public @NotNull V getValue() {
        return this.object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @NotNull ClassValue<V> toClass() {
        Class<?> clazz = ReflectionUtils.getPrimitiveClass(getValue().getClass());
        return (ClassValue<V>) PrimitiveClassValue.valueOf(clazz.getSimpleName().toUpperCase());
    }

    /**
     * Gets the most appropriate {@link PrimitiveValue} from the given value.
     * Throws {@link ExecutorException} in case of an invalid value.
     *
     * @param <V>   the type of the value
     * @param value the value
     * @return the primitive value
     */
    @SuppressWarnings("unchecked")
    public static <V> @NotNull PrimitiveValue<V> of(@NotNull V value) {
        Value<?> primitiveValue;
        if (value instanceof Double) primitiveValue = new DoubleValue((Double) value);
        else if (value instanceof Float) primitiveValue = new FloatValue((Float) value);
        else if (value instanceof Long) primitiveValue = new LongValue((Long) value);
        else if (value instanceof Boolean)
            if (Boolean.TRUE.equals(value)) primitiveValue = BooleanValue.TRUE;
            else primitiveValue = BooleanValue.FALSE;
        else if (value instanceof Character) primitiveValue = new CharValue((Character) value);
        else if (value instanceof Byte) primitiveValue = new ByteValue(Byte.parseByte(value.toString()));
        else if (value instanceof Short) primitiveValue = new ShortValue(Short.parseShort(value.toString()));
        else if (value instanceof Integer) primitiveValue = new IntValue(Integer.parseInt(value.toString()));
        else throw ExecutorException.invalidPrimitiveValue(value);
        return (PrimitiveValue<V>) primitiveValue;
    }

}
