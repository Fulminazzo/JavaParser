package it.fulminazzo.javaparser.parser.node.values;

import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#BOOLEAN_VALUE} literal.
 */
public class BooleanValueLiteral extends ValueLiteral {

    /**
     * Instantiates a new Boolean literal.
     *
     * @param rawValue the raw value
     */
    public BooleanValueLiteral(final @NotNull String rawValue) throws NodeException {
        super(rawValue, TokenType.BOOLEAN_VALUE);
    }

}
