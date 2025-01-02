package it.fulminazzo.javaparser.executor.values.classes;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.environment.Info;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

public final class PrimitiveClassValue<V> extends ObjectWrapper<Class<V>> implements ClassValue<V>, Info {
    public static final ClassValue<Byte> BYTE = new PrimitiveClassValue<>(Byte.class);
    public static final ClassValue<Short> SHORT = new PrimitiveClassValue<>(Short.class);
    public static final ClassValue<Character> CHAR = new PrimitiveClassValue<>(Character.class);
    public static final ClassValue<Integer> INT = new PrimitiveClassValue<>(Integer.class);
    public static final ClassValue<Long> LONG = new PrimitiveClassValue<>(Long.class);
    public static final ClassValue<Float> FLOAT = new PrimitiveClassValue<>(Float.class);
    public static final ClassValue<Double> DOUBLE = new PrimitiveClassValue<>(Double.class);
    public static final ClassValue<Boolean> BOOLEAN = new PrimitiveClassValue<>(Boolean.class);

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
