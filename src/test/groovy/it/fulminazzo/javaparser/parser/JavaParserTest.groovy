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
        'return 1;' | new Return(new NumberLiteral('1'))
        'return 1'  | new Return(new NumberLiteral('1'))
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
        output == new Return(new NumberLiteral('1'))

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
                new Assignment(new Literal('int'), new Literal('i'), new NumberLiteral('0')),
                new BooleanLiteral('true'),
                new Increment(new Literal('i'), false),
                new CodeBlock(new Continue())
        )
        'for (i = 0; true; i++) continue;' | new ForStatement(
                new ReAssign(new Literal('i'), new NumberLiteral('0')),
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

    def 'test flow control statement: #expected.class.simpleName'() {
        when:
        startReading(code)
        def output = this.parser.parseSingleStatement()

        then:
        output == expected

        where:
        code | expected
        'while (true) continue;' | new WhileStatement(new BooleanLiteral('true'), new CodeBlock(new Continue()))
        'do continue; while (true);' | new DoStatement(new BooleanLiteral('true'), new CodeBlock(new Continue()))
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
                new BooleanLiteral('true'),
                new CodeBlock(new Continue()),
                new Statement(new EmptyLiteral())
        )
        'if (true) continue; else if (false) break;' | new IfStatement(
                new BooleanLiteral('true'),
                new CodeBlock(new Continue()),
                new IfStatement(
                        new BooleanLiteral('false'),
                        new CodeBlock(new Break()),
                        new Statement(new EmptyLiteral())
                )
        )
        'if (true) continue; else if (false) break; else return 1;' | new IfStatement(
                new BooleanLiteral('true'),
                new CodeBlock(new Continue()),
                new IfStatement(
                        new BooleanLiteral('false'),
                        new CodeBlock(new Break()),
                        new CodeBlock(
                                new Return(new NumberLiteral('1'))
                        )
                )
        )
    }

    def 'test array assignment'() {
        given:
        def expected = new Assignment(
                new ArrayLiteral(new Literal('int')),
                new Literal('arr'),
                new StaticArray(new Literal('int'), new NumberLiteral('0'))
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
        def expected = new StaticArray(new Literal('int'), new NumberLiteral('0'))
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
                new NumberLiteral('1'),
                new NumberLiteral('2')
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
                new MethodInvocation([new StringLiteral('\"Hello"')])
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
                        new StringLiteral('\"%s, %s!\"'),
                        new StringLiteral('\"Hello\"'),
                        new StringLiteral('\"world\"')
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
                        new NumberLiteral('1'),
                        new BooleanLiteral('true')
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
        'int i = 1;' | new Assignment(new Literal('int'), new Literal('i'), new NumberLiteral('1'))
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
        def operationNode = expectedClass.newInstance(expected, new NumberLiteral('2'))
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
        def expected = new ReAssign(new Literal('var'), new NumberLiteral('1'))

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
                        new NumberLiteral('1'),
                        new NumberLiteral('1')),
                new Subtract(
                        new NumberLiteral('1'),
                        new NumberLiteral('1')
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
                new NumberLiteral('1'), 
                new NumberLiteral('2')
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
        '-1'    | new Minus(new NumberLiteral('1'))
        '!true' | new Not(new BooleanLiteral('true'))
        '(1 + 1)'| new Add(new NumberLiteral('1'), new NumberLiteral('1'))
        'false' | new BooleanLiteral('false')
        'int'   | new Literal('int')
    }

    def 'test cast'() {
        given:
        def expected = new Cast(
                new Literal('double'),
                new Cast(new Literal('int'), new NumberLiteral('1'))
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
        def expected = new Minus(new NumberLiteral('1'))

        when:
        startReading('-1')
        def parsed = this.parser.parseMinus()

        then:
        parsed == expected
    }

    def 'test not'() {
        given:
        def expected = new Not(new BooleanLiteral('true'))

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
        when:
        startReading('invalid')
        this.parser.parseTypeValue()

        then:
        def e = thrown(ParserException)
        e.message == new ParserException(TokenType.LITERAL, this.parser).message
    }

    def 'test parse literal'() {
        when:
        def literal = this.parser.createLiteral(BooleanLiteral, 'true')

        then:
        literal == new BooleanLiteral('true')
    }

    def 'test parse literal LiteralException'() {
        when:
        this.parser.createLiteral(BooleanLiteral, 'a')

        then:
        thrown(ParserException)
    }

    def 'test parse literal RuntimeException'() {
        when:
        this.parser.createLiteral(MockLiteral, 'a')

        then:
        thrown(IllegalArgumentException)
    }

    static class MockLiteral extends BaseTypeLiteral {

        MockLiteral(@NotNull String rawValue, @NotNull TokenType type) throws LiteralException {
            super(rawValue, type)
            throw new IllegalArgumentException()
        }
    }

}