package it.fulminazzo.javaparser.visitors.visitorobjects.variables

import it.fulminazzo.javaparser.handler.elements.ClassElement
import it.fulminazzo.javaparser.handler.elements.Element
import it.fulminazzo.javaparser.handler.elements.variables.ElementVariableContainer
import it.fulminazzo.javaparser.visitors.visitorobjects.ClassVisitorObject
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject
import spock.lang.Specification

import java.lang.reflect.Array
import java.lang.reflect.Modifier

class VariableContainerTest extends Specification {
    private static def variable
    private static def container

    void setupSpec() {
        def tuple = setupVariableContainer()
        variable = tuple[0]
        container = tuple[1]
    }

    def setupVariableContainer() {
        def variable = Mock(Element)
        def container = new ElementVariableContainer(null, ClassElement.of(Double), 'variable', variable)
        return new Tuple<>(variable, container)
    }

    def 'test method #method(#parameters) should first be invoked on container'() {
        when:
        def result = container."${method}"(parameters)

        then:
        result == expected

        where:
        method  | parameters               | expected
        'is'    | ElementVariableContainer | true
        'is'    | container                | true
        'check' | VariableContainer        | container
    }

    def 'test container.#method.name(#method.parameterTypes) should call variable.#method.name(#method.parameterTypes)'() {
        given:
        def tuple = setupVariableContainer()
        def variable = tuple[0]
        def container = tuple[1]

        and:
        def parameters = method.parameterTypes
                .collect {
                    if (it == Class) return String
                    else if (it == String) return 'Hello, world!'
                    else if (it == ClassVisitorObject) return ClassElement.of(Integer)
                    else if (it == VisitorObject) return Element.of(1)
                    else if (it == Object) return Element.of(1)
                    else if (it.array) return Array.newInstance(it.componentType, 1)
                    else throw new IllegalArgumentException(it.toString())
                }

        when:
        container."${method.name}"(*parameters)

        then:
        if (parameters.isEmpty()) 1 * variable."${method.name}"()
        else 1 * variable."${method.name}"(*parameters)

        where:
        method << VariableContainer.methods
                .findAll { it.declaringClass == VariableContainer }
                .findAll { !['set', 'getType', 'getName', 'getVariable', 'invokeMethod'].contains(it.name) }
                .findAll { !Modifier.isAbstract(it.modifiers) }
    }

}
