package it.fulminazzo.javaparser.executor.values

import it.fulminazzo.javaparser.executor.ExecutorException
import spock.lang.Specification

class LiteralValueTest extends Specification {
    private LiteralValue literal

    void setup() {
        this.literal = new LiteralValue('world')
    }

    def 'test getValue'() {
        when:
        def literal = this.literal.getValue()

        then:
        literal == 'world'
    }

    def 'test toClassValue'() {
        when:
        this.literal.toClassValue()

        then:
        def e = thrown(ExecutorException)
        e.message == ExecutorException.noClassValue(LiteralValue).message
    }

}