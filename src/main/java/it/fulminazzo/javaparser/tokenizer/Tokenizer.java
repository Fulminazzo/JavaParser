package it.fulminazzo.javaparser.tokenizer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * A converter from raw {@link InputStream} to {@link TokenType}.
 */
public class Tokenizer implements Iterable<TokenType>, Iterator<TokenType> {
    private final @NotNull InputStream input;
    private @NotNull TokenType lastToken = TokenType.EOF;
    private @NotNull String lastRead = "";
    private String previousRead = "";

    /**
     * Instantiates a new Tokenizer.
     *
     * @param input the input stream
     */
    public Tokenizer(final @NotNull InputStream input) {
        this.input = input;
    }

    /**
     * Verifies that input still has available data.
     *
     * @return true if it does
     */
    @Override
    public boolean hasNext() {
        try {
            return this.input.available() > 0 || this.lastToken != TokenType.EOF;
        } catch (IOException e) {
            throw new TokenizerException(e);
        }
    }

    /**
     * Reads from the input the next {@link TokenType}.
     * Repeats readings until a token different from {@link TokenType#SPACE} is found.
     *
     * @return the token type
     */
    public @NotNull TokenType nextSpaceless() {
        do {
            next();
        } while (this.lastToken == TokenType.SPACE);
        return this.lastToken;
    }

    /**
     * Reads from the input the next {@link TokenType}.
     *
     * @return the token type
     */
    @Override
    public @NotNull TokenType next() {
        try {
            String read = this.previousRead;
            if (isTokenType(read)) return readTokenType(read);
            while (this.input.available() > 0) {
                read += (char) this.input.read();
                if (isTokenType(read)) return readTokenType(read);
            }
            return eof();
        } catch (IOException e) {
            throw new TokenizerException(e);
        }
    }

    private @NotNull TokenType readTokenType(@NotNull String read) throws IOException {
        while (this.input.available() > 0) {
            char c = (char) this.input.read();
            read += c;
            if (!isTokenType(read)) {
                String subString = read.substring(0, read.length() - 1);
                // Line necessary to properly read DOUBLE_VALUE and FLOAT_VALUE
                if (TokenType.fromString(subString) == TokenType.NUMBER_VALUE && c == '.')
                    continue;
                this.previousRead = read.substring(read.length() - 1);
                return updateTokenType(subString);
            }
        }
        return updateTokenType(read);
    }

    private @NotNull TokenType updateTokenType(final @NotNull String read) {
        this.lastRead = read;
        this.lastToken = TokenType.fromString(read);
        return this.lastToken;
    }

    private @NotNull TokenType eof() {
        this.lastRead = "";
        this.lastToken = TokenType.EOF;
        return this.lastToken;
    }

    @Override
    public @NotNull Iterator<TokenType> iterator() {
        return this;
    }

    private boolean isTokenType(final @NotNull String read) {
        try {
            return TokenType.fromString(read) != TokenType.EOF;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Gets the last token type.
     *
     * @return the token type
     */
    public @NotNull TokenType lastToken() {
        return this.lastToken;
    }

    /**
     * Gets the last read string.
     *
     * @return the string
     */
    public @NotNull String lastRead() {
        return this.lastRead;
    }

}
