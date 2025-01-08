package it.fulminazzo.mojito.parser.node.values;

import it.fulminazzo.mojito.parser.node.NodeException;
import it.fulminazzo.mojito.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#LONG_VALUE} literal.
 */
public class LongValueLiteral extends ValueLiteral {

    /**
     * Instantiates a new Long literal.
     *
     * @param rawValue the raw value
     */
    public LongValueLiteral(final @NotNull String rawValue) throws NodeException {
        super(rawValue, TokenType.LONG_VALUE);
    }

}
