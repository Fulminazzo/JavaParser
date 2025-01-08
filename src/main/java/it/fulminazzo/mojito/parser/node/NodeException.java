package it.fulminazzo.mojito.parser.node;

import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by {@link TokenizedNode} when reading invalid value.
 */
public final class NodeException extends Exception {

    /**
     * Instantiates a new Node exception.
     *
     * @param message the message
     */
    public NodeException(final @NotNull String message) {
        super(message);
    }

}
