package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.objects.ObjectValue;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Represents a general array {@link Value}.
 *
 * @param <V> the type of the array
 */
@SuppressWarnings("unchecked")
public class ArrayValue<V> extends ObjectWrapper<Value<V>[]> implements Value<Value<V>[]> {

    /**
     * Instantiates a static array value.
     *
     * @param componentsType the components type
     * @param size           the size of the array
     */
    public ArrayValue(final @NotNull ClassValue<V> componentsType, final int size) {
        this(componentsType, (Value<V>[]) Array.newInstance(Value.class, size));
    }

    /**
     * Instantiates a dynamic array value.
     *
     * @param componentsType the components type
     * @param values         the values of the array
     */
    public ArrayValue(final @NotNull ClassValue<V> componentsType, final @NotNull Collection<Value<V>> values) {
        this(componentsType, values.stream().toArray(a -> (Value<V>[]) Array.newInstance(Value.class, a)));
    }

    /**
     * Instantiates a new Array value.
     *
     * @param componentsType the components type
     * @param values         the values
     */
    @SafeVarargs
    public ArrayValue(final @NotNull ClassValue<V> componentsType, final Value<V> @NotNull ... values) {
        super(values);
    }

    @Override
    public @NotNull ArrayClassValue<V> toClassValue() {
        Class<V> componentsClass = (Class<V>) getValue().getClass().getComponentType();
        return new ArrayClassValue<>(ClassValue.of(componentsClass));
    }

    @Override
    public Value<V>[] getValue() {
        return this.object;
    }

    @Override
    public int hashCode() {
        int code = getClass().hashCode();
        for (Value<V> v : this.object) code ^= v.hashCode();
        return code;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ArrayValue && Arrays.equals(this.object, ((ArrayValue<?>) o).object);
    }

    @Override
    public String toString() {
        return String.format("%s([%s])", getClass().getSimpleName(), Arrays.stream(this.object)
                .map(Value::getValue)
                .map(o -> o == null ? "null" : o.toString())
                .collect(Collectors.joining(", ")));
    }

    /**
     * Gets the most appropriate {@link Value} class from the given {@link ClassValue}.
     *
     * @param <V>        the type of the value
     * @param classValue the class value
     * @return the class
     */
    static <V> Class<Value<V>> valueClassFromClassValue(final @NotNull ClassValue<V> classValue) {
        final Class<?> valueClass;
        if (classValue.isPrimitive()) valueClass = classValue.toValue().getClass();
        else valueClass = ObjectValue.class;
        return (Class<Value<V>>) valueClass;
    }

}
