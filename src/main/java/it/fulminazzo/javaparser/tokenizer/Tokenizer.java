package it.fulminazzo.javaparser.tokenizer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class Tokenizer implements Iterable<TokenType>, Iterator<TokenType> {
    private final @NotNull InputStream input;
    private @NotNull TokenType lastToken = TokenType.EOF;
    private @NotNull String lastRead = "";
    private String previousRead = "";

    public Tokenizer(final @NotNull InputStream input) {
        this.input = input;
    }

    @Override
    public boolean hasNext() {
        try {
            return this.input.available() > 0;
        } catch (IOException e) {
            //TODO:
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull TokenType next() {
        try {
            String read = this.previousRead;
            if (isTokenType(read)) return readTokenType(read);
            while (this.input.available() > 0) {
                read += (char) this.input.read();
                if (isTokenType(read)) return readTokenType(read);
            }
            return TokenType.EOF;
        } catch (IOException e) {
            //TODO:
            throw new RuntimeException(e);
        }
    }

    private @NotNull TokenType readTokenType(@NotNull String read) throws IOException {
        while (this.input.available() > 0) {
            read += (char) this.input.read();
            if (!isTokenType(read)) {
                this.previousRead = read.substring(read.length() - 1);
                return updateTokenType(read.substring(0, read.length() - 1));
            }
        }
        return updateTokenType(read);
    }

    private @NotNull TokenType updateTokenType(final @NotNull String read) {
        this.lastRead = read;
        this.lastToken = TokenType.fromString(read);
        return this.lastToken;
    }

    @Override
    public @NotNull Iterator<TokenType> iterator() {
        return this;
    }

    private boolean isTokenType(final @NotNull String read) {
        try {
            TokenType.fromString(read);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public @NotNull TokenType lastToken() {
        return this.lastToken;
    }

    public @NotNull String lastRead() {
        return this.lastRead;
    }

}
