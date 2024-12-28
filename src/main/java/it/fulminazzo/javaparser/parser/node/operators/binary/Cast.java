package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the cast made to an expression.
 */
public class Cast extends BinaryOperation {

    /**
     * Instantiates a new Cast operation.
     *
     * @param left  the first operand
     * @param right the second operand
     */
    public Cast(@NotNull Node left, @NotNull Node right) {
        super(left, right);
    }

}
