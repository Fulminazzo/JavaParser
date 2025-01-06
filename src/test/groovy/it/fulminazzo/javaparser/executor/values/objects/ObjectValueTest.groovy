package it.fulminazzo.javaparser.executor.values.objects

import it.fulminazzo.fulmicollection.objects.Refl
import it.fulminazzo.javaparser.executor.ExecutorException
import it.fulminazzo.javaparser.executor.values.primitivevalue.BooleanValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue
import spock.lang.Specification

class ObjectValueTest extends Specification {

    def 'test string concatenation #first . #second should return #expected'() {
        when:
        def value = first.add(second)

        then:
        value.getValue() == expected

        where:
        first                            | second                            | expected
        ObjectValue.of('Hello, ')        | ObjectValue.of('world!')          | 'Hello, world!'
        ObjectValue.of('Object: ')       | PrimitiveValue.of(1 as byte)      | 'Object: 1'
        PrimitiveValue.of(1 as byte)     | ObjectValue.of(' is the object!') | '1 is the object!'
        ObjectValue.of('Object: ')       | ObjectValue.of(1 as Byte)         | 'Object: 1'
        ObjectValue.of(1 as Byte)        | ObjectValue.of(' is the object!') | '1 is the object!'
        ObjectValue.of('Object: ')       | PrimitiveValue.of(2 as short)     | 'Object: 2'
        PrimitiveValue.of(2 as short)    | ObjectValue.of(' is the object!') | '2 is the object!'
        ObjectValue.of('Object: ')       | ObjectValue.of(2 as Short)        | 'Object: 2'
        ObjectValue.of(2 as Short)       | ObjectValue.of(' is the object!') | '2 is the object!'
        ObjectValue.of('Object: ')       | PrimitiveValue.of('a' as char)    | 'Object: a'
        PrimitiveValue.of('a' as char)   | ObjectValue.of(' is the object!') | 'a is the object!'
        ObjectValue.of('Object: ')       | ObjectValue.of('a' as Character)  | 'Object: a'
        ObjectValue.of('a' as Character) | ObjectValue.of(' is the object!') | 'a is the object!'
        ObjectValue.of('Object: ')       | PrimitiveValue.of(4)              | 'Object: 4'
        PrimitiveValue.of(4)             | ObjectValue.of(' is the object!') | '4 is the object!'
        ObjectValue.of('Object: ')       | ObjectValue.of(4 as Integer)      | 'Object: 4'
        ObjectValue.of(4 as Integer)     | ObjectValue.of(' is the object!') | '4 is the object!'
        ObjectValue.of('Object: ')       | PrimitiveValue.of(5L)             | 'Object: 5'
        PrimitiveValue.of(5L)            | ObjectValue.of(' is the object!') | '5 is the object!'
        ObjectValue.of('Object: ')       | ObjectValue.of(5L as Long)        | 'Object: 5'
        ObjectValue.of(5L as Long)       | ObjectValue.of(' is the object!') | '5 is the object!'
        ObjectValue.of('Object: ')       | PrimitiveValue.of(6.0f)           | 'Object: 6.0'
        PrimitiveValue.of(6.0f)          | ObjectValue.of(' is the object!') | '6.0 is the object!'
        ObjectValue.of('Object: ')       | ObjectValue.of(6.0f as Float)     | 'Object: 6.0'
        ObjectValue.of(6.0f as Float)    | ObjectValue.of(' is the object!') | '6.0 is the object!'
        ObjectValue.of('Object: ')       | PrimitiveValue.of(7.0d)           | 'Object: 7.0'
        PrimitiveValue.of(7.0d)          | ObjectValue.of(' is the object!') | '7.0 is the object!'
        ObjectValue.of('Object: ')       | ObjectValue.of(7.0d as Double)    | 'Object: 7.0'
        ObjectValue.of(7.0d as Double)   | ObjectValue.of(' is the object!') | '7.0 is the object!'
        ObjectValue.of('Object: ')       | BooleanValue.TRUE                 | 'Object: true'
        ObjectValue.of('Object: ')       | ObjectValue.of(Boolean.TRUE)      | 'Object: true'
        ObjectValue.of(Boolean.TRUE)     | ObjectValue.of(' is the object!') | 'true is the object!'
        ObjectValue.of('Object: ')       | BooleanValue.FALSE                | 'Object: false'
        ObjectValue.of('Object: ')       | ObjectValue.of(Boolean.FALSE)     | 'Object: false'
        ObjectValue.of(Boolean.FALSE)    | ObjectValue.of(' is the object!') | 'false is the object!'
    }

    def 'test toPrimitive should return #expected'() {
        given:
        def primitive = wrapper.toPrimitive()

        expect:
        primitive == expected

        where:
        expected                       | wrapper
        PrimitiveValue.of(1 as byte)   | ObjectValue.of(1 as Byte)
        PrimitiveValue.of(2 as short)  | ObjectValue.of(2 as Short)
        PrimitiveValue.of('a' as char) | ObjectValue.of('a' as Character)
        PrimitiveValue.of(4)           | ObjectValue.of(4 as Integer)
        PrimitiveValue.of(5L)          | ObjectValue.of(5L as Long)
        PrimitiveValue.of(6.0f)        | ObjectValue.of(6.0f as Float)
        PrimitiveValue.of(7.0d)        | ObjectValue.of(7.0d as Double)
        BooleanValue.TRUE              | ObjectValue.of(Boolean.TRUE)
        BooleanValue.FALSE             | ObjectValue.of(Boolean.FALSE)
    }

    def 'test toPrimitive of non-primitive should throw'() {
        given:
        def value = ObjectValue.of(this)

        when:
        value.toPrimitive()

        then:
        def e = thrown(ExecutorException)
        e.message == ExecutorException.noPrimitive(value).message
    }

    def 'test invalid to'() {
        given:
        def value = ObjectValue.of(this)

        when:
        value.to(PrimitiveValue)

        then:
        thrown(ExecutorException)
    }

    def 'test wrapper #wrapper and primitive #primitive should be equal'() {
        expect:
        wrapper.equal(primitive)
        primitive.equal(wrapper)

        where:
        primitive                      | wrapper
        PrimitiveValue.of(1 as byte)   | ObjectValue.of(1 as Byte)
        PrimitiveValue.of(2 as short)  | ObjectValue.of(2 as Short)
        PrimitiveValue.of('a' as char) | ObjectValue.of('a' as Character)
        PrimitiveValue.of(4)           | ObjectValue.of(4 as Integer)
        PrimitiveValue.of(5L)          | ObjectValue.of(5L as Long)
        PrimitiveValue.of(6.0f)        | ObjectValue.of(6.0f as Float)
        PrimitiveValue.of(7.0d)        | ObjectValue.of(7.0d as Double)
        BooleanValue.TRUE              | ObjectValue.of(Boolean.TRUE)
        BooleanValue.FALSE             | ObjectValue.of(Boolean.FALSE)
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
