package it.fulminazzo.javaparser.executor.values.arrays

import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue
import it.fulminazzo.javaparser.executor.values.Value
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue
import spock.lang.Specification

class ArrayValueTest extends Specification {

    def 'test static array initialization should have equal parameters'() {
        given:
        def value = new ArrayValue<>(PrimitiveClassValue.INT, 3)

        and:
        def expected = new Integer[3]

        when:
        def actual = value.getValue()

        then:
        Arrays.equals(actual, expected)
    }

    def 'test dynamic array initialization should have equal parameters'() {
        given:
        def array = [Value.of('hello'), Value.of('world')]

        and:
        def value = new ArrayValue<>(ObjectClassValue.STRING, array)

        and:
        def expected = array.toArray(String[]::new)

        when:
        def actual = value.getValue()

        then:
        Arrays.equals(actual, expected)
    }

    def 'test toClassValue of array should return compatible array class'() {
        given:
        def value = new ArrayValue(ObjectClassValue.STRING, 3)

        when:
        def classValue = value.toClassValue()

        then:
        classValue.compatibleWith(value)
    }

    def 'test equals and hashCode'() {
        given:
        def first = new ArrayValue<>(ObjectClassValue.STRING, [Value.of('hello'), Value.of('world')])
        def second = new ArrayValue<>(ObjectClassValue.STRING, [Value.of('hello'), Value.of('world')])

        expect:
        first == second
        first.hashCode() == second.hashCode()
    }

    def 'test toString'() {
        given:
        def array = [Value.of('Hello'), Value.of(null), Value.of('world!')]

        and:
        def value = new ArrayValue(ObjectClassValue.STRING, array)

        when:
        def string = value.toString()

        then:
        string == "${ArrayValue.simpleName}(${array})"
    }

}