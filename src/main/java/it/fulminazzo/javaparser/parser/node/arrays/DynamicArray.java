package it.fulminazzo.javaparser.parser.node.arrays;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents an array dynamically typed.
 */
public class DynamicArray extends Array {
    private final @NotNull List<Node> parameters;

    /**
     * Instantiates a new Dynamic array.
     *
     * @param type       the type of the array
     * @param parameters the parameters
     */
    public DynamicArray(final @NotNull Node type, final @NotNull List<Node> parameters) {
        super(type);
        this.parameters = parameters;
    }

    @Override
    public int size() {
        return this.parameters.size();
    }

}
