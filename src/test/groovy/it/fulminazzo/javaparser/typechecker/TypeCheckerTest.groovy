package it.fulminazzo.javaparser.typechecker

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.environment.MockEnvironment
import it.fulminazzo.javaparser.environment.ScopeException
import it.fulminazzo.javaparser.environment.scopetypes.ScopeType
import it.fulminazzo.javaparser.parser.node.Assignment
import it.fulminazzo.javaparser.parser.node.AssignmentBlock
import it.fulminazzo.javaparser.parser.node.MethodInvocation
import it.fulminazzo.javaparser.parser.node.container.CodeBlock
import it.fulminazzo.javaparser.parser.node.container.JavaProgram
import it.fulminazzo.javaparser.parser.node.literals.ArrayLiteral
import it.fulminazzo.javaparser.parser.node.literals.EmptyLiteral
import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.literals.NullLiteral
import it.fulminazzo.javaparser.parser.node.operators.binary.NewObject
import it.fulminazzo.javaparser.parser.node.operators.unary.Increment
import it.fulminazzo.javaparser.parser.node.statements.*
import it.fulminazzo.javaparser.parser.node.values.*
import it.fulminazzo.javaparser.typechecker.types.*
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayClassType
import it.fulminazzo.javaparser.typechecker.types.arrays.ArrayType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectClassType
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

    private static final RETURN_BOOL = new Return(BOOL_LIT)
    private static final RETURN_CHAR = new Return(CHAR_LIT)
    private static final RETURN_NUMBER = new Return(NUMBER_LIT)
    private static final RETURN_LONG = new Return(LONG_LIT)
    private static final RETURN_FLOAT = new Return(FLOAT_LIT)
    private static final RETURN_DOUBLE = new Return(DOUBLE_LIT)

    private static final CODE_BLOCK_BOOL = new CodeBlock(RETURN_BOOL)
    private static final CODE_BLOCK_CHAR = new CodeBlock(RETURN_CHAR)
    private static final CODE_BLOCK_NUMBER = new CodeBlock(RETURN_NUMBER)
    private static final CODE_BLOCK_LONG = new CodeBlock(RETURN_LONG)
    private static final CODE_BLOCK_FLOAT = new CodeBlock(RETURN_FLOAT)
    private static final CODE_BLOCK_DOUBLE = new CodeBlock(RETURN_DOUBLE)
    private static final CODE_BLOCK_BREAK = new CodeBlock(new Break())
    private static final CODE_BLOCK_EMPTY = new CodeBlock()

    private static final BOOL_VAR = Literal.of('bool')

    private TypeChecker typeChecker
    private MockEnvironment environment

    void setup() {
        this.typeChecker = new TypeChecker(new TestClass())
        this.environment = new MockEnvironment<>()
        new Refl<>(this.typeChecker).setFieldObject('environment', this.environment)
        this.environment.declare(
                ObjectClassType.BOOLEAN,
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
        type                              | program
        Optional.of(PrimitiveType.INT) | new JavaProgram(new LinkedList<>([RETURN_NUMBER]))
        Optional.empty()                  | new JavaProgram(new LinkedList<>([new Statement()]))
    }

    def 'test visit try statement: (#expression) #block #catchBlocks #finallyBlock should return #expected'() {
        when:
        def type = this.typeChecker.visitTryStatement(block, catchBlocks, finallyBlock, expression)

        then:
        type == expected

        where:
        expression                                                                                               | block           | catchBlocks                                                                           | finallyBlock      | expected
        // Everything
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral()),
                new Assignment(Literal.of(OutputStream.canonicalName), Literal.of('output'), new NullLiteral())
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_NUMBER | PrimitiveType.INT
        // Everything no finally
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral()),
                new Assignment(Literal.of(OutputStream.canonicalName), Literal.of('output'), new NullLiteral())
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_EMPTY  | PrimitiveType.BOOLEAN
        // Everything with different types
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral()),
                new Assignment(Literal.of(OutputStream.canonicalName), Literal.of('output'), new NullLiteral())
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_DOUBLE),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_FLOAT)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_NUMBER | PrimitiveType.INT
        // Everything with different types and no finally
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral()),
                new Assignment(Literal.of(OutputStream.canonicalName), Literal.of('output'), new NullLiteral())
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_DOUBLE),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_FLOAT)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_EMPTY  | Types.NO_TYPE
        // Just one assignment
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral())
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_NUMBER | PrimitiveType.INT
        // Just one assignment no finally
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral())
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_EMPTY  | PrimitiveType.BOOLEAN
        // Just one assignment with different types
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral())
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_DOUBLE),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_FLOAT)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_NUMBER | PrimitiveType.INT
        // Just one assignment with different types and no finally
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral())
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_DOUBLE),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_FLOAT)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_EMPTY  | Types.NO_TYPE
        // No assignments
        new AssignmentBlock([
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_NUMBER | PrimitiveType.INT
        // No assignments no finally
        new AssignmentBlock([
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_EMPTY  | PrimitiveType.BOOLEAN
        // No assignments with different types
        new AssignmentBlock([
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_DOUBLE),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_FLOAT)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_NUMBER | PrimitiveType.INT
        // No assignments with different types and no finally
        new AssignmentBlock([
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_DOUBLE),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_FLOAT)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_EMPTY  | Types.NO_TYPE
        // Just one catch
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral()),
                new Assignment(Literal.of(OutputStream.canonicalName), Literal.of('output'), new NullLiteral()),
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_NUMBER | PrimitiveType.INT
        // Just one catch no finally
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral()),
                new Assignment(Literal.of(OutputStream.canonicalName), Literal.of('output'), new NullLiteral()),
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_BOOL)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_EMPTY  | PrimitiveType.BOOLEAN
        // Just one catch with different types
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral()),
                new Assignment(Literal.of(OutputStream.canonicalName), Literal.of('output'), new NullLiteral()),
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_DOUBLE)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_NUMBER | PrimitiveType.INT
        // Just one catch with different types and no finally
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral()),
                new Assignment(Literal.of(OutputStream.canonicalName), Literal.of('output'), new NullLiteral()),
        ])                                                                                                       | CODE_BLOCK_BOOL | [
                new CatchStatement([Literal.of(IOException.canonicalName)], Literal.of('e'),
                        CODE_BLOCK_DOUBLE)
        ]                                                                                                                                                                                                                  | CODE_BLOCK_EMPTY  | Types.NO_TYPE
        // No catches
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral()),
                new Assignment(Literal.of(OutputStream.canonicalName), Literal.of('output'), new NullLiteral()),
        ])                                                                                                       | CODE_BLOCK_BOOL | [
        ]                                                                                                                                                                                                                  | CODE_BLOCK_NUMBER | PrimitiveType.INT
        // No catches with different types and no finally
        new AssignmentBlock([
                new Assignment(Literal.of(InputStream.canonicalName), Literal.of('input'), new NullLiteral()),
                new Assignment(Literal.of(OutputStream.canonicalName), Literal.of('output'), new NullLiteral()),
        ])                                                                                                       | CODE_BLOCK_BOOL | [
        ]                                                                                                                                                                                                                  | CODE_BLOCK_EMPTY  | PrimitiveType.BOOLEAN
    }

    def 'test visit try statement with no auto-closable'() {
        when:
        this.typeChecker.visitTryStatement(CODE_BLOCK_EMPTY, [], CODE_BLOCK_EMPTY, new AssignmentBlock([
                new Assignment(Literal.of('int'), Literal.of('i'), NUMBER_LIT)
        ]))

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidType(ClassType.of(AutoCloseable.class), PrimitiveClassType.INT).message
    }

    def 'test visit try statement of already caught: #catchBlocks'() {
        when:
        this.typeChecker.visitTryStatement(CODE_BLOCK_EMPTY, catchBlocks, CODE_BLOCK_EMPTY, new AssignmentBlock([]))

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.exceptionAlreadyCaught(expected).message

        where:
        expected                               | catchBlocks
        ClassType.of(RuntimeException)         | [
                new CatchStatement([Literal.of(RuntimeException.canonicalName)], Literal.of('e'), CODE_BLOCK_EMPTY),
                new CatchStatement([Literal.of(RuntimeException.canonicalName)], Literal.of('e'), CODE_BLOCK_EMPTY)
        ]
        ClassType.of(IllegalArgumentException) | [
                new CatchStatement([Literal.of(RuntimeException.canonicalName)], Literal.of('e'), CODE_BLOCK_EMPTY),
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'), CODE_BLOCK_EMPTY)
        ]
    }

    def 'test visit try statement of not caught but extended should not throw'() {
        when:
        this.typeChecker.visitTryStatement(CODE_BLOCK_EMPTY, [
                new CatchStatement([Literal.of(IllegalArgumentException.canonicalName)], Literal.of('e'), CODE_BLOCK_EMPTY),
                new CatchStatement([Literal.of(RuntimeException.canonicalName)], Literal.of('e'), CODE_BLOCK_EMPTY),
        ], CODE_BLOCK_EMPTY, new AssignmentBlock([]))

        then:
        notThrown(TypeCheckerException)
    }

    def 'test visit catch statement: (#exceptions #variable) #block should return #expected'() {
        when:
        def type = this.typeChecker.visitCatchStatement(exceptions, block, variable)

        then:
        type == expected
        this.environment.enteredScope(ScopeType.CATCH)

        where:
        exceptions                                                                                                      | variable | block             | expected
        [Literal.of('IllegalArgumentException'), Literal.of('IllegalStateException'), Literal.of('IllegalAccessError')] |
                Literal.of('e')                                                                                                    | CODE_BLOCK_NUMBER |
                new TupleType<>(new LinkedHashSet<>([ClassType.of(IllegalArgumentException), ClassType.of(IllegalStateException),
                                                     ClassType.of(IllegalAccessError)]), PrimitiveType.INT)
        [Literal.of('IllegalArgumentException'), Literal.of('IllegalStateException')]                                   |
                Literal.of('e')                                                                                                    | CODE_BLOCK_NUMBER |
                new TupleType<>(new LinkedHashSet<>([ClassType.of(IllegalArgumentException), ClassType.of(IllegalStateException)]),
                        PrimitiveType.INT)
        [Literal.of('IllegalArgumentException')]                                                                        |
                Literal.of('e')                                                                                                    | CODE_BLOCK_NUMBER |
                new TupleType<>(new LinkedHashSet<>([ClassType.of(IllegalArgumentException)]), PrimitiveType.INT)
    }

    def 'test visit invalid catch statement: (#exceptions #variable) should throw #expected'() {
        given:
        this.environment.declare(ObjectClassType.STRING, 'f', ObjectType.STRING)

        when:
        this.typeChecker.visitCatchStatement(exceptions, CODE_BLOCK_EMPTY, variable)

        then:
        def e = thrown(TypeCheckerException)
        e.message == expected.message

        where:
        exceptions                                                               | variable        | expected
        [Literal.of('String')]                                                   | Literal.of('e') |
                TypeCheckerException.invalidType(ClassType.of(Throwable.class), ObjectType.STRING)
        [Literal.of('Exception'), Literal.of('Exception')]                       | Literal.of('e') |
                TypeCheckerException.exceptionAlreadyCaught(ObjectClassType.of('Exception'))
        [Literal.of('IllegalArgumentException'), Literal.of('RuntimeException')] | Literal.of('e') |
                TypeCheckerException.exceptionsNotDisjoint(ClassType.of(IllegalArgumentException),
                        ClassType.of(RuntimeException))
        [Literal.of('RuntimeException'), Literal.of('IllegalArgumentException')] | Literal.of('e') |
                TypeCheckerException.exceptionsNotDisjoint(ClassType.of(IllegalArgumentException),
                        ClassType.of(RuntimeException))
        [Literal.of('Exception')]                                                | Literal.of('f') |
                TypeCheckerException.of(ScopeException.alreadyDeclaredVariable('f'))
    }

    def 'test visit switch statement of #expression (#cases, #defaultBlock) should return #expected'() {
        when:
        def type = this.typeChecker.visitSwitchStatement(cases, defaultBlock, expression)

        then:
        type == expected
        this.environment.enteredScope(ScopeType.SWITCH)
        this.environment.isMainScope()

        where:
        expression | cases                                                                                                | defaultBlock | expected
        NUMBER_LIT | [new CaseStatement(NUMBER_LIT, CODE_BLOCK_NUMBER), new CaseStatement(NUMBER_LIT, CODE_BLOCK_NUMBER)] |
                CODE_BLOCK_NUMBER                                                                                                        | PrimitiveType.INT
        NUMBER_LIT | [new CaseStatement(NUMBER_LIT, CODE_BLOCK_NUMBER), new CaseStatement(NUMBER_LIT, CODE_BLOCK_DOUBLE)] |
                CODE_BLOCK_BOOL                                                                                                          | Types.NO_TYPE
        NUMBER_LIT | [new CaseStatement(NUMBER_LIT, CODE_BLOCK_NUMBER)]                                                   |
                CODE_BLOCK_NUMBER                                                                                                        | PrimitiveType.INT
        NUMBER_LIT | [new CaseStatement(NUMBER_LIT, CODE_BLOCK_NUMBER)]                                                   |
                CODE_BLOCK_BOOL                                                                                                          | Types.NO_TYPE
        NUMBER_LIT | []                                                                                                   |
                CODE_BLOCK_NUMBER                                                                                                        | PrimitiveType.INT
        NUMBER_LIT | [new CaseStatement(NUMBER_LIT, CODE_BLOCK_NUMBER)]                                                   |
                CODE_BLOCK_EMPTY                                                                                                         | Types.NO_TYPE
        NUMBER_LIT | []                                                                                                   |
                CODE_BLOCK_EMPTY                                                                                                         | Types.NO_TYPE
    }

    def 'test invalid visit switch statement of expression #expression should throw exception with #expected'() {
        when:
        this.typeChecker.visitSwitchStatement([], CODE_BLOCK_EMPTY, expression)

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidUnexpectedType(expected).message

        where:
        expression         | expected
        new NullLiteral()  | Types.NULL_TYPE
        new EmptyLiteral() | Types.NO_TYPE
    }

    def 'test visit enhanced for statement of (#expression) #codeBlock should return #expected'() {
        given:
        this.environment.declare(new ArrayClassType(PrimitiveClassType.INT), 'arr', new ArrayType(PrimitiveType.INT))
        this.environment.declare(ObjectClassType.of(Iterable), 'iterable', ObjectType.of(Iterable))
        this.environment.declare(ObjectClassType.of(List), 'list', ObjectType.of(List))
        this.environment.declare(ObjectClassType.of(Collection), 'collection', ObjectType.of(Collection))
        this.environment.declare(ObjectClassType.of(Set), 'set', ObjectType.of(Set))

        and:
        def varType = Literal.of('int')
        def varName = Literal.of('i')

        when:
        def type = this.typeChecker.visitEnhancedForStatement(varType, varName, codeBlock, expression)

        then:
        type == expected
        this.environment.enteredScope(ScopeType.FOR)
        this.environment.isMainScope()

        where:
        expected              | codeBlock        | expression
        PrimitiveType.BOOLEAN | CODE_BLOCK_BOOL  | Literal.of('arr')
        PrimitiveType.BOOLEAN | CODE_BLOCK_BOOL  | Literal.of('iterable')
        PrimitiveType.BOOLEAN | CODE_BLOCK_BOOL  | Literal.of('list')
        PrimitiveType.BOOLEAN | CODE_BLOCK_BOOL  | Literal.of('set')
        PrimitiveType.BOOLEAN | CODE_BLOCK_BOOL  | Literal.of('collection')
        Types.NO_TYPE         | CODE_BLOCK_BREAK | Literal.of('arr')
        Types.NO_TYPE         | CODE_BLOCK_BREAK | Literal.of('iterable')
        Types.NO_TYPE         | CODE_BLOCK_BREAK | Literal.of('list')
        Types.NO_TYPE         | CODE_BLOCK_BREAK | Literal.of('set')
        Types.NO_TYPE         | CODE_BLOCK_BREAK | Literal.of('collection')
    }

    def 'test visit enhanced for statement of non-iterable'() {
        given:
        def varType = Literal.of('int')
        def varName = Literal.of('i')

        when:
        this.typeChecker.visitEnhancedForStatement(varType, varName, CODE_BLOCK_BREAK, NUMBER_LIT)

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidType(ObjectClassType.of(Iterable), PrimitiveType.INT).message
    }

    def 'test visit enhanced for statement of array with invalid type'() {
        given:
        def classType = new ArrayClassType(PrimitiveClassType.INT)
        this.environment.declare(classType, 'arr', new ArrayType(PrimitiveType.INT))

        and:
        def varType = Literal.of('boolean')
        def varName = Literal.of('i')

        when:
        this.typeChecker.visitEnhancedForStatement(varType, varName, CODE_BLOCK_BREAK, Literal.of('arr'))

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidType(classType.getComponentType(), PrimitiveClassType.BOOLEAN).message
    }

    def 'test visit for statement of (#expression) #codeBlock should return #expected'() {
        given:
        def assignment = new Assignment(Literal.of('int'), Literal.of('i'), NUMBER_LIT)
        def increment = new Increment(Literal.of('i'), false)

        when:
        def type = this.typeChecker.visitForStatement(assignment, increment, codeBlock, expression)

        then:
        type == expected
        this.environment.enteredScope(ScopeType.FOR)
        this.environment.isMainScope()

        where:
        expected              | codeBlock        | expression
        PrimitiveType.BOOLEAN | CODE_BLOCK_BOOL  | BOOL_LIT
        PrimitiveType.BOOLEAN | CODE_BLOCK_BOOL  | BOOL_VAR
        Types.NO_TYPE         | CODE_BLOCK_BREAK | BOOL_LIT
        Types.NO_TYPE         | CODE_BLOCK_BREAK | BOOL_VAR
    }

    def 'test visit do statement of (#expression) #codeBlock should return #expected'() {
        when:
        def type = this.typeChecker.visitDoStatement(codeBlock, expression)

        then:
        type == expected
        this.environment.enteredScope(ScopeType.DO)
        this.environment.isMainScope()

        where:
        expected              | codeBlock        | expression
        PrimitiveType.BOOLEAN | CODE_BLOCK_BOOL  | BOOL_LIT
        PrimitiveType.BOOLEAN | CODE_BLOCK_BOOL  | BOOL_VAR
        Types.NO_TYPE         | CODE_BLOCK_BREAK | BOOL_LIT
        Types.NO_TYPE         | CODE_BLOCK_BREAK | BOOL_VAR
    }

    def 'test visit while statement of (#expression) #codeBlock should return #expected'() {
        when:
        def type = this.typeChecker.visitWhileStatement(codeBlock, expression)

        then:
        type == expected
        this.environment.enteredScope(ScopeType.WHILE)
        this.environment.isMainScope()

        where:
        expected              | codeBlock        | expression
        PrimitiveType.BOOLEAN | CODE_BLOCK_BOOL  | BOOL_LIT
        PrimitiveType.BOOLEAN | CODE_BLOCK_BOOL  | BOOL_VAR
        Types.NO_TYPE         | CODE_BLOCK_BREAK | BOOL_LIT
        Types.NO_TYPE         | CODE_BLOCK_BREAK | BOOL_VAR
    }

    def 'test visit if statement of code "#code" should return #expected'() {
        given:
        def refl = new Refl<>(code)

        and:
        def expression = refl.getFieldObject('expression')
        def then = refl.getFieldObject('then')
        def elseBranch = refl.getFieldObject('elseBranch')

        when:
        def type = this.typeChecker.visitIfStatement(then, elseBranch, expression)
        this.environment.enteredScope(ScopeType.CODE_BLOCK)
        this.environment.isMainScope()

        then:
        type == expected

        where:
        expected              | code
        PrimitiveType.BOOLEAN | new IfStatement(BOOL_LIT, CODE_BLOCK_BOOL, new EmptyLiteral())
        PrimitiveType.BOOLEAN | new IfStatement(BOOL_VAR, CODE_BLOCK_BOOL, new EmptyLiteral())
        Types.NO_TYPE         | new IfStatement(BOOL_LIT, CODE_BLOCK_EMPTY, new EmptyLiteral())
        Types.NO_TYPE         | new IfStatement(BOOL_VAR, CODE_BLOCK_EMPTY, new EmptyLiteral())
        PrimitiveType.INT | new IfStatement(BOOL_LIT, CODE_BLOCK_NUMBER,
                new IfStatement(BOOL_LIT, CODE_BLOCK_NUMBER, new EmptyLiteral()))
        PrimitiveType.INT | new IfStatement(BOOL_VAR, CODE_BLOCK_NUMBER,
                new IfStatement(BOOL_VAR, CODE_BLOCK_NUMBER, new EmptyLiteral()))
        Types.NO_TYPE         | new IfStatement(BOOL_LIT, CODE_BLOCK_NUMBER,
                new IfStatement(BOOL_LIT, CODE_BLOCK_FLOAT, new EmptyLiteral()))
        Types.NO_TYPE         | new IfStatement(BOOL_VAR, CODE_BLOCK_NUMBER,
                new IfStatement(BOOL_VAR, CODE_BLOCK_FLOAT, new EmptyLiteral()))
        PrimitiveType.DOUBLE  | new IfStatement(BOOL_LIT, CODE_BLOCK_DOUBLE,
                new IfStatement(BOOL_LIT, CODE_BLOCK_DOUBLE,
                        CODE_BLOCK_DOUBLE))
        PrimitiveType.DOUBLE  | new IfStatement(BOOL_VAR, CODE_BLOCK_DOUBLE,
                new IfStatement(BOOL_VAR, CODE_BLOCK_DOUBLE,
                        CODE_BLOCK_DOUBLE))
        Types.NO_TYPE         | new IfStatement(BOOL_LIT, CODE_BLOCK_DOUBLE,
                new IfStatement(BOOL_LIT, CODE_BLOCK_FLOAT,
                        CODE_BLOCK_LONG))
        Types.NO_TYPE         | new IfStatement(BOOL_VAR, CODE_BLOCK_DOUBLE,
                new IfStatement(BOOL_VAR, CODE_BLOCK_FLOAT,
                        CODE_BLOCK_LONG))
    }

    def 'test visit of throw statement of exception #exception should not throw'() {
        given:
        this.environment.enterScope(ScopeType.tryScope([Exception].stream()))

        when:
        this.typeChecker.visitThrow(new NewObject(Literal.of(exception.canonicalName), new MethodInvocation([])))

        then:
        noExceptionThrown()

        where:
        exception << [RuntimeException, IllegalArgumentException, IOException, Exception]
    }

    def 'test visit of throw statement of exception #exception should throw'() {
        when:
        this.typeChecker.visitThrow(new NewObject(Literal.of(exception.canonicalName), new MethodInvocation([])))

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.unhandledException(ClassType.of(exception)).message

        where:
        exception << [IOException, Exception]
    }

    def 'test visit of throw statement of no exception should throw'() {
        when:
        this.typeChecker.visitThrow(new NewObject(Literal.of('String'), new MethodInvocation([])))

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidType(ClassType.of(Throwable), ObjectType.STRING).message
    }

    def 'test visit assignment block of #assignments should return #expected'() {
        when:
        def type = this.typeChecker.visitAssignmentBlock(assignments)

        then:
        type == expected

        where:
        assignments                                                                     | expected
        [
                new Assignment(Literal.of('byte'), Literal.of('b'), NUMBER_LIT),
                new Assignment(Literal.of('Byte'), Literal.of('bW'), NUMBER_LIT),
                new Assignment(Literal.of('HashMap'), Literal.of('map'),
                        new NewObject(Literal.of('HashMap'), new MethodInvocation([])))
        ]                                                                               | new ParameterTypes([PrimitiveType.BYTE, ObjectType.BYTE, ObjectType.of('HashMap')])
        [
                new Assignment(Literal.of('byte'), Literal.of('b'), NUMBER_LIT),
                new Assignment(Literal.of('Byte'), Literal.of('bW'), NUMBER_LIT)
        ]                                                                               | new ParameterTypes([PrimitiveType.BYTE, ObjectType.BYTE])
        [
                new Assignment(Literal.of('byte'), Literal.of('b'), NUMBER_LIT)
        ]                                                                               | new ParameterTypes([PrimitiveType.BYTE])
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
        'byte'      | 'bc'  | CHAR_LIT   | PrimitiveType.BYTE
        'byte'      | 'b'   | NUMBER_LIT | PrimitiveType.BYTE
        'Byte'      | 'bWc' | CHAR_LIT   | ObjectType.BYTE
        'Byte'      | 'bW'  | NUMBER_LIT | ObjectType.BYTE
        'short'     | 'sc'  | CHAR_LIT   | PrimitiveType.SHORT
        'short'     | 's'   | NUMBER_LIT | PrimitiveType.SHORT
        'Short'     | 'sWc' | CHAR_LIT   | ObjectType.SHORT
        'Short'     | 'sW'  | NUMBER_LIT | ObjectType.SHORT
        'char'      | 'c'   | CHAR_LIT   | PrimitiveType.CHAR
        'char'      | 'ci'  | NUMBER_LIT | PrimitiveType.CHAR
        'Character' | 'cW'  | CHAR_LIT   | ObjectType.CHARACTER
        'Character' | 'ciW' | NUMBER_LIT | ObjectType.CHARACTER
        'int'       | 'ic'  | CHAR_LIT   | PrimitiveType.INT
        'int'       | 'i'   | NUMBER_LIT | PrimitiveType.INT
        'Integer'   | 'iW'  | NUMBER_LIT | ObjectType.INTEGER
        'long'      | 'lc'  | CHAR_LIT   | PrimitiveType.LONG
        'long'      | 'li'  | NUMBER_LIT | PrimitiveType.LONG
        'long'      | 'l'   | LONG_LIT   | PrimitiveType.LONG
        'Long'      | 'lW'  | LONG_LIT   | ObjectType.LONG
        'float'     | 'fc'  | CHAR_LIT   | PrimitiveType.FLOAT
        'float'     | 'fi'  | NUMBER_LIT | PrimitiveType.FLOAT
        'float'     | 'fl'  | LONG_LIT   | PrimitiveType.FLOAT
        'float'     | 'f'   | FLOAT_LIT  | PrimitiveType.FLOAT
        'Float'     | 'fW'  | FLOAT_LIT  | ObjectType.FLOAT
        'double'    | 'dc'  | CHAR_LIT   | PrimitiveType.DOUBLE
        'double'    | 'di'  | NUMBER_LIT | PrimitiveType.DOUBLE
        'double'    | 'dl'  | LONG_LIT   | PrimitiveType.DOUBLE
        'double'    | 'df'  | FLOAT_LIT  | PrimitiveType.DOUBLE
        'double'    | 'd'   | DOUBLE_LIT | PrimitiveType.DOUBLE
        'Double'    | 'dW'  | DOUBLE_LIT | ObjectType.DOUBLE
        'boolean'   | 'bo'  | BOOL_LIT   | PrimitiveType.BOOLEAN
        'Boolean'   | 'boW' | BOOL_LIT   | ObjectType.BOOLEAN
        'String'    | 'st'  | STRING_LIT | ObjectType.STRING
        'Object'    | 'o'   | BOOL_LIT   | PrimitiveType.BOOLEAN
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
        'byte'      | 'b'   | PrimitiveType.BYTE
        'Byte'      | 'bW'  | Types.NULL_TYPE
        'short'     | 's'   | PrimitiveType.SHORT
        'Short'     | 'sW'  | Types.NULL_TYPE
        'char'      | 'c'   | PrimitiveType.CHAR
        'Character' | 'cW'  | Types.NULL_TYPE
        'int'       | 'i'   | PrimitiveType.INT
        'Integer'   | 'iW'  | Types.NULL_TYPE
        'long'      | 'l'   | PrimitiveType.LONG
        'Long'      | 'lW'  | Types.NULL_TYPE
        'float'     | 'f'   | PrimitiveType.FLOAT
        'Float'     | 'fW'  | Types.NULL_TYPE
        'double'    | 'd'   | PrimitiveType.DOUBLE
        'Double'    | 'dW'  | Types.NULL_TYPE
        'boolean'   | 'bo'  | PrimitiveType.BOOLEAN
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
                type.accept(this.typeChecker).checkClass(),
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
                type.accept(this.typeChecker).checkClass(),
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
                ObjectClassType.INTEGER, '<init>',
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
                ObjectClassType.INTEGER,
                'method_call_val',
                ObjectType.INTEGER
        )
        this.environment.declare(
                PrimitiveClassType.INT,
                'method_call_val_prim',
                PrimitiveType.INT
        )

        and:
        def nodeExecutor = executor.isEmpty() ? new EmptyLiteral() : Literal.of(executor)
        def methodInvocation = new MethodInvocation(parameters)

        when:
        def type = this.typeChecker.visitMethodCall(nodeExecutor, method, methodInvocation)

        then:
        type == expected

        where:
        executor               | method               | parameters             | expected
        ''                     | 'publicMethod'       | []                     | PrimitiveType.DOUBLE
        ''                     | 'publicMethod'       | [DOUBLE_LIT, BOOL_LIT] | PrimitiveType.DOUBLE
        ''                     | 'publicStaticMethod' | []                     | PrimitiveType.INT
        ''                     | 'publicStaticMethod' | [NUMBER_LIT, BOOL_LIT] | PrimitiveType.INT
        'method_call_val'      | 'toString'           | []                     | ObjectType.STRING
        'method_call_val_prim' | 'toString'           | []                     | ObjectType.STRING
    }

    def 'test type exception visit method call'() {
        given:
        def classType = ObjectClassType.INTEGER
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
                ObjectClassType.of(TestClass),
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
        'publicStaticField' | PrimitiveClassType.INT
        'publicField'       | PrimitiveClassType.DOUBLE
    }

    def 'test invalid visit field of #field should throw exception'() {
        given:
        def classType = ObjectClassType.of(TestClass)

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
                Literal.of(type).accept(this.typeChecker).checkClass(),
                name, val.accept(this.typeChecker)
        )

        when:
        def value = this.typeChecker.visitReAssign(literalName, val)

        then:
        value == expected

        where:
        type        | name  | val        | expected
        'byte'      | 'bc'  | CHAR_LIT   | PrimitiveType.BYTE
        'byte'      | 'b'   | NUMBER_LIT | PrimitiveType.BYTE
        'Byte'      | 'bWc' | CHAR_LIT   | ObjectType.BYTE
        'Byte'      | 'bW'  | NUMBER_LIT | ObjectType.BYTE
        'short'     | 'sc'  | CHAR_LIT   | PrimitiveType.SHORT
        'short'     | 's'   | NUMBER_LIT | PrimitiveType.SHORT
        'Short'     | 'sWc' | CHAR_LIT   | ObjectType.SHORT
        'Short'     | 'sW'  | NUMBER_LIT | ObjectType.SHORT
        'char'      | 'c'   | CHAR_LIT   | PrimitiveType.CHAR
        'char'      | 'ci'  | NUMBER_LIT | PrimitiveType.CHAR
        'Character' | 'cW'  | CHAR_LIT   | ObjectType.CHARACTER
        'Character' | 'ciW' | NUMBER_LIT | ObjectType.CHARACTER
        'int'       | 'ic'  | CHAR_LIT   | PrimitiveType.INT
        'int'       | 'i'   | NUMBER_LIT | PrimitiveType.INT
        'Integer'   | 'iW'  | NUMBER_LIT | ObjectType.INTEGER
        'long'      | 'lc'  | CHAR_LIT   | PrimitiveType.LONG
        'long'      | 'li'  | NUMBER_LIT | PrimitiveType.LONG
        'long'      | 'l'   | LONG_LIT   | PrimitiveType.LONG
        'Long'      | 'lW'  | LONG_LIT   | ObjectType.LONG
        'float'     | 'fc'  | CHAR_LIT   | PrimitiveType.FLOAT
        'float'     | 'fi'  | NUMBER_LIT | PrimitiveType.FLOAT
        'float'     | 'fl'  | LONG_LIT   | PrimitiveType.FLOAT
        'float'     | 'f'   | FLOAT_LIT  | PrimitiveType.FLOAT
        'Float'     | 'fW'  | FLOAT_LIT  | ObjectType.FLOAT
        'double'    | 'dc'  | CHAR_LIT   | PrimitiveType.DOUBLE
        'double'    | 'di'  | NUMBER_LIT | PrimitiveType.DOUBLE
        'double'    | 'dl'  | LONG_LIT   | PrimitiveType.DOUBLE
        'double'    | 'df'  | FLOAT_LIT  | PrimitiveType.DOUBLE
        'double'    | 'd'   | DOUBLE_LIT | PrimitiveType.DOUBLE
        'Double'    | 'dW'  | DOUBLE_LIT | ObjectType.DOUBLE
        'boolean'   | 'bo'  | BOOL_LIT   | PrimitiveType.BOOLEAN
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
        def actualType = type.accept(this.typeChecker).checkClass()
        def varName = 'invalid_re_assign'
        def errorMessage = TypeCheckerException.invalidType(
                actualType, newVal.accept(this.typeChecker)
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
                PrimitiveClassType.values(),
                ObjectClassType.values(),
                ObjectClassType.of(getClass())
        ].flatten()
        type << [
                PrimitiveClassType.values().collect { it.toType() },
                PrimitiveClassType.values().collect { it.toType() },
                ObjectType.STRING,
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
                        CODE_BLOCK_CHAR,
                        new EmptyLiteral()
                ),
                RETURN_NUMBER
        ])

        when:
        def type = this.typeChecker.visitJavaProgram(statements)

        then:
        type == PrimitiveType.INT
    }

    def 'test visit code block'() {
        given:
        def statements = new LinkedList<>([
                new IfStatement(
                        BOOL_LIT,
                        CODE_BLOCK_CHAR,
                        new EmptyLiteral()
                ),
                RETURN_NUMBER
        ])

        when:
        def type = this.typeChecker.visitCodeBlock(statements)

        then:
        type == PrimitiveType.INT
        this.environment.enteredScope(ScopeType.CODE_BLOCK)
        this.environment.isMainScope()
    }

    def 'test visit array literal'() {
        when:
        def type = this.typeChecker.visitArrayLiteral(Literal.of('Integer'))

        then:
        type == new ArrayClassType(ObjectClassType.INTEGER)
    }

    def 'test decrement should return #expected'() {
        given:
        def variableName = 'i'
        this.environment.declare(expected.toClass(), variableName, expected)

        and:
        def type = this.typeChecker.visitDecrement(true, Literal.of(variableName))

        expect:
        type == expected

        where:
        expected << [PrimitiveType.CHAR, PrimitiveType.INT, PrimitiveType.LONG, PrimitiveType.FLOAT, PrimitiveType.DOUBLE]
    }

    def 'test increment should return #expected'() {
        given:
        def variableName = 'i'
        this.environment.declare(expected.toClass(), variableName, expected)

        and:
        def type = this.typeChecker.visitIncrement(false, Literal.of(variableName))

        expect:
        type == expected

        where:
        expected << [PrimitiveType.CHAR, PrimitiveType.INT, PrimitiveType.LONG, PrimitiveType.FLOAT, PrimitiveType.DOUBLE]
    }

    def 'test equal'() {
        given:
        def type = this.typeChecker.visitEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == PrimitiveType.BOOLEAN
    }

    def 'test not equal'() {
        given:
        def type = this.typeChecker.visitNotEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == PrimitiveType.BOOLEAN
    }

    def 'test less than'() {
        given:
        def type = this.typeChecker.visitLessThan(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == PrimitiveType.BOOLEAN
    }

    def 'test less than equal'() {
        given:
        def type = this.typeChecker.visitLessThanEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == PrimitiveType.BOOLEAN
    }

    def 'test greater than'() {
        given:
        def type = this.typeChecker.visitGreaterThan(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == PrimitiveType.BOOLEAN
    }

    def 'test greater than equal'() {
        given:
        def type = this.typeChecker.visitGreaterThanEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == PrimitiveType.BOOLEAN
    }

    def 'test valid and'() {
        given:
        def type = this.typeChecker.visitAnd(BOOL_LIT, BOOL_LIT)

        expect:
        type == PrimitiveType.BOOLEAN
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
        type == PrimitiveType.BOOLEAN
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
        BOOL_LIT   | BOOL_LIT   | PrimitiveType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | PrimitiveType.INT
        NUMBER_LIT | NUMBER_LIT | PrimitiveType.INT
        LONG_LIT   | LONG_LIT   | PrimitiveType.LONG
    }

    def 'test visit bit or of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitBitOr(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        BOOL_LIT   | BOOL_LIT   | PrimitiveType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | PrimitiveType.INT
        NUMBER_LIT | NUMBER_LIT | PrimitiveType.INT
        LONG_LIT   | LONG_LIT   | PrimitiveType.LONG
    }

    def 'test visit bit xor of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitBitXor(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        BOOL_LIT   | BOOL_LIT   | PrimitiveType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | PrimitiveType.INT
        NUMBER_LIT | NUMBER_LIT | PrimitiveType.INT
        LONG_LIT   | LONG_LIT   | PrimitiveType.LONG
    }

    def 'test visit lshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitLShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | PrimitiveType.INT
        NUMBER_LIT | NUMBER_LIT | PrimitiveType.INT
        LONG_LIT   | LONG_LIT   | PrimitiveType.LONG
    }

    def 'test visit rshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitRShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | PrimitiveType.INT
        NUMBER_LIT | NUMBER_LIT | PrimitiveType.INT
        LONG_LIT   | LONG_LIT   | PrimitiveType.LONG
    }

    def 'test visit urshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitURShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | PrimitiveType.INT
        NUMBER_LIT | NUMBER_LIT | PrimitiveType.INT
        LONG_LIT   | LONG_LIT   | PrimitiveType.LONG
    }

    def 'test visit add of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitAdd(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | PrimitiveType.INT
        NUMBER_LIT | NUMBER_LIT | PrimitiveType.INT
        LONG_LIT   | LONG_LIT   | PrimitiveType.LONG
        FLOAT_LIT  | FLOAT_LIT  | PrimitiveType.FLOAT
        DOUBLE_LIT | DOUBLE_LIT | PrimitiveType.DOUBLE
        STRING_LIT | STRING_LIT | ObjectType.STRING
    }

    def 'test visit subtract of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitSubtract(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | PrimitiveType.INT
        NUMBER_LIT | NUMBER_LIT | PrimitiveType.INT
        LONG_LIT   | LONG_LIT   | PrimitiveType.LONG
        FLOAT_LIT  | FLOAT_LIT  | PrimitiveType.FLOAT
        DOUBLE_LIT | DOUBLE_LIT | PrimitiveType.DOUBLE
    }

    def 'test visit multiply of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitMultiply(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | PrimitiveType.INT
        NUMBER_LIT | NUMBER_LIT | PrimitiveType.INT
        LONG_LIT   | LONG_LIT   | PrimitiveType.LONG
        FLOAT_LIT  | FLOAT_LIT  | PrimitiveType.FLOAT
        DOUBLE_LIT | DOUBLE_LIT | PrimitiveType.DOUBLE
    }

    def 'test visit divide of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitDivide(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | PrimitiveType.INT
        NUMBER_LIT | NUMBER_LIT | PrimitiveType.INT
        LONG_LIT   | LONG_LIT   | PrimitiveType.LONG
        FLOAT_LIT  | FLOAT_LIT  | PrimitiveType.FLOAT
        DOUBLE_LIT | DOUBLE_LIT | PrimitiveType.DOUBLE
    }

    def 'test visit modulo of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitModulo(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | PrimitiveType.INT
        NUMBER_LIT | NUMBER_LIT | PrimitiveType.INT
        LONG_LIT   | LONG_LIT   | PrimitiveType.LONG
        FLOAT_LIT  | FLOAT_LIT  | PrimitiveType.FLOAT
        DOUBLE_LIT | DOUBLE_LIT | PrimitiveType.DOUBLE
    }

    def 'test visit minus for #literal should return #expected'() {
        given:
        def type = this.typeChecker.visitMinus(literal)

        expect:
        type == expected

        where:
        literal    | expected
        NUMBER_LIT | PrimitiveType.INT
        CHAR_LIT   | PrimitiveType.INT
        LONG_LIT   | PrimitiveType.LONG
        FLOAT_LIT  | PrimitiveType.FLOAT
        DOUBLE_LIT | PrimitiveType.DOUBLE
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
                ScopeType.CASE
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
        type == PrimitiveType.BOOLEAN
    }

    def 'test visit this should return this object'() {
        when:
        def type = this.typeChecker.visitThisLiteral()

        then:
        type == ObjectType.of(TestClass)
    }

    def 'test visit cast #target to #cast should be of type #expected'() {
        given:
        def type = this.typeChecker.visitCast(cast, target)

        expect:
        type == expected

        where:
        target                                            | cast                                    | expected
        // char
        CHAR_LIT                                          | Literal.of('byte')                      | PrimitiveType.BYTE
        CHAR_LIT                                          | Literal.of('short')                     | PrimitiveType.SHORT
        CHAR_LIT                                          | Literal.of('char')                      | PrimitiveType.CHAR
        CHAR_LIT                                          | Literal.of('Character')                 | ObjectType.CHARACTER
        CHAR_LIT                                          | Literal.of('int')                       | PrimitiveType.INT
        CHAR_LIT                                          | Literal.of('long')                      | PrimitiveType.LONG
        CHAR_LIT                                          | Literal.of('float')                     | PrimitiveType.FLOAT
        CHAR_LIT                                          | Literal.of('double')                    | PrimitiveType.DOUBLE
        CHAR_LIT                                          | Literal.of('Object')                    | ObjectType.OBJECT
        // number
        NUMBER_LIT                                        | Literal.of('byte')                      | PrimitiveType.BYTE
        NUMBER_LIT                                        | Literal.of('short')                     | PrimitiveType.SHORT
        NUMBER_LIT                                        | Literal.of('char')                      | PrimitiveType.CHAR
        NUMBER_LIT                                        | Literal.of('int')                       | PrimitiveType.INT
        NUMBER_LIT                                        | Literal.of('Integer')                   | ObjectType.INTEGER
        NUMBER_LIT                                        | Literal.of('long')                      | PrimitiveType.LONG
        NUMBER_LIT                                        | Literal.of('float')                     | PrimitiveType.FLOAT
        NUMBER_LIT                                        | Literal.of('double')                    | PrimitiveType.DOUBLE
        NUMBER_LIT                                        | Literal.of('Object')                    | ObjectType.OBJECT
        // long
        LONG_LIT                                          | Literal.of('byte')                      | PrimitiveType.BYTE
        LONG_LIT                                          | Literal.of('short')                     | PrimitiveType.SHORT
        LONG_LIT                                          | Literal.of('char')                      | PrimitiveType.CHAR
        LONG_LIT                                          | Literal.of('int')                       | PrimitiveType.INT
        LONG_LIT                                          | Literal.of('long')                      | PrimitiveType.LONG
        LONG_LIT                                          | Literal.of('Long')                      | ObjectType.LONG
        LONG_LIT                                          | Literal.of('float')                     | PrimitiveType.FLOAT
        LONG_LIT                                          | Literal.of('double')                    | PrimitiveType.DOUBLE
        LONG_LIT                                          | Literal.of('Object')                    | ObjectType.OBJECT
        // float
        FLOAT_LIT                                         | Literal.of('byte')                      | PrimitiveType.BYTE
        FLOAT_LIT                                         | Literal.of('short')                     | PrimitiveType.SHORT
        FLOAT_LIT                                         | Literal.of('char')                      | PrimitiveType.CHAR
        FLOAT_LIT                                         | Literal.of('int')                       | PrimitiveType.INT
        FLOAT_LIT                                         | Literal.of('long')                      | PrimitiveType.LONG
        FLOAT_LIT                                         | Literal.of('float')                     | PrimitiveType.FLOAT
        FLOAT_LIT                                         | Literal.of('Float')                     | ObjectType.FLOAT
        FLOAT_LIT                                         | Literal.of('double')                    | PrimitiveType.DOUBLE
        FLOAT_LIT                                         | Literal.of('Object')                    | ObjectType.OBJECT
        // double
        DOUBLE_LIT                                        | Literal.of('byte')                      | PrimitiveType.BYTE
        DOUBLE_LIT                                        | Literal.of('short')                     | PrimitiveType.SHORT
        DOUBLE_LIT                                        | Literal.of('char')                      | PrimitiveType.CHAR
        DOUBLE_LIT                                        | Literal.of('int')                       | PrimitiveType.INT
        DOUBLE_LIT                                        | Literal.of('long')                      | PrimitiveType.LONG
        DOUBLE_LIT                                        | Literal.of('float')                     | PrimitiveType.FLOAT
        DOUBLE_LIT                                        | Literal.of('double')                    | PrimitiveType.DOUBLE
        DOUBLE_LIT                                        | Literal.of('Double')                    | ObjectType.DOUBLE
        DOUBLE_LIT                                        | Literal.of('Object')                    | ObjectType.OBJECT
        // boolean
        BOOL_LIT                                          | Literal.of('boolean')                   | PrimitiveType.BOOLEAN
        BOOL_LIT                                          | Literal.of('Boolean')                   | ObjectType.BOOLEAN
        BOOL_LIT                                          | Literal.of('Object')                    | ObjectType.OBJECT
        // string
        STRING_LIT                                        | Literal.of('String')                    | ObjectType.STRING
        STRING_LIT                                        | Literal.of('Object')                    | ObjectType.OBJECT
        // custom class
        new NewObject(Literal.of(TypeCheckerTest.canonicalName),
                new MethodInvocation([]))                 | Literal.of(Specification.canonicalName) | ObjectType.of(Specification)
        new NewObject(Literal.of(TypeCheckerTest.canonicalName),
                new MethodInvocation([]))                 | Literal.of('Object')                    | ObjectType.OBJECT
    }

    def 'test invalid visit cast #target to #cast'() {
        given:
        this.environment.declare(PrimitiveClassType.INT, 'cast', PrimitiveType.INT)

        and:
        def expectedMessage = TypeCheckerException.invalidCast(cast.equals(Literal.of('String')) ?
                ObjectClassType.STRING : PrimitiveClassType.INT, expected).message

        when:
        this.typeChecker.visitCast(cast, target)

        then:
        def e = thrown(TypeCheckerException)
        e.message == expectedMessage

        where:
        target             | cast                 | expected
        CHAR_LIT           | Literal.of('String') | PrimitiveType.CHAR
        NUMBER_LIT         | Literal.of('String') | PrimitiveType.INT
        LONG_LIT           | Literal.of('String') | PrimitiveType.LONG
        FLOAT_LIT          | Literal.of('String') | PrimitiveType.FLOAT
        DOUBLE_LIT         | Literal.of('String') | PrimitiveType.DOUBLE
        STRING_LIT         | Literal.of('int')    | ObjectType.STRING
        Literal.of('cast') | Literal.of('String') | PrimitiveType.INT
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
