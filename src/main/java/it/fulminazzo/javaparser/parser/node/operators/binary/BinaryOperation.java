package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.operators.Operation;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an {@link Operation} with two operands.
 */
public abstract class BinaryOperation extends Operation {
    protected final @NotNull Node left;
    protected final @NotNull Node right;

    /**
     * Instantiates a new Binary operation.
     *
     * @param left  the first operand
     * @param right the second operand
     */
    public BinaryOperation(final @NotNull Node left, final @NotNull Node right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Gets the left node.
     *
     * @return the node
     */
    public @NotNull Node left() {
        return this.left;
    }

    /**
     * Gets the right node.
     *
     * @return the node
     */
    public @NotNull Node right() {
        return this.right;
    }

}
