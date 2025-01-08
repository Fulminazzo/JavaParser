package it.fulminazzo.mojito.executor.values.primitivevalue

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.mojito.executor.ExecutorException
import it.fulminazzo.mojito.executor.values.PrimitiveClassValue
import spock.lang.Specification

class PrimitiveValueTest extends Specification {

    def 'test toClass of #value should return #expected'() {
        when:
        def classValue = value.toClass()

        then:
        classValue == expected

        where:
        value                      | expected
        new CharValue('c' as char) | PrimitiveClassValue.CHAR
        new ByteValue(1 as byte)   | PrimitiveClassValue.BYTE
        new ShortValue(1 as short) | PrimitiveClassValue.SHORT
        new IntValue(1)            | PrimitiveClassValue.INT
        new LongValue(1)           | PrimitiveClassValue.LONG
        new FloatValue(1)          | PrimitiveClassValue.FLOAT
        new DoubleValue(1)         | PrimitiveClassValue.DOUBLE
        BooleanValue.TRUE          | PrimitiveClassValue.BOOLEAN
        BooleanValue.FALSE         | PrimitiveClassValue.BOOLEAN
    }

    def 'test is#method should return #expected for #value'() {
        given:
        def refl = new Refl<>(value)

        when:
        def actual = refl.invokeMethod("is${method}")

        then:
        actual == expected

        where:
        expected | method      | value
        // Byte
        false    | 'Character' | new ByteValue(1 as byte)
        false    | 'Integer'   | new ByteValue(1 as byte)
        false    | 'Long'      | new ByteValue(1 as byte)
        false    | 'Float'     | new ByteValue(1 as byte)
        false    | 'Double'    | new ByteValue(1 as byte)
        false    | 'Boolean'   | new ByteValue(1 as byte)
        false    | 'String'    | new ByteValue(1 as byte)
        // Short
        false    | 'Character' | new ShortValue(1 as short)
        false    | 'Integer'   | new ShortValue(1 as short)
        false    | 'Long'      | new ShortValue(1 as short)
        false    | 'Float'     | new ShortValue(1 as short)
        false    | 'Double'    | new ShortValue(1 as short)
        false    | 'Boolean'   | new ShortValue(1 as short)
        false    | 'String'    | new ShortValue(1 as short)
        // Character
        true     | 'Character' | new CharValue('a' as char)
        false    | 'Integer'   | new CharValue('a' as char)
        false    | 'Long'      | new CharValue('a' as char)
        false    | 'Float'     | new CharValue('a' as char)
        false    | 'Double'    | new CharValue('a' as char)
        false    | 'Boolean'   | new CharValue('a' as char)
        false    | 'String'    | new CharValue('a' as char)
        // Integer
        false    | 'Character' | new IntValue(1)
        true     | 'Integer'   | new IntValue(1)
        false    | 'Long'      | new IntValue(1)
        false    | 'Float'     | new IntValue(1)
        false    | 'Double'    | new IntValue(1)
        false    | 'Boolean'   | new IntValue(1)
        false    | 'String'    | new IntValue(1)
        // Long
        false    | 'Character' | new LongValue(1L)
        false    | 'Integer'   | new LongValue(1L)
        true     | 'Long'      | new LongValue(1L)
        false    | 'Float'     | new LongValue(1L)
        false    | 'Double'    | new LongValue(1L)
        false    | 'Boolean'   | new LongValue(1L)
        false    | 'String'    | new LongValue(1L)
        // Float
        false    | 'Character' | new FloatValue(1.0f)
        false    | 'Integer'   | new FloatValue(1.0f)
        false    | 'Long'      | new FloatValue(1.0f)
        true     | 'Float'     | new FloatValue(1.0f)
        false    | 'Double'    | new FloatValue(1.0f)
        false    | 'Boolean'   | new FloatValue(1.0f)
        false    | 'String'    | new FloatValue(1.0f)
        // Double
        false    | 'Character' | new DoubleValue(1.0d)
        false    | 'Integer'   | new DoubleValue(1.0d)
        false    | 'Long'      | new DoubleValue(1.0d)
        false    | 'Float'     | new DoubleValue(1.0d)
        true     | 'Double'    | new DoubleValue(1.0d)
        false    | 'Boolean'   | new DoubleValue(1.0d)
        false    | 'String'    | new DoubleValue(1.0d)
        // Boolean
        false    | 'Character' | BooleanValue.TRUE
        false    | 'Integer'   | BooleanValue.TRUE
        false    | 'Long'      | BooleanValue.TRUE
        false    | 'Float'     | BooleanValue.TRUE
        false    | 'Double'    | BooleanValue.TRUE
        true     | 'Boolean'   | BooleanValue.TRUE
        false    | 'String'    | BooleanValue.TRUE
    }

    def 'test toPrimitive of #expected should return #expected'() {
        when:
        def converted = expected.toPrimitive()

        then:
        converted == expected

        where:
        expected << [
                new DoubleValue(1.0d), new FloatValue(1.0f), new LongValue(1L),
                BooleanValue.TRUE, BooleanValue.FALSE, new CharValue('a' as Character),
                new ByteValue(1 as byte), new ShortValue(2 as short), new IntValue(3)
        ]
    }

    def 'test conversion of #value should return #expected'() {
        when:
        def converted = PrimitiveValue.of(value)

        then:
        converted == expected

        where:
        value       | expected
        1.0d        | new DoubleValue(1.0d)
        1.0f        | new FloatValue(1.0f)
        1L          | new LongValue(1L)
        true        | BooleanValue.TRUE
        false       | BooleanValue.FALSE
        'a' as char | new CharValue('a' as Character)
        1 as byte   | new ByteValue(1 as byte)
        2 as short  | new ShortValue(2 as short)
        3           | new IntValue(3)
    }

    def 'test conversion of invalid type'() {
        given:
        def value = new DoubleValue(1.0)

        when:
        PrimitiveValue.of(value)

        then:
        def e = thrown(ExecutorException)
        e.message == ExecutorException.invalidPrimitiveValue(value).message
    }

}