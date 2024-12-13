package it.fulminazzo.javaparser.parser;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.tokenizer.Tokenizer;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * A general parser with an internal {@link Tokenizer}.
 */
@NoArgsConstructor
abstract class Parser {
    private @Nullable Tokenizer tokenizer;

    /**
     * Instantiates a new Parser.
     *
     * @param input the input
     */
    public Parser(final @NotNull InputStream input) {
        setInput(input);
    }

    /**
     * Returns the next {@link TokenType} from the {@link Tokenizer}.
     * Repeats the reading until {@link TokenType} is not {@link TokenType#SPACE}.
     * Uses {@link #getTokenizer()}.
     *
     * @return the token type
     */
    protected @NotNull TokenType nextSpaceless() {
        return getTokenizer().nextSpaceless();
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
     * Wrapper for {@link #match(TokenType)} and {@link #nextSpaceless()} combined execution.
     *
     * @param tokenType the expected token type
     * @return the newly read token type
     */
    protected @NotNull TokenType consume(final @NotNull TokenType tokenType) {
        match(tokenType);
        return nextSpaceless();
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
