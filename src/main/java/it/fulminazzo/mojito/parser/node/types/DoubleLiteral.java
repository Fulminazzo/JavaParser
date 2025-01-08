package it.fulminazzo.mojito.parser.node.types;

import it.fulminazzo.mojito.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#DOUBLE_VALUE} literal.
 */
public class DoubleLiteral extends BaseTypeLiteral {

    /**
     * Instantiates a new Double literal.
     *
     * @param rawValue the raw value
     */
    public DoubleLiteral(final @NotNull String rawValue) {
        super(rawValue, TokenType.DOUBLE_VALUE);
    }

}
