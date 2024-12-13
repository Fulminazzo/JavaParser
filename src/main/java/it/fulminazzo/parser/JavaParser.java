package it.fulminazzo.parser;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.tokenizer.Tokenizer;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * A parser to read Java code using {@link Tokenizer} and {@link TokenType}.
 */
@NoArgsConstructor
public class JavaParser {
    private @Nullable Tokenizer tokenizer;

    /**
     * Instantiates a new Java parser.
     *
     * @param input the input
     */
    public JavaParser(final @NotNull InputStream input) {
        setInput(input);
    }

    /**
     * Returns the next {@link TokenType} from the {@link Tokenizer}.
     * Uses {@link #getTokenizer()}.
     *
     * @return the token type
     */
    protected @NotNull TokenType next() {
        return getTokenizer().next();
    }

    /**
     * Returns the last read {@link TokenType} from the {@link Tokenizer}.
     * Uses {@link #getTokenizer()}.
     *
     * @return the token type
     */
    protected @NotNull TokenType lastToken() {
        return getTokenizer().lastToken();
    }

    /**
     * Verifies that the given {@link TokenType} matches with the {@link #lastToken()} read.
     * If not, throws a {@link ParserException}.
     *
     * @param tokenType the expected token type
     */
    protected void match(final @NotNull TokenType tokenType) {
        TokenType lastToken = lastToken();
        if (lastToken != tokenType)
            throw new ParserException("Expected token " + tokenType + " but found " + lastToken);
    }

    /**
     * Wrapper for {@link #match(TokenType)} and {@link #next()} combined execution.
     *
     * @param tokenType the expected token type
     * @return the newly read token type
     */
    protected @NotNull TokenType consume(final @NotNull TokenType tokenType) {
        match(tokenType);
        return next();
    }

    /**
     * Sets the input code.
     *
     * @param input the input
     */
    public void setInput(final @NotNull String input) {
        setInput(new ByteArrayInputStream(input.getBytes()));
    }

    /**
     * Sets the input code.
     *
     * @param input the input
     */
    public void setInput(final @NotNull InputStream input) {
        this.tokenizer = new Tokenizer(input);
    }

    /**
     * Gets the current tokenizer.
     * If none was set, a {@link ParserException} will be thrown.
     *
     * @return the tokenizer
     */
    protected @NotNull Tokenizer getTokenizer() {
        if (this.tokenizer == null)
            throw new ParserException("No input was provided!");
        return this.tokenizer;
    }

}
