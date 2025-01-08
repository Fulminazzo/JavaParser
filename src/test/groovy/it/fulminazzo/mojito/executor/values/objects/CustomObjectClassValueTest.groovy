package it.fulminazzo.mojito.executor.values.objects

import it.fulminazzo.mojito.executor.values.ClassValue
import it.fulminazzo.mojito.executor.values.PrimitiveClassValue
import it.fulminazzo.mojito.executor.values.Values
import spock.lang.Specification

class CustomObjectClassValueTest extends Specification {

    def 'test compatibleWith'() {
        given:
        def value = new ObjectValue<>(this)
        def classValue = new CustomObjectClassValue(getClass())

        expect:
        classValue.compatibleWith(value)
    }

    def 'test compatibleWith null'() {
        given:
        def value = Values.NULL_VALUE
        def classValue = new CustomObjectClassValue(getClass())

        expect:
        classValue.compatibleWith(value)
    }

    def 'test incompatible with #value'() {
        given:
        // Bad practice, just for testing purposes
        def classValue = new CustomObjectClassValue(String)

        expect:
        !classValue.compatibleWith(value)

        where:
        value << [
                PrimitiveClassValue.values(),
                [new ObjectValue<>(Byte.valueOf((byte) 1)), new ObjectValue<>(Short.valueOf((short) 1)),
                 new ObjectValue<>(new Object())],
                new ObjectValue<>(this),
        ].flatten()
    }

    def 'test equality'() {
        given:
        def first = new CustomObjectClassValue(getClass())
        def second = new CustomObjectClassValue(getClass())

        expect:
        first == second
        first.hashCode() == second.hashCode()
    }

    def 'test inequality'() {
        given:
        def classValue = new CustomObjectClassValue(getClass())

        expect:
        !classValue.equals(value)

        where:
        value << [
                null,
                PrimitiveClassValue.values(),
                ObjectClassValue.values(),
                new CustomObjectClassValue(String)
        ].flatten()
    }

    def 'test toString of value #value'() {
        given:
        def string = value.toString()

        expect:
        string == ClassValue.print(expected)

        where:
        expected                 | value
        'String'                 | new CustomObjectClassValue(String)
        getClass().canonicalName | new CustomObjectClassValue(getClass())
    }

}