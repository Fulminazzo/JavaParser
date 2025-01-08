package it.fulminazzo.javaparser.executor.values.arrays

import it.fulminazzo.javaparser.executor.values.ClassValue
import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue
import it.fulminazzo.javaparser.executor.values.Value
import it.fulminazzo.javaparser.executor.values.Values
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue
import spock.lang.Specification

import java.lang.reflect.Array

class ArrayClassValueTest extends Specification {

    def 'test #classValue should be compatible with #value'() {
        expect:
        classValue.compatibleWith(value)

        where:
        classValue                                      | value
        new ArrayClassValue<>(PrimitiveClassValue.INT)  | new ArrayValue<>(PrimitiveClassValue.INT, [])
        new ArrayClassValue<>(ObjectClassValue.INTEGER) | new ArrayValue<>(ObjectClassValue.INTEGER, [])
        new ArrayClassValue<>(ObjectClassValue.INTEGER) | Values.NULL_VALUE
    }

    def 'test #classValue should not be compatible with #value'() {
        expect:
        !classValue.compatibleWith(value)

        where:
        classValue                                      | value
        new ArrayClassValue<>(ObjectClassValue.INTEGER) | new ArrayValue<>(PrimitiveClassValue.INT, [])
        new ArrayClassValue<>(PrimitiveClassValue.INT)  | new ArrayValue<>(ObjectClassValue.INTEGER, [])
        new ArrayClassValue<>(PrimitiveClassValue.INT)  | Values.NULL_VALUE
        new ArrayClassValue<>(PrimitiveClassValue.INT)  | Value.of('Hello, world!')
    }

    def 'test ArrayClassValue(#classValue) toWrapper should return #expected'() {
        given:
        def array = new ArrayClassValue<>(classValue)

        when:
        def wrapper = array.wrapperValue

        then:
        wrapper == Array.newInstance(expected, 0).class

        where:
        classValue             | expected
        ClassValue.of(byte)    | Byte
        ClassValue.of(short)   | Short
        ClassValue.of(char)    | Character
        ClassValue.of(int)     | Integer
        ClassValue.of(long)    | Long
        ClassValue.of(float)   | Float
        ClassValue.of(double)  | Double
        ClassValue.of(boolean) | Boolean
        ClassValue.of(boolean) | Boolean
    }

    def 'test getValue should return class'() {
        given:
        def arrayClassValue = new ArrayClassValue(ObjectClassValue.STRING)

        when:
        def value = arrayClassValue.getValue()

        then:
        value == Array.newInstance(String, 0).class
    }

    def 'test toString'() {
        given:
        def arrayClassValue = new ArrayClassValue(new ArrayClassValue(new ArrayClassValue(PrimitiveClassValue.INT)))

        when:
        def string = arrayClassValue.toString()

        then:
        string == 'int[][][].class'
    }

}
