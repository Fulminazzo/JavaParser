package it.fulminazzo.javaparser.executor.values.primitivevalue

import spock.lang.Specification

class NumberValueTest extends Specification {
    private static int INT_CHAR = (int) 'a'

    def 'test #first == #second = #third'() {
        when:
        def eval = first.equal(second)

        then:
        eval == third

        where:
        first                      | second                          | third
        // Integer
        new IntValue(INT_CHAR) | new CharacterValue('a' as char) | BooleanValue.TRUE
        new IntValue(INT_CHAR) | new CharacterValue('b' as char) | BooleanValue.FALSE
        new IntValue(4)        | new IntValue(4)                 | BooleanValue.TRUE
        new IntValue(4)        | new IntValue(5)                 | BooleanValue.FALSE
        new IntValue(4)        | new LongValue(4L)               | BooleanValue.TRUE
        new IntValue(4)        | new LongValue(5L)               | BooleanValue.FALSE
        new IntValue(4)        | new FloatValue(4.0f)            | BooleanValue.TRUE
        new IntValue(4)        | new FloatValue(5.0f)            | BooleanValue.FALSE
        new IntValue(4)        | new DoubleValue(4.0d)           | BooleanValue.TRUE
        new IntValue(4)        | new DoubleValue(5.0d)           | BooleanValue.FALSE
        new IntValue(4)        | new StringValue('Hello')        | BooleanValue.FALSE
        // Long
        new LongValue(INT_CHAR)    | new CharacterValue('a' as char) | BooleanValue.TRUE
        new LongValue(INT_CHAR)    | new CharacterValue('b' as char) | BooleanValue.FALSE
        new LongValue(4L)      | new IntValue(4)                 | BooleanValue.TRUE
        new LongValue(4L)      | new IntValue(5)                 | BooleanValue.FALSE
        new LongValue(4L)          | new LongValue(4L)               | BooleanValue.TRUE
        new LongValue(4L)          | new LongValue(5L)               | BooleanValue.FALSE
        new LongValue(4L)          | new FloatValue(4.0f)            | BooleanValue.TRUE
        new LongValue(4L)          | new FloatValue(5.0f)            | BooleanValue.FALSE
        new LongValue(4L)          | new DoubleValue(4.0d)           | BooleanValue.TRUE
        new LongValue(4L)          | new DoubleValue(5.0d)           | BooleanValue.FALSE
        new LongValue(4L)          | new StringValue('Hello')        | BooleanValue.FALSE
        // Float
        new FloatValue(INT_CHAR)   | new CharacterValue('a' as char) | BooleanValue.TRUE
        new FloatValue(INT_CHAR)   | new CharacterValue('b' as char) | BooleanValue.FALSE
        new FloatValue(4.0f)   | new IntValue(4)                 | BooleanValue.TRUE
        new FloatValue(4.0f)   | new IntValue(5)                 | BooleanValue.FALSE
        new FloatValue(4.0f)       | new LongValue(4L)               | BooleanValue.TRUE
        new FloatValue(4.0f)       | new LongValue(5L)               | BooleanValue.FALSE
        new FloatValue(4.0f)       | new FloatValue(4.0f)            | BooleanValue.TRUE
        new FloatValue(4.0f)       | new FloatValue(5.0f)            | BooleanValue.FALSE
        new FloatValue(4.0f)       | new DoubleValue(4.0d)           | BooleanValue.TRUE
        new FloatValue(4.0f)       | new DoubleValue(5.0d)           | BooleanValue.FALSE
        new FloatValue(4.0f)       | new StringValue('Hello')        | BooleanValue.FALSE
        // Double
        new DoubleValue(INT_CHAR)  | new CharacterValue('a' as char) | BooleanValue.TRUE
        new DoubleValue(INT_CHAR)  | new CharacterValue('b' as char) | BooleanValue.FALSE
        new DoubleValue(4.0d)  | new IntValue(4)                 | BooleanValue.TRUE
        new DoubleValue(4.0d)  | new IntValue(5)                 | BooleanValue.FALSE
        new DoubleValue(4.0d)      | new LongValue(4L)               | BooleanValue.TRUE
        new DoubleValue(4.0d)      | new LongValue(5L)               | BooleanValue.FALSE
        new DoubleValue(4.0d)      | new FloatValue(4.0f)            | BooleanValue.TRUE
        new DoubleValue(4.0d)      | new FloatValue(5.0f)            | BooleanValue.FALSE
        new DoubleValue(4.0d)      | new DoubleValue(4.0d)           | BooleanValue.TRUE
        new DoubleValue(4.0d)      | new DoubleValue(5.0d)           | BooleanValue.FALSE
        new DoubleValue(4.0d)      | new StringValue('Hello')        | BooleanValue.FALSE
    }

    def 'test #first != #second = #third'() {
        when:
        def eval = first.notEqual(second)

        then:
        eval == third

        where:
        first                      | second                          | third
        // Integer
        new IntValue(INT_CHAR) | new CharacterValue('a' as char) | BooleanValue.FALSE
        new IntValue(INT_CHAR) | new CharacterValue('b' as char) | BooleanValue.TRUE
        new IntValue(4)        | new IntValue(4)                 | BooleanValue.FALSE
        new IntValue(4)        | new IntValue(5)                 | BooleanValue.TRUE
        new IntValue(4)        | new LongValue(4L)               | BooleanValue.FALSE
        new IntValue(4)        | new LongValue(5L)               | BooleanValue.TRUE
        new IntValue(4)        | new FloatValue(4.0f)            | BooleanValue.FALSE
        new IntValue(4)        | new FloatValue(5.0f)            | BooleanValue.TRUE
        new IntValue(4)        | new DoubleValue(4.0d)           | BooleanValue.FALSE
        new IntValue(4)        | new DoubleValue(5.0d)           | BooleanValue.TRUE
        // Long
        new LongValue(INT_CHAR)    | new CharacterValue('a' as char) | BooleanValue.FALSE
        new LongValue(INT_CHAR)    | new CharacterValue('b' as char) | BooleanValue.TRUE
        new LongValue(4L)      | new IntValue(4)                 | BooleanValue.FALSE
        new LongValue(4L)      | new IntValue(5)                 | BooleanValue.TRUE
        new LongValue(4L)          | new LongValue(4L)               | BooleanValue.FALSE
        new LongValue(4L)          | new LongValue(5L)               | BooleanValue.TRUE
        new LongValue(4L)          | new FloatValue(4.0f)            | BooleanValue.FALSE
        new LongValue(4L)          | new FloatValue(5.0f)            | BooleanValue.TRUE
        new LongValue(4L)          | new DoubleValue(4.0d)           | BooleanValue.FALSE
        new LongValue(4L)          | new DoubleValue(5.0d)           | BooleanValue.TRUE
        // Float
        new FloatValue(INT_CHAR)   | new CharacterValue('a' as char) | BooleanValue.FALSE
        new FloatValue(INT_CHAR)   | new CharacterValue('b' as char) | BooleanValue.TRUE
        new FloatValue(4.0f)   | new IntValue(4)                 | BooleanValue.FALSE
        new FloatValue(4.0f)   | new IntValue(5)                 | BooleanValue.TRUE
        new FloatValue(4.0f)       | new LongValue(4L)               | BooleanValue.FALSE
        new FloatValue(4.0f)       | new LongValue(5L)               | BooleanValue.TRUE
        new FloatValue(4.0f)       | new FloatValue(4.0f)            | BooleanValue.FALSE
        new FloatValue(4.0f)       | new FloatValue(5.0f)            | BooleanValue.TRUE
        new FloatValue(4.0f)       | new DoubleValue(4.0d)           | BooleanValue.FALSE
        new FloatValue(4.0f)       | new DoubleValue(5.0d)           | BooleanValue.TRUE
        // Double
        new DoubleValue(INT_CHAR)  | new CharacterValue('a' as char) | BooleanValue.FALSE
        new DoubleValue(INT_CHAR)  | new CharacterValue('b' as char) | BooleanValue.TRUE
        new DoubleValue(4.0d)  | new IntValue(4)                 | BooleanValue.FALSE
        new DoubleValue(4.0d)  | new IntValue(5)                 | BooleanValue.TRUE
        new DoubleValue(4.0d)      | new LongValue(4L)               | BooleanValue.FALSE
        new DoubleValue(4.0d)      | new LongValue(5L)               | BooleanValue.TRUE
        new DoubleValue(4.0d)      | new FloatValue(4.0f)            | BooleanValue.FALSE
        new DoubleValue(4.0d)      | new FloatValue(5.0f)            | BooleanValue.TRUE
        new DoubleValue(4.0d)      | new DoubleValue(4.0d)           | BooleanValue.FALSE
        new DoubleValue(4.0d)      | new DoubleValue(5.0d)           | BooleanValue.TRUE
    }

    def 'test #first < #second = #third'() {
        when:
        def eval = first.lessThan(second)

        then:
        eval == third

        where:
        first                  | second                           | third
        // Integer
        new IntValue(100)       | new CharacterValue('a' as char) | BooleanValue.FALSE
        new IntValue(100)       | new CharacterValue('z' as char) | BooleanValue.TRUE
        new IntValue(100)       | new CharacterValue('d' as char) | BooleanValue.FALSE
        new IntValue(100)       | new IntValue(2)                 | BooleanValue.FALSE
        new IntValue(100)       | new IntValue(200)               | BooleanValue.TRUE
        new IntValue(100)       | new IntValue(100)               | BooleanValue.FALSE
        new IntValue(100)       | new LongValue(2L)               | BooleanValue.FALSE
        new IntValue(100)       | new LongValue(200L)             | BooleanValue.TRUE
        new IntValue(100)       | new LongValue(100L)             | BooleanValue.FALSE
        new IntValue(100)       | new FloatValue(2.0f)            | BooleanValue.FALSE
        new IntValue(100)       | new FloatValue(200.0f)          | BooleanValue.TRUE
        new IntValue(100)       | new FloatValue(100.0f)          | BooleanValue.FALSE
        new IntValue(100)       | new DoubleValue(2.0d)           | BooleanValue.FALSE
        new IntValue(100)       | new DoubleValue(200.0d)         | BooleanValue.TRUE
        new IntValue(100)       | new DoubleValue(100.0d)         | BooleanValue.FALSE
        // Long
        new LongValue(100L)    | new CharacterValue('a' as char)  | BooleanValue.FALSE
        new LongValue(100L)    | new CharacterValue('z' as char)  | BooleanValue.TRUE
        new LongValue(100L)    | new CharacterValue('d' as char)  | BooleanValue.FALSE
        new LongValue(100L)     | new IntValue(2)                 | BooleanValue.FALSE
        new LongValue(100L)     | new IntValue(200)               | BooleanValue.TRUE
        new LongValue(100L)     | new IntValue(100)               | BooleanValue.FALSE
        new LongValue(100L)    | new LongValue(2L)                | BooleanValue.FALSE
        new LongValue(100L)    | new LongValue(200L)              | BooleanValue.TRUE
        new LongValue(100L)    | new LongValue(100L)              | BooleanValue.FALSE
        new LongValue(100L)    | new FloatValue(2.0f)             | BooleanValue.FALSE
        new LongValue(100L)    | new FloatValue(200.0f)           | BooleanValue.TRUE
        new LongValue(100L)    | new FloatValue(100.0f)           | BooleanValue.FALSE
        new LongValue(100L)    | new DoubleValue(2.0d)            | BooleanValue.FALSE
        new LongValue(100L)    | new DoubleValue(200.0d)          | BooleanValue.TRUE
        new LongValue(100L)    | new DoubleValue(100.0d)          | BooleanValue.FALSE
        // Float
        new FloatValue(100.0f) | new CharacterValue('a' as char)  | BooleanValue.FALSE
        new FloatValue(100.0f) | new CharacterValue('z' as char)  | BooleanValue.TRUE
        new FloatValue(100.0f) | new CharacterValue('d' as char)  | BooleanValue.FALSE
        new FloatValue(100.0f)  | new IntValue(2)                 | BooleanValue.FALSE
        new FloatValue(100.0f)  | new IntValue(200)               | BooleanValue.TRUE
        new FloatValue(100.0f)  | new IntValue(100)               | BooleanValue.FALSE
        new FloatValue(100.0f) | new LongValue(2L)                | BooleanValue.FALSE
        new FloatValue(100.0f) | new LongValue(200L)              | BooleanValue.TRUE
        new FloatValue(100.0f) | new LongValue(100L)              | BooleanValue.FALSE
        new FloatValue(100.0f) | new FloatValue(2.0f)             | BooleanValue.FALSE
        new FloatValue(100.0f) | new FloatValue(200.0f)           | BooleanValue.TRUE
        new FloatValue(100.0f) | new FloatValue(100.0f)           | BooleanValue.FALSE
        new FloatValue(100.0f) | new DoubleValue(2.0d)            | BooleanValue.FALSE
        new FloatValue(100.0f) | new DoubleValue(200.0d)          | BooleanValue.TRUE
        new FloatValue(100.0f) | new DoubleValue(100.0d)          | BooleanValue.FALSE
        // Double
        new DoubleValue(100.0d) | new CharacterValue('a' as char) | BooleanValue.FALSE
        new DoubleValue(100.0d) | new CharacterValue('z' as char) | BooleanValue.TRUE
        new DoubleValue(100.0d) | new CharacterValue('d' as char) | BooleanValue.FALSE
        new DoubleValue(100.0d) | new IntValue(2)                 | BooleanValue.FALSE
        new DoubleValue(100.0d) | new IntValue(200)               | BooleanValue.TRUE
        new DoubleValue(100.0d) | new IntValue(100)               | BooleanValue.FALSE
        new DoubleValue(100.0d) | new LongValue(2L)               | BooleanValue.FALSE
        new DoubleValue(100.0d) | new LongValue(200L)             | BooleanValue.TRUE
        new DoubleValue(100.0d) | new LongValue(100L)             | BooleanValue.FALSE
        new DoubleValue(100.0d) | new FloatValue(2.0f)            | BooleanValue.FALSE
        new DoubleValue(100.0d) | new FloatValue(200.0f)          | BooleanValue.TRUE
        new DoubleValue(100.0d) | new FloatValue(100.0f)          | BooleanValue.FALSE
        new DoubleValue(100.0d) | new DoubleValue(2.0d)           | BooleanValue.FALSE
        new DoubleValue(100.0d) | new DoubleValue(200.0d)         | BooleanValue.TRUE
        new DoubleValue(100.0d) | new DoubleValue(100.0d)         | BooleanValue.FALSE
    }

    def 'test #first <= #second = #third'() {
        when:
        def eval = first.lessThanEqual(second)

        then:
        eval == third

        where:
        first                  | second                           | third
        // Integer
        new IntValue(100)       | new CharacterValue('a' as char) | BooleanValue.FALSE
        new IntValue(100)       | new CharacterValue('z' as char) | BooleanValue.TRUE
        new IntValue(100)       | new CharacterValue('d' as char) | BooleanValue.TRUE
        new IntValue(100)       | new IntValue(2)                 | BooleanValue.FALSE
        new IntValue(100)       | new IntValue(200)               | BooleanValue.TRUE
        new IntValue(100)       | new IntValue(100)               | BooleanValue.TRUE
        new IntValue(100)       | new LongValue(2L)               | BooleanValue.FALSE
        new IntValue(100)       | new LongValue(200L)             | BooleanValue.TRUE
        new IntValue(100)       | new LongValue(100L)             | BooleanValue.TRUE
        new IntValue(100)       | new FloatValue(2.0f)            | BooleanValue.FALSE
        new IntValue(100)       | new FloatValue(200.0f)          | BooleanValue.TRUE
        new IntValue(100)       | new FloatValue(100.0f)          | BooleanValue.TRUE
        new IntValue(100)       | new DoubleValue(2.0d)           | BooleanValue.FALSE
        new IntValue(100)       | new DoubleValue(200.0d)         | BooleanValue.TRUE
        new IntValue(100)       | new DoubleValue(100.0d)         | BooleanValue.TRUE
        // Long
        new LongValue(100L)    | new CharacterValue('a' as char)  | BooleanValue.FALSE
        new LongValue(100L)    | new CharacterValue('z' as char)  | BooleanValue.TRUE
        new LongValue(100L)    | new CharacterValue('d' as char)  | BooleanValue.TRUE
        new LongValue(100L)     | new IntValue(2)                 | BooleanValue.FALSE
        new LongValue(100L)     | new IntValue(200)               | BooleanValue.TRUE
        new LongValue(100L)     | new IntValue(100)               | BooleanValue.TRUE
        new LongValue(100L)    | new LongValue(2L)                | BooleanValue.FALSE
        new LongValue(100L)    | new LongValue(200L)              | BooleanValue.TRUE
        new LongValue(100L)    | new LongValue(100L)              | BooleanValue.TRUE
        new LongValue(100L)    | new FloatValue(2.0f)             | BooleanValue.FALSE
        new LongValue(100L)    | new FloatValue(200.0f)           | BooleanValue.TRUE
        new LongValue(100L)    | new FloatValue(100.0f)           | BooleanValue.TRUE
        new LongValue(100L)    | new DoubleValue(2.0d)            | BooleanValue.FALSE
        new LongValue(100L)    | new DoubleValue(200.0d)          | BooleanValue.TRUE
        new LongValue(100L)    | new DoubleValue(100.0d)          | BooleanValue.TRUE
        // Float
        new FloatValue(100.0f) | new CharacterValue('a' as char)  | BooleanValue.FALSE
        new FloatValue(100.0f) | new CharacterValue('z' as char)  | BooleanValue.TRUE
        new FloatValue(100.0f) | new CharacterValue('d' as char)  | BooleanValue.TRUE
        new FloatValue(100.0f)  | new IntValue(2)                 | BooleanValue.FALSE
        new FloatValue(100.0f)  | new IntValue(200)               | BooleanValue.TRUE
        new FloatValue(100.0f)  | new IntValue(100)               | BooleanValue.TRUE
        new FloatValue(100.0f) | new LongValue(2L)                | BooleanValue.FALSE
        new FloatValue(100.0f) | new LongValue(200L)              | BooleanValue.TRUE
        new FloatValue(100.0f) | new LongValue(100L)              | BooleanValue.TRUE
        new FloatValue(100.0f) | new FloatValue(2.0f)             | BooleanValue.FALSE
        new FloatValue(100.0f) | new FloatValue(200.0f)           | BooleanValue.TRUE
        new FloatValue(100.0f) | new FloatValue(100.0f)           | BooleanValue.TRUE
        new FloatValue(100.0f) | new DoubleValue(2.0d)            | BooleanValue.FALSE
        new FloatValue(100.0f) | new DoubleValue(200.0d)          | BooleanValue.TRUE
        new FloatValue(100.0f) | new DoubleValue(100.0d)          | BooleanValue.TRUE
        // Double
        new DoubleValue(100.0d) | new CharacterValue('a' as char) | BooleanValue.FALSE
        new DoubleValue(100.0d) | new CharacterValue('z' as char) | BooleanValue.TRUE
        new DoubleValue(100.0d) | new CharacterValue('d' as char) | BooleanValue.TRUE
        new DoubleValue(100.0d) | new IntValue(2)                 | BooleanValue.FALSE
        new DoubleValue(100.0d) | new IntValue(200)               | BooleanValue.TRUE
        new DoubleValue(100.0d) | new IntValue(100)               | BooleanValue.TRUE
        new DoubleValue(100.0d) | new LongValue(2L)               | BooleanValue.FALSE
        new DoubleValue(100.0d) | new LongValue(200L)             | BooleanValue.TRUE
        new DoubleValue(100.0d) | new LongValue(100L)             | BooleanValue.TRUE
        new DoubleValue(100.0d) | new FloatValue(2.0f)            | BooleanValue.FALSE
        new DoubleValue(100.0d) | new FloatValue(200.0f)          | BooleanValue.TRUE
        new DoubleValue(100.0d) | new FloatValue(100.0f)          | BooleanValue.TRUE
        new DoubleValue(100.0d) | new DoubleValue(2.0d)           | BooleanValue.FALSE
        new DoubleValue(100.0d) | new DoubleValue(200.0d)         | BooleanValue.TRUE
        new DoubleValue(100.0d) | new DoubleValue(100.0d)         | BooleanValue.TRUE
    }

    def 'test #first > #second = #third'() {
        when:
        def eval = first.greaterThan(second)

        then:
        eval == third

        where:
        first                  | second                           | third
        // Integer
        new IntValue(100)       | new CharacterValue('a' as char) | BooleanValue.TRUE
        new IntValue(100)       | new CharacterValue('z' as char) | BooleanValue.FALSE
        new IntValue(100)       | new CharacterValue('d' as char) | BooleanValue.FALSE
        new IntValue(100)       | new IntValue(2)                 | BooleanValue.TRUE
        new IntValue(100)       | new IntValue(200)               | BooleanValue.FALSE
        new IntValue(100)       | new IntValue(100)               | BooleanValue.FALSE
        new IntValue(100)       | new LongValue(2L)               | BooleanValue.TRUE
        new IntValue(100)       | new LongValue(200L)             | BooleanValue.FALSE
        new IntValue(100)       | new LongValue(100L)             | BooleanValue.FALSE
        new IntValue(100)       | new FloatValue(2.0f)            | BooleanValue.TRUE
        new IntValue(100)       | new FloatValue(200.0f)          | BooleanValue.FALSE
        new IntValue(100)       | new FloatValue(100.0f)          | BooleanValue.FALSE
        new IntValue(100)       | new DoubleValue(2.0d)           | BooleanValue.TRUE
        new IntValue(100)       | new DoubleValue(200.0d)         | BooleanValue.FALSE
        new IntValue(100)       | new DoubleValue(100.0d)         | BooleanValue.FALSE
        // Long
        new LongValue(100L)    | new CharacterValue('a' as char)  | BooleanValue.TRUE
        new LongValue(100L)    | new CharacterValue('z' as char)  | BooleanValue.FALSE
        new LongValue(100L)    | new CharacterValue('d' as char)  | BooleanValue.FALSE
        new LongValue(100L)     | new IntValue(2)                 | BooleanValue.TRUE
        new LongValue(100L)     | new IntValue(200)               | BooleanValue.FALSE
        new LongValue(100L)     | new IntValue(100)               | BooleanValue.FALSE
        new LongValue(100L)    | new LongValue(2L)                | BooleanValue.TRUE
        new LongValue(100L)    | new LongValue(200L)              | BooleanValue.FALSE
        new LongValue(100L)    | new LongValue(100L)              | BooleanValue.FALSE
        new LongValue(100L)    | new FloatValue(2.0f)             | BooleanValue.TRUE
        new LongValue(100L)    | new FloatValue(200.0f)           | BooleanValue.FALSE
        new LongValue(100L)    | new FloatValue(100.0f)           | BooleanValue.FALSE
        new LongValue(100L)    | new DoubleValue(2.0d)            | BooleanValue.TRUE
        new LongValue(100L)    | new DoubleValue(200.0d)          | BooleanValue.FALSE
        new LongValue(100L)    | new DoubleValue(100.0d)          | BooleanValue.FALSE
        // Float
        new FloatValue(100.0f) | new CharacterValue('a' as char)  | BooleanValue.TRUE
        new FloatValue(100.0f) | new CharacterValue('z' as char)  | BooleanValue.FALSE
        new FloatValue(100.0f) | new CharacterValue('d' as char)  | BooleanValue.FALSE
        new FloatValue(100.0f)  | new IntValue(2)                 | BooleanValue.TRUE
        new FloatValue(100.0f)  | new IntValue(200)               | BooleanValue.FALSE
        new FloatValue(100.0f)  | new IntValue(100)               | BooleanValue.FALSE
        new FloatValue(100.0f) | new LongValue(2L)                | BooleanValue.TRUE
        new FloatValue(100.0f) | new LongValue(200L)              | BooleanValue.FALSE
        new FloatValue(100.0f) | new LongValue(100L)              | BooleanValue.FALSE
        new FloatValue(100.0f) | new FloatValue(2.0f)             | BooleanValue.TRUE
        new FloatValue(100.0f) | new FloatValue(200.0f)           | BooleanValue.FALSE
        new FloatValue(100.0f) | new FloatValue(100.0f)           | BooleanValue.FALSE
        new FloatValue(100.0f) | new DoubleValue(2.0d)            | BooleanValue.TRUE
        new FloatValue(100.0f) | new DoubleValue(200.0d)          | BooleanValue.FALSE
        new FloatValue(100.0f) | new DoubleValue(100.0d)          | BooleanValue.FALSE
        // Double
        new DoubleValue(100.0d) | new CharacterValue('a' as char) | BooleanValue.TRUE
        new DoubleValue(100.0d) | new CharacterValue('z' as char) | BooleanValue.FALSE
        new DoubleValue(100.0d) | new CharacterValue('d' as char) | BooleanValue.FALSE
        new DoubleValue(100.0d) | new IntValue(2)                 | BooleanValue.TRUE
        new DoubleValue(100.0d) | new IntValue(200)               | BooleanValue.FALSE
        new DoubleValue(100.0d) | new IntValue(100)               | BooleanValue.FALSE
        new DoubleValue(100.0d) | new LongValue(2L)               | BooleanValue.TRUE
        new DoubleValue(100.0d) | new LongValue(200L)             | BooleanValue.FALSE
        new DoubleValue(100.0d) | new LongValue(100L)             | BooleanValue.FALSE
        new DoubleValue(100.0d) | new FloatValue(2.0f)            | BooleanValue.TRUE
        new DoubleValue(100.0d) | new FloatValue(200.0f)          | BooleanValue.FALSE
        new DoubleValue(100.0d) | new FloatValue(100.0f)          | BooleanValue.FALSE
        new DoubleValue(100.0d) | new DoubleValue(2.0d)           | BooleanValue.TRUE
        new DoubleValue(100.0d) | new DoubleValue(200.0d)         | BooleanValue.FALSE
        new DoubleValue(100.0d) | new DoubleValue(100.0d)         | BooleanValue.FALSE
    }

    def 'test #first >= #second = #third'() {
        when:
        def eval = first.greaterThanEqual(second)

        then:
        eval == third

        where:
        first                  | second                           | third
        // Integer
        new IntValue(100)       | new CharacterValue('a' as char) | BooleanValue.TRUE
        new IntValue(100)       | new CharacterValue('z' as char) | BooleanValue.FALSE
        new IntValue(100)       | new CharacterValue('d' as char) | BooleanValue.TRUE
        new IntValue(100)       | new IntValue(2)                 | BooleanValue.TRUE
        new IntValue(100)       | new IntValue(200)               | BooleanValue.FALSE
        new IntValue(100)       | new IntValue(100)               | BooleanValue.TRUE
        new IntValue(100)       | new LongValue(2L)               | BooleanValue.TRUE
        new IntValue(100)       | new LongValue(200L)             | BooleanValue.FALSE
        new IntValue(100)       | new LongValue(100L)             | BooleanValue.TRUE
        new IntValue(100)       | new FloatValue(2.0f)            | BooleanValue.TRUE
        new IntValue(100)       | new FloatValue(200.0f)          | BooleanValue.FALSE
        new IntValue(100)       | new FloatValue(100.0f)          | BooleanValue.TRUE
        new IntValue(100)       | new DoubleValue(2.0d)           | BooleanValue.TRUE
        new IntValue(100)       | new DoubleValue(200.0d)         | BooleanValue.FALSE
        new IntValue(100)       | new DoubleValue(100.0d)         | BooleanValue.TRUE
        // Long
        new LongValue(100L)    | new CharacterValue('a' as char)  | BooleanValue.TRUE
        new LongValue(100L)    | new CharacterValue('z' as char)  | BooleanValue.FALSE
        new LongValue(100L)    | new CharacterValue('d' as char)  | BooleanValue.TRUE
        new LongValue(100L)     | new IntValue(2)                 | BooleanValue.TRUE
        new LongValue(100L)     | new IntValue(200)               | BooleanValue.FALSE
        new LongValue(100L)     | new IntValue(100)               | BooleanValue.TRUE
        new LongValue(100L)    | new LongValue(2L)                | BooleanValue.TRUE
        new LongValue(100L)    | new LongValue(200L)              | BooleanValue.FALSE
        new LongValue(100L)    | new LongValue(100L)              | BooleanValue.TRUE
        new LongValue(100L)    | new FloatValue(2.0f)             | BooleanValue.TRUE
        new LongValue(100L)    | new FloatValue(200.0f)           | BooleanValue.FALSE
        new LongValue(100L)    | new FloatValue(100.0f)           | BooleanValue.TRUE
        new LongValue(100L)    | new DoubleValue(2.0d)            | BooleanValue.TRUE
        new LongValue(100L)    | new DoubleValue(200.0d)          | BooleanValue.FALSE
        new LongValue(100L)    | new DoubleValue(100.0d)          | BooleanValue.TRUE
        // Float
        new FloatValue(100.0f) | new CharacterValue('a' as char)  | BooleanValue.TRUE
        new FloatValue(100.0f) | new CharacterValue('z' as char)  | BooleanValue.FALSE
        new FloatValue(100.0f) | new CharacterValue('d' as char)  | BooleanValue.TRUE
        new FloatValue(100.0f)  | new IntValue(2)                 | BooleanValue.TRUE
        new FloatValue(100.0f)  | new IntValue(200)               | BooleanValue.FALSE
        new FloatValue(100.0f)  | new IntValue(100)               | BooleanValue.TRUE
        new FloatValue(100.0f) | new LongValue(2L)                | BooleanValue.TRUE
        new FloatValue(100.0f) | new LongValue(200L)              | BooleanValue.FALSE
        new FloatValue(100.0f) | new LongValue(100L)              | BooleanValue.TRUE
        new FloatValue(100.0f) | new FloatValue(2.0f)             | BooleanValue.TRUE
        new FloatValue(100.0f) | new FloatValue(200.0f)           | BooleanValue.FALSE
        new FloatValue(100.0f) | new FloatValue(100.0f)           | BooleanValue.TRUE
        new FloatValue(100.0f) | new DoubleValue(2.0d)            | BooleanValue.TRUE
        new FloatValue(100.0f) | new DoubleValue(200.0d)          | BooleanValue.FALSE
        new FloatValue(100.0f) | new DoubleValue(100.0d)          | BooleanValue.TRUE
        // Double
        new DoubleValue(100.0d) | new CharacterValue('a' as char) | BooleanValue.TRUE
        new DoubleValue(100.0d) | new CharacterValue('z' as char) | BooleanValue.FALSE
        new DoubleValue(100.0d) | new CharacterValue('d' as char) | BooleanValue.TRUE
        new DoubleValue(100.0d) | new IntValue(2)                 | BooleanValue.TRUE
        new DoubleValue(100.0d) | new IntValue(200)               | BooleanValue.FALSE
        new DoubleValue(100.0d) | new IntValue(100)               | BooleanValue.TRUE
        new DoubleValue(100.0d) | new LongValue(2L)               | BooleanValue.TRUE
        new DoubleValue(100.0d) | new LongValue(200L)             | BooleanValue.FALSE
        new DoubleValue(100.0d) | new LongValue(100L)             | BooleanValue.TRUE
        new DoubleValue(100.0d) | new FloatValue(2.0f)            | BooleanValue.TRUE
        new DoubleValue(100.0d) | new FloatValue(200.0f)          | BooleanValue.FALSE
        new DoubleValue(100.0d) | new FloatValue(100.0f)          | BooleanValue.TRUE
        new DoubleValue(100.0d) | new DoubleValue(2.0d)           | BooleanValue.TRUE
        new DoubleValue(100.0d) | new DoubleValue(200.0d)         | BooleanValue.FALSE
        new DoubleValue(100.0d) | new DoubleValue(100.0d)         | BooleanValue.TRUE
    }

    def 'test #first & #second = #third'() {
        when:
        def eval = first.bitAnd(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntValue(4)   | new CharacterValue('a' as char) | new IntValue((4 & INT_CHAR) as Integer)
        new IntValue(4)   | new IntValue(2)                 | new IntValue((4 & 2) as Integer)
        new IntValue(4)   | new LongValue(2L)               | new LongValue((4 & 2L) as Long)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue((4L & INT_CHAR) as Long)
        new LongValue(4L) | new IntValue(2)                 | new LongValue((4L & 2) as Long)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue((4L & 2L) as Long)
    }

    def 'test #first | #second = #third'() {
        when:
        def eval = first.bitOr(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntValue(4)   | new CharacterValue('a' as char) | new IntValue((4 | INT_CHAR) as Integer)
        new IntValue(4)   | new IntValue(2)                 | new IntValue((4 | 2) as Integer)
        new IntValue(4)   | new LongValue(2L)               | new LongValue((4 | 2L) as Long)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue((4L | INT_CHAR) as Long)
        new LongValue(4L) | new IntValue(2)                 | new LongValue((4L | 2) as Long)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue((4L | 2L) as Long)
    }

    def 'test #first ^ #second = #third'() {
        when:
        def eval = first.bitXor(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntValue(4)   | new CharacterValue('a' as char) | new IntValue((4 ^ INT_CHAR) as Integer)
        new IntValue(4)   | new IntValue(2)                 | new IntValue((4 ^ 2) as Integer)
        new IntValue(4)   | new LongValue(2L)               | new LongValue((4 ^ 2L) as Long)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue((4L ^ INT_CHAR) as Long)
        new LongValue(4L) | new IntValue(2)                 | new LongValue((4L ^ 2) as Long)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue((4L ^ 2L) as Long)
    }

    def 'test #first << #second = #third'() {
        when:
        def eval = first.lshift(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntValue(4)   | new CharacterValue('a' as char) | new IntValue((4 << INT_CHAR) as Integer)
        new IntValue(4)   | new IntValue(2)                 | new IntValue((4 << 2) as Integer)
        new IntValue(4)   | new LongValue(2L)               | new LongValue((4 << 2L) as Long)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue((4L << INT_CHAR) as Long)
        new LongValue(4L) | new IntValue(2)                 | new LongValue((4L << 2) as Long)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue((4L << 2L) as Long)
    }

    def 'test #first >> #second = #third'() {
        when:
        def eval = first.rshift(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntValue(4)   | new CharacterValue('a' as char) | new IntValue((4 >> INT_CHAR) as Integer)
        new IntValue(4)   | new IntValue(2)                 | new IntValue((4 >> 2) as Integer)
        new IntValue(4)   | new LongValue(2L)               | new LongValue((4 >> 2L) as Long)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue((4L >> INT_CHAR) as Long)
        new LongValue(4L) | new IntValue(2)                 | new LongValue((4L >> 2) as Long)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue((4L >> 2L) as Long)
    }

    def 'test #first >>> #second = #third'() {
        when:
        def eval = first.urshift(second)

        then:
        eval == third

        where:
        first                | second                           | third
        // Integer
        new IntValue(4)   | new CharacterValue('a' as char) | new IntValue((4 >>> INT_CHAR) as Integer)
        new IntValue(4)   | new IntValue(2)                 | new IntValue((4 >>> 2) as Integer)
        new IntValue(4)   | new LongValue(2L)               | new LongValue((4 >>> 2L) as Long)
        // Long
        new LongValue(4L)    | new CharacterValue('a' as char)  | new LongValue((4L >>> INT_CHAR) as Long)
        new LongValue(4L) | new IntValue(2)                 | new LongValue((4L >>> 2) as Long)
        new LongValue(4L)    | new LongValue(2L)                | new LongValue((4L >>> 2L) as Long)
    }

    def 'test #first + #second = #third'() {
        when:
        def eval = first.add(second)

        then:
        eval == third

        where:
        first                 | second                          | third
        // Integer
        new IntValue(4)       | new CharacterValue('a' as char) | new IntValue((4 + INT_CHAR) as Integer)
        new IntValue(4)       | new IntValue(2)                 | new IntValue((4 + 2) as Integer)
        new IntValue(4)       | new LongValue(2L)               | new LongValue((4 + 2L) as Long)
        new IntValue(4)       | new FloatValue(2.0f)            | new FloatValue((4 + 2.0f) as Float)
        new IntValue(4)       | new DoubleValue(2.0d)           | new DoubleValue((4 + 2.0d) as Double)
        // Long
        new LongValue(4L)     | new CharacterValue('a' as char) | new LongValue((4L + INT_CHAR) as Long)
        new LongValue(4L)     | new IntValue(2)                 | new LongValue((4L + 2) as Long)
        new LongValue(4L)     | new LongValue(2L)               | new LongValue((4L + 2L) as Long)
        new LongValue(4L)     | new FloatValue(2.0f)            | new FloatValue((4L + 2.0f) as Float)
        new LongValue(4L)     | new DoubleValue(2.0d)           | new DoubleValue((4L + 2.0d) as Double)
        // Float
        new FloatValue(4.0f)  | new CharacterValue('a' as char) | new FloatValue((4.0f + INT_CHAR) as Float)
        new FloatValue(4.0f)  | new IntValue(2)                 | new FloatValue((4.0f + 2) as Float)
        new FloatValue(4.0f)  | new LongValue(2L)               | new FloatValue((4.0f + 2L) as Float)
        new FloatValue(4.0f)  | new FloatValue(2.0f)            | new FloatValue((4.0f + 2.0f) as Float)
        new FloatValue(4.0f)  | new DoubleValue(2.0d)           | new DoubleValue((4.0f + 2.0d) as Double)
        // Double
        new DoubleValue(4.0d) | new CharacterValue('a' as char) | new DoubleValue((4.0d + INT_CHAR) as Double)
        new DoubleValue(4.0d) | new IntValue(2)                 | new DoubleValue((4.0d + 2) as Double)
        new DoubleValue(4.0d) | new LongValue(2L)               | new DoubleValue((4.0d + 2L) as Double)
        new DoubleValue(4.0d) | new FloatValue(2.0f)            | new DoubleValue((4.0d + 2.0f) as Double)
        new DoubleValue(4.0d) | new DoubleValue(2.0d)           | new DoubleValue((4.0d + 2.0d) as Double)
    }

    def 'test #first - #second = #third'() {
        when:
        def eval = first.subtract(second)

        then:
        eval == third

        where:
        first                 | second                          | third
        // Integer
        new IntValue(4)       | new CharacterValue('a' as char) | new IntValue((4 - INT_CHAR) as Integer)
        new IntValue(4)       | new IntValue(2)                 | new IntValue((4 - 2) as Integer)
        new IntValue(4)       | new LongValue(2L)               | new LongValue((4 - 2L) as Long)
        new IntValue(4)       | new FloatValue(2.0f)            | new FloatValue((4 - 2.0f) as Float)
        new IntValue(4)       | new DoubleValue(2.0d)           | new DoubleValue((4 - 2.0d) as Double)
        // Long
        new LongValue(4L)     | new CharacterValue('a' as char) | new LongValue((4L - INT_CHAR) as Long)
        new LongValue(4L)     | new IntValue(2)                 | new LongValue((4L - 2) as Long)
        new LongValue(4L)     | new LongValue(2L)               | new LongValue((4L - 2L) as Long)
        new LongValue(4L)     | new FloatValue(2.0f)            | new FloatValue((4L - 2.0f) as Float)
        new LongValue(4L)     | new DoubleValue(2.0d)           | new DoubleValue((4L - 2.0d) as Double)
        // Float
        new FloatValue(4.0f)  | new CharacterValue('a' as char) | new FloatValue((4.0f - INT_CHAR) as Float)
        new FloatValue(4.0f)  | new IntValue(2)                 | new FloatValue((4.0f - 2) as Float)
        new FloatValue(4.0f)  | new LongValue(2L)               | new FloatValue((4.0f - 2L) as Float)
        new FloatValue(4.0f)  | new FloatValue(2.0f)            | new FloatValue((4.0f - 2.0f) as Float)
        new FloatValue(4.0f)  | new DoubleValue(2.0d)           | new DoubleValue((4.0f - 2.0d) as Double)
        // Double
        new DoubleValue(4.0d) | new CharacterValue('a' as char) | new DoubleValue((4.0d - INT_CHAR) as Double)
        new DoubleValue(4.0d) | new IntValue(2)                 | new DoubleValue((4.0d - 2) as Double)
        new DoubleValue(4.0d) | new LongValue(2L)               | new DoubleValue((4.0d - 2L) as Double)
        new DoubleValue(4.0d) | new FloatValue(2.0f)            | new DoubleValue((4.0d - 2.0f) as Double)
        new DoubleValue(4.0d) | new DoubleValue(2.0d)           | new DoubleValue((4.0d - 2.0d) as Double)
    }

    def 'test #first * #second = #third'() {
        when:
        def eval = first.multiply(second)

        then:
        eval == third

        where:
        first                 | second                          | third
        // Integer
        new IntValue(4)       | new CharacterValue('a' as char) | new IntValue((4 * INT_CHAR) as Integer)
        new IntValue(4)       | new IntValue(2)                 | new IntValue((4 * 2) as Integer)
        new IntValue(4)       | new LongValue(2L)               | new LongValue((4 * 2L) as Long)
        new IntValue(4)       | new FloatValue(2.0f)            | new FloatValue((4 * 2.0f) as Float)
        new IntValue(4)       | new DoubleValue(2.0d)           | new DoubleValue((4 * 2.0d) as Double)
        // Long
        new LongValue(4L)     | new CharacterValue('a' as char) | new LongValue((4L * INT_CHAR) as Long)
        new LongValue(4L)     | new IntValue(2)                 | new LongValue((4L * 2) as Long)
        new LongValue(4L)     | new LongValue(2L)               | new LongValue((4L * 2L) as Long)
        new LongValue(4L)     | new FloatValue(2.0f)            | new FloatValue((4L * 2.0f) as Float)
        new LongValue(4L)     | new DoubleValue(2.0d)           | new DoubleValue((4L * 2.0d) as Double)
        // Float
        new FloatValue(4.0f)  | new CharacterValue('a' as char) | new FloatValue((4.0f * INT_CHAR) as Float)
        new FloatValue(4.0f)  | new IntValue(2)                 | new FloatValue((4.0f * 2) as Float)
        new FloatValue(4.0f)  | new LongValue(2L)               | new FloatValue((4.0f * 2L) as Float)
        new FloatValue(4.0f)  | new FloatValue(2.0f)            | new FloatValue((4.0f * 2.0f) as Float)
        new FloatValue(4.0f)  | new DoubleValue(2.0d)           | new DoubleValue((4.0f * 2.0d) as Double)
        // Double
        new DoubleValue(4.0d) | new CharacterValue('a' as char) | new DoubleValue((4.0d * INT_CHAR) as Double)
        new DoubleValue(4.0d) | new IntValue(2)                 | new DoubleValue((4.0d * 2) as Double)
        new DoubleValue(4.0d) | new LongValue(2L)               | new DoubleValue((4.0d * 2L) as Double)
        new DoubleValue(4.0d) | new FloatValue(2.0f)            | new DoubleValue((4.0d * 2.0f) as Double)
        new DoubleValue(4.0d) | new DoubleValue(2.0d)           | new DoubleValue((4.0d * 2.0d) as Double)
    }

    def 'test #first / #second = #third'() {
        when:
        def eval = first.divide(second)

        then:
        eval == third

        where:
        first                 | second                          | third
        // Integer
        new IntValue(4)       | new CharacterValue('a' as char) | new IntValue((4 / INT_CHAR) as Integer)
        new IntValue(4)       | new IntValue(2)                 | new IntValue((4 / 2) as Integer)
        new IntValue(4)       | new LongValue(2L)               | new LongValue((4 / 2L) as Long)
        new IntValue(4)       | new FloatValue(2.0f)            | new FloatValue((4 / 2.0f) as Float)
        new IntValue(4)       | new DoubleValue(2.0d)           | new DoubleValue((4 / 2.0d) as Double)
        // Long
        new LongValue(4L)     | new CharacterValue('a' as char) | new LongValue((4 / INT_CHAR) as Long)
        new LongValue(4L)     | new IntValue(2)                 | new LongValue((4 / 2) as Long)
        new LongValue(4L)     | new LongValue(2L)               | new LongValue((4 / 2L) as Long)
        new LongValue(4L)     | new FloatValue(2.0f)            | new FloatValue((4 / 2.0f) as Float)
        new LongValue(4L)     | new DoubleValue(2.0d)           | new DoubleValue((4 / 2.0d) as Double)
        // Float
        new FloatValue(4.0f)  | new CharacterValue('a' as char) | new FloatValue((4.0f / INT_CHAR) as Float)
        new FloatValue(4.0f)  | new IntValue(2)                 | new FloatValue((4.0f / 2) as Float)
        new FloatValue(4.0f)  | new LongValue(2L)               | new FloatValue((4.0f / 2L) as Float)
        new FloatValue(4.0f)  | new FloatValue(2.0f)            | new FloatValue((4.0f / 2.0f) as Float)
        new FloatValue(4.0f)  | new DoubleValue(2.0d)           | new DoubleValue((4.0f / 2.0d) as Double)
        // Double
        new DoubleValue(4.0d) | new CharacterValue('a' as char) | new DoubleValue((4.0d / INT_CHAR) as Double)
        new DoubleValue(4.0d) | new IntValue(2)                 | new DoubleValue((4.0d / 2) as Double)
        new DoubleValue(4.0d) | new LongValue(2L)               | new DoubleValue((4.0d / 2L) as Double)
        new DoubleValue(4.0d) | new FloatValue(2.0f)            | new DoubleValue((4.0d / 2.0f) as Double)
        new DoubleValue(4.0d) | new DoubleValue(2.0d)           | new DoubleValue((4.0d / 2.0d) as Double)
    }

    def 'test #first % #second = #third'() {
        when:
        def eval = first.modulo(second)

        then:
        eval == third

        where:
        first                 | second                          | third
        // Integer
        new IntValue(4)       | new CharacterValue('a' as char) | new IntValue((4 % INT_CHAR) as Integer)
        new IntValue(4)       | new IntValue(2)                 | new IntValue((4 % 2) as Integer)
        new IntValue(4)       | new LongValue(2L)               | new LongValue((4 % 2L) as Long)
        new IntValue(4)       | new FloatValue(2.0f)            | new FloatValue((4 % 2.0f) as Float)
        new IntValue(4)       | new DoubleValue(2.0d)           | new DoubleValue((4 % 2.0d) as Double)
        // Long
        new LongValue(4L)     | new CharacterValue('a' as char) | new LongValue((4L % INT_CHAR) as Long)
        new LongValue(4L)     | new IntValue(2)                 | new LongValue((4L % 2) as Long)
        new LongValue(4L)     | new LongValue(2L)               | new LongValue((4L % 2L) as Long)
        new LongValue(4L)     | new FloatValue(2.0f)            | new FloatValue((4L % 2.0f) as Float)
        new LongValue(4L)     | new DoubleValue(2.0d)           | new DoubleValue((4L % 2.0d) as Double)
        // Float
        new FloatValue(4.0f)  | new CharacterValue('a' as char) | new FloatValue((4.0f % INT_CHAR) as Float)
        new FloatValue(4.0f)  | new IntValue(2)                 | new FloatValue((4.0f % 2) as Float)
        new FloatValue(4.0f)  | new LongValue(2L)               | new FloatValue((4.0f % 2L) as Float)
        new FloatValue(4.0f)  | new FloatValue(2.0f)            | new FloatValue((4.0f % 2.0f) as Float)
        new FloatValue(4.0f)  | new DoubleValue(2.0d)           | new DoubleValue((4.0f % 2.0d) as Double)
        // Double
        new DoubleValue(4.0d) | new CharacterValue('a' as char) | new DoubleValue((4.0d % INT_CHAR) as Double)
        new DoubleValue(4.0d) | new IntValue(2)                 | new DoubleValue((4.0d % 2) as Double)
        new DoubleValue(4.0d) | new LongValue(2L)               | new DoubleValue((4.0d % 2L) as Double)
        new DoubleValue(4.0d) | new FloatValue(2.0f)            | new DoubleValue((4.0d % 2.0f) as Double)
        new DoubleValue(4.0d) | new DoubleValue(2.0d)           | new DoubleValue((4.0d % 2.0d) as Double)
    }

}