package it.fulminazzo.javaparser.parser

import it.fulminazzo.javaparser.parser.node.operators.binary.*
import it.fulminazzo.javaparser.parser.node.types.BooleanLiteral
import it.fulminazzo.javaparser.parser.node.types.NumberLiteral
import spock.lang.Specification

class JavaParserTest extends Specification {

    def "test simple parseBinaryOperation"() {
        given:
        def code = "1 + 2"
        def expected = new Add(new NumberLiteral("1"), new NumberLiteral("2"))
        def parser = new JavaParser()
        parser.setInput(code)

        when:
        def output = parser.parseExpression()

        then:
        output == expected
    }

    def "test parseBinaryOperation"() {
        given:
        def code = "18 % 17 / 16 * 15 - 14 + 13 " +
                ">>> 12 >> 11 << 10 " +
                "^ 9 | 8 & 7 || 6 && 5 " +
                ">= 4 > 3 <= 2 < 1 " +
                "!= false == true"
        def expected = new Modulo(new NumberLiteral("18"), new NumberLiteral("17"))
        expected = new Divide(expected, new NumberLiteral("16"))
        expected = new Multiply(expected, new NumberLiteral("15"))
        expected = new Subtract(expected, new NumberLiteral("14"))
        expected = new Add(expected, new NumberLiteral("13"))
        expected = new URShift(expected, new NumberLiteral("12"))
        expected = new RShift(expected, new NumberLiteral("11"))
        expected = new LShift(expected, new NumberLiteral("10"))
        expected = new BitXor(expected, new NumberLiteral("9"))
        expected = new BitOr(expected, new NumberLiteral("8"))
        expected = new BitAnd(expected, new NumberLiteral("7"))
        expected = new Or(expected, new NumberLiteral("6"))
        expected = new And(expected, new NumberLiteral("5"))
        expected = new GreaterThanEqual(expected, new NumberLiteral("4"))
        expected = new GreaterThan(expected, new NumberLiteral("3"))
        expected = new LessThanEqual(expected, new NumberLiteral("2"))
        expected = new LessThanEqual(expected, new NumberLiteral("1"))
        expected = new NotEqual(expected, new BooleanLiteral("false"))
        expected = new Equal(expected, new BooleanLiteral("true"))
        def parser = new JavaParser()
        parser.setInput(code)

        when:
        def output = parser.parseExpression()

        then:
        output == expected
    }

}