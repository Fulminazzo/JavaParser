package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.Values;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

/**
 * Represents the class value for an array.
 *
 * @param <V> the type of the components
 */
@SuppressWarnings("unchecked")
public class ArrayClassValue<V> extends ObjectWrapper<ClassValue<V>> implements ClassValue<Value<V>[]> {

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
        if (value.is(Values.NULL_VALUE) && !this.object.isPrimitive()) return true;
        if (value.is(ArrayValue.class)) {
            Class<V> objectClass = this.object.getValue();
            Class<?> componentsClass = ((ArrayValue<?>) value).getComponentsType().getValue();
            return objectClass.isAssignableFrom(componentsClass);
        } else return false;
    }

    @Override
    public @NotNull Class<Value<V>[]> getValue() {
        return (Class<Value<V>[]>) Array.newInstance(this.object.getClass(), 0).getClass();
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
