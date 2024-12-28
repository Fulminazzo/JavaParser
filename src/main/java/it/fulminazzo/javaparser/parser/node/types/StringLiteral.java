package it.fulminazzo.javaparser.parser.node.types;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#STRING_VALUE} literal.
 */
public class StringLiteral extends BaseTypeLiteral {

    /**
     * Instantiates a new String literal.
     *
     * @param rawValue the raw value
     */
    public StringLiteral(final @NotNull String rawValue) throws LiteralException {
        super(rawValue, TokenType.STRING_VALUE);
    }

}
