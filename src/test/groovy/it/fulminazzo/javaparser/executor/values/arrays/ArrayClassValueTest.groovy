package it.fulminazzo.javaparser.executor.values.arrays

import it.fulminazzo.javaparser.executor.values.ClassValue
import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue
import spock.lang.Specification

class ArrayClassValueTest extends Specification {

    def 'test getValue should return class'() {
        given:
        def arrayClassValue = new ArrayClassValue(ClassValue.of(String))

        when:
        def value = arrayClassValue.getValue()

        then:
        value == String[]
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
