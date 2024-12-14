package it.fulminazzo.javaparser.parser


import it.fulminazzo.javaparser.parser.node.MethodCall
import it.fulminazzo.javaparser.parser.node.MethodInvocation
import it.fulminazzo.javaparser.parser.node.arrays.DynamicArray
import it.fulminazzo.javaparser.parser.node.arrays.StaticArray
import it.fulminazzo.javaparser.parser.node.container.CodeBlock
import it.fulminazzo.javaparser.parser.node.operators.binary.*
import it.fulminazzo.javaparser.parser.node.operators.unary.Decrement
import it.fulminazzo.javaparser.parser.node.operators.unary.Increment
import it.fulminazzo.javaparser.parser.node.statements.Break
import it.fulminazzo.javaparser.parser.node.statements.Continue
import it.fulminazzo.javaparser.parser.node.statements.IfStatement
import it.fulminazzo.javaparser.parser.node.statements.Return
import it.fulminazzo.javaparser.parser.node.statements.Statement
import it.fulminazzo.javaparser.parser.node.statements.WhileStatement
import it.fulminazzo.javaparser.parser.node.types.BooleanLiteral
import it.fulminazzo.javaparser.parser.node.types.IntType
import it.fulminazzo.javaparser.parser.node.types.Literal
import it.fulminazzo.javaparser.parser.node.types.NumberLiteral
import spock.lang.Specification

class JavaParserTest extends Specification {
    JavaParser parser

    void setup() {
        this.parser = new JavaParser()
    }

    def "test simple parseBinaryOperation"() {
        given:
        def code = "1 + 2"
        def expected = new Add(new NumberLiteral("1"), new NumberLiteral("2"))
        this.parser.setInput(code)

        when:
        this.parser.startReading()
        def output = this.parser.parseExpression()

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
        expected = new LessThan(expected, new NumberLiteral("1"))
        expected = new NotEqual(expected, new BooleanLiteral("false"))
        expected = new Equal(expected, new BooleanLiteral("true"))
        this.parser.setInput(code)

        when:
        this.parser.startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def "test parseReAssign with operation"() {
        given:
        def code = "var += 2"
        def expected = new Literal("var")
        expected = new ReAssign(expected, new Add(expected, new NumberLiteral("2")))
        this.parser.setInput(code)

        when:
        this.parser.startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def "test increment or decrement"() {
        given:
        this.parser.setInput(code)

        when:
        this.parser.startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected

        where:
        code    | expected
        "var++" | new Increment(new Literal("var"), false)
        "++var" | new Increment(new Literal("var"), true)
        "var--" | new Decrement(new Literal("var"), false)
        "--var" | new Decrement(new Literal("var"), true)
    }

    def "test method call"() {
        given:
        def expected = new MethodCall(
                new Literal("var"),
                new MethodInvocation([
                        new Literal("a"),
                        new NumberLiteral("1"),
                        new BooleanLiteral("true")
                ])
        )
        def code = "var(a, 1, true)"
        this.parser.setInput(code)

        when:
        this.parser.startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def "test static array initialization"() {
        given:
        def expected = new StaticArray(new IntType(), new NumberLiteral("0"))
        def code = "new int[0]"
        this.parser.setInput(code)

        when:
        this.parser.startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def "test dynamic array initialization"() {
        given:
        def expected = new DynamicArray(new IntType(), [
                new NumberLiteral("1")
        ])
        def code = "new int[]{1}"
        this.parser.setInput(code)

        when:
        this.parser.startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def "test parseSingleStatement"() {
        given:
        this.parser.setInput(code)

        when:
        this.parser.startReading()
        def output = this.parser.parseSingleStatement()

        then:
        output == expected

        where:
        code     | expected
        "break;" | new Break()
        ";"      | new Statement()
    }

    def "test if statement"() {
        given:
        def code = "if (true) continue; else if (false) break; else return 1;"
        this.parser.setInput(code)
        def expected = new IfStatement(
                new BooleanLiteral("false"), new CodeBlock(new Break()),
                new CodeBlock(new Return(new NumberLiteral("1")))
        )
        expected = new IfStatement(
                new BooleanLiteral("true"), new CodeBlock(new Continue()), expected
        )

        when:
        this.parser.startReading()
        def output = this.parser.parseSingleStatement()

        then:
        output == expected
    }

    def "test while statement"() {
        given:
        def code = "while (true) continue;"
        this.parser.setInput(code)
        def expected = new WhileStatement(new BooleanLiteral("true"), new CodeBlock(new Continue()))

        when:
        this.parser.startReading()
        def output = this.parser.parseSingleStatement()

        then:
        output == expected
    }

}