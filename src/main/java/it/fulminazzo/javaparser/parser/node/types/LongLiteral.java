package it.fulminazzo.javaparser.parser.node.types;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#LONG_VALUE} literal.
 */
public class LongLiteral extends BaseTypeLiteral {

    /**
     * Instantiates a new Long literal.
     *
     * @param rawValue the raw value
     */
    public LongLiteral(final @NotNull String rawValue) throws LiteralException {
        super(rawValue, TokenType.LONG_VALUE);
    }

}
