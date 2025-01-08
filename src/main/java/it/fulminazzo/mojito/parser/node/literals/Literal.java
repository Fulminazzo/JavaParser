package it.fulminazzo.mojito.parser.node.literals;

import it.fulminazzo.mojito.parser.node.Node;
import it.fulminazzo.mojito.parser.node.NodeException;
import it.fulminazzo.mojito.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#LITERAL} token in the program.
 */
public interface Literal extends Node {

    /**
     * Gets the internal.
     *
     * @return the literal
     */
    @NotNull String getLiteral();

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
