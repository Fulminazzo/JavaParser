package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * The operation associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#LESS_THAN_EQUAL}.
 */
public class LessThanEqual extends BinaryOperation {

    /**
     * Instantiates a new Less than equal operation.
     *
     * @param left  the first operand
     * @param right the second operand
     */
    public LessThanEqual(@NotNull Node left, @NotNull Node right) {
        super(left, right);
    }

}
