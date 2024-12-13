package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * The operation associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#BIT_XOR}.
 */
public class BitXor extends BinaryOperation {

    /**
     * Instantiates a new Bit xor operation.
     *
     * @param left  the first operand
     * @param right the second operand
     */
    public BitXor(@NotNull Node left, @NotNull Node right) {
        super(left, right);
    }

}
