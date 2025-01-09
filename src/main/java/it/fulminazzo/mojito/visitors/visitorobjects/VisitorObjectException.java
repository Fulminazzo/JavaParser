package it.fulminazzo.mojito.visitors.visitorobjects;

import it.fulminazzo.mojito.exceptions.FormatException;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

/**
 * A general {@link Exception} associated with a {@link VisitorObject}.
 */
public abstract class VisitorObjectException extends FormatException {

    /**
     * Instantiates a new Visitor object exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    protected VisitorObjectException(final @NotNull String message, final Object @NotNull ... args) {
        super(message, args);
    }

    /**
     * Formats the given parameters to a string.
     *
     * @param parameters the parameters
     * @return the string
     */
    protected static @NotNull String formatParameters(final @NotNull ParameterVisitorObjects<?, ?, ?> parameters) {
        return "(" + parameters.stream()
                .map(VisitorObject::toString)
                .collect(Collectors.joining(", ")) + ")";
    }

    /**
     * Formats the given method and parameters to a string.
     *
     * @param method     the method
     * @param parameters the parameters
     * @return the string
     */
    protected static @NotNull String formatMethod(final @NotNull String method,
                                                  final @NotNull ParameterVisitorObjects<?, ?, ?> parameters) {
        return String.format("%s%s", method, formatParameters(parameters));
    }

}
