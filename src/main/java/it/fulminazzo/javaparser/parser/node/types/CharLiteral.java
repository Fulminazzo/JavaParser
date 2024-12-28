package it.fulminazzo.javaparser.parser.node.types;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#CHAR_VALUE} literal.
 */
public class CharLiteral extends BaseTypeLiteral {

    /**
     * Instantiates a new Char literal.
     *
     * @param rawValue the raw value
     */
    public CharLiteral(final @NotNull String rawValue) throws LiteralException {
        super(rawValue, TokenType.CHAR_VALUE);
    }

}
