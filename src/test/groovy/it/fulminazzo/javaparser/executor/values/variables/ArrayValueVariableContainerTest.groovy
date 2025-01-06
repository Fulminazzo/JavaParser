package it.fulminazzo.javaparser.executor.values.variables

import it.fulminazzo.javaparser.environment.MockEnvironment
import it.fulminazzo.javaparser.executor.values.arrays.ArrayValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue
import spock.lang.Specification

class ArrayValueVariableContainerTest extends Specification {

    def 'test set method'() {
        given:
        def environment = new MockEnvironment()

        and:
        def array = new int[1][2][3]
        array = ArrayValue.of(array)
        environment.declare(array.toClass(), 'arr', array)

        and:
        def container = new ValueLiteralVariableContainer(environment, array.toClass(), 'arr', array)
        container = new ArrayValueVariableContainer<>(container, array.componentsType.toClass(), '0', array)
        container = new ArrayValueVariableContainer<>(container, array.componentsType.componentsType.toClass(), '1', array)
        container = new ArrayValueVariableContainer<>(container, array.componentsType.componentsType.componentsType.toClass(), '2', array)

        when:
        container.set(PrimitiveValue.of(10))
        int[][][] value = array.getValue()

        then:
        value[0][1][2] == 10
    }

}