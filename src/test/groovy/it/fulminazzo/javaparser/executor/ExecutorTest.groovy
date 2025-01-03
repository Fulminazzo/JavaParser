package it.fulminazzo.javaparser.executor

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.environment.ScopeException
import it.fulminazzo.javaparser.executor.values.*
import it.fulminazzo.javaparser.executor.values.arrays.ArrayValue
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue
import it.fulminazzo.javaparser.executor.values.objects.ObjectValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.BooleanValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue
import it.fulminazzo.javaparser.parser.node.MethodInvocation
import it.fulminazzo.javaparser.parser.node.container.CodeBlock
import it.fulminazzo.javaparser.parser.node.literals.ArrayLiteral
import it.fulminazzo.javaparser.parser.node.literals.EmptyLiteral
import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.operators.binary.LessThan
import it.fulminazzo.javaparser.parser.node.operators.binary.LessThanEqual
import it.fulminazzo.javaparser.parser.node.operators.unary.Decrement
import it.fulminazzo.javaparser.parser.node.operators.unary.Increment
import it.fulminazzo.javaparser.parser.node.statements.IfStatement
import it.fulminazzo.javaparser.parser.node.statements.Return
import it.fulminazzo.javaparser.parser.node.statements.Statement
import it.fulminazzo.javaparser.parser.node.values.*
import spock.lang.Specification

class ExecutorTest extends Specification {
    private static final BOOL_LIT_TRUE = new BooleanValueLiteral('true')
    private static final BOOL_LIT_FALSE = new BooleanValueLiteral('false')
    private static final CHAR_LIT = new CharValueLiteral('\'a\'')
    private static final NUMBER_LIT = new NumberValueLiteral('1')
    private static final LONG_LIT = new LongValueLiteral('2L')
    private static final FLOAT_LIT = new FloatValueLiteral('3.0f')
    private static final DOUBLE_LIT = new DoubleValueLiteral('4.0d')
    private static final STRING_LIT = new StringValueLiteral('\"Hello, world!\"')

    private static final CODE_BLOCK_EMPTY = new CodeBlock()
    private static final CODE_BLOCK_1 = new CodeBlock(new Return(new NumberValueLiteral('1')))
    private static final CODE_BLOCK_2 = new CodeBlock(new Return(new NumberValueLiteral('2')))
    private static final CODE_BLOCK_3 = new CodeBlock(new Return(new NumberValueLiteral('3')))

    private Executor executor

    void setup() {
        this.executor = new Executor(new TestClass())
    }

    def 'test visit while statement of (#expression) #codeBlock should return #expected'() {
        given:
        this.executor.environment.declare(PrimitiveClassValue.INT, 'i', PrimitiveValue.of(0))

        when:
        def value = this.executor.visitWhileStatement(codeBlock, expression)
        def counter = this.executor.environment.lookup('i')

        then:
        value == expected
        counter == expectedCounter

        where:
        expected | expectedCounter | expression | codeBlock
        Values.NO_VALUE | PrimitiveValue.of(10) | new LessThan(Literal.of('i'), new NumberValueLiteral('10')) |
                new CodeBlock(new Statement(new Increment(Literal.of('i'), false)))
        PrimitiveValue.of(1) | PrimitiveValue.of(0) | BOOL_LIT_TRUE | CODE_BLOCK_1
        Values.NO_VALUE | PrimitiveValue.of(0) | BOOL_LIT_FALSE | CODE_BLOCK_EMPTY
    }

    def 'test visit if statement of code "#code" should return #expected'() {
        given:
        def refl = new Refl<>(code)

        and:
        def expression = refl.getFieldObject('expression')
        def then = refl.getFieldObject('then')
        def elseBranch = refl.getFieldObject('elseBranch')

        when:
        def value = this.executor.visitIfStatement(then, elseBranch, expression)

        then:
        value == expected

        where:
        expected             | code
        // one if, no else
        PrimitiveValue.of(1) | new IfStatement(BOOL_LIT_TRUE, CODE_BLOCK_1, CODE_BLOCK_EMPTY)
        Values.NO_VALUE      | new IfStatement(BOOL_LIT_FALSE, CODE_BLOCK_1, CODE_BLOCK_EMPTY)
        // one if, one else
        PrimitiveValue.of(1) | new IfStatement(BOOL_LIT_TRUE, CODE_BLOCK_1, CODE_BLOCK_3)
        PrimitiveValue.of(3) | new IfStatement(BOOL_LIT_FALSE, CODE_BLOCK_1, CODE_BLOCK_3)
        // two if, no else
        PrimitiveValue.of(1) | new IfStatement(BOOL_LIT_TRUE, CODE_BLOCK_1,
                new IfStatement(BOOL_LIT_TRUE, CODE_BLOCK_2, CODE_BLOCK_EMPTY))
        PrimitiveValue.of(2) | new IfStatement(BOOL_LIT_FALSE, CODE_BLOCK_1,
                new IfStatement(BOOL_LIT_TRUE, CODE_BLOCK_2, CODE_BLOCK_EMPTY))
        Values.NO_VALUE      | new IfStatement(BOOL_LIT_FALSE, CODE_BLOCK_1,
                new IfStatement(BOOL_LIT_FALSE, CODE_BLOCK_2, CODE_BLOCK_EMPTY))
        // two if, one else
        PrimitiveValue.of(1) | new IfStatement(BOOL_LIT_TRUE, CODE_BLOCK_1,
                new IfStatement(BOOL_LIT_TRUE, CODE_BLOCK_2, CODE_BLOCK_3))
        PrimitiveValue.of(2) | new IfStatement(BOOL_LIT_FALSE, CODE_BLOCK_1,
                new IfStatement(BOOL_LIT_TRUE, CODE_BLOCK_2, CODE_BLOCK_3))
        PrimitiveValue.of(3) | new IfStatement(BOOL_LIT_FALSE, CODE_BLOCK_1,
                new IfStatement(BOOL_LIT_FALSE, CODE_BLOCK_2, CODE_BLOCK_3))
    }

    def 'test visit new object #parameters'() {
        given:
        def nodeExecutor = Literal.of(TestClass.canonicalName)
        def methodInvocation = new MethodInvocation(parameters)

        when:
        def type = this.executor.visitNewObject(nodeExecutor, methodInvocation)

        then:
        type == ObjectValue.of(expected)

        where:
        parameters                   | expected
        []                           | new TestClass()
        [NUMBER_LIT, BOOL_LIT_TRUE]  | new TestClass(1, true)
        [NUMBER_LIT, BOOL_LIT_FALSE] | new TestClass(1, false)
    }

    def 'test visit method call: #object #method #parameters should return #expected'() {
        given:
        this.executor.environment.declare(
                ObjectClassValue.INTEGER,
                'method_call_val',
                ObjectValue.of(1)
        )
        this.executor.environment.declare(
                PrimitiveClassValue.INT,
                'method_call_val_prim',
                PrimitiveValue.of(1)
        )

        and:
        def nodeExecutor = object.isEmpty() ? new EmptyLiteral() : Literal.of(object)
        def methodInvocation = new MethodInvocation(parameters)

        when:
        def value = this.executor.visitMethodCall(nodeExecutor, method, methodInvocation)

        then:
        value == expected

        where:
        object                 | method               | parameters                                   | expected
        ''                     | 'publicMethod'       | []                                           | PrimitiveValue.of(1.0d)
        ''                     | 'publicMethod'       | [DOUBLE_LIT, BOOL_LIT_TRUE]                  | PrimitiveValue.of(4.0d)
        ''                     | 'publicMethod'       | [DOUBLE_LIT, BOOL_LIT_FALSE]                 | PrimitiveValue.of(1.0d)
        ''                     | 'publicStaticMethod' | []                                           | PrimitiveValue.of(1)
        ''                     | 'publicStaticMethod' | [new NumberValueLiteral('7'), BOOL_LIT_TRUE] | PrimitiveValue.of(7)
        ''                     | 'publicStaticMethod' | [NUMBER_LIT, BOOL_LIT_FALSE]                 | PrimitiveValue.of(1)
        'method_call_val'      | 'toString'           | []                                           | ObjectValue.of('1')
        'method_call_val_prim' | 'toString'           | []                                           | ObjectValue.of('1')
    }

    def 'test visit field of #field should return #expected'() {
        given:
        this.executor.environment.declare(
                ObjectClassValue.of(TestClass),
                'field_var',
                ObjectValue.of(new TestClass())
        )

        and:
        def left = Literal.of('field_var')
        def right = Literal.of(field)

        when:
        def type = this.executor.visitField(left, right)

        then:
        type == expected

        where:
        field               | expected
        'publicStaticField' | PrimitiveValue.of(1)
        'publicField'       | ObjectValue.of(1.0d)
    }

    def 'test visit assignment: #valueClass #name = #val should return value #expected'() {
        given:
        def literalType = Literal.of(valueClass)
        def literalName = Literal.of(name)

        when:
        this.executor.visitAssignment(literalType, literalName, val)
        def value = this.executor.environment.lookup(name)

        then:
        value == expected

        where:
        valueClass  | name  | val            | expected
        'byte'      | 'bc'  | CHAR_LIT       | Value.of((byte) 97)
        'byte'      | 'b'   | NUMBER_LIT     | Value.of((byte) 1)
        'Byte'      | 'bWc' | CHAR_LIT       | ObjectValue.of((Byte) 97)
        'Byte'      | 'bW'  | NUMBER_LIT     | ObjectValue.of((Byte) 1)
        'short'     | 'sc'  | CHAR_LIT       | Value.of((short) 97)
        'short'     | 's'   | NUMBER_LIT     | Value.of((short) 1)
        'Short'     | 'sWc' | CHAR_LIT       | ObjectValue.of((Short) 97)
        'Short'     | 'sW'  | NUMBER_LIT     | ObjectValue.of((Short) 1)
        'char'      | 'c'   | CHAR_LIT       | Value.of((char) 'a')
        'char'      | 'ci'  | NUMBER_LIT     | Value.of((char) 1)
        'Character' | 'cW'  | CHAR_LIT       | ObjectValue.of(Character.valueOf(97 as char))
        'Character' | 'ciW' | NUMBER_LIT     | ObjectValue.of(Character.valueOf(1 as char))
        'int'       | 'ic'  | CHAR_LIT       | Value.of((int) 97)
        'int'       | 'i'   | NUMBER_LIT     | Value.of((int) 1)
        'Integer'   | 'iW'  | NUMBER_LIT     | ObjectValue.of((Integer) 1)
        'long'      | 'lc'  | CHAR_LIT       | Value.of((long) 97)
        'long'      | 'li'  | NUMBER_LIT     | Value.of((long) 1)
        'long'      | 'l'   | LONG_LIT       | Value.of((long) 2L)
        'Long'      | 'lW'  | LONG_LIT       | ObjectValue.of((Long) 2L)
        'float'     | 'fc'  | CHAR_LIT       | Value.of((float) 97)
        'float'     | 'fi'  | NUMBER_LIT     | Value.of((float) 1)
        'float'     | 'fl'  | LONG_LIT       | Value.of((float) 2L)
        'float'     | 'f'   | FLOAT_LIT      | Value.of((float) 3.0f)
        'Float'     | 'fW'  | FLOAT_LIT      | ObjectValue.of((Float) 3.0f)
        'double'    | 'dc'  | CHAR_LIT       | Value.of((double) 97)
        'double'    | 'di'  | NUMBER_LIT     | Value.of((double) 1)
        'double'    | 'dl'  | LONG_LIT       | Value.of((double) 2L)
        'double'    | 'df'  | FLOAT_LIT      | Value.of((double) 3.0f)
        'double'    | 'd'   | DOUBLE_LIT     | Value.of((double) 4.0d)
        'Double'    | 'dW'  | DOUBLE_LIT     | ObjectValue.of((Double) 4.0d)
        'boolean'   | 'bo'  | BOOL_LIT_FALSE | BooleanValue.FALSE
        'boolean'   | 'bo'  | BOOL_LIT_TRUE  | BooleanValue.TRUE
        'Boolean'   | 'boW' | BOOL_LIT_FALSE | ObjectValue.of(false)
        'Boolean'   | 'boW' | BOOL_LIT_TRUE  | ObjectValue.of(true)
        'String'    | 'st'  | STRING_LIT     | ObjectValue.of('Hello, world!')
        'Object'    | 'o'   | BOOL_LIT_TRUE  | PrimitiveValue.of(true)
    }

    def 'test visit assignment: #valueClass #name should return value #expected'() {
        given:
        def literalType = Literal.of(valueClass)
        def literalName = Literal.of(name)

        when:
        this.executor.visitAssignment(literalType, literalName, new EmptyLiteral())
        def value = this.executor.environment.lookup(name)

        then:
        value == expected

        where:
        valueClass  | name  | expected
        'byte'      | 'b'   | PrimitiveValue.of((byte) 0)
        'Byte'      | 'bW'  | Values.NULL_VALUE
        'short'     | 's'   | PrimitiveValue.of((short) 0)
        'Short'     | 'sW'  | Values.NULL_VALUE
        'char'      | 'c'   | PrimitiveValue.of((char) 0)
        'Character' | 'cW'  | Values.NULL_VALUE
        'int'       | 'i'   | PrimitiveValue.of(0)
        'Integer'   | 'iW'  | Values.NULL_VALUE
        'long'      | 'l'   | PrimitiveValue.of((long) 0)
        'Long'      | 'lW'  | Values.NULL_VALUE
        'float'     | 'f'   | PrimitiveValue.of((float) 0)
        'Float'     | 'fW'  | Values.NULL_VALUE
        'double'    | 'd'   | PrimitiveValue.of((double) 0)
        'Double'    | 'dW'  | Values.NULL_VALUE
        'boolean'   | 'bo'  | PrimitiveValue.of((boolean) false)
        'Boolean'   | 'boW' | Values.NULL_VALUE
        'String'    | 'st'  | Values.NULL_VALUE
        'Object'    | 'o'   | Values.NULL_VALUE
    }

    def 'test visit re-assignment: #name = #val should return value #expected'() {
        given:
        name += '_reassign'
        def literalName = Literal.of(name)

        and:
        this.executor.environment.declare(
                Literal.of(valueClass).accept(this.executor).to(ClassValue),
                name, val.accept(this.executor)
        )

        when:
        def value = this.executor.visitReAssign(literalName, val)

        then:
        value == expected

        where:
        valueClass  | name  | val            | expected
        'byte'      | 'bc'  | CHAR_LIT       | Value.of((byte) 97)
        'byte'      | 'b'   | NUMBER_LIT     | Value.of((byte) 1)
        'Byte'      | 'bWc' | CHAR_LIT       | ObjectValue.of((Byte) 97)
        'Byte'      | 'bW'  | NUMBER_LIT     | ObjectValue.of((Byte) 1)
        'short'     | 'sc'  | CHAR_LIT       | Value.of((short) 97)
        'short'     | 's'   | NUMBER_LIT     | Value.of((short) 1)
        'Short'     | 'sWc' | CHAR_LIT       | ObjectValue.of((Short) 97)
        'Short'     | 'sW'  | NUMBER_LIT     | ObjectValue.of((Short) 1)
        'char'      | 'c'   | CHAR_LIT       | Value.of((char) 'a')
        'char'      | 'ci'  | NUMBER_LIT     | Value.of((char) 1)
        'Character' | 'cW'  | CHAR_LIT       | ObjectValue.of(Character.valueOf(97 as char))
        'Character' | 'ciW' | NUMBER_LIT     | ObjectValue.of(Character.valueOf(1 as char))
        'int'       | 'ic'  | CHAR_LIT       | Value.of((int) 97)
        'int'       | 'i'   | NUMBER_LIT     | Value.of((int) 1)
        'Integer'   | 'iW'  | NUMBER_LIT     | ObjectValue.of((Integer) 1)
        'long'      | 'lc'  | CHAR_LIT       | Value.of((long) 97)
        'long'      | 'li'  | NUMBER_LIT     | Value.of((long) 1)
        'long'      | 'l'   | LONG_LIT       | Value.of((long) 2L)
        'Long'      | 'lW'  | LONG_LIT       | ObjectValue.of((Long) 2L)
        'float'     | 'fc'  | CHAR_LIT       | Value.of((float) 97)
        'float'     | 'fi'  | NUMBER_LIT     | Value.of((float) 1)
        'float'     | 'fl'  | LONG_LIT       | Value.of((float) 2L)
        'float'     | 'f'   | FLOAT_LIT      | Value.of((float) 3.0f)
        'Float'     | 'fW'  | FLOAT_LIT      | ObjectValue.of((Float) 3.0f)
        'double'    | 'dc'  | CHAR_LIT       | Value.of((double) 97)
        'double'    | 'di'  | NUMBER_LIT     | Value.of((double) 1)
        'double'    | 'dl'  | LONG_LIT       | Value.of((double) 2L)
        'double'    | 'df'  | FLOAT_LIT      | Value.of((double) 3.0f)
        'double'    | 'd'   | DOUBLE_LIT     | Value.of((double) 4.0d)
        'Double'    | 'dW'  | DOUBLE_LIT     | ObjectValue.of((Double) 4.0d)
        'boolean'   | 'bo'  | BOOL_LIT_FALSE | BooleanValue.FALSE
        'boolean'   | 'bo'  | BOOL_LIT_TRUE  | BooleanValue.TRUE
        'Boolean'   | 'boW' | BOOL_LIT_FALSE | ObjectValue.of(false)
        'Boolean'   | 'boW' | BOOL_LIT_TRUE  | ObjectValue.of(true)
        'String'    | 'st'  | STRING_LIT     | ObjectValue.of('Hello, world!')
        'Object'    | 'o'   | BOOL_LIT_TRUE  | ObjectValue.of(true)
    }

    def 'test visit re-assignment not declared'() {
        given:
        def varName = 'visit_re_assignment_declared'
        def name = Literal.of(varName)
        def value = NUMBER_LIT

        when:
        this.executor.visitReAssign(name, value)

        then:
        def e = thrown(ExecutorException)
        e.message == ScopeException.noSuchVariable(varName).message
    }

    def 'test visit dynamic array'() {
        given:
        def value = this.executor.visitDynamicArray(
                Arrays.asList(BOOL_LIT_TRUE, BOOL_LIT_FALSE),
                new ArrayLiteral(Literal.of('boolean'))
        )

        and:
        def expected = ArrayValue.of(PrimitiveClassValue.BOOLEAN,
                [BooleanValue.TRUE, BooleanValue.FALSE])

        expect:
        value == expected
    }

    def 'test visit static array'() {
        given:
        def value = this.executor.visitStaticArray(
                1,
                new ArrayLiteral(Literal.of('boolean'))
        )

        and:
        def expected = ArrayValue.of(PrimitiveClassValue.BOOLEAN, 1)

        expect:
        value == expected
    }

    def 'test visit #operation of #literal should return #value and update variable to #expected'() {
        given:
        this.executor.environment.declare(PrimitiveClassValue.INT, 'i', PrimitiveValue.of(1))

        when:
        def actual = this.executor."visit${operation}"(literal.before, literal.operand)
        def declared = this.executor.environment.lookup('i')

        then:
        actual == value
        declared == expected

        where:
        operation   | literal                               | value                | expected
        'Increment' | new Increment(Literal.of('i'), true)  | PrimitiveValue.of(2) | PrimitiveValue.of(2)
        'Increment' | new Increment(Literal.of('i'), false) | PrimitiveValue.of(1) | PrimitiveValue.of(2)
        'Decrement' | new Decrement(Literal.of('i'), true)  | PrimitiveValue.of(0) | PrimitiveValue.of(0)
        'Decrement' | new Decrement(Literal.of('i'), false) | PrimitiveValue.of(1) | PrimitiveValue.of(0)
    }

    def 'test equal'() {
        given:
        def result = this.executor.visitEqual(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        NUMBER_LIT | NUMBER_LIT | BooleanValue.TRUE
        CHAR_LIT   | NUMBER_LIT | BooleanValue.FALSE
    }

    def 'test not equal'() {
        given:
        def result = this.executor.visitNotEqual(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        NUMBER_LIT | NUMBER_LIT | BooleanValue.FALSE
        CHAR_LIT   | NUMBER_LIT | BooleanValue.TRUE
    }

    def 'test less than'() {
        given:
        def result = this.executor.visitLessThan(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | BooleanValue.FALSE
        NUMBER_LIT | LONG_LIT   | BooleanValue.TRUE
        NUMBER_LIT | NUMBER_LIT | BooleanValue.FALSE
    }

    def 'test less than equal'() {
        given:
        def result = this.executor.visitLessThanEqual(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | BooleanValue.FALSE
        NUMBER_LIT | LONG_LIT   | BooleanValue.TRUE
        NUMBER_LIT | NUMBER_LIT | BooleanValue.TRUE
    }

    def 'test greater than'() {
        given:
        def result = this.executor.visitGreaterThan(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | BooleanValue.TRUE
        NUMBER_LIT | LONG_LIT   | BooleanValue.FALSE
        NUMBER_LIT | NUMBER_LIT | BooleanValue.FALSE
    }

    def 'test greater than equal'() {
        given:
        def result = this.executor.visitGreaterThanEqual(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | BooleanValue.TRUE
        NUMBER_LIT | LONG_LIT   | BooleanValue.FALSE
        NUMBER_LIT | NUMBER_LIT | BooleanValue.TRUE
    }

    def 'test and'() {
        given:
        def result = this.executor.visitAnd(first, second)

        expect:
        result == expected

        where:
        first          | second         | expected
        BOOL_LIT_TRUE  | BOOL_LIT_TRUE  | BooleanValue.TRUE
        BOOL_LIT_FALSE | BOOL_LIT_TRUE  | BooleanValue.FALSE
        BOOL_LIT_TRUE  | BOOL_LIT_FALSE | BooleanValue.FALSE
        BOOL_LIT_FALSE | BOOL_LIT_FALSE | BooleanValue.FALSE
    }

    def 'test or'() {
        given:
        def result = this.executor.visitOr(first, second)

        expect:
        result == expected

        where:
        first          | second         | expected
        BOOL_LIT_TRUE  | BOOL_LIT_TRUE  | BooleanValue.TRUE
        BOOL_LIT_FALSE | BOOL_LIT_TRUE  | BooleanValue.TRUE
        BOOL_LIT_TRUE  | BOOL_LIT_FALSE | BooleanValue.TRUE
        BOOL_LIT_FALSE | BOOL_LIT_FALSE | BooleanValue.FALSE
    }

    def 'test bit and'() {
        given:
        def result = this.executor.visitBitAnd(first, second)

        expect:
        result == expected

        where:
        first         | second         | expected
        BOOL_LIT_TRUE | BOOL_LIT_FALSE | PrimitiveValue.of(true & false)
        NUMBER_LIT    | NUMBER_LIT     | PrimitiveValue.of(1 & 1)
    }

    def 'test bit or'() {
        given:
        def result = this.executor.visitBitOr(first, second)

        expect:
        result == expected

        where:
        first         | second         | expected
        BOOL_LIT_TRUE | BOOL_LIT_FALSE | PrimitiveValue.of(true | false)
        NUMBER_LIT    | NUMBER_LIT     | PrimitiveValue.of(1 | 1)
    }

    def 'test bit xor'() {
        given:
        def result = this.executor.visitBitXor(first, second)

        expect:
        result == expected

        where:
        first         | second         | expected
        BOOL_LIT_TRUE | BOOL_LIT_FALSE | PrimitiveValue.of(true)
        NUMBER_LIT    | NUMBER_LIT     | PrimitiveValue.of(1 ^ 1)
    }

    def 'test lshift'() {
        given:
        def result = this.executor.visitLShift(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | PrimitiveValue.of(2L << 1)
        NUMBER_LIT | LONG_LIT   | PrimitiveValue.of((1 << 2L) as long)
    }

    def 'test rshift'() {
        given:
        def result = this.executor.visitRShift(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | PrimitiveValue.of(2L >> 1)
        NUMBER_LIT | LONG_LIT   | PrimitiveValue.of((1 >> 2L) as long)
    }

    def 'test urshift'() {
        given:
        def result = this.executor.visitURShift(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | PrimitiveValue.of(2L >>> 1)
        NUMBER_LIT | LONG_LIT   | PrimitiveValue.of((1 >>> 2L) as long)
    }

    def 'test add'() {
        given:
        def result = this.executor.visitAdd(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | PrimitiveValue.of(2L + 1)
        FLOAT_LIT  | NUMBER_LIT | PrimitiveValue.of((3.0f + 1) as float)
        DOUBLE_LIT | NUMBER_LIT | PrimitiveValue.of(4.0d + 1)
    }

    def 'test subtract'() {
        given:
        def result = this.executor.visitSubtract(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | PrimitiveValue.of(2L - 1)
        FLOAT_LIT  | NUMBER_LIT | PrimitiveValue.of((3.0f - 1) as float)
        DOUBLE_LIT | NUMBER_LIT | PrimitiveValue.of(4.0d - 1)
    }

    def 'test multiply'() {
        given:
        def result = this.executor.visitMultiply(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | PrimitiveValue.of(2L * 1)
        FLOAT_LIT  | NUMBER_LIT | PrimitiveValue.of((3.0f * 1) as float)
        DOUBLE_LIT | NUMBER_LIT | PrimitiveValue.of(4.0d * 1)
    }

    def 'test divide'() {
        given:
        def result = this.executor.visitDivide(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | PrimitiveValue.of(2L)
        FLOAT_LIT  | NUMBER_LIT | PrimitiveValue.of(3.0f)
        DOUBLE_LIT | NUMBER_LIT | PrimitiveValue.of(4.0d)
    }

    def 'test modulo'() {
        given:
        def result = this.executor.visitModulo(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | PrimitiveValue.of(2L % 1)
        FLOAT_LIT  | NUMBER_LIT | PrimitiveValue.of((3.0f % 1) as float)
        DOUBLE_LIT | NUMBER_LIT | PrimitiveValue.of(4.0d % 1)
    }

    def 'test minus'() {
        given:
        def result = this.executor.visitMinus(operand)

        expect:
        result == expected

        where:
        operand    | expected
        NUMBER_LIT | PrimitiveValue.of(-1)
        LONG_LIT   | PrimitiveValue.of(-2L)
        FLOAT_LIT  | PrimitiveValue.of(-3.0f)
        DOUBLE_LIT | PrimitiveValue.of(-4.0d)
    }

    def 'test not'() {
        given:
        def result = this.executor.visitNot(operand)

        expect:
        result == expected

        where:
        operand        | expected
        BOOL_LIT_TRUE  | BooleanValue.FALSE
        BOOL_LIT_FALSE | BooleanValue.TRUE
    }


    def 'test visit cast #target to #cast should be of type #expected'() {
        given:
        def type = this.executor.visitCast(cast, target)

        expect:
        type == expected

        where:
        target         | cast                    | expected
        // char
        CHAR_LIT       | Literal.of('byte')      | PrimitiveValue.of((byte) 'a')
        CHAR_LIT       | Literal.of('short')     | PrimitiveValue.of((short) 'a')
        CHAR_LIT       | Literal.of('char')      | PrimitiveValue.of((char) 'a')
        CHAR_LIT       | Literal.of('Character') | ObjectValue.of((Character) 'a')
        CHAR_LIT       | Literal.of('int')       | PrimitiveValue.of((int) 'a')
        CHAR_LIT       | Literal.of('long')      | PrimitiveValue.of((long) 'a')
        CHAR_LIT       | Literal.of('float')     | PrimitiveValue.of((float) 'a')
        CHAR_LIT       | Literal.of('double')    | PrimitiveValue.of((double) 'a')
        CHAR_LIT       | Literal.of('Object')    | ObjectValue.of((char) 'a')
        // number
        NUMBER_LIT     | Literal.of('byte')      | PrimitiveValue.of((byte) 1)
        NUMBER_LIT     | Literal.of('short')     | PrimitiveValue.of((short) 1)
        NUMBER_LIT     | Literal.of('char')      | PrimitiveValue.of((char) 1)
        NUMBER_LIT     | Literal.of('int')       | PrimitiveValue.of((int) 1)
        NUMBER_LIT     | Literal.of('Integer')   | ObjectValue.of((Integer) 1)
        NUMBER_LIT     | Literal.of('long')      | PrimitiveValue.of((long) 1)
        NUMBER_LIT     | Literal.of('float')     | PrimitiveValue.of((float) 1)
        NUMBER_LIT     | Literal.of('double')    | PrimitiveValue.of((double) 1)
        NUMBER_LIT     | Literal.of('Object')    | ObjectValue.of(1)
        // long
        LONG_LIT       | Literal.of('byte')      | PrimitiveValue.of((byte) 2L)
        LONG_LIT       | Literal.of('short')     | PrimitiveValue.of((short) 2L)
        LONG_LIT       | Literal.of('char')      | PrimitiveValue.of((char) 2L)
        LONG_LIT       | Literal.of('int')       | PrimitiveValue.of((int) 2L)
        LONG_LIT       | Literal.of('long')      | PrimitiveValue.of((long) 2L)
        LONG_LIT       | Literal.of('Long')      | ObjectValue.of((long) 2L)
        LONG_LIT       | Literal.of('float')     | PrimitiveValue.of((float) 2L)
        LONG_LIT       | Literal.of('double')    | PrimitiveValue.of((double) 2L)
        LONG_LIT       | Literal.of('Object')    | ObjectValue.of(2L)
        // float
        FLOAT_LIT      | Literal.of('byte')      | PrimitiveValue.of((byte) 3.0f)
        FLOAT_LIT      | Literal.of('short')     | PrimitiveValue.of((short) 3.0f)
        FLOAT_LIT      | Literal.of('char')      | PrimitiveValue.of((char) 3.0f)
        FLOAT_LIT      | Literal.of('int')       | PrimitiveValue.of((int) 3.0f)
        FLOAT_LIT      | Literal.of('long')      | PrimitiveValue.of((long) 3.0f)
        FLOAT_LIT      | Literal.of('float')     | PrimitiveValue.of((float) 3.0f)
        FLOAT_LIT      | Literal.of('Float')     | ObjectValue.of((float) 3.0f)
        FLOAT_LIT      | Literal.of('double')    | PrimitiveValue.of((double) 3.0f)
        FLOAT_LIT      | Literal.of('Object')    | ObjectValue.of(3.0f)
        // double
        DOUBLE_LIT     | Literal.of('byte')      | PrimitiveValue.of((byte) 4.0d)
        DOUBLE_LIT     | Literal.of('short')     | PrimitiveValue.of((short) 4.0d)
        DOUBLE_LIT     | Literal.of('char')      | PrimitiveValue.of((char) 4.0d)
        DOUBLE_LIT     | Literal.of('int')       | PrimitiveValue.of((int) 4.0d)
        DOUBLE_LIT     | Literal.of('long')      | PrimitiveValue.of((long) 4.0d)
        DOUBLE_LIT     | Literal.of('float')     | PrimitiveValue.of((float) 4.0d)
        DOUBLE_LIT     | Literal.of('double')    | PrimitiveValue.of((double) 4.0d)
        DOUBLE_LIT     | Literal.of('Double')    | ObjectValue.of((double) 4.0d)
        DOUBLE_LIT     | Literal.of('Object')    | ObjectValue.of(4.0d)
        // boolean
        BOOL_LIT_TRUE  | Literal.of('boolean')   | PrimitiveValue.of(true)
        BOOL_LIT_FALSE | Literal.of('boolean')   | PrimitiveValue.of(false)
        BOOL_LIT_TRUE  | Literal.of('Boolean')   | ObjectValue.of(true)
        BOOL_LIT_FALSE | Literal.of('Boolean')   | ObjectValue.of(false)
        BOOL_LIT_TRUE  | Literal.of('Object')    | ObjectValue.of(true)
        BOOL_LIT_FALSE | Literal.of('Object')    | ObjectValue.of(false)
        // string
        STRING_LIT     | Literal.of('String')    | ObjectValue.of((String) 'Hello, world!')
        STRING_LIT     | Literal.of('Object')    | ObjectValue.of('Hello, world!')
        // custom class
        //TODO:
//        new NewObject(Literal.of(ExecutorTest.canonicalName),
//                new MethodInvocation([]))                 | Literal.of(Specification.canonicalName) | ObjectValue.of((Specification) this)
//        new NewObject(Literal.of(ExecutorTest.canonicalName),
//                new MethodInvocation([]))                 | Literal.of('Object')                    | ObjectValue.of(this)
    }

    def 'test conversion of #literal should return #expected'() {
        when:
        def converted = literal.accept(this.executor)

        then:
        converted == expected

        where:
        literal                           | expected
        CHAR_LIT                          | PrimitiveValue.of('a' as char)
        // int
        NUMBER_LIT                        | PrimitiveValue.of(1)
        // long
        new LongValueLiteral('10L')       | PrimitiveValue.of(10L as Long)
        new LongValueLiteral('10l')       | PrimitiveValue.of(10l as Long)
        new LongValueLiteral('10')        | PrimitiveValue.of(10 as Long)
        // float
        new FloatValueLiteral('2.1E2f')   | PrimitiveValue.of(2.1E2f as Float)
        new FloatValueLiteral('2.1E-2f')  | PrimitiveValue.of(2.1E-2f as Float)
        new FloatValueLiteral('2.1E2F')   | PrimitiveValue.of(2.1E2F as Float)
        new FloatValueLiteral('2.1E-2F')  | PrimitiveValue.of(2.1E-2F as Float)
        new FloatValueLiteral('2.1E2')    | PrimitiveValue.of(2.1E2 as Float)
        new FloatValueLiteral('2.1E-2')   | PrimitiveValue.of(2.1E-2 as Float)
        new FloatValueLiteral('2.1f')     | PrimitiveValue.of(2.1f as Float)
        new FloatValueLiteral('2.1f')     | PrimitiveValue.of(2.1f as Float)
        new FloatValueLiteral('2.1F')     | PrimitiveValue.of(2.1F as Float)
        new FloatValueLiteral('2.1F')     | PrimitiveValue.of(2.1F as Float)
        new FloatValueLiteral('2.1')      | PrimitiveValue.of(2.1 as Float)
        new FloatValueLiteral('2.1')      | PrimitiveValue.of(2.1 as Float)
        new FloatValueLiteral('2f')       | PrimitiveValue.of(2f as Float)
        new FloatValueLiteral('2f')       | PrimitiveValue.of(2f as Float)
        new FloatValueLiteral('2F')       | PrimitiveValue.of(2F as Float)
        new FloatValueLiteral('2F')       | PrimitiveValue.of(2F as Float)
        new FloatValueLiteral('2')        | PrimitiveValue.of(2 as Float)
        new FloatValueLiteral('2')        | PrimitiveValue.of(2 as Float)
        // double
        new DoubleValueLiteral('2.1E2d')  | PrimitiveValue.of(2.1E2d as Double)
        new DoubleValueLiteral('2.1E-2d') | PrimitiveValue.of(2.1E-2d as Double)
        new DoubleValueLiteral('2.1E2D')  | PrimitiveValue.of(2.1E2D as Double)
        new DoubleValueLiteral('2.1E-2D') | PrimitiveValue.of(2.1E-2D as Double)
        new DoubleValueLiteral('2.1E2')   | PrimitiveValue.of(2.1E2 as Double)
        new DoubleValueLiteral('2.1E-2')  | PrimitiveValue.of(2.1E-2 as Double)
        new DoubleValueLiteral('2.1d')    | PrimitiveValue.of(2.1d as Double)
        new DoubleValueLiteral('2.1d')    | PrimitiveValue.of(2.1d as Double)
        new DoubleValueLiteral('2.1D')    | PrimitiveValue.of(2.1D as Double)
        new DoubleValueLiteral('2.1D')    | PrimitiveValue.of(2.1D as Double)
        new DoubleValueLiteral('2.1')     | PrimitiveValue.of(2.1 as Double)
        new DoubleValueLiteral('2.1')     | PrimitiveValue.of(2.1 as Double)
        new DoubleValueLiteral('2d')      | PrimitiveValue.of(2d as Double)
        new DoubleValueLiteral('2d')      | PrimitiveValue.of(2d as Double)
        new DoubleValueLiteral('2D')      | PrimitiveValue.of(2D as Double)
        new DoubleValueLiteral('2D')      | PrimitiveValue.of(2D as Double)
        new DoubleValueLiteral('2')       | PrimitiveValue.of(2 as Double)
        new DoubleValueLiteral('2')       | PrimitiveValue.of(2 as Double)
        // boolean
        BOOL_LIT_TRUE                     | BooleanValue.TRUE
        BOOL_LIT_FALSE                    | BooleanValue.FALSE
        // String
        STRING_LIT                        | ObjectValue.of('Hello, world!')
    }

}