package it.fulminazzo.javaparser.executor.values.primitive

import spock.lang.Specification

class NumberValueTest extends Specification {

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
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue(4L)
        new LongValue(4L)    | new IntegerValue(2)              | new LongValue(0)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue(0)
        new LongValue(4L)    | new FloatValue(2.0f)             | new FloatValue(0)
        new LongValue(4L)    | new DoubleValue(2.0d)            | new DoubleValue(0)
        // Float
        new FloatValue(4.0f) | new CharacterValue('a' as char)  | new FloatValue(4.0f)
        new FloatValue(4.0f) | new IntegerValue(2)              | new FloatValue(0)
        new FloatValue(4.0f) | new LongValue(2L)                | new FloatValue(0)
        new FloatValue(4.0f) | new FloatValue(2.0f)             | new FloatValue(0)
        new FloatValue(4.0f) | new DoubleValue(2.0d)            | new DoubleValue(0)
        // Double
        new DoubleValue(4.0d) | new CharacterValue('a' as char) | new DoubleValue(4.0d)
        new DoubleValue(4.0d) | new IntegerValue(2)             | new DoubleValue(0)
        new DoubleValue(4.0d) | new LongValue(2L)               | new DoubleValue(0)
        new DoubleValue(4.0d) | new FloatValue(2.0f)            | new DoubleValue(0)
        new DoubleValue(4.0d) | new DoubleValue(2.0d)           | new DoubleValue(0)
    }

}