package it.fulminazzo.javaparser.parser;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Exception used to notify about errors coming from {@link JavaParser}.
 */
public class ParserException extends RuntimeException {

    /**
     * Instantiates a new Parser exception.
     *
     * @param message the message of the exception
     */
    public ParserException(final @NotNull String message) {
        super(message);
    }

    /**
     * Instantiates a new Parser exception with message "<i>Unexpected token %tokenType%</i>".
     *
     * @param tokenType the token type
     */
    public ParserException(final @NotNull TokenType tokenType) {
        this("Unexpected token: "  + tokenType);
    }

}
