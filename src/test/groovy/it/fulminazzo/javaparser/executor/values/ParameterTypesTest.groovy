package it.fulminazzo.javaparser.executor.values

import it.fulminazzo.javaparser.executor.ExecutorException
import spock.lang.Specification

class ParameterValuesTest extends Specification {

    def 'test toClassValue should throw exception'() {
        given:
        def types = new ParameterValues([])

        when:
        types.toClassValue()

        then:
        def e = thrown(ExecutorException)
        e.message == ExecutorException.noClassValue(ParameterValues).message
    }

}