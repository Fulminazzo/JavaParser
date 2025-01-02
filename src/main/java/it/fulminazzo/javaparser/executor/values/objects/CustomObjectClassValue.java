package it.fulminazzo.javaparser.executor.values.objects;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.Values;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link ObjectClassValue} with a class different from the default types.
 */
class CustomObjectClassValue<V> extends ObjectWrapper<Class<V>> implements ClassValue<V> {

    /**
     * Instantiates a new Object value.
     *
     * @param clazz the class
     */
    public CustomObjectClassValue(@NotNull Class<V> clazz) {
        super(clazz);
    }

    @Override
    public @NotNull Class<V> getValue() {
        return this.object;
    }

    @Override
    public boolean compatibleWith(@NotNull Value<?> value) {
        if (value.is(ObjectValue.class))
            return getValue().isAssignableFrom(value.getValue().getClass());
        else return value.equals(Values.NULL_VALUE);
    }

    @Override
    public String toString() {
        return ClassValue.print(ObjectValue.getClassName(this.object));
    }

}
