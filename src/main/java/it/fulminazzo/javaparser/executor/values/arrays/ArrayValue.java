package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

/**
 * Represents a general array {@link Value}.
 *
 * @param <V> the type of the array
 */
@SuppressWarnings("unchecked")
public class ArrayValue<V> extends ObjectWrapper<V[]> implements Value<V[]> {

    /**
     * Instantiates a static array value.
     *
     * @param componentsClass the components class
     * @param size            the size of the array
     */
    public ArrayValue(final Class<V> componentsClass, final int size) {
        this((V[]) Array.newInstance(componentsClass, size));
    }

    /**
     * Instantiates a dynamic array value.
     *
     * @param componentsClass the components class
     * @param values          the values of the array
     */
    public ArrayValue(final Class<V> componentsClass, final @NotNull Collection<V> values) {
        this(values.toArray((V[]) Array.newInstance(componentsClass, values.size())));
    }

    /**
     * Instantiates a new Array value.
     *
     * @param array the array
     */
    @SafeVarargs
    public ArrayValue(final V @NotNull ... array) {
        super(array);
    }

    @Override
    public @NotNull ClassValue<V[]> toClassValue() {
        return new ArrayClassValue<>((Class<V[]>) getValue().getClass());
    }

    @Override
    public V[] getValue() {
        return this.object;
    }

    /**
     * Creates a new {@link ArrayValue} for a primitive type
     * (essentially initializing all its values to 0).
     *
     * @param <V>             the primitive type
     * @param componentsClass the components class
     * @param size            the size of the array
     * @return the array value
     */
    public static <V> @NotNull ArrayValue<V> ofPrimitive(final Class<V> componentsClass,
                                                         final int size) {
        ArrayValue<?> value = new ArrayValue<>(ReflectionUtils.getWrapperClass(componentsClass), size);
        Arrays.fill(value.object, ClassValue.of(componentsClass).toValue().getValue());
        return (ArrayValue<V>) value;
    }

}
