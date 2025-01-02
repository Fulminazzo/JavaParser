package it.fulminazzo.javaparser.executor.values.objects;

import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an {@link Object} value, declared from its value.
 * It acts as a wrapper for values.
 *
 * @param <V> the type parameter
 */
public final class ObjectValue<V> extends ObjectWrapper<V> implements Value<V> {

    /**
     * Instantiates a new Object value.
     *
     * @param object the object
     */
    private ObjectValue(final @NotNull V object) {
        super(object);
    }

    @Override
    public @NotNull V getValue() {
        return this.object;
    }

    /**
     * Obtains an instance of {@link ObjectValue} from the given object.
     *
     * @param <V>    the type of the object
     * @param object the object
     * @return the object value
     */
    public static <V> ObjectValue<V> of(final @NotNull V object) {
        return new ObjectValue<>(object);
    }

}
