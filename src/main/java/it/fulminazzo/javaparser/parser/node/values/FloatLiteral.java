package it.fulminazzo.javaparser.parser.node.values;

import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#FLOAT_VALUE} literal.
 */
public class FloatLiteral extends ValueLiteral {

    /**
     * Instantiates a new Float literal.
     *
     * @param rawValue the raw value
     */
    public FloatLiteral(final @NotNull String rawValue) throws NodeException {
        super(rawValue, TokenType.FLOAT_VALUE);
    }

}
