package it.fulminazzo.javaparser.visitors

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.fulmicollection.structures.tuples.Tuple
import it.fulminazzo.fulmicollection.utils.ClassUtils
import it.fulminazzo.fulmicollection.utils.ReflectionUtils
import it.fulminazzo.fulmicollection.utils.StringUtils
import it.fulminazzo.javaparser.environment.MockEnvironment
import it.fulminazzo.javaparser.environment.scopetypes.ScopeType
import it.fulminazzo.javaparser.handler.Handler
import it.fulminazzo.javaparser.handler.HandlerException
import it.fulminazzo.javaparser.handler.elements.ClassElement
import it.fulminazzo.javaparser.handler.elements.Element
import it.fulminazzo.javaparser.handler.elements.ElementException
import it.fulminazzo.javaparser.handler.elements.ParameterElements
import it.fulminazzo.javaparser.parser.node.MethodInvocation
import it.fulminazzo.javaparser.parser.node.MockNode
import it.fulminazzo.javaparser.parser.node.Node
import it.fulminazzo.javaparser.parser.node.literals.EmptyLiteral
import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.literals.ThisLiteral
import it.fulminazzo.javaparser.parser.node.operators.binary.Field
import it.fulminazzo.javaparser.parser.node.values.BooleanValueLiteral
import it.fulminazzo.javaparser.parser.node.values.DoubleValueLiteral
import it.fulminazzo.javaparser.parser.node.values.NumberValueLiteral
import it.fulminazzo.javaparser.parser.node.values.StringValueLiteral
import it.fulminazzo.javaparser.tokenizer.TokenType
import it.fulminazzo.javaparser.visitors.visitorobjects.TestClass
import spock.lang.Specification

import java.lang.reflect.Modifier

class VisitorTest extends Specification {
    private Visitor visitor
    private MockEnvironment environment

    void setup() {
        this.visitor = new Handler(new TestClass())
        this.environment = this.visitor.environment as MockEnvironment
    }

    def 'test visitMethodCall #executor #methodName(#parameters) should return #expected'() {
        given:
        def methodInvocation = new MethodInvocation(parameters)

        when:
        def element = this.visitor.visitMethodCall(executor, methodName, methodInvocation)

        then:
        element == expected

        where:
        executor                    | methodName     | parameters                                                       | expected
        new EmptyLiteral()          | 'publicMethod' | []                                                               | Element.of(1.0d)
        new EmptyLiteral()          | 'publicMethod' | [new DoubleValueLiteral('2.0'), new BooleanValueLiteral('true')] | Element.of(2.0d)
        new EmptyLiteral()          | 'publicMethod' | [new DoubleValueLiteral('1.0'), new BooleanValueLiteral('true')] | Element.of(1.0d)
        new ThisLiteral()           | 'publicMethod' | []                                                               | Element.of(1.0d)
        new ThisLiteral()           | 'publicMethod' | [new DoubleValueLiteral('2.0'), new BooleanValueLiteral('true')] | Element.of(2.0d)
        new ThisLiteral()           | 'publicMethod' | [new DoubleValueLiteral('1.0'), new BooleanValueLiteral('true')] | Element.of(1.0d)
        new NumberValueLiteral('1') | 'toString'     | []                                                               | Element.of('1')
    }

    def 'test visitMethodCall exception'() {
        given:
        def executor = new ThisLiteral()
        def methodName = 'invalid'
        def parameters = new ParameterElements([])

        and:
        def expected = Element.of(null).methodNotFound(ClassElement.of(TestClass),
                methodName, parameters).message

        when:
        this.visitor.visitMethodCall(executor, methodName, new MethodInvocation([]))

        then:
        def e = thrown(HandlerException)
        e.message == expected
    }

    def 'test visitField'() {
        given:
        def parent = Element.of(this.visitor.executingObject)
        def type = ClassElement.of(double)
        def fieldName = 'publicField'
        def value = Element.of(1.0d)

        and:
        def executor = new ThisLiteral()
        def fieldLiteral = Literal.of(fieldName)

        when:
        def field = this.visitor.visitField(executor, fieldLiteral)

        then:
        field.container == parent
        field.type == type
        field.name == fieldName
        field.variable == value
    }

    def 'test visitField exception'() {
        given:
        def executor = new ThisLiteral()
        def fieldLiteral = Literal.of('invalid')

        and:
        def expected = Element.of(null).fieldNotFound(ClassElement.of(TestClass), 'invalid').message

        when:
        this.visitor.visitField(executor, fieldLiteral)

        then:
        def e = thrown(HandlerException)
        e.message == expected
    }

    def 'test visit#token(#parameters) should throw unsupported operation exception'() {
        given:
        def methodName = StringUtils.capitalize(token.toString())
                .replace('_', '')
                .replace('shift', 'Shift')
                .replace('rShift', 'RShift')
        if (parameters.size() < 2 && token == TokenType.SUBTRACT) methodName = 'Minus'

        and:
        def message = Element.of(null).unsupportedOperation([token, operands].flatten()).message

        when:
        def e = this.visitor."visit${methodName}"(parameters)

        then:
        e.element.message == message

        where:
        token                        | parameters                                                                 | operands
        // Comparisons
        TokenType.AND                | [new BooleanValueLiteral('true'), new BooleanValueLiteral('false')]        | [Element.of(true), Element.of(false)]
        TokenType.OR                 | [new BooleanValueLiteral('true'), new BooleanValueLiteral('false')]        | [Element.of(true), Element.of(false)]
        TokenType.EQUAL              | [new NumberValueLiteral('1'), new StringValueLiteral('\"Hello, world!\"')] | [Element.of(1), Element.of('Hello, world!')]
        TokenType.NOT_EQUAL          | [new DoubleValueLiteral('1.0d'), new BooleanValueLiteral('false')]         | [Element.of(1.0d), Element.of(false)]
        TokenType.LESS_THAN          | [new NumberValueLiteral('1'), new NumberValueLiteral('2')]                 | [Element.of(1), Element.of(2)]
        TokenType.LESS_THAN_EQUAL    | [new NumberValueLiteral('1'), new NumberValueLiteral('2')]                 | [Element.of(1), Element.of(2)]
        TokenType.GREATER_THAN       | [new NumberValueLiteral('1'), new NumberValueLiteral('2')]                 | [Element.of(1), Element.of(2)]
        TokenType.GREATER_THAN_EQUAL | [new NumberValueLiteral('1'), new NumberValueLiteral('2')]                 | [Element.of(1), Element.of(2)]
        // Bit operations
        TokenType.BIT_AND            | [new NumberValueLiteral('1'), new NumberValueLiteral('2')]                 | [Element.of(1), Element.of(2)]
        TokenType.BIT_OR             | [new NumberValueLiteral('1'), new NumberValueLiteral('2')]                 | [Element.of(1), Element.of(2)]
        TokenType.BIT_XOR            | [new NumberValueLiteral('1'), new NumberValueLiteral('2')]                 | [Element.of(1), Element.of(2)]
        TokenType.LSHIFT             | [new NumberValueLiteral('1'), new NumberValueLiteral('2')]                 | [Element.of(1), Element.of(2)]
        TokenType.RSHIFT             | [new NumberValueLiteral('1'), new NumberValueLiteral('2')]                 | [Element.of(1), Element.of(2)]
        TokenType.URSHIFT            | [new NumberValueLiteral('1'), new NumberValueLiteral('2')]                 | [Element.of(1), Element.of(2)]
        // Operations
        TokenType.ADD                | [new DoubleValueLiteral('4.0d'), new NumberValueLiteral('2')]              | [Element.of(4.0), Element.of(2)]
        TokenType.SUBTRACT           | [new DoubleValueLiteral('4.0d'), new NumberValueLiteral('2')]              | [Element.of(4.0), Element.of(2)]
        TokenType.MULTIPLY           | [new DoubleValueLiteral('4.0d'), new NumberValueLiteral('2')]              | [Element.of(4.0), Element.of(2)]
        TokenType.DIVIDE             | [new DoubleValueLiteral('4.0d'), new NumberValueLiteral('2')]              | [Element.of(4.0), Element.of(2)]
        TokenType.MODULO             | [new DoubleValueLiteral('4.0d'), new NumberValueLiteral('2')]              | [Element.of(4.0), Element.of(2)]
        // Unary
        TokenType.SUBTRACT           | [new NumberValueLiteral('1')]                                              | [Element.of(1)]
        TokenType.NOT                | [new BooleanValueLiteral('true')]                                          | [Element.of(true)]
    }

    def 'test visitCast of #node should return #expected'() {
        given:
        def cast = Literal.of('Integer')

        when:
        def element = this.visitor.visitCast(cast, node)

        then:
        element == expected

        where:
        node                                                          | expected
        new NumberValueLiteral('2')                                   | Element.of(2)
        new Field(new ThisLiteral(), Literal.of('publicStaticField')) | Element.of(1)
    }

    def 'test visitScoped of #scopeType'() {
        given:
        def expected = Element.of('Hello, world!')

        when:
        def element = this.visitor.visitScoped(scopeType, () -> expected)

        then:
        element == expected
        this.environment.enteredScope(scopeType)
        this.environment.scopeType() != scopeType

        where:
        scopeType << ScopeType.values().findAll { it != ScopeType.MAIN }
    }

    def 'test visitScoped of #tuple.key should throw #tuple.value.simpleName'() {
        given:
        def scopeType = tuple.key
        def exception = tuple.value

        and:
        def message = 'this is the message'

        when:
        this.visitor.visitScoped(scopeType, () -> {
            throw exception.newInstance(message)
        })

        then:
        def e = thrown(HandlerException)
        e.message == message
        this.environment.enteredScope(scopeType)
        this.environment.scopeType() != scopeType

        where:
        tuple << ScopeType.values()
                .findAll { it != ScopeType.MAIN }
                .collect {
                    [ElementException, HandlerException]
                            .collect { ex -> new Tuple<>(it, ex) }
                }
                .flatten()
    }

    def 'test accept mock node'() {
        given:
        def node = new MockNode('mock', 1)
        def visitor = new Handler(this)

        when:
        def converted = node.accept(visitor).element

        then:
        converted == "${node.name}${node.version}"
    }

    def "visitor should have method: visit#clazz.simpleName(#parameters.type.simpleName) "() {
        given:
        def methodName = "visit${clazz.simpleName}"

        when:
        def method = Visitor.class.getDeclaredMethods()
                .findAll { it.name == methodName }
                .findAll { it.parameterCount == parameters.length }
                .find { parameters.collect { f -> f.type } == it.parameterTypes.toList() }

        then:
        if (method == null) writeMethod(methodName, parameters)
        method != null

        where:
        clazz << nodeClasses()
        parameters << nodeClasses()
                .collect { new Refl<>(it) }
                .collect { it.nonStaticFields }
                .collect { it.toArray() }
    }

    static writeMethod(def methodName, Object[] fieldParameters) {
        def cwd = System.getProperty('user.dir')
        def path = "${VisitorTest.class.package.name.replace('.', File.separator)}"
        def file = new File(cwd, "src/main/java/${path}${File.separator}${Visitor.class.simpleName}.java")

        def lines = file.readLines()
        def toWrite = lines.subList(0, lines.size() - 2)
        def stringParameters = fieldParameters.collect {
            def value = "${it.type.simpleName} ${it.name}"
            if (!ReflectionUtils.isPrimitive(it.type)) value = '@NotNull ' + value
            return value
        }.join(', ')
        toWrite.add("    @NotNull T ${methodName}(${stringParameters});\n")
        toWrite.add('\n}')

        file.delete()
        toWrite.each { file << "${it}\n" }

        println "Updated ${Visitor.class.simpleName} class with method ${methodName}"
    }

    static nodeClasses() {
        ClassUtils.findClassesInPackage(Node.class.package.name)
                .findAll { !Modifier.isAbstract(it.modifiers) }
                .findAll { !it.isInterface() }
                .findAll { !it.simpleName.contains('Test') }
                .findAll { !it.simpleName.contains('Exception') }
                .findAll { !it.simpleName.contains('Mock') }
    }

}