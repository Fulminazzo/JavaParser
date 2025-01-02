package it.fulminazzo.javaparser.executor.values.objects;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.ValueException;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

/**
 * Represents an {@link Object} value, declared from its value.
 * It acts as a wrapper for values.
 *
 * @param <V> the type parameter
 */
public final class ObjectValue<V> extends ObjectWrapper<V> implements Value<V> {
    private static final String[] IMPLIED_PACKAGES = new String[]{
            String.class.getPackage().getName(),
            Map.class.getPackage().getName(),
            IOException.class.getPackage().getName()
    };

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

    /**
     * Searches for a class matching the given class name.
     * If no match is found, it tries to append the packages in
     * {@link #IMPLIED_PACKAGES}.
     * If still nothing is found, a {@link ValueException} is thrown.
     *
     * @param className the class name
     * @return the class
     * @throws ValueException the exception thrown in case the class is not found
     */
    static <V> @NotNull Class<V> getClass(final @NotNull String className) throws ValueException {
        try {
            return ReflectionUtils.getClass(className);
        } catch (IllegalArgumentException e) {
            for (String impliedPackage : IMPLIED_PACKAGES) {
                String name = impliedPackage + "." + className;
                try {
                    return ReflectionUtils.getClass(name);
                } catch (IllegalArgumentException ignored) {}
            }
            throw ValueException.classNotFound(className);
        }
    }

}
