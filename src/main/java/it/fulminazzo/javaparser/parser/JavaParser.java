package it.fulminazzo.javaparser.parser;

import it.fulminazzo.javaparser.parser.node.types.*;
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
     * TYPE_VALUE := {@link TokenType#NUMBER_VALUE} | {@link TokenType#LONG_VALUE} |
     *               {@link TokenType#DOUBLE_VALUE} | {@link TokenType#FLOAT_VALUE} |
     *               {@link TokenType#BOOLEAN_VALUE} | {@link TokenType#CHAR_VALUE} |
     *               {@link TokenType#STRING_VALUE}
     *
     * @return the node
     */
    protected @NotNull BaseTypeLiteral parseTypeValue() {
        final String read = getTokenizer().lastRead();
        final BaseTypeLiteral literal;
        switch (lastToken()) {
            case NUMBER_VALUE: {
                literal = new NumberLiteral(read);
                break;
            }
            case LONG_VALUE: {
                literal = new LongLiteral(read);
                break;
            }
            case DOUBLE_VALUE: {
                literal = new DoubleLiteral(read);
                break;
            }
            case FLOAT_VALUE: {
                literal = new FloatLiteral(read);
                break;
            }
            case BOOLEAN_VALUE: {
                literal = new BooleanLiteral(read);
                break;
            }
            case CHAR_VALUE: {
                literal = new CharLiteral(read);
                break;
            }
            case STRING_VALUE: {
                literal = new StringLiteral(read);
                break;
            }
            default:
                throw new ParserException("Unexpected token: " + lastToken());
        }
        nextSpaceless();
        return literal;
    }

    /**
     * LITERAL := {@link TokenType#LITERAL}
     *
     * @return the node
     */
    protected @NotNull String parseLiteral() {
        final String literal = getTokenizer().lastRead();
        consume(LITERAL);
        return literal;
    }

    /**
     * LITERAL_NO_DOT := {@link TokenType#LITERAL_NO_DOT}
     *
     * @return the node
     */
    protected @NotNull String parseLiteralNoDot() {
        final String literalNoDot = getTokenizer().lastRead();
        consume(LITERAL_NO_DOT);
        return literalNoDot;
    }

}
