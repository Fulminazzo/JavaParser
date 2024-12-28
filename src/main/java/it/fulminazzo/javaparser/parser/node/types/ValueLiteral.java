package it.fulminazzo.javaparser.parser.node.types;

import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a literal which holds a raw value from the TYPE_VALUE directive.
 */
@Getter
public abstract class ValueLiteral extends Node {
    protected final @NotNull String rawValue;

    /**
     * Instantiates a new Value literal.
     *
     * @param rawValue  the raw value
     * @param type      the type
     */
    public ValueLiteral(final @NotNull String rawValue,
                        final @NotNull TokenType type) throws NodeException {
        if (type.matches(rawValue)) this.rawValue = rawValue;
        else throw new NodeException();
    }

}
