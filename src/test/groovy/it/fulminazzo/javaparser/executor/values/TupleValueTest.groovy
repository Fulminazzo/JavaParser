package it.fulminazzo.javaparser.executor.values

import it.fulminazzo.javaparser.executor.ExecutorException
import spock.lang.Specification

class TupleValueTest extends Specification {

    def 'test toClass should throw'() {
        when:
        new TupleValue<>(null, null).toClass()

        then:
        def e = thrown(ExecutorException)
        e.message == ExecutorException.noClassValue(TupleValue).message
    }

}
