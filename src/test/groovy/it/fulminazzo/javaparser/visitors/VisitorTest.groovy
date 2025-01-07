package it.fulminazzo.javaparser.visitors

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.fulmicollection.structures.tuples.Tuple
import it.fulminazzo.fulmicollection.utils.ClassUtils
import it.fulminazzo.fulmicollection.utils.ReflectionUtils
import it.fulminazzo.javaparser.environment.MockEnvironment
import it.fulminazzo.javaparser.environment.scopetypes.ScopeType
import it.fulminazzo.javaparser.handler.Handler
import it.fulminazzo.javaparser.handler.HandlerException
import it.fulminazzo.javaparser.handler.elements.Element
import it.fulminazzo.javaparser.handler.elements.ElementException
import it.fulminazzo.javaparser.parser.node.MockNode
import it.fulminazzo.javaparser.parser.node.Node
import spock.lang.Specification

import java.lang.reflect.Modifier

class VisitorTest extends Specification {
    private Visitor visitor
    private MockEnvironment environment

    void setup() {
        this.visitor = new Handler(this)
        this.environment = this.visitor.environment as MockEnvironment
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
                .collect {[ElementException, HandlerException]
                        .collect { ex -> new Tuple<>(it, ex) }  }
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
                .collect{ it.toArray() }
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