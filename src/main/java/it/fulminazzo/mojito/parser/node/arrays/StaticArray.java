package it.fulminazzo.mojito.parser.node.arrays;

import it.fulminazzo.mojito.parser.node.Node;
import it.fulminazzo.mojito.parser.node.types.NumberLiteral;
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
    public StaticArray(final @NotNull Node type, final @NotNull NumberLiteral size) {
        super(type);
        this.size = Integer.parseInt(size.getRawValue());
    }

    @Override
    public int size() {
        return this.size;
    }

}
