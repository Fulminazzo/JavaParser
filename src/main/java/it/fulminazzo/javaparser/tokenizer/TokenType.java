package it.fulminazzo.javaparser.tokenizer;

import org.jetbrains.annotations.NotNull;

/**
 * Contains all the tokens utilized by {@link it.fulminazzo.javaparser.parser.JavaParser}.
 */
public enum TokenType {

    // General
    SPACE("[\r\t\n ]"),
    EOF("")
    ;

    private final @NotNull String regex;

    TokenType(final @NotNull String regex) {
        this.regex = regex;
    }

    /**
     * Verifies if the given token matches with the whole regex.
     *
     * @param token the token
     * @return true if it matches
     */
    public boolean matches(final @NotNull String token) {
        return token.matches(this.regex);
    }

    /**
     * Gets the corresponding {@link TokenType} from the given token.
     * If none is found, an {@link IllegalArgumentException} is thrown.
     *
     * @param token the token
     * @return the token type
     */
    public static @NotNull TokenType fromString(final @NotNull String token) {
        for (final TokenType tokenType : TokenType.values())
            if (tokenType.matches(token)) return tokenType;
        throw new IllegalArgumentException("Unknown token type: " + token);
    }

}
