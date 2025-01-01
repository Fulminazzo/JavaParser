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

}
