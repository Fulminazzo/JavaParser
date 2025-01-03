package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
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
     * @param componentsClass the components class
     * @param size            the size of the array
     */
    public ArrayValue(final @NotNull ClassValue<V> componentsClass, final int size) {
        this((Value<V>[]) Array.newInstance(componentsClass.getValue(), size));
    }

    /**
     * Instantiates a dynamic array value.
     *
     * @param componentsClass the components class
     * @param values          the values of the array
     */
    public ArrayValue(final @NotNull ClassValue<V> componentsClass, final @NotNull Collection<Value<V>> values) {
        this(values.toArray((Value<V>[]) Array.newInstance(componentsClass.getValue(), values.size())));
    }

    /**
     * Instantiates a new Array value.
     *
     * @param values the values
     */
    @SafeVarargs
    public ArrayValue(final Value<V> @NotNull ... values) {
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

}
