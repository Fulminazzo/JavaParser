package it.fulminazzo.mojito.parser.node.operators.binary;

import it.fulminazzo.mojito.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * The operation associated with {@link it.fulminazzo.mojito.tokenizer.TokenType#OR}.
 */
public class Or extends BinaryOperation {

    /**
     * Instantiates a new Or operation.
     *
     * @param left  the first operand
     * @param right the second operand
     */
    public Or(@NotNull Node left, @NotNull Node right) {
        super(left, right);
    }

}
