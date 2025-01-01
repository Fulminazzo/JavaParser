package it.fulminazzo.javaparser.parser;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Exception used to notify about errors coming from {@link JavaParser}.
 */
final class ParserException extends RuntimeException {

    /**
     * Instantiates a new Parser exception.
     *
     * @param parser  the parser that generated the exception
     * @param message the message of the exception
     * @param args    the arguments to format
     */
    private ParserException(final @Nullable Parser parser, final @NotNull String message,
                            final Object @NotNull ... args) {
        super(String.format("At line %s, column %s: %s",
                parser == null ? "0" : parser.getTokenizer().line(),
                parser == null ? "0" : parser.getTokenizer().column(),
                String.format(message, args))
        );
    }

    /**
     * Generates a {@link ParserException} with message:
     * <i>Unexpected token: %token%</i>
     *
     * @param token  the token
     * @param parser the parser
     * @return the parser exception
     */
    public static @NotNull ParserException unexpectedToken(final @NotNull TokenType token,
                                                           final @NotNull Parser parser) {
        return new ParserException(parser, "Unexpected token: %s", token);
    }

}
