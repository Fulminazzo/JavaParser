package it.fulminazzo.javaparser.executor.values.objects

import it.fulminazzo.fulmicollection.objects.Refl
import spock.lang.Specification

class ObjectValueTest extends Specification {

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
        true     | "Character" | new ObjectValue('a' as char)
        false    | "Integer"   | new ObjectValue('a' as char)
        false    | "Long"      | new ObjectValue('a' as char)
        false    | "Float"     | new ObjectValue('a' as char)
        false    | "Double"    | new ObjectValue('a' as char)
        false    | "Boolean"   | new ObjectValue('a' as char)
        false    | "String"    | new ObjectValue('a' as char)
        // Integer
        false    | "Character" | new ObjectValue(1)
        true     | "Integer"   | new ObjectValue(1)
        false    | "Long"      | new ObjectValue(1)
        false    | "Float"     | new ObjectValue(1)
        false    | "Double"    | new ObjectValue(1)
        false    | "Boolean"   | new ObjectValue(1)
        false    | "String"    | new ObjectValue(1)
        // Long
        false    | "Character" | new ObjectValue(1L)
        false    | "Integer"   | new ObjectValue(1L)
        true     | "Long"      | new ObjectValue(1L)
        false    | "Float"     | new ObjectValue(1L)
        false    | "Double"    | new ObjectValue(1L)
        false    | "Boolean"   | new ObjectValue(1L)
        false    | "String"    | new ObjectValue(1L)
        // Float
        false    | "Character" | new ObjectValue(1.0f)
        false    | "Integer"   | new ObjectValue(1.0f)
        false    | "Long"      | new ObjectValue(1.0f)
        true     | "Float"     | new ObjectValue(1.0f)
        false    | "Double"    | new ObjectValue(1.0f)
        false    | "Boolean"   | new ObjectValue(1.0f)
        false    | "String"    | new ObjectValue(1.0f)
        // Double
        false    | "Character" | new ObjectValue(1.0d)
        false    | "Integer"   | new ObjectValue(1.0d)
        false    | "Long"      | new ObjectValue(1.0d)
        false    | "Float"     | new ObjectValue(1.0d)
        true     | "Double"    | new ObjectValue(1.0d)
        false    | "Boolean"   | new ObjectValue(1.0d)
        false    | "String"    | new ObjectValue(1.0d)
        // Boolean
        false    | "Character" | new ObjectValue(true)
        false    | "Integer"   | new ObjectValue(true)
        false    | "Long"      | new ObjectValue(true)
        false    | "Float"     | new ObjectValue(true)
        false    | "Double"    | new ObjectValue(true)
        true     | "Boolean"   | new ObjectValue(true)
        false    | "String"    | new ObjectValue(true)
        // String
        false    | "Character" | new ObjectValue('Hello, world!')
        false    | "Integer"   | new ObjectValue('Hello, world!')
        false    | "Long"      | new ObjectValue('Hello, world!')
        false    | "Float"     | new ObjectValue('Hello, world!')
        false    | "Double"    | new ObjectValue('Hello, world!')
        false    | "Boolean"   | new ObjectValue('Hello, world!')
        true     | "String"    | new ObjectValue('Hello, world!')
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
