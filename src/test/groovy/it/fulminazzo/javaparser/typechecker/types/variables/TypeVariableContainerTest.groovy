package it.fulminazzo.javaparser.typechecker.types.variables


import it.fulminazzo.javaparser.typechecker.types.*
import it.fulminazzo.javaparser.visitors.visitorobjects.ParameterVisitorObjects
import spock.lang.Specification

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

class TypeVariableContainerTest extends Specification {

    def 'test container.#method.name(#method.parameterTypes) should call variable.#method.name(#method.parameterTypes)'() {
        given:
        def methodName = method.name

        and:
        def variable = Mock(Type)
        def container = new TypeVariableContainer() {
            @Override
            Type getVariable() {
                return variable
            }

            @Override
            ClassType toClass() {
                return null
            }
        }

        and:
        def parameters = method.parameterTypes.collect {
            switch (it) {
                case Type[] -> new Type[]{PrimitiveType.INT}
                case ClassType -> PrimitiveClassType.INT
                case Method -> TestClass.getMethod('publicMethod')
                case Field -> TestClass.getField('publicField')
                case ParameterVisitorObjects -> new ParameterTypes([])
                case ParameterTypes -> new ParameterTypes([])
                default -> throw new IllegalArgumentException(it.canonicalName)
            }
        }

        when:
        if (parameters.size() == 0) container."${methodName}"()
        else container."${methodName}"(*parameters)

        then:
        if (parameters.size() == 0) 1 * variable."${methodName}"()
        else 1 * variable."${methodName}"(*parameters)

        where:
        method << TypeVariableContainer.methods
                .findAll { it.declaringClass == TypeVariableContainer }
                .findAll { !Modifier.isAbstract(it.modifiers) }
    }

}
