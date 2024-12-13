package it.fulminazzo.javaparser.parser.node.operators.unary;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * The operation associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#MINUS}.
 */
public class Minus extends UnaryOperation {

    /**
     * Instantiates a new Minus operation.
     *
     * @param operand the operand
     */
    public Minus(@NotNull Node operand) {
        super(operand);
    }

}
