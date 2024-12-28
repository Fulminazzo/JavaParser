package it.fulminazzo.javaparser.parser

import it.fulminazzo.javaparser.parser.node.Assignment
import it.fulminazzo.javaparser.parser.node.NodeException
import it.fulminazzo.javaparser.parser.node.MethodCall
import it.fulminazzo.javaparser.parser.node.MethodInvocation
import it.fulminazzo.javaparser.parser.node.arrays.DynamicArray
import it.fulminazzo.javaparser.parser.node.arrays.StaticArray
import it.fulminazzo.javaparser.parser.node.container.CodeBlock
import it.fulminazzo.javaparser.parser.node.literals.ArrayLiteral
import it.fulminazzo.javaparser.parser.node.literals.EmptyLiteral
import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.operators.binary.*
import it.fulminazzo.javaparser.parser.node.operators.unary.Decrement
import it.fulminazzo.javaparser.parser.node.operators.unary.Increment
import it.fulminazzo.javaparser.parser.node.operators.unary.Minus
import it.fulminazzo.javaparser.parser.node.operators.unary.Not
import it.fulminazzo.javaparser.parser.node.statements.*
import it.fulminazzo.javaparser.parser.node.values.*
import it.fulminazzo.javaparser.tokenizer.TokenType
import org.jetbrains.annotations.NotNull
import spock.lang.Specification

class JavaParserTest extends Specification {
    JavaParser parser

    void setup() {
        this.parser = new JavaParser()
    }

    void startReading(final String code) {
        this.parser.setInput(code)
        this.parser.tokenizer.nextSpaceless()
    }

    def 'parse test_program file'() {
        given:
        def file = new File(System.getProperty('user.dir'), 'build/resources/test/test_program.java')
        def parser = new JavaParser(file.newInputStream())

        when:
        parser.parseProgram()

        then:
        noExceptionThrown()
    }

    def 'test parseBlock: #code'() {
        when:
        startReading(code)
        def output = this.parser.parseBlock()

        then:
        output == expected

        where:
        code              | expected
        '{\ncontinue;\n}' | new CodeBlock(new Continue())
        'continue;'       | new CodeBlock(new Continue())
    }

    def 'test parseSingleStatement: #code'() {
        when:
        startReading(code)
        def output = this.parser.parseSingleStatement()

        then:
        output == expected

        where:
        code        | expected
        'return 1;' | new Return(new NumberValueLiteral('1'))
        'return 1'  | new Return(new NumberValueLiteral('1'))
        'break;'    | new Break()
        ';'         | new Statement()
    }

    def 'test parse comments'() {
        given:
        def code = "${comment}return 1;"

        when:
        startReading(code)
        def output = this.parser.parseSingleStatement()

        then:
        output == new Return(new NumberValueLiteral('1'))

        where:
        comment << [
                '//First line\n',
                '//First line\n//Second line\n//Third line\n',
                '/*\nComment block\n*/',
                '/**\n *Javadoc block\n */'
        ]
    }

    def 'test endless comments'() {
        when:
        startReading(code)
        this.parser.parseSingleStatement()

        then:
        def e = thrown(ParserException)
        e.message == new ParserException(TokenType.EOF, this.parser).message

        where:
        code << [
                '//First line',
                '//First line\n//Second line\n//Third line',
                '/*\nComment block\n',
                '/**\n *Javadoc block\n '
        ]
    }

    def 'test for statements'() {
        when:
        startReading(code)
        def output = this.parser.parseSingleStatement()

        then:
        output == expected

        where:
        code | expected
        'for (int i = 0; true; i++) continue;' | new ForStatement(
                new Assignment(new Literal('int'), new Literal('i'), new NumberValueLiteral('0')),
                new BooleanValueLiteral('true'),
                new Increment(new Literal('i'), false),
                new CodeBlock(new Continue())
        )
        'for (i = 0; true; i++) continue;' | new ForStatement(
                new ReAssign(new Literal('i'), new NumberValueLiteral('0')),
                new BooleanValueLiteral('true'),
                new Increment(new Literal('i'), false),
                new CodeBlock(new Continue())
        )
        'for (; true; i++) continue;' | new ForStatement(
                new Statement(),
                new BooleanValueLiteral('true'),
                new Increment(new Literal('i'), false),
                new CodeBlock(new Continue())
        )
        'for (int i = 0; ; i++) continue;' | new ForStatement(
                new Assignment(new Literal('int'), new Literal('i'), new NumberValueLiteral('0')),
                new Statement(),
                new Increment(new Literal('i'), false),
                new CodeBlock(new Continue())
        )
        'for (int i = 0; true; ) continue;' | new ForStatement(
                new Assignment(new Literal('int'), new Literal('i'), new NumberValueLiteral('0')),
                new BooleanValueLiteral('true'),
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
                new Assignment(new Literal('int'), new Literal('i'), new NumberValueLiteral('0')),
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

    def 'test flow control statement: #expected.class.simpleName'() {
        when:
        startReading(code)
        def output = this.parser.parseSingleStatement()

        then:
        output == expected

        where:
        code | expected
        'while (true) continue;' | new WhileStatement(new BooleanValueLiteral('true'), new CodeBlock(new Continue()))
        'do continue; while (true);' | new DoStatement(new BooleanValueLiteral('true'), new CodeBlock(new Continue()))
    }

    def 'test if statement: #code'() {
        when:
        startReading(code)
        def output = this.parser.parseSingleStatement()

        then:
        output == expected

        where:
        code | expected
        'if (true) continue;' | new IfStatement(
                new BooleanValueLiteral('true'),
                new CodeBlock(new Continue()),
                new Statement(new EmptyLiteral())
        )
        'if (true) continue; else if (false) break;' | new IfStatement(
                new BooleanValueLiteral('true'),
                new CodeBlock(new Continue()),
                new IfStatement(
                        new BooleanValueLiteral('false'),
                        new CodeBlock(new Break()),
                        new Statement(new EmptyLiteral())
                )
        )
        'if (true) continue; else if (false) break; else return 1;' | new IfStatement(
                new BooleanValueLiteral('true'),
                new CodeBlock(new Continue()),
                new IfStatement(
                        new BooleanValueLiteral('false'),
                        new CodeBlock(new Break()),
                        new CodeBlock(
                                new Return(new NumberValueLiteral('1'))
                        )
                )
        )
    }

    def 'test array assignment'() {
        given:
        def expected = new Assignment(
                new ArrayLiteral(new Literal('int')),
                new Literal('arr'),
                new StaticArray(new Literal('int'), new NumberValueLiteral('0'))
        )
        def code = 'int[] arr = new int[0]'

        when:
        startReading(code)
        def output = this.parser.parseAssignment()

        then:
        output == expected
    }

    def 'test static array initialization'() {
        given:
        def expected = new StaticArray(new Literal('int'), new NumberValueLiteral('0'))
        def code = 'new int[0]'

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test dynamic array initialization'() {
        given:
        def expected = new DynamicArray(new Literal('int'), [
                new NumberValueLiteral('1'),
                new NumberValueLiteral('2')
        ])
        def code = 'new int[]{1, 2}'

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test parse new object'() {
        given:
        def expected = new NewObject(
                new Literal('String'),
                new MethodInvocation([new StringValueLiteral('\"Hello"')])
        )
        def code = 'new String(\"Hello\")'

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test execution of printf'() {
        given:
        def expected = new Statement(new MethodCall(
                new Literal('System.out.printf'),
                new MethodInvocation([
                        new StringValueLiteral('\"%s, %s!\"'),
                        new StringValueLiteral('\"Hello\"'),
                        new StringValueLiteral('\"world\"')
                ])
        ))
        def code = 'System.out.printf(\"%s, %s!\", \"Hello\", \"world\");'

        when:
        startReading(code)
        def output = this.parser.parseStatement()

        then:
        output == expected
    }

    def 'test method call'() {
        given:
        def expected = new MethodCall(
                new Literal('var'),
                new MethodInvocation([
                        new Literal('a'),
                        new NumberValueLiteral('1'),
                        new BooleanValueLiteral('true')
                ])
        )
        def code = 'var(a, 1, true)'

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test parse assignment: #code'() {
        when:
        startReading(code)
        def output = this.parser.parseAssignment()

        then:
        output == expected

        where:
        code         | expected
        'int i = 1;' | new Assignment(new Literal('int'), new Literal('i'), new NumberValueLiteral('1'))
        'int i;'     | new Assignment(new Literal('int'), new Literal('i'), new EmptyLiteral())
    }

    def 'test increment or decrement: #code'() {
        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
        output.isBefore() == before

        where:
        code    | expected                                 | before
        'var++' | new Increment(new Literal('var'), false) | false
        '++var' | new Increment(new Literal('var'), true)  | true
        'var--' | new Decrement(new Literal('var'), false) | false
        '--var' | new Decrement(new Literal('var'), true)  | true
    }

    def 'test parseReAssign with operation: #operation'() {
        given:
        def code = "var ${operation}= 2"
        def expected = new Literal('var')
        def operationNode = expectedClass.newInstance(expected, new NumberValueLiteral('2'))
        expected = new ReAssign(expected, operationNode)

        when:
        startReading(code)
        def output = this.parser.parseAssignment()

        then:
        output == expected

        where:
        operation    | expectedClass
        '+'          | Add.class
        '-'          | Subtract.class
        '*'          | Multiply.class
        '/'          | Divide.class
        '%'          | Modulo.class
        '&'          | BitAnd.class
        '|'          | BitOr.class
        '^'          | BitXor.class
        '<<'         | LShift.class
        '>>'         | RShift.class
        '>>>'        | URShift.class
    }

    def 'test parseReAssign with no operation'() {
        given:
        def code = 'var = 1'
        def expected = new ReAssign(new Literal('var'), new NumberValueLiteral('1'))

        when:
        startReading(code)
        def output = this.parser.parseAssignment()

        then:
        output == expected
    }

    def 'test complex parseBinaryOperation'() {
        given:
        def code = '18 % 17 / 16 * 15 - 14 + 13 ' +
                '>>> 12 >> 11 << 10 ' +
                '^ 9 | 8 & 7 || 6 && 5 ' +
                '>= 4 > 3 <= 2 < 1 ' +
                '!= false == true'
        def expected = new Modulo(new NumberValueLiteral('18'), new NumberValueLiteral('17'))
        expected = new Divide(expected, new NumberValueLiteral('16'))
        expected = new Multiply(expected, new NumberValueLiteral('15'))
        expected = new Subtract(expected, new NumberValueLiteral('14'))
        expected = new Add(expected, new NumberValueLiteral('13'))
        expected = new URShift(expected, new NumberValueLiteral('12'))
        expected = new RShift(expected, new NumberValueLiteral('11'))
        expected = new LShift(expected, new NumberValueLiteral('10'))
        expected = new BitXor(expected, new NumberValueLiteral('9'))
        expected = new BitOr(expected, new NumberValueLiteral('8'))
        expected = new BitAnd(expected, new NumberValueLiteral('7'))
        expected = new Or(expected, new NumberValueLiteral('6'))
        expected = new And(expected, new NumberValueLiteral('5'))
        expected = new GreaterThanEqual(expected, new NumberValueLiteral('4'))
        expected = new GreaterThan(expected, new NumberValueLiteral('3'))
        expected = new LessThanEqual(expected, new NumberValueLiteral('2'))
        expected = new LessThan(expected, new NumberValueLiteral('1'))
        expected = new NotEqual(expected, new BooleanValueLiteral('false'))
        expected = new Equal(expected, new BooleanValueLiteral('true'))

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test parenthesized operation'() {
        given:
        def expected = new Divide(
                new Add(
                        new NumberValueLiteral('1'),
                        new NumberValueLiteral('1')),
                new Subtract(
                        new NumberValueLiteral('1'),
                        new NumberValueLiteral('1')
                ))
        def code = '(1 + 1) / (1 - 1)'

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test simple parseBinaryOperation (#operation)'() {
        given:
        def code = "1 ${operation} 2"
        def expected = expectedClass.newInstance(
                new NumberValueLiteral('1'),
                new NumberValueLiteral('2')
        )

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected

        where:
        operation    | expectedClass
        '+'          | Add.class
        '-'          | Subtract.class
        '*'          | Multiply.class
        '/'          | Divide.class
        '%'          | Modulo.class
        '&'          | BitAnd.class
        '|'          | BitOr.class
        '^'          | BitXor.class
        '<<'         | LShift.class
        '>>'         | RShift.class
        '>>>'        | URShift.class
    }

    def 'test parseAtom: #code'() {
        when:
        startReading(code)
        def parsed = this.parser.parseAtom()

        then:
        parsed == expected

        where:
        code    | expected
        '-1'    | new Minus(new NumberValueLiteral('1'))
        '!true' | new Not(new BooleanValueLiteral('true'))
        '(1 + 1)'| new Add(new NumberValueLiteral('1'), new NumberValueLiteral('1'))
        'false' | new BooleanValueLiteral('false')
        'int'   | new Literal('int')
    }

    def 'test cast'() {
        given:
        def expected = new Cast(
                new Literal('double'),
                new Cast(new Literal('int'), new NumberValueLiteral('1'))
        )
        def code = '(double) (int) 1'

        when:
        startReading(code)
        def parsed = this.parser.parseCast()

        then:
        parsed == expected
    }

    def 'test minus'() {
        given:
        def expected = new Minus(new NumberValueLiteral('1'))

        when:
        startReading('-1')
        def parsed = this.parser.parseMinus()

        then:
        parsed == expected
    }

    def 'test not'() {
        given:
        def expected = new Not(new BooleanValueLiteral('true'))

        when:
        startReading('!true')
        def parsed = this.parser.parseNot()

        then:
        parsed == expected
    }

    def 'test parse type value of literal #literal'() {
        when:
        startReading(literal)
        def parsed = this.parser.parseTypeValue()

        then:
        parsed == expected

        where:
        literal           | expected
        '1'               | new NumberValueLiteral('1')
        '1L'              | new LongValueLiteral('1L')
        '1D'              | new DoubleValueLiteral('1D')
        '1F'              | new FloatValueLiteral('1F')
        'true'            | new BooleanValueLiteral('true')
        'false'           | new BooleanValueLiteral('false')
        '\'a\''           | new CharValueLiteral('\'a\'')
        '\"Hello world\"' | new StringValueLiteral('\"Hello world\"')
    }

    def 'test parse type of invalid'() {
        when:
        startReading('invalid')
        this.parser.parseTypeValue()

        then:
        def e = thrown(ParserException)
        e.message == new ParserException(TokenType.LITERAL, this.parser).message
    }

    def 'test parse literal'() {
        when:
        def literal = this.parser.createLiteral(BooleanValueLiteral, 'true')

        then:
        literal == new BooleanValueLiteral('true')
    }

    def 'test parse literal LiteralException'() {
        when:
        this.parser.createLiteral(BooleanValueLiteral, 'a')

        then:
        thrown(ParserException)
    }

    def 'test parse literal RuntimeException'() {
        when:
        this.parser.createLiteral(MockLiteral, 'a')

        then:
        thrown(IllegalArgumentException)
    }

    static class MockLiteral extends ValueLiteral {

        MockLiteral(@NotNull String rawValue, @NotNull TokenType type) throws NodeException {
            super(rawValue, type)
            throw new IllegalArgumentException()
        }
    }

}