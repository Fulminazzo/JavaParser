package it.fulminazzo.mojito.parser.node.arrays;

import it.fulminazzo.mojito.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a general array declaration.
 */
abstract class Array extends Node{
    protected final @NotNull Node type;

    /**
     * Instantiates a new Array.
     *
     * @param type the type of the array
     */
    public Array(final @NotNull Node type) {
        this.type = type;
    }

    /**
     * The size of the array.
     *
     * @return the size
     */
    public abstract int size();

}
