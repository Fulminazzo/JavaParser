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

    def 'test toClassValue of array should return compatible array class'() {
        given:
        def value = new ArrayValue(String, 3)

        when:
        def classValue = value.toClassValue()

        then:
        classValue.compatibleWith(value)
    }

    def 'test equals and hashCode'() {
        given:
        def first = new ArrayValue(String, ['Hello', 'world!'])
        def second = new ArrayValue(String, ['Hello', 'world!'])

        expect:
        first == second
        first.hashCode() == second.hashCode()
    }

    def 'test toString'() {
        given:
        def array = ['Hello', 'world!']

        and:
        def value = new ArrayValue(String, array)

        when:
        def string = value.toString()

        then:
        string == "${ArrayValue.simpleName}(${array})"
    }

}