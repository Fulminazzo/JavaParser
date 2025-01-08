package it.fulminazzo.mojito.parser.node.statements;

import it.fulminazzo.mojito.parser.node.Node;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link it.fulminazzo.mojito.tokenizer.TokenType#RETURN} statement.
 */
public class Return extends Statement {

    /**
     * Instantiates a new Return.
     *
     * @param expr the returned exception
     */
    public Return(final @NotNull Node expr) {
        super(expr);
    }

}
