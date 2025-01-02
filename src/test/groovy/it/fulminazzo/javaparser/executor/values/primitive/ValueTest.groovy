package it.fulminazzo.javaparser.executor.values.primitive

import it.fulminazzo.javaparser.executor.values.Value
import spock.lang.Specification

class ValueTest extends Specification {

    def 'test empty check'() {
        given:
        def value = new MockValue()

        when:
        value.check()

        then:
        thrown(IllegalArgumentException)
    }

    static class MockValue implements Value {

        @Override
        Object getValue() {
            return null
        }
    }

}