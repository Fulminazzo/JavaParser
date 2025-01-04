package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import lombok.Getter;
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
@Getter
@SuppressWarnings("unchecked")
public class ArrayValue<V> extends ObjectWrapper<V[]> implements Value<V[]> {
    private final @NotNull ClassValue<V> componentsType;

    /**
     * Instantiates a static array value.
     *
     * @param componentsType the components type
     * @param size           the size of the array
     */
    ArrayValue(final @NotNull ClassValue<V> componentsType, final int size) {
        this(componentsType, (Value<V>[]) Array.newInstance(Value.class, size));
        for (int i = 0; i < size; i++) this.object[i] = componentsType.toValue().getValue();
    }

    /**
     * Instantiates a dynamic array value.
     *
     * @param componentsType the components type
     * @param values         the values of the array
     */
    ArrayValue(final @NotNull ClassValue<V> componentsType, final @NotNull Collection<Value<V>> values) {
        super(values.stream().map(Value::getValue).toArray(a -> (V[]) Array.newInstance(componentsType.getValue(), a)));
        this.componentsType = componentsType;
    }

    @Override
    public @NotNull ClassValue<V[]> toClassValue() {
        return new ArrayClassValue<>(this.componentsType);
    }

    @Override
    public V[] getValue() {
        return this.object;
    }

    @Override
    public int hashCode() {
        int code = getClass().hashCode();
        for (V v : this.object) code ^= v.hashCode();
        return code;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ArrayValue && Arrays.equals(this.object, ((ArrayValue<?>) o).object);
    }

    @Override
    public String toString() {
        return String.format("%s(%s, [%s])", getClass().getSimpleName(), this.componentsType,
                Arrays.stream(this.object)
                        .map(o -> o == null ? "null" : o.toString())
                        .collect(Collectors.joining(", ")));
    }

    /**
     * Instantiates a static array value.
     *
     * @param <V>            the type of the value
     * @param componentsType the components type
     * @param size           the size of the array
     * @return the array value
     */
    public static <V> @NotNull ArrayValue<V> of(final @NotNull ClassValue<V> componentsType,
                                                final int size) {
        return new ArrayValue<>(componentsType, size);
    }

    /**
     * Instantiates a dynamic array value.
     *
     * @param <V>            the type of the value
     * @param componentsType the components type
     * @param values         the values of the array
     * @return the array value
     */
    public static <V> @NotNull ArrayValue<V> of(final @NotNull ClassValue<V> componentsType,
                                                final @NotNull Collection<Value<V>> values) {
        return new ArrayValue<>(componentsType, values);
    }

}
