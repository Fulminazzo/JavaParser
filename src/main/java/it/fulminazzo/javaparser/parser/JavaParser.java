package it.fulminazzo.javaparser.parser;

import it.fulminazzo.javaparser.parser.node.BaseValue;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.operators.unary.Minus;
import it.fulminazzo.javaparser.parser.node.operators.unary.Not;
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
     * ATOM := MINUS | NOT | TYPE_VALUE
     *
     * @return the node
     */
    protected @NotNull Node parseAtom() {
        switch (lastToken()) {
            case MINUS: return parseMinus();
            case NOT: return parseNot();
            default: return parseTypeValue();
        }
    }

    /**
     * MINUS := - EXPR
     *
     * @return the node
     */
    protected @NotNull Node parseMinus() {
        consume(MINUS);
        return new Minus(parseExpression());
    }

    /**
     * NOT := ! EXPR
     *
     * @return the node
     */
    protected @NotNull Node parseNot() {
        consume(NOT);
        return new Not(parseExpression());
    }

    /**
     * TYPE_VALUE := {@link TokenType#NUMBER_VALUE} | {@link TokenType#LONG_VALUE} |
     *               {@link TokenType#DOUBLE_VALUE} | {@link TokenType#FLOAT_VALUE} |
     *               {@link TokenType#BOOLEAN_VALUE} | {@link TokenType#CHAR_VALUE} |
     *               {@link TokenType#STRING_VALUE}
     *
     * @return the node
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
