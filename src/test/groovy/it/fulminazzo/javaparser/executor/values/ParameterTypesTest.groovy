package it.fulminazzo.javaparser.executor.values

import it.fulminazzo.javaparser.executor.ExecutorException
import spock.lang.Specification

class ParameterValuesTest extends Specification {

    def 'test toClass should throw exception'() {
        given:
        def types = new ParameterValues([])

        when:
        types.toClass()

        then:
        def e = thrown(ExecutorException)
        e.message == ExecutorException.noClassValue(ParameterValues).message
    }

}