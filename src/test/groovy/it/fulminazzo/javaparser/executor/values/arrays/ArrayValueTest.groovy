package it.fulminazzo.javaparser.executor.values.arrays

import spock.lang.Specification

class ArrayValueTest extends Specification {

    def 'test static array initialization should have equal parameters'() {
        given:
        def value = new ArrayValue<>(Integer, 3)

        and:
        def expected = new Integer[3]

        when:
        def actual = value.getValue()

        then:
        Arrays.equals(actual, expected)
    }

    def 'test dynamic array initialization should have equal parameters'() {
        given:
        def value = new ArrayValue<>(String, ['hello', 'world'])

        and:
        def expected = new String[]{'hello', 'world'}

        when:
        def actual = value.getValue()

        then:
        Arrays.equals(actual, expected)
    }

}