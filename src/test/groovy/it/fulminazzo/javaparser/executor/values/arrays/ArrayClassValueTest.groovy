package it.fulminazzo.javaparser.executor.values.arrays

import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue
import spock.lang.Specification

class ArrayClassValueTest extends Specification {

    def 'test toString'() {
        given:
        def arrayClassValue = new ArrayClassValue(new ArrayClassValue(new ArrayClassValue(PrimitiveClassValue.INT)))

        when:
        def string = arrayClassValue.toString()

        then:
        string == 'int[][][].class'
    }

}
