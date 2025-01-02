package it.fulminazzo.javaparser.executor

import it.fulminazzo.javaparser.executor.values.ValueException
import it.fulminazzo.javaparser.executor.values.primitivevalue.BooleanValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.ByteValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.CharValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.DoubleValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.FloatValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.IntValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.LongValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.ShortValue
import spock.lang.Specification

class ExecutorTest extends Specification {
    private Executor executor

    void setup() {
        this.executor = new Executor(getClass())
    }

    def 'test getPrimitiveValueFromLiteral of #value should be #expected'() {
        when:
        def converted = this.executor.getPrimitiveValueFromLiteral(value)

        then:
        converted == expected

        where:
        value            | expected
        1.0d             | PrimitiveValue.of(1.0d)
        1.0f             | PrimitiveValue.of(1.0f)
        1L               | PrimitiveValue.of(1L)
        true             | BooleanValue.TRUE
        false            | BooleanValue.FALSE
        'a' as char      | PrimitiveValue.of('a' as Character)
        1 as byte        | PrimitiveValue.of(1 as byte)
        2 as short       | PrimitiveValue.of(2 as short)
        3                | PrimitiveValue.of(3)
    }

    def 'test invalid getPrimitiveValueFromLiteral'() {
        when:
        this.executor.getPrimitiveValueFromLiteral(null)

        then:
        def e = thrown(ExecutorException)
        e.message == ExecutorException.of(ValueException.invalidPrimitiveValue(null)).message
    }

}