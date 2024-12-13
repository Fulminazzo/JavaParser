package it.fulminazzo.javaparser.parser.node;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a basic type of value.
 * Allowed types are found in {@link #ALLOWED_TYPES}.
 */
@Getter
public class BaseValue extends Node {
    private static final TokenType[] ALLOWED_TYPES = new TokenType[]{
            TokenType.NUMBER_VALUE, TokenType.DOUBLE_VALUE, TokenType.FLOAT_VALUE,
            TokenType.LONG_VALUE, TokenType.BOOLEAN_VALUE, TokenType.CHAR_VALUE,
            TokenType.STRING_VALUE
    };
    private final @NotNull TokenType type;
    private final @NotNull String rawValue;

    /**
     * Instantiates a new Base value.
     *
     * @param rawValue the raw value
     */
    public BaseValue(final @NotNull String rawValue) {
        for (final TokenType type : ALLOWED_TYPES)
            if (type.matches(rawValue)) {
                this.rawValue = rawValue;
                this.type = type;
                return;
            }
        throw new IllegalArgumentException("Invalid raw value: " + rawValue);
    }

}
