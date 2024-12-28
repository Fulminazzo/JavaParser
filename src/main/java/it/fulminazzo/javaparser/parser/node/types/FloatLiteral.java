package it.fulminazzo.javaparser.parser.node.types;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#FLOAT_VALUE} literal.
 */
public class FloatLiteral extends BaseTypeLiteral {

    /**
     * Instantiates a new Float literal.
     *
     * @param rawValue the raw value
     */
    public FloatLiteral(final @NotNull String rawValue) throws LiteralException {
        super(rawValue, TokenType.FLOAT_VALUE);
    }

}
