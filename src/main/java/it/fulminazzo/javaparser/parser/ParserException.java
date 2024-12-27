package it.fulminazzo.javaparser.parser;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * Exception used to notify about errors coming from {@link JavaParser}.
 */
final class ParserException extends RuntimeException {

    /**
     * Instantiates a new Parser exception.
     *
     * @param message the message of the exception
     * @param parser  the parser that generated the exception
     */
    public ParserException(final @NotNull String message,
                           final @NotNull Parser parser) {
        super(String.format("At line %s, column %s: %s",
                parser.getTokenizer().line(),
                parser.getTokenizer().column(),
                message)
        );
    }

    /**
     * Instantiates a new Parser exception with message "<i>Unexpected token %tokenType%</i>".
     *
     * @param tokenType the token type
     * @param parser  the parser that generated the exception
     */
    public ParserException(final @NotNull TokenType tokenType,
                           final @NotNull Parser parser) {
        this("Unexpected token: "  + tokenType, parser);
    }

}
