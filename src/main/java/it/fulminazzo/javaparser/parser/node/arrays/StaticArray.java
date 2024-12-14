package it.fulminazzo.javaparser.parser.node.arrays;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an array initialized with a fixed dimension.
 */
public class StaticArray extends Array {
    protected final int size;

    /**
     * Instantiates a new Static array.
     *
     * @param type the type of the array
     * @param size the size
     */
    public StaticArray(final @NotNull Node type, final int size) {
        super(type);
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }

}
