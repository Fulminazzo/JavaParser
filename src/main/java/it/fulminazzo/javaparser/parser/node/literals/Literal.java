package it.fulminazzo.javaparser.parser.node.literals;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link TokenType#LITERAL} token in the program.
 */
public interface Literal extends Node {

    /**
     * Splits the literal by checking if the character '.' is present.
     * If it is not, it will return a {@link Tuple} with key this {@link Literal} and value null.
     * If it is, it will return a {@link Tuple} with key every string until the last '.' and value the remainder.
     * <br/>
     * For example, "hello.dear.world" will be split to ("hello.dear", "world")
     *
     * @return the tuple
     */
    @NotNull Tuple<Literal, Literal> splitLastDot();

    /**
     * Checks if the character '.' is present in the given literal.
     *
     * @return true if it is
     */
    boolean isDotted();

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
