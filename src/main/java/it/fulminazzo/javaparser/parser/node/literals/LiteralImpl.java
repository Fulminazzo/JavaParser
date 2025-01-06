package it.fulminazzo.javaparser.parser.node.literals;

import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.parser.node.TokenizedNode;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull String getLiteral() {
        return this.value;
    }

    @Override
    public @NotNull String toString() {
        String clazzName = getClass().getSimpleName();
        // Remove 'Impl' suffix
        return clazzName.substring(0, clazzName.length() - 4) +
                super.toString().substring(clazzName.length());
    }

}
