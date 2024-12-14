package it.fulminazzo.javaparser.parser.node.statements;

import it.fulminazzo.javaparser.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link it.fulminazzo.javaparser.tokenizer.TokenType#RETURN} statement.
 */
public class Return extends Statement {
    private final @NotNull Node expr;

    /**
     * Instantiates a new Return.
     *
     * @param expr the returned exception
     */
    public Return(final @NotNull Node expr) {
        this.expr = expr;
    }

}
