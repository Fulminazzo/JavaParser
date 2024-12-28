package it.fulminazzo.javaparser.parser.node;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a general node of the parser.
 */
public interface Node {

    /**
     * Checks whether the current node is of the specified type.
     *
     * @param nodeType the node type
     * @return true if it is
     */
    boolean is(final @NotNull Class<? extends Node> nodeType);

}
