package it.fulminazzo.javaparser.parser.node.literals;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.structures.tuples.Tuple;
import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.parser.node.TokenizedNode;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Represents a {@link TokenType#LITERAL} token in the program.
 */
class LiteralImpl extends TokenizedNode implements Literal {
    private final @NotNull String value;

    /**
     * Instantiates a new Literal.
     *
     * @param rawValue the raw value
     */
    public LiteralImpl(final @NotNull String rawValue) throws NodeException {
        super(rawValue, TokenType.LITERAL);
        this.value = rawValue;
    }

    @Override
    public @NotNull Tuple<Literal, Literal> splitLastDot() {
        if (!isDotted()) return new Tuple<>(this, null);
        String[] tmp = this.value.split("\\.");
        String last = tmp[tmp.length - 1];
        String first = String.join(".", Arrays.copyOfRange(tmp, 0, tmp.length - 1));
        return new Tuple<>(
                new Refl<>(LiteralImpl.class, first).getObject(),
                new Refl<>(LiteralImpl.class, last).getObject()
        );
    }

    @Override
    public boolean isDotted() {
        return this.value.contains(".");
    }

}
