package it.fulminazzo.javaparser.executor.values.primitivevalue

import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue
import it.fulminazzo.javaparser.executor.values.TestClass
import it.fulminazzo.javaparser.executor.values.Value
import it.fulminazzo.javaparser.executor.values.Values
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue
import it.fulminazzo.javaparser.executor.values.objects.ObjectValue
import spock.lang.Specification

class ValueTest extends Specification {

    def 'test access to field #field should return #expected'() {
        given:
        def value = Value.of(new TestClass())

        when:
        def fieldValue = value.getField(field)

        then:
        fieldValue.key == expectedClass
        fieldValue.value == expected

        where:
        field               | expectedClass               | expected
        'publicStaticField' | PrimitiveClassValue.INT     | PrimitiveValue.of(1)
        'publicField'       | ObjectClassValue.of(Double) | ObjectValue.of(1.0d)
    }

    def 'test of #object should return #expected'() {
        given:
        def value = Value.of(object)

        expect:
        value == expected

        where:
        object          | expected
        1 as byte       | PrimitiveValue.of(1 as byte)
        2 as short      | PrimitiveValue.of(2 as short)
        'a' as char     | PrimitiveValue.of('a' as char)
        4               | PrimitiveValue.of(4)
        5L              | PrimitiveValue.of(5L)
        6.0f            | PrimitiveValue.of(6.0f)
        7.0d            | PrimitiveValue.of(7.0d)
        true            | BooleanValue.TRUE
        false           | BooleanValue.FALSE
        'Hello, world!' | ObjectValue.of('Hello, world!')
        null            | Values.NULL_VALUE
    }

}