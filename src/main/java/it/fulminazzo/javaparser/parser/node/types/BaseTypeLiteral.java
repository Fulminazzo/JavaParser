package it.fulminazzo.javaparser.parser.node.types;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.tokenizer.TokenType;
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
                           final @NotNull TokenType type) throws LiteralException {
        if (type.matches(rawValue)) this.rawValue = rawValue;
        else throw new LiteralException();
    }

}
