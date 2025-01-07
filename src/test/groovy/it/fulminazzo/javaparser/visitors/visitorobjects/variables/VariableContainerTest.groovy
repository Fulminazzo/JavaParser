package it.fulminazzo.javaparser.visitors.visitorobjects.variables

import it.fulminazzo.javaparser.handler.elements.ClassElement
import it.fulminazzo.javaparser.handler.elements.Element
import it.fulminazzo.javaparser.handler.elements.ParameterElements
import it.fulminazzo.javaparser.handler.elements.variables.ElementVariableContainer
import it.fulminazzo.javaparser.visitors.visitorobjects.ClassVisitorObject
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject
import spock.lang.Specification

import java.lang.reflect.Array
import java.lang.reflect.Modifier

class VariableContainerTest extends Specification {

    static ElementVariableContainer generateContainer() {
        return new ElementVariableContainer(null, ClassElement.of(Class), 'variable', ClassElement.of(Double))
    }

    def 'test method #method(#parameters) should first be invoked on container'() {
        given:
        def container = generateContainer()

        when:
        def result = container."${method}"(parameters)

        then:
        result == expected

        where:
        method  | parameters                             | expected
        'is'    | ElementVariableContainer               | true
        'is'    | ClassElement                           | true
        'is'    | new Element[]{generateContainer()}     | true
        'is'    | new Element[]{ClassElement.of(Double)} | true
        'is'    | new Element[]{ClassElement.of(String)} | false
        'check' | VariableContainer                      | generateContainer()
    }

    def 'test container.invokeMethod should call variable.invokeMethod'() {
        given:
        def variable = Mock(Element)
        def container = new ElementVariableContainer(null, ClassElement.of(Double), 'variable', variable)

        and:
        def name = 'method'
        def parameters = new ParameterElements([Element.of(1), Element.of(true), Element.of('a')])

        when:
        container.invokeMethod(name, parameters)

        then:
        1 * variable.invokeMethod(name, parameters)
    }

    def 'test container.#method.name(#method.parameterTypes) should call variable.#method.name(#method.parameterTypes)'() {
        given:
        def variable = Mock(Element)
        def container = new ElementVariableContainer(null, ClassElement.of(Double), 'variable', variable)

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
