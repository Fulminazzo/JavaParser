package it.fulminazzo.javaparser.executor.values.primitivevalue

import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue
import it.fulminazzo.javaparser.executor.values.TestClass
import it.fulminazzo.javaparser.executor.values.Value
import it.fulminazzo.javaparser.executor.values.Values
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue
import it.fulminazzo.javaparser.executor.values.objects.ObjectValue
import spock.lang.Specification

class ValueTest extends Specification {

    def 'test #value cast #classValue should return #expected'() {
        when:
        def cast = value.cast(classValue)

        then:
        cast == expected

        where:
        value                                         | classValue                  | expected
        PrimitiveValue.of((byte) 1)                   | ObjectClassValue.BYTE       | ObjectValue.of(Byte.valueOf((byte) 1))
        ObjectValue.of(Byte.valueOf((byte) 1))        | PrimitiveClassValue.BYTE    | PrimitiveValue.of((byte) 1)
        PrimitiveValue.of((short) 2)                  | ObjectClassValue.SHORT      | ObjectValue.of(Short.valueOf((short) 2))
        ObjectValue.of(Short.valueOf((short) 2))      | PrimitiveClassValue.SHORT   | PrimitiveValue.of((short) 2)
        PrimitiveValue.of((char) 'a')                 | ObjectClassValue.CHARACTER  | ObjectValue.of(Character.valueOf((char) 'a'))
        ObjectValue.of(Character.valueOf((char) 'a')) | PrimitiveClassValue.CHAR    | PrimitiveValue.of((char) 'a')
        PrimitiveValue.of(4)                          | ObjectClassValue.INTEGER    | ObjectValue.of(Integer.valueOf(4))
        ObjectValue.of(Integer.valueOf(4))            | PrimitiveClassValue.INT     | PrimitiveValue.of(4)
        PrimitiveValue.of(5L)                         | ObjectClassValue.LONG       | ObjectValue.of(Long.valueOf(5L))
        ObjectValue.of(Long.valueOf(5L))              | PrimitiveClassValue.LONG    | PrimitiveValue.of(5L)
        PrimitiveValue.of(6.0f)                       | ObjectClassValue.FLOAT      | ObjectValue.of(Float.valueOf(6.0f))
        ObjectValue.of(Float.valueOf(6.0f))           | PrimitiveClassValue.FLOAT   | PrimitiveValue.of(6.0f)
        PrimitiveValue.of(7.0d)                       | ObjectClassValue.DOUBLE     | ObjectValue.of(Double.valueOf(7.0d))
        ObjectValue.of(Double.valueOf(7.0d))          | PrimitiveClassValue.DOUBLE  | PrimitiveValue.of(7.0d)
        PrimitiveValue.of(true)                       | ObjectClassValue.BOOLEAN    | ObjectValue.of(Boolean.valueOf(true))
        ObjectValue.of(Boolean.valueOf(true))         | PrimitiveClassValue.BOOLEAN | PrimitiveValue.of(true)
        PrimitiveValue.of((byte) 1)                   | PrimitiveClassValue.INT     | PrimitiveValue.of(1)
        PrimitiveValue.of((short) 2)                  | PrimitiveClassValue.INT     | PrimitiveValue.of(2)
        PrimitiveValue.of((char) 'a')                 | PrimitiveClassValue.INT     | PrimitiveValue.of(97)
        PrimitiveValue.of(97)                         | ObjectClassValue.BYTE       | ObjectValue.of((byte) 97)
        PrimitiveValue.of((char) 'a')                 | ObjectClassValue.BYTE       | ObjectValue.of((byte) 97)
        PrimitiveValue.of(97)                         | ObjectClassValue.SHORT      | ObjectValue.of((short) 97)
        PrimitiveValue.of((char) 'a')                 | ObjectClassValue.SHORT      | ObjectValue.of((short) 97)
        PrimitiveValue.of(97)                         | PrimitiveClassValue.CHAR    | PrimitiveValue.of((char) 'a')
        PrimitiveValue.of(5L)                         | PrimitiveClassValue.INT     | PrimitiveValue.of(5)
        PrimitiveValue.of(6.0f)                       | PrimitiveClassValue.INT     | PrimitiveValue.of(6)
        PrimitiveValue.of(7.0d)                       | PrimitiveClassValue.INT     | PrimitiveValue.of(7)
    }

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