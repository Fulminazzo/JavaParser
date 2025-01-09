package it.fulminazzo.mojito.exceptions;

import org.jetbrains.annotations.NotNull;

/**
 * A special {@link RuntimeException} that provides a constructor with
 * inner {@link String#format(String, Object...)} call.
 */
public abstract class FormatRuntimeException extends RuntimeException {

    /**
     * Instantiates a new Format exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    protected FormatRuntimeException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

    /**
     * Instantiates a new Format exception.
     *
     * @param message the message
     * @param cause   the inner exception
     * @param args    the arguments to add in the message format
     */
    protected FormatRuntimeException(final @NotNull String message, final @NotNull Throwable cause, final Object @NotNull ... args) {
        super(String.format(message, args), cause);
    }

    /**
     * Generates a {@link FormatRuntimeException} with message the one from the given {@link Throwable}.
     *
     * @param cause the throwable
     */
    protected FormatRuntimeException(final @NotNull Throwable cause) {
        this(cause.getMessage());
    }

}
