package it.fulminazzo.mojito.parser.node.types;

import it.fulminazzo.mojito.parser.node.Node;
import it.fulminazzo.mojito.tokenizer.TokenType;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a general base type literal.
 */
@Getter
public abstract class BaseTypeLiteral extends Node {
    protected final @NotNull String rawValue;

    /**
     * Instantiates a new Base type literal.
     *
     * @param rawValue  the raw value
     * @param type the type
     */
    public BaseTypeLiteral(final @NotNull String rawValue,
                           final @NotNull TokenType type) {
        if (type.matches(rawValue)) this.rawValue = rawValue;
        else throw new IllegalArgumentException("Invalid raw value: " + rawValue);
    }

}
