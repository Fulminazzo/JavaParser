package it.fulminazzo.mojito.executor.values.objects

import spock.lang.Specification

class StringObjectValueTest extends Specification {

    def 'test to class value'() {
        given:
        def string = new StringObjectValue('Hello, world!')

        when:
        def classValue = string.toClass()

        then:
        classValue == ObjectClassValue.STRING
    }

    def 'test add'() {
        given:
        def first = new StringObjectValue('Hello, ')
        def second = new StringObjectValue('world!')

        when:
        def result = first.add(second)

        then:
        result.value == 'Hello, world!'
    }

}