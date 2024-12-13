package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * The operation associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#LSHIFT}.
 */
public class LShift extends BinaryOperation {

    /**
     * Instantiates a new LShift operation.
     *
     * @param left  the first operand
     * @param right the second operand
     */
    public LShift(@NotNull Node left, @NotNull Node right) {
        super(left, right);
    }

}
