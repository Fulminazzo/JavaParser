package it.fulminazzo.javaparser.executor.values.primitive

import spock.lang.Specification

class CharacterValueTest extends Specification {

    def 'test #first + #second = #third'() {
        when:
        def eval = first.add(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue(194)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue(99)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue(99)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue(99)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue(99)
    }

    def 'test #first - #second = #third'() {
        when:
        def eval = first.subtract(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue(0)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue(95)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue(95)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue(95)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue(95)
    }

    def 'test #first * #second = #third'() {
        when:
        def eval = first.multiply(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue(9409)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue(194)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue(194)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue(194)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue(194)
    }

    def 'test #first / #second = #third'() {
        when:
        def eval = first.divide(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue(1)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue(48)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue(48)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue(48.5)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue(48.5)
    }

    def 'test #first % #second = #third'() {
        when:
        def eval = first.modulo(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue(0)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue(1)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue(1)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue(1)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue(1)
    }

}