package it.fulminazzo.mojito.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * A special {@link Exception} that provides a constructor with
 * inner {@link String#format(String, Object...)} call.
 */
public abstract class FormatException extends Exception {

    /**
     * Instantiates a new Format exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    protected FormatException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

}
