package it.fulminazzo.javaparser.parser.node.values;

import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#NUMBER_VALUE} literal.
 */
public class NumberValueLiteral extends ValueLiteral {

    /**
     * Instantiates a new Number literal.
     *
     * @param rawValue the raw value
     */
    public NumberValueLiteral(final @NotNull String rawValue) throws NodeException {
        super(rawValue, TokenType.NUMBER_VALUE);
    }

}
