package it.fulminazzo.javaparser.executor.values;

import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by {@link Value} objects.
 */
public class ValueException extends RuntimeException {

    /**
     * Instantiates a new Value exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    private ValueException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

    /**
     * Generates a {@link ValueException} with message:
     * <i>Value %value% is not a valid primitive type</i>
     *
     * @param value the value
     * @return the value exception
     */
    public static @NotNull ValueException invalidPrimitiveValue(final @NotNull Object value) {
        return new ValueException("Value %s is not a valid primitive type", value);
    }

    /**
     * Generates a {@link ValueException} with message:
     * <i>Could not find class '%clazz%'</i>
     *
     * @param clazz the clazz
     * @return the value exception
     */
    public static @NotNull ValueException classNotFound(final @NotNull String clazz) {
        return new ValueException("Could not find class '%s'", clazz);
    }

}
