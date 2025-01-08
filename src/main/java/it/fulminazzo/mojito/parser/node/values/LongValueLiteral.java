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
     * @throws NodeException in case the value does not match with the {@link TokenType#regex()}
     */
    public LongValueLiteral(final @NotNull String rawValue) throws NodeException {
        super(rawValue, TokenType.LONG_VALUE);
    }

}
