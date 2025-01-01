package it.fulminazzo.javaparser.executor.values.primitive;

import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a primitive {@link Value}.
 *
 * @param <V> the type of the value
 */
abstract class PrimitiveValue<V> extends ObjectWrapper<V> implements Value {

    /**
     * Instantiates a new Primitive value.
     *
     * @param value the value
     */
    public PrimitiveValue(@NotNull V value) {
        super(value);
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    public @NotNull V getValue() {
        return this.object;
    }

}
