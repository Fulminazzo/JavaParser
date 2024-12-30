package it.fulminazzo.javaparser.typechecker.types;

import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by {@link Type} objects.
 */
public class TypeException extends Exception {

    /**
     * Instantiates a new Type exception.
     *
     * @param message the message
     */
    private TypeException(final @NotNull String message) {
        super(message);
    }

    /**
     * Generates a {@link TypeException} with message:
     * <i>Could not find class %clazz%</i>
     *
     * @param clazz the clazz
     * @return the type exception
     */
    public static TypeException classNotFound(final @NotNull String clazz) {
        return new TypeException("Could not find class: " + clazz);
    }

}
