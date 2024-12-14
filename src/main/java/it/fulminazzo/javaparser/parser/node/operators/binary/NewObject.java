package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * The operation associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#NEW}.
 */
public class NewObject extends BinaryOperation {

    /**
     * Instantiates a new New object operation.
     *
     * @param left  the first operand
     * @param right the second operand
     */
    public NewObject(@NotNull Node left, @NotNull Node right) {
        super(left, right);
    }

}
