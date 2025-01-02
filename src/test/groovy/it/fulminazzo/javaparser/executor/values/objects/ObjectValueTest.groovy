package it.fulminazzo.javaparser.executor.values.objects

import it.fulminazzo.fulmicollection.objects.Refl
import spock.lang.Specification

class ObjectValueTest extends Specification {

    def 'test wrapper #wrapper and primitive #primitive should be equal'() {
        expect:
        wrapper == primitive
        primitive == wrapper

        where:
        primitive | wrapper
        BYTE      | BYTE_WRAPPER
        SHORT     | SHORT_WRAPPER
        CHAR      | CHARACTER
        INT       | INTEGER
        LONG      | LONG_WRAPPER
        FLOAT     | FLOAT_WRAPPER
        DOUBLE    | DOUBLE_WRAPPER
        BOOLEAN   | BOOLEAN_WRAPPER
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
        // Character
        true     | "Character" | ObjectValue.of('a' as char)
        false    | "Integer"   | ObjectValue.of('a' as char)
        false    | "Long"      | ObjectValue.of('a' as char)
        false    | "Float"     | ObjectValue.of('a' as char)
        false    | "Double"    | ObjectValue.of('a' as char)
        false    | "Boolean"   | ObjectValue.of('a' as char)
        false    | "String"    | ObjectValue.of('a' as char)
        // Integer
        false    | "Character" | ObjectValue.of(1)
        true     | "Integer"   | ObjectValue.of(1)
        false    | "Long"      | ObjectValue.of(1)
        false    | "Float"     | ObjectValue.of(1)
        false    | "Double"    | ObjectValue.of(1)
        false    | "Boolean"   | ObjectValue.of(1)
        false    | "String"    | ObjectValue.of(1)
        // Long
        false    | "Character" | ObjectValue.of(1L)
        false    | "Integer"   | ObjectValue.of(1L)
        true     | "Long"      | ObjectValue.of(1L)
        false    | "Float"     | ObjectValue.of(1L)
        false    | "Double"    | ObjectValue.of(1L)
        false    | "Boolean"   | ObjectValue.of(1L)
        false    | "String"    | ObjectValue.of(1L)
        // Float
        false    | "Character" | ObjectValue.of(1.0f)
        false    | "Integer"   | ObjectValue.of(1.0f)
        false    | "Long"      | ObjectValue.of(1.0f)
        true     | "Float"     | ObjectValue.of(1.0f)
        false    | "Double"    | ObjectValue.of(1.0f)
        false    | "Boolean"   | ObjectValue.of(1.0f)
        false    | "String"    | ObjectValue.of(1.0f)
        // Double
        false    | "Character" | ObjectValue.of(1.0d)
        false    | "Integer"   | ObjectValue.of(1.0d)
        false    | "Long"      | ObjectValue.of(1.0d)
        false    | "Float"     | ObjectValue.of(1.0d)
        true     | "Double"    | ObjectValue.of(1.0d)
        false    | "Boolean"   | ObjectValue.of(1.0d)
        false    | "String"    | ObjectValue.of(1.0d)
        // Boolean
        false    | "Character" | ObjectValue.of(true)
        false    | "Integer"   | ObjectValue.of(true)
        false    | "Long"      | ObjectValue.of(true)
        false    | "Float"     | ObjectValue.of(true)
        false    | "Double"    | ObjectValue.of(true)
        true     | "Boolean"   | ObjectValue.of(true)
        false    | "String"    | ObjectValue.of(true)
        // String
        false    | "Character" | ObjectValue.of('Hello, world!')
        false    | "Integer"   | ObjectValue.of('Hello, world!')
        false    | "Long"      | ObjectValue.of('Hello, world!')
        false    | "Float"     | ObjectValue.of('Hello, world!')
        false    | "Double"    | ObjectValue.of('Hello, world!')
        false    | "Boolean"   | ObjectValue.of('Hello, world!')
        true     | "String"    | ObjectValue.of('Hello, world!')
    }

    def 'test toString of value #value should be equal to #expected'() {
        given:
        def objectValue = ObjectValue.of(value)
        expected = String.format(expected, value)

        when:
        def output = objectValue.toString()

        then:
        output == expected

        where:
        value                         | expected
        Byte.valueOf((byte) 1)        | 'ByteWrapperValue(%s)'
        Short.valueOf((short) 1)      | 'ShortWrapperValue(%s)'
        Character.valueOf((char) 'a') | 'CharacterValue(%s)'
        Integer.valueOf(1)            | 'IntegerValue(%s)'
        Long.valueOf(1)               | 'LongWrapperValue(%s)'
        Float.valueOf(1)              | 'FloatWrapperValue(%s)'
        Double.valueOf(1)             | 'DoubleWrapperValue(%s)'
        Boolean.valueOf(true)         | 'BooleanWrapperValue(%s)'
        Boolean.valueOf(false)        | 'BooleanWrapperValue(%s)'
        "Hello, world!"               | 'StringValue(%s)'
        [1, 2, 3]                     | 'ArrayListValue(%s)'
        ['key': 'value']              | 'LinkedHashMapValue(%s)'
        this                          | 'ObjectValue(%s)'
    }

}
