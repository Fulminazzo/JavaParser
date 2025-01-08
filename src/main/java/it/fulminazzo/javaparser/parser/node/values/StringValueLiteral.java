package it.fulminazzo.javaparser.parser.node.values;

import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#STRING_VALUE} literal.
 */
public class StringValueLiteral extends ValueLiteral {

    /**
     * Instantiates a new String literal.
     *
     * @param rawValue the raw value
     */
    public StringValueLiteral(final @NotNull String rawValue) throws NodeException {
        super(unescapeString(rawValue), TokenType.STRING_VALUE);
    }

}
