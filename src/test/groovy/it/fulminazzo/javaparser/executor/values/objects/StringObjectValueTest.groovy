package it.fulminazzo.javaparser.executor.values.objects

import spock.lang.Specification

class StringObjectValueTest extends Specification {

    def 'test add'() {
        given:
        def first = new StringObjectValue('Hello, ')
        def second = new StringObjectValue('world!')

        when:
        def result = first.add(second)

        then:
        result.getValue() == 'Hello, world!'
    }

}