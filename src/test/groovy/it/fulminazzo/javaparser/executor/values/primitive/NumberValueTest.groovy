package it.fulminazzo.javaparser.executor.values.primitive

import spock.lang.Specification

class NumberValueTest extends Specification {

    def 'test #first << #second = #third'() {
        when:
        def eval = first.lshift(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntegerValue(4)  | new CharacterValue('a' as char)  | new IntegerValue(8)
        new IntegerValue(4)  | new IntegerValue(2)              | new IntegerValue(16)
        new IntegerValue(4)  | new LongValue(2L)                | new LongValue(16)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue(8)
        new LongValue(4L)    | new IntegerValue(2)              | new LongValue(16)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue(34359738368)
    }

    def 'test #first >> #second = #third'() {
        when:
        def eval = first.rshift(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntegerValue(4)  | new CharacterValue('a' as char)  | new IntegerValue(2)
        new IntegerValue(4)  | new IntegerValue(2)              | new IntegerValue(1)
        new IntegerValue(4)  | new LongValue(2L)                | new LongValue(1)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue(101)
        new LongValue(4L)    | new IntegerValue(2)              | new LongValue(6)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue(6)
    }

    def 'test #first >>> #second = #third'() {
        when:
        def eval = first.urshift(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntegerValue(4)  | new CharacterValue('a' as char)  | new IntegerValue(101)
        new IntegerValue(4)  | new IntegerValue(2)              | new IntegerValue(6)
        new IntegerValue(4)  | new LongValue(2L)                | new LongValue(6)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue(101)
        new LongValue(4L)    | new IntegerValue(2)              | new LongValue(6)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue(6)
    }

    def 'test #first + #second = #third'() {
        when:
        def eval = first.add(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntegerValue(4)  | new CharacterValue('a' as char)  | new IntegerValue(101)
        new IntegerValue(4)  | new IntegerValue(2)              | new IntegerValue(6)
        new IntegerValue(4)  | new LongValue(2L)                | new LongValue(6)
        new IntegerValue(4)  | new FloatValue(2.0f)             | new FloatValue(6)
        new IntegerValue(4)  | new DoubleValue(2.0d)            | new DoubleValue(6)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue(101)
        new LongValue(4L)    | new IntegerValue(2)              | new LongValue(6)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue(6)
        new LongValue(4L)    | new FloatValue(2.0f)             | new FloatValue(6)
        new LongValue(4L)    | new DoubleValue(2.0d)            | new DoubleValue(6)
        // Float
        new FloatValue(4.0f) | new CharacterValue('a' as char)  | new FloatValue(101)
        new FloatValue(4.0f) | new IntegerValue(2)              | new FloatValue(6)
        new FloatValue(4.0f) | new LongValue(2L)                | new FloatValue(6)
        new FloatValue(4.0f) | new FloatValue(2.0f)             | new FloatValue(6)
        new FloatValue(4.0f) | new DoubleValue(2.0d)            | new DoubleValue(6)
        // Double
        new DoubleValue(4.0d) | new CharacterValue('a' as char) | new DoubleValue(101)
        new DoubleValue(4.0d) | new IntegerValue(2)             | new DoubleValue(6)
        new DoubleValue(4.0d) | new LongValue(2L)               | new DoubleValue(6)
        new DoubleValue(4.0d) | new FloatValue(2.0f)            | new DoubleValue(6)
        new DoubleValue(4.0d) | new DoubleValue(2.0d)           | new DoubleValue(6)
    }

    def 'test #first - #second = #third'() {
        when:
        def eval = first.subtract(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntegerValue(4)  | new CharacterValue('a' as char)  | new IntegerValue(-93)
        new IntegerValue(4)  | new IntegerValue(2)              | new IntegerValue(2)
        new IntegerValue(4)  | new LongValue(2L)                | new LongValue(2)
        new IntegerValue(4)  | new FloatValue(2.0f)             | new FloatValue(2)
        new IntegerValue(4)  | new DoubleValue(2.0d)            | new DoubleValue(2)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue(-93)
        new LongValue(4L)    | new IntegerValue(2)              | new LongValue(2)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue(2)
        new LongValue(4L)    | new FloatValue(2.0f)             | new FloatValue(2)
        new LongValue(4L)    | new DoubleValue(2.0d)            | new DoubleValue(2)
        // Float
        new FloatValue(4.0f) | new CharacterValue('a' as char)  | new FloatValue(-93)
        new FloatValue(4.0f) | new IntegerValue(2)              | new FloatValue(2)
        new FloatValue(4.0f) | new LongValue(2L)                | new FloatValue(2)
        new FloatValue(4.0f) | new FloatValue(2.0f)             | new FloatValue(2)
        new FloatValue(4.0f) | new DoubleValue(2.0d)            | new DoubleValue(2)
        // Double
        new DoubleValue(4.0d) | new CharacterValue('a' as char) | new DoubleValue(-93)
        new DoubleValue(4.0d) | new IntegerValue(2)             | new DoubleValue(2)
        new DoubleValue(4.0d) | new LongValue(2L)               | new DoubleValue(2)
        new DoubleValue(4.0d) | new FloatValue(2.0f)            | new DoubleValue(2)
        new DoubleValue(4.0d) | new DoubleValue(2.0d)           | new DoubleValue(2)
    }

    def 'test #first * #second = #third'() {
        when:
        def eval = first.multiply(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntegerValue(4)  | new CharacterValue('a' as char)  | new IntegerValue(388)
        new IntegerValue(4)  | new IntegerValue(2)              | new IntegerValue(8)
        new IntegerValue(4)  | new LongValue(2L)                | new LongValue(8)
        new IntegerValue(4)  | new FloatValue(2.0f)             | new FloatValue(8)
        new IntegerValue(4)  | new DoubleValue(2.0d)            | new DoubleValue(8)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue(388)
        new LongValue(4L)    | new IntegerValue(2)              | new LongValue(8)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue(8)
        new LongValue(4L)    | new FloatValue(2.0f)             | new FloatValue(8)
        new LongValue(4L)    | new DoubleValue(2.0d)            | new DoubleValue(8)
        // Float
        new FloatValue(4.0f) | new CharacterValue('a' as char)  | new FloatValue(388)
        new FloatValue(4.0f) | new IntegerValue(2)              | new FloatValue(8)
        new FloatValue(4.0f) | new LongValue(2L)                | new FloatValue(8)
        new FloatValue(4.0f) | new FloatValue(2.0f)             | new FloatValue(8)
        new FloatValue(4.0f) | new DoubleValue(2.0d)            | new DoubleValue(8)
        // Double
        new DoubleValue(4.0d) | new CharacterValue('a' as char) | new DoubleValue(388)
        new DoubleValue(4.0d) | new IntegerValue(2)             | new DoubleValue(8)
        new DoubleValue(4.0d) | new LongValue(2L)               | new DoubleValue(8)
        new DoubleValue(4.0d) | new FloatValue(2.0f)            | new DoubleValue(8)
        new DoubleValue(4.0d) | new DoubleValue(2.0d)           | new DoubleValue(8)
    }

    def 'test #first / #second = #third'() {
        when:
        def eval = first.divide(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntegerValue(4)  | new CharacterValue('a' as char)  | new IntegerValue(0)
        new IntegerValue(4)  | new IntegerValue(2)              | new IntegerValue(2)
        new IntegerValue(4)  | new LongValue(2L)                | new LongValue(2)
        new IntegerValue(4)  | new FloatValue(2.0f)             | new FloatValue(2)
        new IntegerValue(4)  | new DoubleValue(2.0d)            | new DoubleValue(2)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue(0)
        new LongValue(4L)    | new IntegerValue(2)              | new LongValue(2)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue(2)
        new LongValue(4L)    | new FloatValue(2.0f)             | new FloatValue(2)
        new LongValue(4L)    | new DoubleValue(2.0d)            | new DoubleValue(2)
        // Float
        new FloatValue(4.0f) | new CharacterValue('a' as char)  | new FloatValue(0.041237112)
        new FloatValue(4.0f) | new IntegerValue(2)              | new FloatValue(2)
        new FloatValue(4.0f) | new LongValue(2L)                | new FloatValue(2)
        new FloatValue(4.0f) | new FloatValue(2.0f)             | new FloatValue(2)
        new FloatValue(4.0f) | new DoubleValue(2.0d)            | new DoubleValue(2)
        // Double
        new DoubleValue(4.0d) | new CharacterValue('a' as char) | new DoubleValue(0.041237113402061855)
        new DoubleValue(4.0d) | new IntegerValue(2)             | new DoubleValue(2)
        new DoubleValue(4.0d) | new LongValue(2L)               | new DoubleValue(2)
        new DoubleValue(4.0d) | new FloatValue(2.0f)            | new DoubleValue(2)
        new DoubleValue(4.0d) | new DoubleValue(2.0d)           | new DoubleValue(2)
    }

    def 'test #first % #second = #third'() {
        when:
        def eval = first.modulo(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntegerValue(4)  | new CharacterValue('a' as char)  | new IntegerValue(4)
        new IntegerValue(4)  | new IntegerValue(2)              | new IntegerValue(0)
        new IntegerValue(4)  | new LongValue(2L)                | new LongValue(0)
        new IntegerValue(4)  | new FloatValue(2.0f)             | new FloatValue(0)
        new IntegerValue(4)  | new DoubleValue(2.0d)            | new DoubleValue(0)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue(4)
        new LongValue(4L)    | new IntegerValue(2)              | new LongValue(0)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue(0)
        new LongValue(4L)    | new FloatValue(2.0f)             | new FloatValue(0)
        new LongValue(4L)    | new DoubleValue(2.0d)            | new DoubleValue(0)
        // Float
        new FloatValue(4.0f) | new CharacterValue('a' as char)  | new FloatValue(4)
        new FloatValue(4.0f) | new IntegerValue(2)              | new FloatValue(0)
        new FloatValue(4.0f) | new LongValue(2L)                | new FloatValue(0)
        new FloatValue(4.0f) | new FloatValue(2.0f)             | new FloatValue(0)
        new FloatValue(4.0f) | new DoubleValue(2.0d)            | new DoubleValue(0)
        // Double
        new DoubleValue(4.0d) | new CharacterValue('a' as char) | new DoubleValue(4)
        new DoubleValue(4.0d) | new IntegerValue(2)             | new DoubleValue(0)
        new DoubleValue(4.0d) | new LongValue(2L)               | new DoubleValue(0)
        new DoubleValue(4.0d) | new FloatValue(2.0f)            | new DoubleValue(0)
        new DoubleValue(4.0d) | new DoubleValue(2.0d)           | new DoubleValue(0)
    }

}