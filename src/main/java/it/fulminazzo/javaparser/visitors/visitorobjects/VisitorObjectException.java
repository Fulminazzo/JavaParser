package it.fulminazzo.javaparser.visitors.visitorobjects;

import org.jetbrains.annotations.NotNull;

/**
 * A general {@link Exception} associated with a {@link VisitorObject}.
 */
public abstract class VisitorObjectException extends Exception {

    /**
     * Instantiates a new Visitor object exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    protected VisitorObjectException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

}
