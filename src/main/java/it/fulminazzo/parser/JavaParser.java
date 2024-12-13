package it.fulminazzo.parser;

import it.fulminazzo.javaparser.tokenizer.Tokenizer;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static it.fulminazzo.javaparser.tokenizer.TokenType.*;

/**
 * A parser to read Java code using {@link Tokenizer} and {@link it.fulminazzo.javaparser.tokenizer.TokenType}.
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
