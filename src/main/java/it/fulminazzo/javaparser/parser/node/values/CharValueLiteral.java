package it.fulminazzo.javaparser.parser.node.values;

import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#CHAR_VALUE} literal.
 */
public class CharValueLiteral extends ValueLiteral {

    /**
     * Instantiates a new Char literal.
     *
     * @param rawValue the raw value
     */
    public CharValueLiteral(final @NotNull String rawValue) throws NodeException {
        super(rawValue, TokenType.CHAR_VALUE);
    }

}
