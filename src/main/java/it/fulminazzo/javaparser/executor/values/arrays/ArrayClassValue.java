package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the class value for an array.
 *
 * @param <V> the type of the components
 */
public class ArrayClassValue<V> extends ObjectWrapper<Class<V[]>> implements ClassValue<V[]> {

    /**
     * Instantiates a new Array class value.
     *
     * @param arrayClass the array class
     */
    public ArrayClassValue(final @NotNull Class<V[]> arrayClass) {
        super(arrayClass);
    }

    @Override
    public boolean compatibleWith(@NotNull Value<?> value) {
        return getValue().isAssignableFrom(value.getValue().getClass());
    }

    @Override
    public @NotNull Class<V[]> getValue() {
        return this.object;
    }

}
