package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Represents the default conversion for {@link it.fulminazzo.javaparser.tokenizer.TokenType#LITERAL}.
 */
public class LiteralType extends ObjectWrapper<String> implements Type {

    /**
     * Instantiates a new Literal type.
     *
     * @param literal the literal
     */
    public LiteralType(final @NotNull String literal) {
        super(Objects.requireNonNull(literal, "Expected literal to be not null"));
    }

    /**
     * Gets the inner literal.
     *
     * @return the literal
     */
    public @NotNull String getLiteral() {
        return this.object;
    }

    @Override
    public @NotNull ClassType toClass() {
        throw TypeCheckerException.noClassType(getClass());
    }

}
