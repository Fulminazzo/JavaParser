package it.fulminazzo.mojito.parser.node.types;

import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.mojito.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Represents a {@link TokenType#LITERAL} token in the program.
 */
public class Literal extends BaseTypeLiteral {

    /**
     * Instantiates a new Literal.
     *
     * @param rawValue the raw value
     */
    public Literal(final @NotNull String rawValue) {
        super(rawValue, TokenType.LITERAL);
    }

    /**
     * Splits the literal by checking if the character '.' is present.
     * If it is not, it will return a {@link Tuple} with key this {@link Literal} and value null.
     * If it is, it will return a {@link Tuple} with key every string until the last '.' and value the remainder.
     * <br/>
     * For example, "hello.dear.world" will be split to ("hello.dear", "world")
     *
     * @return the tuple
     */
    public @NotNull Tuple<Literal, Literal> splitLastDot() {
        if (!isDotted()) return new Tuple<>(this, null);
        String[] tmp = this.rawValue.split("\\.");
        String last = tmp[tmp.length - 1];
        String first = String.join(".", Arrays.copyOfRange(tmp, 0, tmp.length - 1));
        return new Tuple<>(new Literal(first), new Literal(last));
    }

    /**
     * Checks if the character '.' is present in the given literal.
     *
     * @return true if it is
     */
    public boolean isDotted() {
        return this.rawValue.contains(".");
    }

}
