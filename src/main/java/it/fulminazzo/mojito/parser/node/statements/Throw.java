package it.fulminazzo.mojito.parser.node.statements;

import it.fulminazzo.mojito.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link it.fulminazzo.mojito.tokenizer.TokenType#THROW} statement.
 */
public class Throw extends Statement {

    /**
     * Instantiates a new Throw.
     *
     * @param expression the thrown exception
     */
    public Throw(final @NotNull Node expression) {
        super(expression);
    }

}
