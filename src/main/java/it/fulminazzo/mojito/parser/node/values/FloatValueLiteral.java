package it.fulminazzo.mojito.parser.node.values;

import it.fulminazzo.mojito.parser.node.NodeException;
import it.fulminazzo.mojito.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#FLOAT_VALUE} literal.
 */
public class FloatValueLiteral extends ValueLiteral {

    /**
     * Instantiates a new Float literal.
     *
     * @param rawValue the raw value
     */
    public FloatValueLiteral(final @NotNull String rawValue) throws NodeException {
        super(rawValue, TokenType.FLOAT_VALUE);
    }

}
