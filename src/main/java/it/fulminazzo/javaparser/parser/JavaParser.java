package it.fulminazzo.javaparser.parser;

import it.fulminazzo.javaparser.parser.node.BaseValue;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.tokenizer.Tokenizer;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

import static it.fulminazzo.javaparser.tokenizer.TokenType.*;

/**
 * A parser to read Java code using {@link Tokenizer} and {@link TokenType}.
 */
@NoArgsConstructor
public class JavaParser extends Parser {

    /**
     * Instantiates a new Java parser.
     *
     * @param input the input
     */
    public JavaParser(@NotNull InputStream input) {
        super(input);
    }

    /**
     * TYPE_VALUE := NUMBER_VALUE | LONG_VALUE | DOUBLE_VALUE | FLOAT_VALUE |
     *               BOOLEAN_VALUE | CHARACTER_VALUE | STRING_VALUE
     *
     * @return the base value
     */
    protected @NotNull BaseValue parseTypeValue() {
        switch (lastToken()) {
            case NUMBER_VALUE:
            case LONG_VALUE:
            case DOUBLE_VALUE:
            case FLOAT_VALUE:
            case BOOLEAN_VALUE:
            case CHAR_VALUE:
            case STRING_VALUE: {
                BaseValue node = new BaseValue(getTokenizer().lastRead());
                nextSpaceless();
                return node;
            }
            default:
                throw new ParserException("Unexpected token: " + lastToken());
        }
    }

    /**
     * LITERAL := ([a-zA-Z_][a-zA-Z0-9_.]*)
     *
     * @return the string
     */
    protected @NotNull String parseLiteral() {
        final String literal = getTokenizer().lastRead();
        consume(LITERAL);
        return literal;
    }

    /**
     * LITERAL_NO_DOT := ([a-zA-Z_][a-zA-Z0-9_]*)
     *
     * @return the string
     */
    protected @NotNull String parseLiteralNoDot() {
        final String literalNoDot = getTokenizer().lastRead();
        consume(LITERAL_NO_DOT);
        return literalNoDot;
    }

}
