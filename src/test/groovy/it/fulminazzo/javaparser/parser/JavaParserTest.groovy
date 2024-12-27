package it.fulminazzo.javaparser.parser


import it.fulminazzo.javaparser.parser.node.Assignment
import it.fulminazzo.javaparser.parser.node.MethodCall
import it.fulminazzo.javaparser.parser.node.MethodInvocation
import it.fulminazzo.javaparser.parser.node.arrays.DynamicArray
import it.fulminazzo.javaparser.parser.node.arrays.StaticArray
import it.fulminazzo.javaparser.parser.node.container.CodeBlock
import it.fulminazzo.javaparser.parser.node.operators.binary.*
import it.fulminazzo.javaparser.parser.node.operators.unary.Decrement
import it.fulminazzo.javaparser.parser.node.operators.unary.Increment
import it.fulminazzo.javaparser.parser.node.operators.unary.Minus
import it.fulminazzo.javaparser.parser.node.operators.unary.Not
import it.fulminazzo.javaparser.parser.node.statements.*
import it.fulminazzo.javaparser.parser.node.types.*
import it.fulminazzo.javaparser.tokenizer.TokenType
import spock.lang.Specification

class JavaParserTest extends Specification {
    JavaParser parser

    void setup() {
        this.parser = new JavaParser()
    }

    void startReading() {
        this.parser.tokenizer.nextSpaceless()
    }

    def 'test parseSingleStatement'() {
        given:
        this.parser.setInput(code)

        when:
        startReading()
        def output = this.parser.parseSingleStatement()

        then:
        output == expected

        where:
        code     | expected
        'break;' | new Break()
        ';'      | new Statement()
    }

    def 'test flow control statements'() {
        given:
        this.parser.setInput(code)

        when:
        startReading()
        def output = this.parser.parseSingleStatement()

        then:
        output == expected

        where:
        code | expected
        'if (true) continue; else if (false) break; else return 1;' | new IfStatement(
                new BooleanLiteral('true'), new CodeBlock(new Continue()), new IfStatement(
                new BooleanLiteral('false'), new CodeBlock(new Break()),
                new CodeBlock(new Return(new NumberLiteral('1')))))
        'while (true) continue;' | new WhileStatement(new BooleanLiteral('true'), new CodeBlock(new Continue()))
        'do continue; while (true);' | new DoStatement(new BooleanLiteral('true'), new CodeBlock(new Continue()))
    }

    def 'test for statements'() {
        given:
        this.parser.setInput(code)

        when:
        startReading()
        def output = this.parser.parseSingleStatement()

        then:
        output == expected

        where:
        code | expected
        'for (int i = 0; true; i++) continue;' | new ForStatement(
                new Assignment(new Literal('int'), new Literal('i'), new NumberLiteral('0')),
                new BooleanLiteral('true'),
                new Increment(new Literal('i'), false),
                new CodeBlock(new Continue())
        )
        'for (; true; i++) continue;' | new ForStatement(
                new Statement(),
                new BooleanLiteral('true'),
                new Increment(new Literal('i'), false),
                new CodeBlock(new Continue())
        )
        'for (int i = 0; ; i++) continue;' | new ForStatement(
                new Assignment(new Literal('int'), new Literal('i'), new NumberLiteral('0')),
                new Statement(),
                new Increment(new Literal('i'), false),
                new CodeBlock(new Continue())
        )
        'for (int i = 0; true; ) continue;' | new ForStatement(
                new Assignment(new Literal('int'), new Literal('i'), new NumberLiteral('0')),
                new BooleanLiteral('true'),
                new Statement(),
                new CodeBlock(new Continue())
        )
        'for (; ; i++) continue;' | new ForStatement(
                new Statement(),
                new Statement(),
                new Increment(new Literal('i'), false),
                new CodeBlock(new Continue())
        )
        'for (int i = 0; ; ) continue;' | new ForStatement(
                new Assignment(new Literal('int'), new Literal('i'), new NumberLiteral('0')),
                new Statement(),
                new Statement(),
                new CodeBlock(new Continue())
        )
        'for (int i : arr) continue;' | new EnhancedForStatement(
                new Literal('int'),
                new Literal('i'),
                new Literal('arr'),
                new CodeBlock(new Continue())
        )
    }

    def 'test static array initialization'() {
        given:
        def expected = new StaticArray(new Literal('int'), new NumberLiteral('0'))
        def code = 'new int[0]'
        this.parser.setInput(code)

        when:
        startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test dynamic array initialization'() {
        given:
        def expected = new DynamicArray(new Literal('int'), [
                new NumberLiteral('1')
        ])
        def code = 'new int[]{1}'
        this.parser.setInput(code)

        when:
        startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test method call'() {
        given:
        def expected = new MethodCall(
                new Literal('var'),
                new MethodInvocation([
                        new Literal('a'),
                        new NumberLiteral('1'),
                        new BooleanLiteral('true')
                ])
        )
        def code = 'var(a, 1, true)'
        this.parser.setInput(code)

        when:
        startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test increment or decrement'() {
        given:
        this.parser.setInput(code)

        when:
        startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected

        where:
        code    | expected
        'var++' | new Increment(new Literal('var'), false)
        '++var' | new Increment(new Literal('var'), true)
        'var--' | new Decrement(new Literal('var'), false)
        '--var' | new Decrement(new Literal('var'), true)
    }

    def 'test parseReAssign with operation: #code'() {
        given:
        def expected = new Literal('var')
        def operation = expectedClass.newInstance(expected, new NumberLiteral('2'))
        expected = new ReAssign(expected, operation)
        this.parser.setInput(code)

        when:
        startReading()
        def output = this.parser.parseAssignment()

        then:
        output == expected

        where:
        code         | expectedClass
        'var += 2'   | Add.class
        'var -= 2'   | Subtract.class
        'var *= 2'   | Multiply.class
        'var /= 2'   | Divide.class
        'var %= 2'   | Modulo.class
        'var &= 2'   | BitAnd.class
        'var |= 2'   | BitOr.class
        'var ^= 2'   | BitXor.class
        'var <<= 2'  | LShift.class
        'var >>= 2'  | RShift.class
        'var >>>= 2' | URShift.class
    }

    def 'test complex parseBinaryOperation'() {
        given:
        def code = '18 % 17 / 16 * 15 - 14 + 13 ' +
                '>>> 12 >> 11 << 10 ' +
                '^ 9 | 8 & 7 || 6 && 5 ' +
                '>= 4 > 3 <= 2 < 1 ' +
                '!= false == true'
        def expected = new Modulo(new NumberLiteral('18'), new NumberLiteral('17'))
        expected = new Divide(expected, new NumberLiteral('16'))
        expected = new Multiply(expected, new NumberLiteral('15'))
        expected = new Subtract(expected, new NumberLiteral('14'))
        expected = new Add(expected, new NumberLiteral('13'))
        expected = new URShift(expected, new NumberLiteral('12'))
        expected = new RShift(expected, new NumberLiteral('11'))
        expected = new LShift(expected, new NumberLiteral('10'))
        expected = new BitXor(expected, new NumberLiteral('9'))
        expected = new BitOr(expected, new NumberLiteral('8'))
        expected = new BitAnd(expected, new NumberLiteral('7'))
        expected = new Or(expected, new NumberLiteral('6'))
        expected = new And(expected, new NumberLiteral('5'))
        expected = new GreaterThanEqual(expected, new NumberLiteral('4'))
        expected = new GreaterThan(expected, new NumberLiteral('3'))
        expected = new LessThanEqual(expected, new NumberLiteral('2'))
        expected = new LessThan(expected, new NumberLiteral('1'))
        expected = new NotEqual(expected, new BooleanLiteral('false'))
        expected = new Equal(expected, new BooleanLiteral('true'))
        this.parser.setInput(code)

        when:
        startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test simple parseBinaryOperation'() {
        given:
        def code = '1 + 2'
        def expected = new Add(new NumberLiteral('1'), new NumberLiteral('2'))
        this.parser.setInput(code)

        when:
        startReading()
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test parseAtom: #text'() {
        given:
        this.parser.setInput(text)

        when:
        startReading()
        def parsed = this.parser.parseAtom()

        then:
        parsed == expected

        where:
        text    | expected
        '-1'    | new Minus(new NumberLiteral('1'))
        '!true' | new Not(new BooleanLiteral('true'))
        'false' | new BooleanLiteral('false')
        'int'   | new Literal('int')
    }

    def 'test minus'() {
        given:
        def expected = new Minus(new NumberLiteral('1'))
        this.parser.setInput('-1')

        when:
        startReading()
        def parsed = this.parser.parseMinus()

        then:
        parsed == expected
    }

    def 'test not'() {
        given:
        def expected = new Not(new BooleanLiteral('true'))
        this.parser.setInput('!true')

        when:
        startReading()
        def parsed = this.parser.parseNot()

        then:
        parsed == expected
    }

    def 'test parse type value of literal #literal'() {
        given:
        this.parser.setInput(literal)

        when:
        startReading()
        def parsed = this.parser.parseTypeValue()

        then:
        parsed == expected

        where:
        literal           | expected
        '1'               | new NumberLiteral('1')
        '1L'              | new LongLiteral('1L')
        '1D'              | new DoubleLiteral('1D')
        '1F'              | new FloatLiteral('1F')
        'true'            | new BooleanLiteral('true')
        'false'           | new BooleanLiteral('false')
        '\'a\''           | new CharLiteral('\'a\'')
        '\"Hello world\"' | new StringLiteral('\"Hello world\"')
    }

    def 'test parse type of invalid'() {
        given:
        this.parser.setInput('invalid')
        startReading()

        when:
        this.parser.parseTypeValue()

        then:
        def e = thrown(ParserException)
        e.message == "Unexpected token: ${TokenType.LITERAL}"
    }

}