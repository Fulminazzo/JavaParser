package it.fulminazzo.javaparser.tokenizer;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A converter from raw {@link InputStream} to {@link TokenType}.
 */
public class Tokenizer implements Iterable<TokenType>, Iterator<TokenType> {
    private final @NotNull InputStream input;
    private @NotNull TokenType lastToken = TokenType.EOF;
    private @NotNull String lastRead = "";
    private String previousRead = "";
    private int line = -1;
    private int column = -1;

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
     * Reads until the character <code>\n</code> is met.
     * Then, it invokes {@link #nextSpaceless()}.
     *
     * @return the newly read token type.
     */
    public @NotNull TokenType readUntilNextLine() {
        return readUntil(TokenType.NEW_LINE);
    }

    /**
     * Reads until the given {@link TokenType} is met.
     * Then, it invokes {@link #nextSpaceless()}.
     *
     * @return the newly read token type
     */
    public @NotNull TokenType readUntil(final @NotNull TokenType tokenType) {
        readUntilInclusive(tokenType);
        return nextSpaceless();
    }

    /**
     * Reads until the given {@link TokenType} is met.
     */
    public void readUntilInclusive(final @NotNull TokenType tokenType) {
        try {
            final @NotNull String regex = "((?:.|\n)*)(" + tokenType.regex() + ")$";
            String read = getPreviousRead();
            while (this.input.available() > 0 && !read.matches(regex))
                read += updateLineCount(this.input.read());
            Matcher matcher = Pattern.compile(regex).matcher(read);
            if (matcher.matches()) {
                this.lastRead = matcher.group(1);
                this.previousRead = matcher.group(2);
            } else {
                this.lastRead = read;
                this.previousRead = "";
            }
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
            if (this.line == -1) this.line = 1;
            if (this.column == -1) this.column = 0;
            String read = getPreviousRead();
            if (isTokenType(read)) return readTokenType(read);
            while (this.input.available() > 0) {
                read += updateLineCount(this.input.read());
                if (isTokenType(read)) return readTokenType(read);
            }
            return eof();
        } catch (IOException e) {
            throw new TokenizerException(e);
        }
    }

    private @NotNull TokenType readTokenType(@NotNull String read) throws IOException {
        while (this.input.available() > 0) {
            int previousLine = this.line;
            int previousColumn = this.column;
            char c = updateLineCount(this.input.read());
            read += c;
            if (!isTokenType(read)) {
                String subString = read.substring(0, read.length() - 1);
                // Line necessary to properly read LITERAL, DOUBLE_VALUE and FLOAT_VALUE
                if (c == '.') {
                    TokenType previous = TokenType.fromString(subString);
                    if (previous == TokenType.NUMBER_VALUE || previous == TokenType.LITERAL)
                        continue;
                }
                this.line = previousLine;
                this.column = previousColumn;
                this.previousRead = read.substring(read.length() - 1);
                return updateTokenType(subString);
            }
        }
        this.previousRead = "";
        return updateTokenType(read);
    }

    private String getPreviousRead() {
        for (char c : this.previousRead.toCharArray()) updateLineCount(c);
        return this.previousRead;
    }

    private char updateLineCount(int c) {
        if (c == '\n') {
            this.line++;
            this.column = 0;
        } else this.column++;
        return (char) c;
    }

    private @NotNull TokenType updateTokenType(final @NotNull String read) {
        this.lastRead = read;
        this.lastToken = TokenType.fromString(read);
        return this.lastToken;
    }

    private @NotNull TokenType eof() {
        this.lastRead = "";
        this.lastToken = TokenType.EOF;
        this.line = -1;
        this.column = -1;
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

    /**
     * Gets the last line read.
     * <code>-1</code> in case of {@link TokenType#EOF}.
     *
     * @return the line
     */
    public int line() {
        return this.line;
    }

    /**
     * Gets the last column read
     * <code>-1</code> in case of {@link TokenType#EOF}.
     *
     * @return the column
     */
    public int column() {
        return this.column;
    }

}
