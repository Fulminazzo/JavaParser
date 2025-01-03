package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

/**
 * Represents the class value for an array.
 *
 * @param <V> the type of the components
 */
@SuppressWarnings("unchecked")
public class ArrayClassValue<V> extends ObjectWrapper<ClassValue<V>> implements ClassValue<V[]> {

    /**
     * Instantiates a new Array class value.
     *
     * @param classValue the {@link ClassValue} of the components
     */
    public ArrayClassValue(final ClassValue<V> classValue) {
        super(classValue);
    }

    @Override
    public boolean compatibleWith(@NotNull Value<?> value) {
        return getValue().isAssignableFrom(value.getValue().getClass());
    }

    @Override
    public @NotNull Class<V[]> getValue() {
        return (Class<V[]>) Array.newInstance(this.object.getValue(), 0).getClass();
    }

    /**
     * Gets the components type.
     *
     * @return the components type
     */
    public ClassValue<V> getComponentsType() {
        return this.object;
    }

    @Override
    public String toString() {
        return getComponentsType().toString().replace(".class", "[].class");
    }

}
