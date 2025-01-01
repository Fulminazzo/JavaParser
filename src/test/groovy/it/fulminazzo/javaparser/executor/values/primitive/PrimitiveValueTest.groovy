package it.fulminazzo.javaparser.executor.values.primitive

import it.fulminazzo.javaparser.executor.values.ValueException
import spock.lang.Specification

class PrimitiveValueTest extends Specification {

    def 'test conversion of #value should return #expected'() {
        when:
        def converted = PrimitiveValue.of(value)

        then:
        converted == expected

        where:
        value            | expected
        1.0d             | new DoubleValue(1.0d)
        1.0f             | new FloatValue(1.0f)
        1L               | new LongValue(1L)
        true             | BooleanValue.TRUE
        'Hello, world!'  | new StringValue('Hello, world!')
        'a' as char      | new CharacterValue('a' as Character)
        1 as byte        | new IntegerValue(1)
        2 as short       | new IntegerValue(1)
        3                | new IntegerValue(1)
    }

    def 'test conversion of invalid type'() {
        given:
        def value = new DoubleValue(1.0)

        when:
        PrimitiveValue.of(value)

        then:
        def e = thrown(ValueException)
        e.message == ValueException.invalidPrimitiveValue(value).message + 'a'
    }

}