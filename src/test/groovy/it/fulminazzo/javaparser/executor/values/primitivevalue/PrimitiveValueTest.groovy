package it.fulminazzo.javaparser.executor.values.primitivevalue

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue
import it.fulminazzo.javaparser.executor.values.ValueException
import spock.lang.Specification

class PrimitiveValueTest extends Specification {

    def 'test toClassValue of #value should return #expected'() {
        when:
        def classValue = value.toClassValue()

        then:
        classValue == expected

        where:
        value | expected
        new CharacterValue('c' as char) | PrimitiveClassValue.CHAR
        new IntegerValue(1)             | PrimitiveClassValue.INT
        new LongValue(1)                | PrimitiveClassValue.LONG
        new FloatValue(1)               | PrimitiveClassValue.FLOAT
        new DoubleValue(1)              | PrimitiveClassValue.DOUBLE
        BooleanValue.TRUE               | PrimitiveClassValue.BOOLEAN
        BooleanValue.FALSE              | PrimitiveClassValue.BOOLEAN
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
        true     | "Character" | new CharacterValue('a' as char)
        false    | "Integer"   | new CharacterValue('a' as char)
        false    | "Long"      | new CharacterValue('a' as char)
        false    | "Float"     | new CharacterValue('a' as char)
        false    | "Double"    | new CharacterValue('a' as char)
        false    | "Boolean"   | new CharacterValue('a' as char)
        false    | "String"    | new CharacterValue('a' as char)
        // Integer
        false    | "Character" | new IntegerValue(1)
        true     | "Integer"   | new IntegerValue(1)
        false    | "Long"      | new IntegerValue(1)
        false    | "Float"     | new IntegerValue(1)
        false    | "Double"    | new IntegerValue(1)
        false    | "Boolean"   | new IntegerValue(1)
        false    | "String"    | new IntegerValue(1)
        // Long
        false    | "Character" | new LongValue(1L)
        false    | "Integer"   | new LongValue(1L)
        true     | "Long"      | new LongValue(1L)
        false    | "Float"     | new LongValue(1L)
        false    | "Double"    | new LongValue(1L)
        false    | "Boolean"   | new LongValue(1L)
        false    | "String"    | new LongValue(1L)
        // Float
        false    | "Character" | new FloatValue(1.0f)
        false    | "Integer"   | new FloatValue(1.0f)
        false    | "Long"      | new FloatValue(1.0f)
        true     | "Float"     | new FloatValue(1.0f)
        false    | "Double"    | new FloatValue(1.0f)
        false    | "Boolean"   | new FloatValue(1.0f)
        false    | "String"    | new FloatValue(1.0f)
        // Double
        false    | "Character" | new DoubleValue(1.0d)
        false    | "Integer"   | new DoubleValue(1.0d)
        false    | "Long"      | new DoubleValue(1.0d)
        false    | "Float"     | new DoubleValue(1.0d)
        true     | "Double"    | new DoubleValue(1.0d)
        false    | "Boolean"   | new DoubleValue(1.0d)
        false    | "String"    | new DoubleValue(1.0d)
        // Boolean
        false    | "Character" | BooleanValue.TRUE
        false    | "Integer"   | BooleanValue.TRUE
        false    | "Long"      | BooleanValue.TRUE
        false    | "Float"     | BooleanValue.TRUE
        false    | "Double"    | BooleanValue.TRUE
        true     | "Boolean"   | BooleanValue.TRUE
        false    | "String"    | BooleanValue.TRUE
        // String
        false    | "Character" | new StringValue('Hello, world!')
        false    | "Integer"   | new StringValue('Hello, world!')
        false    | "Long"      | new StringValue('Hello, world!')
        false    | "Float"     | new StringValue('Hello, world!')
        false    | "Double"    | new StringValue('Hello, world!')
        false    | "Boolean"   | new StringValue('Hello, world!')
        true     | "String"    | new StringValue('Hello, world!')
    }

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
        false            | BooleanValue.FALSE
        'Hello, world!'  | new StringValue('Hello, world!')
        'a' as char      | new CharacterValue('a' as Character)
        1 as byte        | new IntegerValue(1)
        2 as short       | new IntegerValue(2)
        3                | new IntegerValue(3)
    }

    def 'test conversion of invalid type'() {
        given:
        def value = new DoubleValue(1.0)

        when:
        PrimitiveValue.of(value)

        then:
        def e = thrown(ValueException)
        e.message == ValueException.invalidPrimitiveValue(value).message
    }

}