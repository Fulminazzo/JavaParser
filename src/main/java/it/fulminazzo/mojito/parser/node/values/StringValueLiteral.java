package it.fulminazzo.mojito.parser.node.values;

import it.fulminazzo.mojito.parser.node.NodeException;
import it.fulminazzo.mojito.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#STRING_VALUE} literal.
 */
public class StringValueLiteral extends ValueLiteral {

    /**
     * Instantiates a new String literal.
     *
     * @param rawValue the raw value
     * @throws NodeException in case the value does not match with the {@link TokenType#regex()}
     */
    public StringValueLiteral(final @NotNull String rawValue) throws NodeException {
        super(unescapeString(rawValue), TokenType.STRING_VALUE);
    }

}
