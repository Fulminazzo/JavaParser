package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the cast made to an expression.
 */
public class Cast extends BinaryOperation {

    /**
     * Instantiates a new Cast operation.
     *
     * @param type       the type to cast to
     * @param expression the expression to cast
     */
    public Cast(@NotNull Node type, @NotNull Node expression) {
        super(type, expression);
    }

}
