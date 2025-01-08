package it.fulminazzo.mojito.parser.node.values;

import it.fulminazzo.mojito.parser.node.NodeException;
import it.fulminazzo.mojito.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#DOUBLE_VALUE} literal.
 */
public class DoubleValueLiteral extends ValueLiteral {

    /**
     * Instantiates a new Double literal.
     *
     * @param rawValue the raw value
     * @throws NodeException in case the value does not match with the {@link TokenType#regex()}
     */
    public DoubleValueLiteral(final @NotNull String rawValue) throws NodeException {
        super(rawValue, TokenType.DOUBLE_VALUE);
    }

}
