package it.fulminazzo.mojito.parser.node.operators.binary;

import it.fulminazzo.mojito.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * The operation associated with {@link it.fulminazzo.mojito.tokenizer.TokenType#MULTIPLY}.
 */
public class Multiply extends BinaryOperation {

    /**
     * Instantiates a new Multiply operation.
     *
     * @param left  the first operand
     * @param right the second operand
     */
    public Multiply(@NotNull Node left, @NotNull Node right) {
        super(left, right);
    }

}
