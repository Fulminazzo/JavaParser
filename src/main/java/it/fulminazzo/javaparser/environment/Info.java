package it.fulminazzo.javaparser.environment;

import org.jetbrains.annotations.NotNull;

/**
 * Holds the information of an object.
 *
 * @param <T> the type of the object
 */
public interface Info<T> {

    /**
     * Verifies that the object is compatible with the provided one.
     *
     * @param object the other object
     * @return true if it is
     */
    boolean compatibleWith(final @NotNull Object object);

}
