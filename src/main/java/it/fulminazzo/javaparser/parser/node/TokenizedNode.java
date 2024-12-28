package it.fulminazzo.javaparser.parser.node;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Node} associated with a {@link TokenType}.
 * The node will compare the raw value given with the regex associated with the token,
 * and throw a {@link NodeException} in case of error.
 */
@Getter
public abstract class TokenizedNode extends Node {

    /**
     * Instantiates a new Tokenized node.
     *
     * @param rawValue  the raw value
     * @param type      the token of this node
     */
    public TokenizedNode(final @NotNull String rawValue,
                         final @NotNull TokenType type) throws NodeException {
        if (!type.matches(rawValue)) throw new NodeException();
    }

}
