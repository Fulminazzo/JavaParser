package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * The operation associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#BIT_OR}.
 */
public class BitOr extends BinaryOperation {

    /**
     * Instantiates a new Bit or operation.
     *
     * @param left  the first operand
     * @param right the second operand
     */
    public BitOr(@NotNull Node left, @NotNull Node right) {
        super(left, right);
    }

}
