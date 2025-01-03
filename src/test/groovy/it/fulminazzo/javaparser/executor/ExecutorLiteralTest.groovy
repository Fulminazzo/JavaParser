package it.fulminazzo.javaparser.executor

import it.fulminazzo.fulmicollection.structures.tuples.Tuple
import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue
import spock.lang.Specification

class ExecutorLiteralTest extends Specification {
    private Executor executor

    void setup() {
        this.executor = new Executor()
    }

    def 'test getTypeFromLiteral #literal'() {
        given:
        this.executor.environment.declare(PrimitiveClassValue.INT, 'i', PrimitiveValue.of(1))

        when:
        def tuple = this.executor.getValueFromLiteral(literal)

        then:
        tuple == expected

        where:
        literal   | expected
        'int'     | new Tuple<>(PrimitiveClassValue.INT, PrimitiveClassValue.INT)
        'i'       | new Tuple<>(PrimitiveClassValue.INT, PrimitiveValue.of(1))
        'invalid' | new Tuple<>()
    }

}