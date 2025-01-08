package it.fulminazzo.mojito.visitors.visitorobjects.variables


import it.fulminazzo.mojito.handler.elements.ClassElement
import it.fulminazzo.mojito.handler.elements.Element
import it.fulminazzo.mojito.handler.elements.ParameterElements
import it.fulminazzo.mojito.handler.elements.variables.ElementVariableContainer
import it.fulminazzo.mojito.visitors.visitorobjects.ClassVisitorObject
import it.fulminazzo.mojito.visitors.visitorobjects.VisitorObject
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
                    switch (it) {
                        case Class -> String
                        case String -> 'Hello, world!'
                        case ClassVisitorObject -> ClassElement.of(Integer)
                        case VisitorObject -> Element.of(1)
                        case Object -> Element.of(1)
                        case it.array -> Array.newInstance(it.componentType, 1)
                        default -> throw new IllegalArgumentException(it.toString())
                    }
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
