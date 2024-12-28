package it.fulminazzo.javaparser.parser.node.literals;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#LITERAL} token in the program.
 */
public interface Literal extends Node {

    /**
     * Creates a new {@link Literal} from the given value.
     *
     * @param value the value
     * @return the literal
     * @throws NodeException in case the given value is invalid
     */
    static @NotNull Literal of(final @NotNull String value) throws NodeException {
        return new LiteralImpl(value);
    }

}
