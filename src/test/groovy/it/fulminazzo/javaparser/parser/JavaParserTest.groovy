package it.fulminazzo.javaparser.parser

import it.fulminazzo.javaparser.parser.node.Assignment
import it.fulminazzo.javaparser.parser.node.NodeException
import it.fulminazzo.javaparser.parser.node.MethodCall
import it.fulminazzo.javaparser.parser.node.MethodInvocation
import it.fulminazzo.javaparser.parser.node.arrays.DynamicArray
import it.fulminazzo.javaparser.parser.node.arrays.StaticArray
import it.fulminazzo.javaparser.parser.node.container.CaseBlock
import it.fulminazzo.javaparser.parser.node.container.CodeBlock
import it.fulminazzo.javaparser.parser.node.container.DefaultBlock
import it.fulminazzo.javaparser.parser.node.literals.ArrayLiteral
import it.fulminazzo.javaparser.parser.node.literals.EmptyLiteral
import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.literals.NullLiteral
import it.fulminazzo.javaparser.parser.node.literals.ThisLiteral
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
        def cwd = System.getProperty('user.dir')

        and:
        def file = new File(cwd, 'build/resources/test/parser_test_program.java')
        def parser = new JavaParser(file.newInputStream())

        and:
        def nextTestFile = new File(cwd, "src/test/resources/typechecker_test_program.dat")
        if (nextTestFile.isFile()) nextTestFile.delete()

        when:
        def parsed = parser.parseProgram()
        nextTestFile.withObjectOutputStream {
            it.writeObject(parsed)
        }
        def other = nextTestFile.newObjectInputStream().readObject()
        other == parsed

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

    def 'test parse case block of code: #code'() {
        when:
        startReading(code)
        def block = this.parser.parseCaseBlock()

        then:
        block == expected

        where:
        expected                                                                                | code
        new CaseBlock(new BooleanValueLiteral('true'), new Return(new NumberValueLiteral('1'))) |
                'case true: return 1;}'
        new CaseBlock(new BooleanValueLiteral('true'), new Return(new NumberValueLiteral('1'))) |
                'case true: {return 1;}}'
    }

    def 'test parse default block of code: #code'() {
        when:
        startReading(code)
        def block = this.parser.parseDefaultBlock()

        then:
        block == expected

        where:
        expected                                                  | code
        new DefaultBlock(new Return(new NumberValueLiteral('1'))) | 'default: return 1;}'
        new DefaultBlock(new Return(new NumberValueLiteral('1'))) | 'default: {return 1;}}'
    }

    def 'test invalid for statement'() {
        given:
        def code = 'for (int i; i < 10; i++)'

        when:
        startReading(code)
        this.parser.parseForStatement()

        then:
        thrown(ParserException)
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
                new Assignment(Literal.of('int'), Literal.of('i'), new NumberValueLiteral('0')),
                new BooleanValueLiteral('true'),
                new Increment(Literal.of('i'), false),
                new CodeBlock(new Continue())
        )
        'for (i = 0; true; i++) continue;' | new ForStatement(
                new ReAssign(Literal.of('i'), new NumberValueLiteral('0')),
                new BooleanValueLiteral('true'),
                new Increment(Literal.of('i'), false),
                new CodeBlock(new Continue())
        )
        'for (; true; i++) continue;' | new ForStatement(
                new Statement(),
                new BooleanValueLiteral('true'),
                new Increment(Literal.of('i'), false),
                new CodeBlock(new Continue())
        )
        'for (int i = 0; ; i++) continue;' | new ForStatement(
                new Assignment(Literal.of('int'), Literal.of('i'), new NumberValueLiteral('0')),
                new Statement(),
                new Increment(Literal.of('i'), false),
                new CodeBlock(new Continue())
        )
        'for (int i = 0; true; ) continue;' | new ForStatement(
                new Assignment(Literal.of('int'), Literal.of('i'), new NumberValueLiteral('0')),
                new BooleanValueLiteral('true'),
                new Statement(),
                new CodeBlock(new Continue())
        )
        'for (; ; i++) continue;' | new ForStatement(
                new Statement(),
                new Statement(),
                new Increment(Literal.of('i'), false),
                new CodeBlock(new Continue())
        )
        'for (int i = 0; ; ) continue;' | new ForStatement(
                new Assignment(Literal.of('int'), Literal.of('i'), new NumberValueLiteral('0')),
                new Statement(),
                new Statement(),
                new CodeBlock(new Continue())
        )
        'for (int i : arr) continue;' | new EnhancedForStatement(
                Literal.of('int'),
                Literal.of('i'),
                Literal.of('arr'),
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

    def 'test invalid array assignment'() {
        given:
        def code = 'int[] 1 = new int[0]'

        when:
        startReading(code)
        this.parser.parseAssignment()

        then:
        thrown(ParserException)
    }

    def 'test array assignment'() {
        given:
        def expected = new Assignment(
                new ArrayLiteral(Literal.of('int')),
                Literal.of('arr'),
                new StaticArray(Literal.of('int'), new NumberValueLiteral('0'))
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
        def expected = new StaticArray(
                new StaticArray(
                        new StaticArray(
                                new StaticArray(
                                        new StaticArray(Literal.of('int'), new NumberValueLiteral('2')),
                                        new NumberValueLiteral('0')),
                                new NumberValueLiteral('1')
                        ), new NumberValueLiteral('0')
                ), new NumberValueLiteral('0'))
        def code = 'new int[][][1][][2]'

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test dynamic array initialization'() {
        given:
        def expected = new DynamicArray(new ArrayLiteral(new ArrayLiteral(new ArrayLiteral(
                Literal.of('int')
        ))), [
                new NumberValueLiteral('1'),
                new NumberValueLiteral('2')
        ])
        def code = 'new int[][][]{1, 2}'

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test parse new object'() {
        given:
        def expected = new NewObject(
                Literal.of('String'),
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
                Literal.of('System.out'),
                'printf',
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

    def 'test chained method call'() {
        given:
        def expected = new MethodCall(
                new MethodCall(
                        new EmptyLiteral(),
                        'method',
                        new MethodInvocation([
                                Literal.of('a'),
                                new NumberValueLiteral('1'),
                                new BooleanValueLiteral('true')
                        ])
                ),
                'toString',
                new MethodInvocation([])
        )
        def code = 'method(a, 1, true).toString()'

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test field retrieval from method call'() {
        given:
        def expected = new Field(
                new Field(
                        new MethodCall(
                                new ThisLiteral(),
                                'toString',
                                new MethodInvocation([])
                        ), Literal.of('char_array')
                ), Literal.of('internal')
        )
        def code = 'this.toString().char_array.internal'

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test method call'() {
        given:
        def expected = new MethodCall(
                new EmptyLiteral(),
                'method',
                new MethodInvocation([
                        Literal.of('a'),
                        new NumberValueLiteral('1'),
                        new BooleanValueLiteral('true')
                ])
        )
        def code = 'method(a, 1, true)'

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
        'int i = 1;' | new Assignment(Literal.of('int'), Literal.of('i'), new NumberValueLiteral('1'))
        'int i;'     | new Assignment(Literal.of('int'), Literal.of('i'), new EmptyLiteral())
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
        'var++' | new Increment(Literal.of('var'), false) | false
        '++var' | new Increment(Literal.of('var'), true)  | true
        'var--' | new Decrement(Literal.of('var'), false) | false
        '--var' | new Decrement(Literal.of('var'), true)  | true
    }

    def 'test parseReAssign with operation: #operation'() {
        given:
        def code = "var ${operation}= 2"
        def expected = Literal.of('var')
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
        def expected = new ReAssign(Literal.of('var'), new NumberValueLiteral('1'))

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
        def expected = new NumberValueLiteral('18')
        expected = new Modulo(expected, new NumberValueLiteral('17'))
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
        def second = new GreaterThanEqual(new NumberValueLiteral('5'), new NumberValueLiteral('4'))
        second = new GreaterThan(second, new NumberValueLiteral('3'))
        second = new LessThanEqual(second, new NumberValueLiteral('2'))
        second = new LessThan(second, new NumberValueLiteral('1'))
        second = new NotEqual(second, new BooleanValueLiteral('false'))
        second = new Equal(second, new BooleanValueLiteral('true'))
        expected = new And(expected, second)

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected
    }

    def 'test parenthesized operation: #operation'() {
        given:
        def expected = clazz.newInstance(
                new Add(
                        new NumberValueLiteral('1'),
                        new NumberValueLiteral('1')),
                new Subtract(
                        new NumberValueLiteral('1'),
                        new NumberValueLiteral('1')
                ))
        def code = "(1 + 1) ${operation} (1 - 1)"

        when:
        startReading(code)
        def output = this.parser.parseExpression()

        then:
        output == expected

        where:
        operation | clazz
        '/'       | Divide
        '+'       | Add
        '-'       | Subtract
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
        'int'   | Literal.of('int')
    }

    def 'test null cast'() {
        given:
        def expected = new Cast(
                Literal.of('Double'),
                new NullLiteral()
        )
        def code = '(Double) null'

        when:
        startReading(code)
        def parsed = this.parser.parseCast()

        then:
        parsed == expected
    }

    def 'test invalid cast'() {
        given:
        def code = "(int) -+1"

        when:
        startReading(code)
        this.parser.parseCast()

        then:
        thrown(ParserException)
    }

    def 'test cast of #object'() {
        given:
        def expected = new Cast(
                Literal.of('double'),
                new Cast(
                        Literal.of('float'),
                        new Cast(Literal.of('int'), objectLiteral)
                )
        )
        def code = "(double) (float) ((int) ${object})"

        when:
        startReading(code)
        def parsed = this.parser.parseCast()

        then:
        parsed == expected

        where:
        object | objectLiteral
        '1'    | new NumberValueLiteral('1')
        '-1'   | new Minus(new NumberValueLiteral('1'))
        '-(1)' | new Minus(new NumberValueLiteral('1'))
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

    def 'test invalid literal'() {
        given:
        this.parser.setInput('$$$')

        when:
        this.parser.parseLiteral()

        then:
        thrown(ParserException)
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