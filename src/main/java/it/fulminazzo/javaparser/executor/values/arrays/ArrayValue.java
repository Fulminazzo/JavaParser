package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

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
     * @param arrayClass the array class
     * @param size       the size of the array
     */
    public ArrayValue(final Class<V[]> arrayClass, final int size) {
       this((V[]) Array.newInstance(arrayClass, size));
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
        //TODO:
        return null;
    }

    @Override
    public V[] getValue() {
        return this.object;
    }

}
