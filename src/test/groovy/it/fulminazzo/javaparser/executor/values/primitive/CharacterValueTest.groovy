package it.fulminazzo.javaparser.executor.values.primitive

import spock.lang.Specification

class CharacterValueTest extends Specification {
    private static int INT_CHAR = (int) 'a'

    def 'test #first + #second = #third'() {
        when:
        def eval = first.add(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue((INT_CHAR + INT_CHAR) as Integer)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue((INT_CHAR + 2) as Integer)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue((INT_CHAR + 2L) as Long)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue((INT_CHAR + 2.0f) as Float)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue((INT_CHAR + 2.0d) as Double)
    }

    def 'test #first - #second = #third'() {
        when:
        def eval = first.subtract(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue((INT_CHAR - INT_CHAR) as Integer)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue((INT_CHAR - 2) as Integer)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue((INT_CHAR - 2L) as Long)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue((INT_CHAR - 2.0f) as Float)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue((INT_CHAR - 2.0d) as Double)
    }

    def 'test #first * #second = #third'() {
        when:
        def eval = first.multiply(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue((INT_CHAR * INT_CHAR) as Integer)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue((INT_CHAR * 2) as Integer)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue((INT_CHAR * 2L) as Long)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue((INT_CHAR * 2.0f) as Float)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue((INT_CHAR * 2.0d) as Double)
    }

    def 'test #first / #second = #third'() {
        when:
        def eval = first.divide(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue((INT_CHAR / INT_CHAR) as Integer)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue((INT_CHAR / 2) as Integer)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue((INT_CHAR / 2L) as Long)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue((INT_CHAR / 2.0f) as Float)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue((INT_CHAR / 2.0d) as Double)
    }

    def 'test #first % #second = #third'() {
        when:
        def eval = first.modulo(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue((INT_CHAR % INT_CHAR) as Integer)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue((INT_CHAR % 2) as Integer)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue((INT_CHAR % 2L) as Long)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue((INT_CHAR % 2.0f) as Float)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue((INT_CHAR % 2.0d) as Double)
    }

}