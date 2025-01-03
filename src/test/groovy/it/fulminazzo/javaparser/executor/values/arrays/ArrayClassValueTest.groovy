package it.fulminazzo.javaparser.executor.values.arrays


import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue
import spock.lang.Specification

import java.lang.reflect.Array

class ArrayClassValueTest extends Specification {

    def 'test getValue should return class'() {
        given:
        def classValue = ObjectClassValue.STRING

        and:
        def arrayClassValue = new ArrayClassValue(classValue)

        when:
        def value = arrayClassValue.getValue()

        then:
        value == Array.newInstance(classValue.class, 0).class
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
