package it.fulminazzo.javaparser.executor.values.arrays

import spock.lang.Specification

class ArrayValueTest extends Specification {

    def 'test dynamic array initialization should have equal parameters'() {
        given:
        def value = new ArrayValue<>(String.class, ['hello', 'world'])

        and:
        def expected = new String[]{'hello', 'world'}

        when:
        def actual = value.getValue()

        then:
        Arrays.deepEquals(actual, expected)
    }

}