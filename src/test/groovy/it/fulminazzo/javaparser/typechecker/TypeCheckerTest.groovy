package it.fulminazzo.javaparser.typechecker

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.environment.MockEnvironment
import it.fulminazzo.javaparser.environment.ScopeException
import it.fulminazzo.javaparser.environment.ScopeType
import it.fulminazzo.javaparser.parser.node.Assignment
import it.fulminazzo.javaparser.parser.node.MethodInvocation
import it.fulminazzo.javaparser.parser.node.container.CodeBlock
import it.fulminazzo.javaparser.parser.node.container.JavaProgram
import it.fulminazzo.javaparser.parser.node.literals.ArrayLiteral
import it.fulminazzo.javaparser.parser.node.literals.EmptyLiteral
import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.operators.unary.Increment
import it.fulminazzo.javaparser.parser.node.statements.Break
import it.fulminazzo.javaparser.parser.node.statements.IfStatement
import it.fulminazzo.javaparser.parser.node.statements.Return
import it.fulminazzo.javaparser.parser.node.statements.Statement
import it.fulminazzo.javaparser.parser.node.values.*
import it.fulminazzo.javaparser.typechecker.types.*
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayClassType
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayType
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

import java.util.concurrent.Callable

class TypeCheckerTest extends Specification {
    private static final BOOL_LIT = new BooleanValueLiteral('true')
    private static final CHAR_LIT = new CharValueLiteral('\'a\'')
    private static final NUMBER_LIT = new NumberValueLiteral('1')
    private static final LONG_LIT = new LongValueLiteral('1L')
    private static final FLOAT_LIT = new FloatValueLiteral('1.0f')
    private static final DOUBLE_LIT = new DoubleValueLiteral('1.0d')
    private static final STRING_LIT = new StringValueLiteral('\"Hello, world!\"')
    private static final BOOL_VAR = Literal.of('bool')

    private TypeChecker typeChecker
    private MockEnvironment environment

    void setup() {
        this.typeChecker = new TypeChecker(new TestClass())
        this.environment = new MockEnvironment<>()
        new Refl<>(this.typeChecker).setFieldObject('environment', this.environment)
        this.environment.declare(
                ClassObjectType.BOOLEAN,
                'bool',
                ObjectType.BOOLEAN,
        )
    }

    def 'parse test_program file'() {
        given:
        def cwd = System.getProperty('user.dir')

        and:
        def file = new File(cwd, 'build/resources/test/typechecker_test_program.dat')
        JavaProgram program = file.newObjectInputStream().readObject() as JavaProgram

        when:
        program.accept(this.typeChecker)

        then:
        noExceptionThrown()
    }

    def 'test visit program #program should return #type'() {
        given:
        def actual = this.typeChecker.visitProgram(program)

        expect:
        actual == type

        where:
        type                          | program
        Optional.of(ValueType.NUMBER) | new JavaProgram(new LinkedList<>([new Return(NUMBER_LIT)]))
        Optional.empty()              | new JavaProgram(new LinkedList<>([new Statement()]))
    }

    def 'test visit enhanced for statement of (#expr) #codeBlock should return #expected'() {
        given:
        this.environment.declare(new ArrayClassType(PrimitiveType.INT), 'arr', new ArrayType(ValueType.NUMBER))
        this.environment.declare(ClassObjectType.of(Iterable), 'iterable', ObjectType.of(Iterable))
        this.environment.declare(ClassObjectType.of(List), 'list', ObjectType.of(List))
        this.environment.declare(ClassObjectType.of(Collection), 'collection', ObjectType.of(Collection))
        this.environment.declare(ClassObjectType.of(Set), 'set', ObjectType.of(Set))

        and:
        def varType = Literal.of('int')
        def varName = Literal.of('i')

        when:
        def type = this.typeChecker.visitEnhancedForStatement(varType, varName, codeBlock, expr)

        then:
        type == expected
        this.environment.enteredScope(ScopeType.FOR)
        this.environment.isMainScope()

        where:
        expected          | codeBlock                           | expr
        ValueType.BOOLEAN | new CodeBlock(new Return(BOOL_LIT)) | Literal.of('arr')
        ValueType.BOOLEAN | new CodeBlock(new Return(BOOL_LIT)) | Literal.of('iterable')
        ValueType.BOOLEAN | new CodeBlock(new Return(BOOL_LIT)) | Literal.of('list')
        ValueType.BOOLEAN | new CodeBlock(new Return(BOOL_LIT)) | Literal.of('set')
        ValueType.BOOLEAN | new CodeBlock(new Return(BOOL_LIT)) | Literal.of('collection')
        Types.NO_TYPE | new CodeBlock(new Break()) | Literal.of('arr')
        Types.NO_TYPE | new CodeBlock(new Break()) | Literal.of('iterable')
        Types.NO_TYPE | new CodeBlock(new Break()) | Literal.of('list')
        Types.NO_TYPE | new CodeBlock(new Break()) | Literal.of('set')
        Types.NO_TYPE | new CodeBlock(new Break()) | Literal.of('collection')
    }

    def 'test visit enhanced for statement of non-iterable'() {
        given:
        def varType = Literal.of('int')
        def varName = Literal.of('i')

        when:
        this.typeChecker.visitEnhancedForStatement(varType, varName, new CodeBlock(new Break()), NUMBER_LIT)

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidType(ClassObjectType.of(Iterable), ValueType.NUMBER).message
    }

    def 'test visit enhanced for statement of array with invalid type'() {
        given:
        def classType = new ArrayClassType(PrimitiveType.INT)
        this.environment.declare(classType, 'arr', new ArrayType(ValueType.NUMBER))

        and:
        def varType = Literal.of('boolean')
        def varName = Literal.of('i')

        when:
        this.typeChecker.visitEnhancedForStatement(varType, varName, new CodeBlock(new Break()), Literal.of('arr'))

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidType(classType.getComponentType(), PrimitiveType.BOOLEAN).message
    }

    def 'test visit for statement of (#expr) #codeBlock should return #expected'() {
        given:
        def assignment = new Assignment(Literal.of('int'), Literal.of('i'), NUMBER_LIT)
        def increment = new Increment(Literal.of('i'), false)

        when:
        def type = this.typeChecker.visitForStatement(assignment, increment, codeBlock, expr)

        then:
        type == expected
        this.environment.enteredScope(ScopeType.FOR)
        this.environment.isMainScope()

        where:
        expected          | codeBlock                           | expr
        ValueType.BOOLEAN | new CodeBlock(new Return(BOOL_LIT)) | BOOL_LIT
        ValueType.BOOLEAN | new CodeBlock(new Return(BOOL_LIT)) | BOOL_VAR
        Types.NO_TYPE | new CodeBlock(new Break()) | BOOL_LIT
        Types.NO_TYPE | new CodeBlock(new Break()) | BOOL_VAR
    }

    def 'test visit do statement of (#expr) #codeBlock should return #expected'() {
        when:
        def type = this.typeChecker.visitDoStatement(codeBlock, expr)

        then:
        type == expected
        this.environment.enteredScope(ScopeType.DO)
        this.environment.isMainScope()

        where:
        expected          | codeBlock                           | expr
        ValueType.BOOLEAN | new CodeBlock(new Return(BOOL_LIT)) | BOOL_LIT
        ValueType.BOOLEAN | new CodeBlock(new Return(BOOL_LIT)) | BOOL_VAR
        Types.NO_TYPE | new CodeBlock(new Break()) | BOOL_LIT
        Types.NO_TYPE | new CodeBlock(new Break()) | BOOL_VAR
    }

    def 'test visit while statement of (#expr) #codeBlock should return #expected'() {
        when:
        def type = this.typeChecker.visitWhileStatement(codeBlock, expr)

        then:
        type == expected
        this.environment.enteredScope(ScopeType.WHILE)
        this.environment.isMainScope()

        where:
        expected          | codeBlock                           | expr
        ValueType.BOOLEAN | new CodeBlock(new Return(BOOL_LIT)) | BOOL_LIT
        ValueType.BOOLEAN | new CodeBlock(new Return(BOOL_LIT)) | BOOL_VAR
        Types.NO_TYPE | new CodeBlock(new Break()) | BOOL_LIT
        Types.NO_TYPE | new CodeBlock(new Break()) | BOOL_VAR
    }

    def 'test visit if statement of code "#code" should return #expected'() {
        given:
        def refl = new Refl<>(code)

        and:
        def expr = refl.getFieldObject('expr')
        def then = refl.getFieldObject('then')
        def elseBranch = refl.getFieldObject('elseBranch')

        when:
        def type = this.typeChecker.visitIfStatement(then, elseBranch, expr)
        this.environment.enteredScope(ScopeType.CODE_BLOCK)
        this.environment.isMainScope()

        then:
        type == expected

        where:
        expected          | code
        ValueType.BOOLEAN | new IfStatement(BOOL_LIT, new CodeBlock(new Return(BOOL_LIT)), new Statement())
        ValueType.BOOLEAN | new IfStatement(BOOL_VAR, new CodeBlock(new Return(BOOL_LIT)), new Statement())
        Types.NO_TYPE | new IfStatement(BOOL_LIT, new CodeBlock(new Statement()), new Statement())
        Types.NO_TYPE | new IfStatement(BOOL_VAR, new CodeBlock(new Statement()), new Statement())
        ValueType.NUMBER  | new IfStatement(BOOL_LIT, new CodeBlock(new Return(NUMBER_LIT)),
                new IfStatement(BOOL_LIT, new CodeBlock(new Return(NUMBER_LIT)), new Statement()))
        ValueType.NUMBER  | new IfStatement(BOOL_VAR, new CodeBlock(new Return(NUMBER_LIT)),
                new IfStatement(BOOL_VAR, new CodeBlock(new Return(NUMBER_LIT)), new Statement()))
        Types.NO_TYPE | new IfStatement(BOOL_LIT, new CodeBlock(new Return(NUMBER_LIT)),
                new IfStatement(BOOL_LIT, new CodeBlock(new Return(FLOAT_LIT)), new Statement()))
        Types.NO_TYPE | new IfStatement(BOOL_VAR, new CodeBlock(new Return(NUMBER_LIT)),
                new IfStatement(BOOL_VAR, new CodeBlock(new Return(FLOAT_LIT)), new Statement()))
        ValueType.DOUBLE  | new IfStatement(BOOL_LIT, new CodeBlock(new Return(DOUBLE_LIT)),
                new IfStatement(BOOL_LIT, new CodeBlock(new Return(DOUBLE_LIT)),
                        new CodeBlock(new Return(DOUBLE_LIT))))
        ValueType.DOUBLE  | new IfStatement(BOOL_VAR, new CodeBlock(new Return(DOUBLE_LIT)),
                new IfStatement(BOOL_VAR, new CodeBlock(new Return(DOUBLE_LIT)),
                        new CodeBlock(new Return(DOUBLE_LIT))))
        Types.NO_TYPE | new IfStatement(BOOL_LIT, new CodeBlock(new Return(DOUBLE_LIT)),
                new IfStatement(BOOL_LIT, new CodeBlock(new Return(FLOAT_LIT)),
                        new CodeBlock(new Return(LONG_LIT))))
        Types.NO_TYPE | new IfStatement(BOOL_VAR, new CodeBlock(new Return(DOUBLE_LIT)),
                new IfStatement(BOOL_VAR, new CodeBlock(new Return(FLOAT_LIT)),
                        new CodeBlock(new Return(LONG_LIT))))
    }

    def 'test visit assignment: #type #name = #val should return type #expected'() {
        given:
        def literalType = Literal.of(type)
        def literalName = Literal.of(name)

        when:
        this.typeChecker.visitAssignment(literalType, literalName, val)
        def value = this.environment.lookup(name)

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

    def 'test visit assignment: #type #name should return type #expected'() {
        given:
        def literalType = Literal.of(type)
        def literalName = Literal.of(name)

        when:
        this.typeChecker.visitAssignment(literalType, literalName, new EmptyLiteral())
        def value = this.environment.lookup(name)

        then:
        value == expected

        where:
        type        | name  | expected
        'byte'      | 'bc'  | ValueType.BYTE
        'byte'      | 'b'   | ValueType.BYTE
        'Byte'      | 'bWc' | Types.NULL_TYPE
        'Byte'      | 'bW'  | Types.NULL_TYPE
        'short'     | 'sc'  | ValueType.SHORT
        'short'     | 's'   | ValueType.SHORT
        'Short'     | 'sWc' | Types.NULL_TYPE
        'Short'     | 'sW'  | Types.NULL_TYPE
        'char'      | 'c'   | ValueType.CHAR
        'char'      | 'ci'  | ValueType.CHAR
        'Character' | 'cW'  | Types.NULL_TYPE
        'Character' | 'ciW' | Types.NULL_TYPE
        'int'       | 'ic'  | ValueType.NUMBER
        'int'       | 'i'   | ValueType.NUMBER
        'Integer'   | 'iW'  | Types.NULL_TYPE
        'long'      | 'lc'  | ValueType.LONG
        'long'      | 'li'  | ValueType.LONG
        'long'      | 'l'   | ValueType.LONG
        'Long'      | 'lW'  | Types.NULL_TYPE
        'float'     | 'fc'  | ValueType.FLOAT
        'float'     | 'fi'  | ValueType.FLOAT
        'float'     | 'fl'  | ValueType.FLOAT
        'float'     | 'f'   | ValueType.FLOAT
        'Float'     | 'fW'  | Types.NULL_TYPE
        'double'    | 'dc'  | ValueType.DOUBLE
        'double'    | 'di'  | ValueType.DOUBLE
        'double'    | 'dl'  | ValueType.DOUBLE
        'double'    | 'df'  | ValueType.DOUBLE
        'double'    | 'd'   | ValueType.DOUBLE
        'Double'    | 'dW'  | Types.NULL_TYPE
        'boolean'   | 'bo'  | ValueType.BOOLEAN
        'Boolean'   | 'boW' | Types.NULL_TYPE
        'String'    | 'st'  | Types.NULL_TYPE
        'Object'    | 'o'   | Types.NULL_TYPE
    }

    def 'test visit assignment already declared'() {
        given:
        def varName = 'visit_assignment_declared'
        def type = Literal.of('int')
        def name = Literal.of(varName)
        def value = NUMBER_LIT

        and:
        this.environment.declare(
                type.accept(this.typeChecker).checkClassType(),
                varName,
                value.accept(this.typeChecker)
        )

        when:
        this.typeChecker.visitAssignment(type, name, value)

        then:
        def e = thrown(TypeCheckerException)
        e.message == ScopeException.alreadyDeclaredVariable(varName).message
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

    def 'test visit new object #parameters'() {
        given:
        def nodeExecutor = Literal.of(TestClass.canonicalName)
        def methodInvocation = new MethodInvocation(parameters)

        when:
        def type = this.typeChecker.visitNewObject(nodeExecutor, methodInvocation)

        then:
        type == ObjectType.of(TestClass)

        where:
        parameters << [
            [],
            [NUMBER_LIT, BOOL_LIT]
        ]
    }

    def 'test type exception visit new object'() {
        given:
        def methodInvocation = new MethodInvocation([BOOL_LIT, DOUBLE_LIT])

        and:
        def exceptionMessage = TypeException.methodNotFound(
                ClassObjectType.INTEGER, '<init>',
                new ParameterTypes([PrimitiveType.BOOLEAN, PrimitiveType.DOUBLE])
        ).message

        when:
        this.typeChecker.visitNewObject(Literal.of('Integer'), methodInvocation)

        then:
        def e = thrown(TypeCheckerException)
        e.message == exceptionMessage
    }

    def 'test visit method call: #executor #method #parameters should return #expected'() {
        given:
        this.environment.declare(
                ClassObjectType.INTEGER,
                'method_call_val',
                ObjectType.INTEGER
        )
        this.environment.declare(
                PrimitiveType.INT,
                'method_call_val_prim',
                ValueType.NUMBER
        )

        and:
        def nodeExecutor = executor.isEmpty() ? new EmptyLiteral() : Literal.of(executor)
        def methodInvocation = new MethodInvocation(parameters)

        when:
        def type = this.typeChecker.visitMethodCall(nodeExecutor, method, methodInvocation)

        then:
        type == expected

        where:
        executor                | method               | parameters             | expected
        ''                      | 'publicMethod'       | []                     | ValueType.DOUBLE
        ''                      | 'publicMethod'       | [DOUBLE_LIT, BOOL_LIT] | ValueType.DOUBLE
        ''                      | 'publicStaticMethod' | []                     | ValueType.NUMBER
        ''                      | 'publicStaticMethod' | [NUMBER_LIT, BOOL_LIT] | ValueType.NUMBER
        'method_call_val'       | 'toString'           | []                     | ObjectType.STRING
        'method_call_val_prim'  | 'toString'           | []                     | ObjectType.STRING
    }

    def 'test type exception visit method call'() {
        given:
        def classType = ClassObjectType.INTEGER
        this.environment.declare(
                classType,
                'method_call_invalid_val',
                ObjectType.INTEGER
        )

        and:
        def nodeExecutor = Literal.of('method_call_invalid_val')
        def methodInvocation = new MethodInvocation([])

        and:
        def exceptionMessage = TypeException.methodNotFound(
                classType, 'nonExisting', new ParameterTypes([])
        ).message

        when:
        this.typeChecker.visitMethodCall(nodeExecutor, 'nonExisting', methodInvocation)

        then:
        def e = thrown(TypeCheckerException)
        e.message == exceptionMessage
    }

    def 'test visit field of #field should return #expected'() {
        given:
        this.environment.declare(
                ClassObjectType.of(TestClass),
                'field_var',
                ObjectType.of(TestClass)
        )

        and:
        def left = Literal.of('field_var')
        def right = Literal.of(field)

        when:
        def type = this.typeChecker.visitField(left, right)

        then:
        type == expected

        where:
        field               | expected
        'publicStaticField' | PrimitiveType.INT
        'publicField'       | PrimitiveType.DOUBLE
    }

    def 'test invalid visit field of #field should throw exception'() {
        given:
        def classType = ClassObjectType.of(TestClass)

        and:
        this.environment.declare(
                classType,
                'field_var_invalid',
                ObjectType.of(TestClass)
        )
        def exceptionMessage = TypeException.cannotAccessField(
                classType,
                TestClass.getDeclaredField(field)
        ).message

        and:
        def left = Literal.of('field_var_invalid')
        def right = Literal.of(field)

        when:
        this.typeChecker.visitField(left, right)

        then:
        def e = thrown(TypeCheckerException)
        e.message == exceptionMessage

        where:
        field << [
                'packageStaticField', 'protectedStaticField', 'privateStaticField',
                'packageField', 'protectedField', 'privateField'
        ]
    }

    def 'test visit re-assignment: #name = #val should return type #expected'() {
        given:
        name += '_reassign'
        def literalName = Literal.of(name)

        and:
        this.environment.declare(
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
        e.message == ScopeException.noSuchVariable(varName).message
    }

    def 'test re-visit assignment invalid: #type invalid = #val'() {
        given:
        def actualType = type.accept(this.typeChecker).checkClassType()
        def varName = 'invalid_re_assign'
        def errorMessage = TypeCheckerException.invalidType(
                actualType.toType(), newVal.accept(this.typeChecker)
        ).message

        and:
        this.environment.declare(
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
                new ArrayLiteral(Literal.of('boolean'))
        )

        and:
        def expected = new ArrayType(ValueType.BOOLEAN)

        expect:
        type == expected
    }

    def 'test visit static array'() {
        given:
        def type = this.typeChecker.visitStaticArray(
                1,
                new ArrayLiteral(Literal.of('boolean'))
        )

        and:
        def expected = new ArrayType(ValueType.BOOLEAN)

        expect:
        type == expected
    }

    def 'test visit static array invalid size'() {
        when:
        this.typeChecker.visitStaticArray(
                -1,
                new ArrayLiteral(Literal.of('boolean'))
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
        this.environment.enteredScope(ScopeType.CODE_BLOCK)
        this.environment.isMainScope()
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

    def 'test visit break with scope #scope should not throw exception'() {
        given:
        this.environment.enterScope(scope)

        when:
        this.typeChecker.visitBreak(new EmptyLiteral())
        this.environment.exitScope()

        then:
        noExceptionThrown()

        where:
        scope << [
                ScopeType.WHILE, ScopeType.DO,
                ScopeType.FOR, ScopeType.SWITCH,
        ]
    }

    def 'test invalid visit break with scope #scope should throw exception'() {
        given:
        this.environment.enterScope(scope)

        and:
        def exceptionMessage = ScopeException.scopeTypeMismatch(TypeChecker.BREAK_SCOPES).message

        when:
        this.typeChecker.visitBreak(new EmptyLiteral())
        this.environment.exitScope()

        then:
        def e = thrown(TypeCheckerException)
        e.message == exceptionMessage

        where:
        scope << [
                ScopeType.MAIN, ScopeType.CODE_BLOCK
        ]
    }

    def 'test visit continue with scope #scope should not throw exception'() {
        given:
        this.environment.enterScope(scope)

        when:
        this.typeChecker.visitContinue(new EmptyLiteral())
        this.environment.exitScope()

        then:
        noExceptionThrown()

        where:
        scope << [
                ScopeType.WHILE, ScopeType.DO,
                ScopeType.FOR,
        ]
    }

    def 'test invalid visit continue with scope #scope should throw exception'() {
        given:
        this.environment.enterScope(scope)

        and:
        def exceptionMessage = ScopeException.scopeTypeMismatch(TypeChecker.CONTINUE_SCOPES).message

        when:
        this.typeChecker.visitContinue(new EmptyLiteral())
        this.environment.exitScope()

        then:
        def e = thrown(TypeCheckerException)
        e.message == exceptionMessage

        where:
        scope << [
                ScopeType.MAIN, ScopeType.CODE_BLOCK,
                ScopeType.SWITCH
        ]
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
        this.environment.declare(PrimitiveType.INT, 'cast', ValueType.NUMBER)

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

    def 'test visitScoped with exception #exception should throw #expected'() {
        given:
        Callable<Type> function = () -> {
            throw exception.newInstance('')
        }

        when:
        this.typeChecker.visitScoped(ScopeType.CODE_BLOCK, function)

        then:
        thrown(expected)

        where:
        exception                | expected
        IllegalArgumentException | IllegalArgumentException
        IOException              | TypeCheckerException
    }

}
