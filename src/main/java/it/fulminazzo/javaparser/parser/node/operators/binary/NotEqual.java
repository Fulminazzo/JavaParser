package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * The operation associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#NOT_EQUAL}.
 */
public class NotEqual extends BinaryOperation {

    /**
     * Instantiates a new Not equal operation.
     *
     * @param left  the first operand
     * @param right the second operand
     */
    public NotEqual(@NotNull Node left, @NotNull Node right) {
        super(left, right);
    }

}
