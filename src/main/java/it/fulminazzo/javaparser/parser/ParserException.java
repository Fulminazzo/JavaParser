package it.fulminazzo.javaparser.parser;

import it.fulminazzo.javaparser.parser.node.container.CaseBlock;
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
     * @param parser the parser
     * @param token  the token
     * @return the parser exception
     */
    public static @NotNull ParserException unexpectedToken(final @NotNull Parser parser,
                                                           final @NotNull TokenType token) {
        return new ParserException(parser, "Unexpected token: %s", token);
    }

    /**
     * Generates a {@link ParserException} with message:
     * <i>Invalid value '%value%' provided for value type '%parser#lastToken%'</i>
     *
     * @param parser the parser
     * @param value  the value
     * @return the parser exception
     */
    public static @NotNull ParserException invalidValueProvidedException(final @NotNull Parser parser,
                                                                         final @NotNull String value) {
        return new ParserException(parser, "Invalid value '%s' provided for value type %s", value, parser.lastToken().name());
    }

    /**
     * Generates a {@link ParserException} with message:
     * <i>Case block with expression '%expression%' already defined</i>
     *
     * @param parser    the parser
     * @param caseBlock the case block
     * @return the parser exception
     */
    public static @NotNull ParserException caseBlockAlreadyDefined(final @NotNull Parser parser,
                                                                   final @NotNull CaseBlock caseBlock) {
        return new ParserException(parser, "Case block with expression '%s' already defined", caseBlock.getExpression());
    }

}
