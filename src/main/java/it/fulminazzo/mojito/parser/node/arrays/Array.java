package it.fulminazzo.mojito.parser.node.arrays;

import it.fulminazzo.mojito.parser.node.Node;
import it.fulminazzo.mojito.parser.node.NodeImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a general array declaration.
 */
abstract class Array extends NodeImpl {
    protected @NotNull Node type;

    /**
     * Instantiates a new Array.
     *
     * @param type the type of the array
     */
    public Array(final @NotNull Node type) {
        this.type = type;
    }

    /**
     * Sets the current component type to the given one.
     * If the current type is a {@link Array},
     * its component type will be updated instead.
     *
     * @param type the type
     */
    public void updateComponentType(final @NotNull Node type) {
        if (this.type instanceof Array) ((Array) this.type).updateComponentType(type);
        else this.type = type;
    }

    /**
     * The size of the array.
     *
     * @return the size
     */
    public abstract int size();

}
