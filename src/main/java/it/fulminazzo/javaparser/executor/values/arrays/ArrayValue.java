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
public class ArrayValue<V> extends ObjectWrapper<Value<V>[]> implements Value<Value<V>[]> {
    private final @NotNull ClassValue<V> componentsType;

    /**
     * Instantiates a static array value.
     *
     * @param componentsType the components type
     * @param size           the size of the array
     */
    public ArrayValue(final @NotNull ClassValue<V> componentsType, final int size) {
        this(componentsType, (Value<V>[]) Array.newInstance(Value.class, size));
        for (int i = 0; i < size; i++) this.object[i] = componentsType.toValue();
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
        this.componentsType = componentsType;
    }

    @Override
    public @NotNull ClassValue<Value<V>[]> toClassValue() {
        return new ArrayClassValue<>(this.componentsType);
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
        return String.format("%s(%s, [%s])", getClass().getSimpleName(), this.componentsType,
                Arrays.stream(this.object)
                        .map(Value::getValue)
                        .map(o -> o == null ? "null" : o.toString())
                        .collect(Collectors.joining(", ")));
    }

}
