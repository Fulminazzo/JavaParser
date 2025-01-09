package it.fulminazzo.mojito.parser.node;

import it.fulminazzo.mojito.exceptions.FormatException;
import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by {@link TokenizedNode} when reading invalid value.
 */
public final class NodeException extends FormatException {

    /**
     * Instantiates a new Node exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    public NodeException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

}
