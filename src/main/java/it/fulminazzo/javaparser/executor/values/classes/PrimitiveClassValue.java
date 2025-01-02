package it.fulminazzo.javaparser.executor.values.classes;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.environment.Info;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a primitive {@link ClassValue}.
 *
 * @param <V> the type of the primitive
 */
public final class PrimitiveClassValue<V> extends ObjectWrapper<Class<V>> implements ClassValue<V>, Info {
    public static final ClassValue<Byte> BYTE = new PrimitiveClassValue<>(byte.class);
    public static final ClassValue<Short> SHORT = new PrimitiveClassValue<>(short.class);
    public static final ClassValue<Character> CHAR = new PrimitiveClassValue<>(char.class);
    public static final ClassValue<Integer> INT = new PrimitiveClassValue<>(int.class);
    public static final ClassValue<Long> LONG = new PrimitiveClassValue<>(long.class);
    public static final ClassValue<Float> FLOAT = new PrimitiveClassValue<>(float.class);
    public static final ClassValue<Double> DOUBLE = new PrimitiveClassValue<>(double.class);
    public static final ClassValue<Boolean> BOOLEAN = new PrimitiveClassValue<>(boolean.class);

    /**
     * Instantiates a new Primitive class value.
     *
     * @param clazz the clazz
     */
    public PrimitiveClassValue(final @NotNull Class<V> clazz) {
        super(clazz);
    }

    @Override
    public @NotNull Class<V> getValue() {
        return this.object;
    }

    @Override
    public boolean compatibleWith(@NotNull Value<?> type) {
        Object actual = type.getValue();
        if (actual == null) return false;
        return ReflectionUtils.getWrapperClass(getValue()).isAssignableFrom(actual.getClass());
    }

}
