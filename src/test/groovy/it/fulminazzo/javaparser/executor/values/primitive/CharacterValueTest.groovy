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
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue((((int) 'a') + ((int) 'a')) as Integer)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue((((int) 'a') + 2) as Integer)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue((((int) 'a') + 2L) as Long)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue((((int) 'a') + 2.0f) as Float)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue((((int) 'a') + 2.0d) as Double)
    }

    def 'test #first - #second = #third'() {
        when:
        def eval = first.subtract(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue((((int) 'a') - ((int) 'a')) as Integer)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue((((int) 'a') - 2) as Integer)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue((((int) 'a') - 2L) as Long)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue((((int) 'a') - 2.0f) as Float)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue((((int) 'a') - 2.0d) as Double)
    }

    def 'test #first * #second = #third'() {
        when:
        def eval = first.multiply(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue((((int) 'a') * ((int) 'a')) as Integer)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue((((int) 'a') * 2) as Integer)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue((((int) 'a') * 2L) as Long)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue((((int) 'a') * 2.0f) as Float)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue((((int) 'a') * 2.0d) as Double)
    }

    def 'test #first / #second = #third'() {
        when:
        def eval = first.divide(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue((((int) 'a') / ((int) 'a')) as Integer)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue((((int) 'a') / 2) as Integer)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue((((int) 'a') / 2L) as Long)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue((((int) 'a') / 2.0f) as Float)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue((((int) 'a') / 2.0d) as Double)
    }

    def 'test #first % #second = #third'() {
        when:
        def eval = first.modulo(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharacterValue('a' as char)  | new CharacterValue('a' as char)  | new IntegerValue((((int) 'a') % ((int) 'a')) as Integer)
        new CharacterValue('a' as char)  | new IntegerValue(2)              | new IntegerValue((((int) 'a') % 2) as Integer)
        new CharacterValue('a' as char)  | new LongValue(2L)                | new LongValue((((int) 'a') % 2L) as Long)
        new CharacterValue('a' as char)  | new FloatValue(2.0f)             | new FloatValue((((int) 'a') % 2.0f) as Float)
        new CharacterValue('a' as char)  | new DoubleValue(2.0d)            | new DoubleValue((((int) 'a') % 2.0d) as Double)
    }

}