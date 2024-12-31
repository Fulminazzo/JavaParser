package it.fulminazzo.javaparser.typechecker

import it.fulminazzo.javaparser.parser.node.MethodInvocation
import it.fulminazzo.javaparser.parser.node.container.CodeBlock
import it.fulminazzo.javaparser.parser.node.container.JavaProgram
import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.statements.Break
import it.fulminazzo.javaparser.parser.node.statements.IfStatement
import it.fulminazzo.javaparser.parser.node.statements.Return
import it.fulminazzo.javaparser.parser.node.statements.Statement
import it.fulminazzo.javaparser.parser.node.values.*
import it.fulminazzo.javaparser.typechecker.types.PrimitiveType
import it.fulminazzo.javaparser.typechecker.types.ValueType
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayClassType
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayType
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class TypeCheckerTest extends Specification {
    private static final BOOL_LIT = new BooleanValueLiteral('true')
    private static final CHAR_LIT = new CharValueLiteral('\'a\'')
    private static final NUMBER_LIT = new NumberValueLiteral('1')
    private static final LONG_LIT = new LongValueLiteral('1L')
    private static final FLOAT_LIT = new FloatValueLiteral('1.0f')
    private static final DOUBLE_LIT = new DoubleValueLiteral('1.0d')
    private static final STRING_LIT = new StringValueLiteral('\"Hello, world!\"')

    private TypeChecker typeChecker

    void setup() {
        this.typeChecker = new TypeChecker()
    }

    def 'test visit program #program should return #type'() {
        given:
        def actual = this.typeChecker.visitProgram(program)

        expect:
        actual == type

        where:
        type                          | program
        Optional.of(ValueType.NUMBER) | new JavaProgram(new LinkedList<>([new Return(NUMBER_LIT)]))
        Optional.empty()              | new JavaProgram(new LinkedList<>([new Break()]))
    }

    def 'test visit assignment: #type #name = #val should return type #expected'() {
        given:
        def literalType = Literal.of(type)
        def literalName = Literal.of(name)

        when:
        this.typeChecker.visitAssignment(literalType, literalName, val)
        def value = this.typeChecker.environment.lookup(name)

        then:
        value == expected

        where:
        type        | name  | val        | expected
        'byte'      | 'bc'  | CHAR_LIT   | ValueType.BYTE
        'byte'      | 'b'   | NUMBER_LIT | ValueType.BYTE
        'Byte'      | 'bWc' | CHAR_LIT   | ObjectType.BYTE
        'Byte'      | 'bW'  | NUMBER_LIT | ObjectType.BYTE
        'short'     | 'sc'  | CHAR_LIT   | ValueType.SHORT
        'short'     | 's'   | NUMBER_LIT | ValueType.SHORT
        'Short'     | 'sWc' | CHAR_LIT   | ObjectType.SHORT
        'Short'     | 'sW'  | NUMBER_LIT | ObjectType.SHORT
        'char'      | 'c'   | CHAR_LIT   | ValueType.CHAR
        'char'      | 'ci'  | NUMBER_LIT | ValueType.CHAR
        'Character' | 'cW'  | CHAR_LIT   | ObjectType.CHARACTER
        'Character' | 'ciW' | NUMBER_LIT | ObjectType.CHARACTER
        'int'       | 'ic'  | CHAR_LIT   | ValueType.NUMBER
        'int'       | 'i'   | NUMBER_LIT | ValueType.NUMBER
        'Integer'   | 'iW'  | NUMBER_LIT | ObjectType.INTEGER
        'long'      | 'lc'  | CHAR_LIT   | ValueType.LONG
        'long'      | 'li'  | NUMBER_LIT | ValueType.LONG
        'long'      | 'l'   | LONG_LIT   | ValueType.LONG
        'Long'      | 'lW'  | LONG_LIT   | ObjectType.LONG
        'float'     | 'fc'  | CHAR_LIT   | ValueType.FLOAT
        'float'     | 'fi'  | NUMBER_LIT | ValueType.FLOAT
        'float'     | 'fl'  | LONG_LIT   | ValueType.FLOAT
        'float'     | 'f'   | FLOAT_LIT  | ValueType.FLOAT
        'Float'     | 'fW'  | FLOAT_LIT  | ObjectType.FLOAT
        'double'    | 'dc'  | CHAR_LIT   | ValueType.DOUBLE
        'double'    | 'di'  | NUMBER_LIT | ValueType.DOUBLE
        'double'    | 'dl'  | LONG_LIT   | ValueType.DOUBLE
        'double'    | 'df'  | FLOAT_LIT  | ValueType.DOUBLE
        'double'    | 'd'   | DOUBLE_LIT | ValueType.DOUBLE
        'Double'    | 'dW'  | DOUBLE_LIT | ObjectType.DOUBLE
        'boolean'   | 'bo'  | BOOL_LIT   | ValueType.BOOLEAN
        'Boolean'   | 'boW' | BOOL_LIT   | ObjectType.BOOLEAN
        'String'    | 'st'  | STRING_LIT | ObjectType.STRING
        'Object'    | 'o'   | BOOL_LIT   | ValueType.BOOLEAN
    }

    def 'test visit assignment already declared'() {
        given:
        def varName = 'visit_assignment_declared'
        def type = Literal.of('int')
        def name = Literal.of(varName)
        def value = NUMBER_LIT

        and:
        this.typeChecker.environment.declare(
                type.accept(this.typeChecker).checkClassType(),
                varName,
                value.accept(this.typeChecker)
        )

        when:
        this.typeChecker.visitAssignment(type, name, value)

        then:
        def e = thrown(TypeCheckerException)
        e.message == this.typeChecker.environment.alreadyDeclaredVariable(varName).message
    }

    def 'test visit assignment invalid: #type invalid = #val'() {
        given:
        def errorMessage = TypeCheckerException.invalidType(
                type.accept(this.typeChecker).checkClassType().toType(),
                val.accept(this.typeChecker)
        ).message

        when:
        this.typeChecker.visitAssignment(type, Literal.of('invalid'), val)

        then:
        def e = thrown(TypeCheckerException)
        e.message == errorMessage

        where:
        type                    | val
        Literal.of('String')    | CHAR_LIT
        Literal.of('boolean')   | NUMBER_LIT
        Literal.of('Character') | LONG_LIT
        Literal.of('String')    | FLOAT_LIT
        Literal.of('Integer')   | DOUBLE_LIT
        Literal.of('int')       | BOOL_LIT
        Literal.of('double')    | STRING_LIT
        Literal.of('byte')      | LONG_LIT
        Literal.of('short')     | FLOAT_LIT
    }

    def 'test visit method call: #executor #method #parameters should return #expected'() {
        given:
        //TODO: ValueType does not allow methods like toString or equal
        this.typeChecker.environment.declare(
                ClassObjectType.INTEGER,
                'method_call_val',
                ObjectType.INTEGER
        )
        this.typeChecker.environment.declare(
                ClassObjectType.INTEGER,
                'method_call_val_prim',
                ObjectType.INTEGER
        )

        and:
        def nodeExecutor = Literal.of(executor + method)
        def methodInvocation = new MethodInvocation(parameters)

        when:
        def type = this.typeChecker.visitMethodCall(nodeExecutor, methodInvocation)

        then:
        type == expected

        where:
        executor           | method     | parameters | expected
        'method_call_val.' | 'toString' | []         | ObjectType.STRING
    }

    def 'test visit re-assignment: #name = #val should return type #expected'() {
        given:
        name += '_reassign'
        def literalName = Literal.of(name)

        and:
        this.typeChecker.environment.declare(
                Literal.of(type).accept(this.typeChecker).checkClassType(),
                name, val.accept(this.typeChecker)
        )

        when:
        def value = this.typeChecker.visitReAssign(literalName, val)

        then:
        value == expected

        where:
        type        | name  | val        | expected
        'byte'      | 'bc'  | CHAR_LIT   | ValueType.BYTE
        'byte'      | 'b'   | NUMBER_LIT | ValueType.BYTE
        'Byte'      | 'bWc' | CHAR_LIT   | ObjectType.BYTE
        'Byte'      | 'bW'  | NUMBER_LIT | ObjectType.BYTE
        'short'     | 'sc'  | CHAR_LIT   | ValueType.SHORT
        'short'     | 's'   | NUMBER_LIT | ValueType.SHORT
        'Short'     | 'sWc' | CHAR_LIT   | ObjectType.SHORT
        'Short'     | 'sW'  | NUMBER_LIT | ObjectType.SHORT
        'char'      | 'c'   | CHAR_LIT   | ValueType.CHAR
        'char'      | 'ci'  | NUMBER_LIT | ValueType.CHAR
        'Character' | 'cW'  | CHAR_LIT   | ObjectType.CHARACTER
        'Character' | 'ciW' | NUMBER_LIT | ObjectType.CHARACTER
        'int'       | 'ic'  | CHAR_LIT   | ValueType.NUMBER
        'int'       | 'i'   | NUMBER_LIT | ValueType.NUMBER
        'Integer'   | 'iW'  | NUMBER_LIT | ObjectType.INTEGER
        'long'      | 'lc'  | CHAR_LIT   | ValueType.LONG
        'long'      | 'li'  | NUMBER_LIT | ValueType.LONG
        'long'      | 'l'   | LONG_LIT   | ValueType.LONG
        'Long'      | 'lW'  | LONG_LIT   | ObjectType.LONG
        'float'     | 'fc'  | CHAR_LIT   | ValueType.FLOAT
        'float'     | 'fi'  | NUMBER_LIT | ValueType.FLOAT
        'float'     | 'fl'  | LONG_LIT   | ValueType.FLOAT
        'float'     | 'f'   | FLOAT_LIT  | ValueType.FLOAT
        'Float'     | 'fW'  | FLOAT_LIT  | ObjectType.FLOAT
        'double'    | 'dc'  | CHAR_LIT   | ValueType.DOUBLE
        'double'    | 'di'  | NUMBER_LIT | ValueType.DOUBLE
        'double'    | 'dl'  | LONG_LIT   | ValueType.DOUBLE
        'double'    | 'df'  | FLOAT_LIT  | ValueType.DOUBLE
        'double'    | 'd'   | DOUBLE_LIT | ValueType.DOUBLE
        'Double'    | 'dW'  | DOUBLE_LIT | ObjectType.DOUBLE
        'boolean'   | 'bo'  | BOOL_LIT   | ValueType.BOOLEAN
        'Boolean'   | 'boW' | BOOL_LIT   | ObjectType.BOOLEAN
        'String'    | 'st'  | STRING_LIT | ObjectType.STRING
        'Object'    | 'o'   | BOOL_LIT   | ObjectType.OBJECT
    }

    def 'test visit re-assignment not declared'() {
        given:
        def varName = 'visit_re_assignment_declared'
        def name = Literal.of(varName)
        def value = NUMBER_LIT

        when:
        this.typeChecker.visitReAssign(name, value)

        then:
        def e = thrown(TypeCheckerException)
        e.message == this.typeChecker.environment.noSuchVariable(varName).message
    }

    def 'test re-visit assignment invalid: #type invalid = #val'() {
        given:
        def actualType = type.accept(this.typeChecker).checkClassType()
        def varName = 'invalid_re_assign'
        def errorMessage = TypeCheckerException.invalidType(
                actualType.toType(), newVal.accept(this.typeChecker)
        ).message

        and:
        this.typeChecker.environment.declare(
                actualType,
                varName,
                val.accept(this.typeChecker)
        )

        when:
        this.typeChecker.visitReAssign(Literal.of('invalid_re_assign'), newVal)

        then:
        def e = thrown(TypeCheckerException)
        e.message == errorMessage

        where:
        type                    | val        | newVal
        Literal.of('String')    | STRING_LIT | CHAR_LIT
        Literal.of('boolean')   | BOOL_LIT   | NUMBER_LIT
        Literal.of('Character') | CHAR_LIT   | LONG_LIT
        Literal.of('String')    | STRING_LIT | FLOAT_LIT
        Literal.of('Integer')   | NUMBER_LIT | DOUBLE_LIT
        Literal.of('int')       | NUMBER_LIT | BOOL_LIT
        Literal.of('double')    | DOUBLE_LIT | STRING_LIT
    }

    def 'test convertValue of #classType and #type should return #classType'() {
        when:
        def converted = TypeChecker.convertValue(classType, type)

        then:
        converted == classType.toType()

        where:
        classType << [
                PrimitiveType.values(),
                ClassObjectType.values(),
                ClassObjectType.of(getClass())
        ].flatten()
        type << [
                PrimitiveType.values().collect { it.toType() },
                PrimitiveType.values().collect { it.toType() },
                ValueType.STRING,
                ObjectType.of(Object),
                ObjectType.of(getClass())
        ].flatten()
    }

    def 'test visit dynamic array'() {
        given:
        def type = this.typeChecker.visitDynamicArray(
                Arrays.asList(BOOL_LIT, BOOL_LIT),
                Literal.of('boolean')
        )

        and:
        def expected = new ArrayType(PrimitiveType.BOOLEAN)

        expect:
        type == expected
    }

    def 'test visit static array'() {
        given:
        def type = this.typeChecker.visitStaticArray(
                1,
                Literal.of('boolean')
        )

        and:
        def expected = new ArrayType(PrimitiveType.BOOLEAN)

        expect:
        type == expected
    }

    def 'test visit static array invalid size'() {
        when:
        this.typeChecker.visitStaticArray(
                -1,
                Literal.of('boolean')
        )

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidArraySize(-1).message
    }

    def 'test visit java program'() {
        given:
        def statements = new LinkedList<>([
                new IfStatement(
                        BOOL_LIT,
                        new CodeBlock(new Return(CHAR_LIT)),
                        new Statement()
                ),
                new Return(NUMBER_LIT)
        ])

        when:
        def type = this.typeChecker.visitJavaProgram(statements)

        then:
        type == ValueType.NUMBER
    }

    def 'test visit code block'() {
        given:
        def statements = new LinkedList<>([
                new IfStatement(
                        BOOL_LIT,
                        new CodeBlock(new Return(CHAR_LIT)),
                        new Statement()
                ),
                new Return(NUMBER_LIT)
        ])

        when:
        def type = this.typeChecker.visitCodeBlock(statements)

        then:
        type == ValueType.NUMBER
    }

    def 'test visit array literal'() {
        when:
        def type = this.typeChecker.visitArrayLiteral(Literal.of('Integer'))

        then:
        type == new ArrayClassType(ClassObjectType.INTEGER)
    }

    def 'test decrement for #literal should return #expected'() {
        given:
        def type = this.typeChecker.visitDecrement(true, literal)

        expect:
        type == expected

        where:
        literal     | expected
        NUMBER_LIT  | ValueType.NUMBER
        CHAR_LIT    | ValueType.CHAR
        LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test increment for #literal should return #expected'() {
        given:
        def type = this.typeChecker.visitIncrement(false, literal)

        expect:
        type == expected

        where:
        literal     | expected
        NUMBER_LIT  | ValueType.NUMBER
        CHAR_LIT    | ValueType.CHAR
        LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test equal'() {
        given:
        def type = this.typeChecker.visitEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test not equal'() {
        given:
        def type = this.typeChecker.visitNotEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test less than'() {
        given:
        def type = this.typeChecker.visitLessThan(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test less than equal'() {
        given:
        def type = this.typeChecker.visitLessThanEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test greater than'() {
        given:
        def type = this.typeChecker.visitGreaterThan(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test greater than equal'() {
        given:
        def type = this.typeChecker.visitGreaterThanEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test valid and'() {
        given:
        def type = this.typeChecker.visitAnd(BOOL_LIT, BOOL_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test invalid and between #first and #second'() {
        when:
        this.typeChecker.visitAnd(first, second)

        then:
        thrown(TypeCheckerException)

        where:
        first      | second
        BOOL_LIT   | NUMBER_LIT
        NUMBER_LIT | BOOL_LIT
        NUMBER_LIT | NUMBER_LIT
    }

    def 'test valid or'() {
        given:
        def type = this.typeChecker.visitOr(BOOL_LIT, BOOL_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test invalid or between #first and #second'() {
        when:
        this.typeChecker.visitOr(first, second)

        then:
        thrown(TypeCheckerException)

        where:
        first      | second
        BOOL_LIT   | NUMBER_LIT
        NUMBER_LIT | BOOL_LIT
        NUMBER_LIT | NUMBER_LIT
    }

    def 'test visit bit and of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitBitAnd(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        BOOL_LIT   | BOOL_LIT   | ValueType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit bit or of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitBitOr(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        BOOL_LIT   | BOOL_LIT   | ValueType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit bit xor of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitBitXor(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        BOOL_LIT   | BOOL_LIT   | ValueType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit lshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitLShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit rshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitRShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit urshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitURShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit add of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitAdd(first, second)

        then:
        type == expected

        where:
        first       | second      | expected
        CHAR_LIT    | CHAR_LIT    | ValueType.NUMBER
        NUMBER_LIT  | NUMBER_LIT  | ValueType.NUMBER
        LONG_LIT    | LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | DOUBLE_LIT  | ValueType.DOUBLE
        STRING_LIT  | STRING_LIT  | ValueType.STRING
    }

    def 'test visit subtract of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitSubtract(first, second)

        then:
        type == expected

        where:
        first       | second      | expected
        CHAR_LIT    | CHAR_LIT    | ValueType.NUMBER
        NUMBER_LIT  | NUMBER_LIT  | ValueType.NUMBER
        LONG_LIT    | LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test visit multiply of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitMultiply(first, second)

        then:
        type == expected

        where:
        first       | second      | expected
        CHAR_LIT    | CHAR_LIT    | ValueType.NUMBER
        NUMBER_LIT  | NUMBER_LIT  | ValueType.NUMBER
        LONG_LIT    | LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test visit divide of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitDivide(first, second)

        then:
        type == expected

        where:
        first       | second      | expected
        CHAR_LIT    | CHAR_LIT    | ValueType.NUMBER
        NUMBER_LIT  | NUMBER_LIT  | ValueType.NUMBER
        LONG_LIT    | LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test visit modulo of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitModulo(first, second)

        then:
        type == expected

        where:
        first       | second      | expected
        CHAR_LIT    | CHAR_LIT    | ValueType.NUMBER
        NUMBER_LIT  | NUMBER_LIT  | ValueType.NUMBER
        LONG_LIT    | LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test visit minus for #literal should return #expected'() {
        given:
        def type = this.typeChecker.visitMinus(literal)

        expect:
        type == expected

        where:
        literal     | expected
        NUMBER_LIT  | ValueType.NUMBER
        CHAR_LIT    | ValueType.NUMBER
        LONG_LIT    | ValueType.LONG
        FLOAT_LIT   | ValueType.FLOAT
        DOUBLE_LIT  | ValueType.DOUBLE
    }

    def 'test visit not'() {
        given:
        def type = this.typeChecker.visitNot(BOOL_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test visit cast #target to #cast should be of type #expected'() {
        given:
        def type = this.typeChecker.visitCast(cast, target)

        expect:
        type == expected

        where:
        target     | cast                    | expected
        // char
        CHAR_LIT   | Literal.of('byte')      | ValueType.BYTE
        CHAR_LIT   | Literal.of('short')     | ValueType.SHORT
        CHAR_LIT   | Literal.of('char')      | ValueType.CHAR
        CHAR_LIT   | Literal.of('Character') | ObjectType.CHARACTER
        CHAR_LIT   | Literal.of('int')       | ValueType.NUMBER
        CHAR_LIT   | Literal.of('long')      | ValueType.LONG
        CHAR_LIT   | Literal.of('float')     | ValueType.FLOAT
        CHAR_LIT   | Literal.of('double')    | ValueType.DOUBLE
        CHAR_LIT   | Literal.of('Object')    | ObjectType.OBJECT
        // number
        NUMBER_LIT | Literal.of('byte')      | ValueType.BYTE
        NUMBER_LIT | Literal.of('short')     | ValueType.SHORT
        NUMBER_LIT | Literal.of('char')      | ValueType.CHAR
        NUMBER_LIT | Literal.of('int')       | ValueType.NUMBER
        NUMBER_LIT | Literal.of('Integer')   | ObjectType.INTEGER
        NUMBER_LIT | Literal.of('long')      | ValueType.LONG
        NUMBER_LIT | Literal.of('float')     | ValueType.FLOAT
        NUMBER_LIT | Literal.of('double')    | ValueType.DOUBLE
        NUMBER_LIT | Literal.of('Object')    | ObjectType.OBJECT
        // long
        LONG_LIT   | Literal.of('byte')      | ValueType.BYTE
        LONG_LIT   | Literal.of('short')     | ValueType.SHORT
        LONG_LIT   | Literal.of('char')      | ValueType.CHAR
        LONG_LIT   | Literal.of('int')       | ValueType.NUMBER
        LONG_LIT   | Literal.of('long')      | ValueType.LONG
        LONG_LIT   | Literal.of('Long')      | ObjectType.LONG
        LONG_LIT   | Literal.of('float')     | ValueType.FLOAT
        LONG_LIT   | Literal.of('double')    | ValueType.DOUBLE
        LONG_LIT   | Literal.of('Object')    | ObjectType.OBJECT
        // float
        FLOAT_LIT  | Literal.of('byte')      | ValueType.BYTE
        FLOAT_LIT  | Literal.of('short')     | ValueType.SHORT
        FLOAT_LIT  | Literal.of('char')      | ValueType.CHAR
        FLOAT_LIT  | Literal.of('int')       | ValueType.NUMBER
        FLOAT_LIT  | Literal.of('long')      | ValueType.LONG
        FLOAT_LIT  | Literal.of('float')     | ValueType.FLOAT
        FLOAT_LIT  | Literal.of('Float')     | ObjectType.FLOAT
        FLOAT_LIT  | Literal.of('double')    | ValueType.DOUBLE
        FLOAT_LIT  | Literal.of('Object')    | ObjectType.OBJECT
        // double
        DOUBLE_LIT | Literal.of('byte')      | ValueType.BYTE
        DOUBLE_LIT | Literal.of('short')     | ValueType.SHORT
        DOUBLE_LIT | Literal.of('char')      | ValueType.CHAR
        DOUBLE_LIT | Literal.of('int')       | ValueType.NUMBER
        DOUBLE_LIT | Literal.of('long')      | ValueType.LONG
        DOUBLE_LIT | Literal.of('float')     | ValueType.FLOAT
        DOUBLE_LIT | Literal.of('double')    | ValueType.DOUBLE
        DOUBLE_LIT | Literal.of('Double')    | ObjectType.DOUBLE
        DOUBLE_LIT | Literal.of('Object')    | ObjectType.OBJECT
        // boolean
        BOOL_LIT   | Literal.of('boolean')   | ValueType.BOOLEAN
        BOOL_LIT   | Literal.of('Boolean')   | ObjectType.BOOLEAN
        BOOL_LIT   | Literal.of('Object')    | ObjectType.OBJECT
        // string
        STRING_LIT | Literal.of('String')    | ObjectType.STRING
        STRING_LIT | Literal.of('Object')    | ObjectType.OBJECT
        // custom class
        //TODO: first ObjectType should be replaced with Literal values, but visitNew method is required
//        ObjectType.of(TypeCheckerTest) | Literal.of(Specification.canonicalName) | ObjectType.of(Specification)
//        ObjectType.of(TypeCheckerTest) | Literal.of('Object')                    | ObjectType.OBJECT
    }

    def 'test invalid visit cast #target to #cast'() {
        given:
        this.typeChecker.environment.declare(PrimitiveType.INT, 'cast', ValueType.NUMBER)

        and:
        def expectedMessage = TypeCheckerException.invalidCast(cast.equals(Literal.of('String')) ?
                ClassObjectType.STRING : PrimitiveType.INT, expected).message

        when:
        this.typeChecker.visitCast(cast, target)

        then:
        def e = thrown(TypeCheckerException)
        e.message == expectedMessage

        where:
        target             | cast                 | expected
        CHAR_LIT           | Literal.of('String') | ValueType.CHAR
        NUMBER_LIT         | Literal.of('String') | ValueType.NUMBER
        LONG_LIT           | Literal.of('String') | ValueType.LONG
        FLOAT_LIT          | Literal.of('String') | ValueType.FLOAT
        DOUBLE_LIT         | Literal.of('String') | ValueType.DOUBLE
        STRING_LIT         | Literal.of('int')    | ValueType.STRING
        Literal.of('cast') | Literal.of('String') | ValueType.NUMBER
    }

}
