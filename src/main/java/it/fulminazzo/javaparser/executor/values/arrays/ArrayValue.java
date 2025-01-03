package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a general array {@link Value}.
 *
 * @param <V> the type of the array
 */
public class ArrayValue<V> extends ObjectWrapper<V[]> implements Value<V[]> {

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
