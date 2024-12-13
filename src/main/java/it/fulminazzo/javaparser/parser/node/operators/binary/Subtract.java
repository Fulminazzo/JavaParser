package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * The operation associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#SUBTRACT}.
 */
public class Subtract extends BinaryOperation {

    /**
     * Instantiates a new Subtract operation.
     *
     * @param left  the first operand
     * @param right the second operand
     */
    public Subtract(@NotNull Node left, @NotNull Node right) {
        super(left, right);
    }

}
