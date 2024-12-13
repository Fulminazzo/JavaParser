package it.fulminazzo.javaparser.parser.node.operators.unary;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * The operation associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#NOT}.
 */
public class Not extends UnaryOperation {

    /**
     * Instantiates a new Not operation.
     *
     * @param operand the operand
     */
    public Not(@NotNull Node operand) {
        super(operand);
    }

}
