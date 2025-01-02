package it.fulminazzo.javaparser.executor.values.primitivevalue

import spock.lang.Specification

class CharValueTest extends Specification {
    private static int INT_CHAR = (int) 'a'

    def 'test #first == #second = #third'() {
        when:
        def eval = first.equal(second)

        then:
        eval == third

        where:
        first                           | second                            | third
        // Character
        new CharValue('d' as char) | new CharValue('d' as char) | BooleanValue.TRUE
        new CharValue('d' as char) | new CharValue('e' as char) | BooleanValue.FALSE
        new CharValue('d' as char) | new IntValue(100)          | BooleanValue.TRUE
        new CharValue('d' as char) | new IntValue(101)          | BooleanValue.FALSE
        new CharValue('d' as char) | new LongValue(100L)        | BooleanValue.TRUE
        new CharValue('d' as char) | new LongValue(101L)        | BooleanValue.FALSE
        new CharValue('d' as char) | new FloatValue(100.0f)     | BooleanValue.TRUE
        new CharValue('d' as char) | new FloatValue(101.0f)     | BooleanValue.FALSE
        new CharValue('d' as char) | new DoubleValue(100.0d)    | BooleanValue.TRUE
        new CharValue('d' as char) | new DoubleValue(101.0d)    | BooleanValue.FALSE
        new CharValue('d' as char) | new StringValue('Hello')   | BooleanValue.FALSE
    }

    def 'test #first != #second = #third'() {
        when:
        def eval = first.notEqual(second)

        then:
        eval == third

        where:
        first                           | second                            | third
        // Character
        new CharValue('d' as char) | new CharValue('d' as char) | BooleanValue.FALSE
        new CharValue('d' as char) | new CharValue('e' as char) | BooleanValue.TRUE
        new CharValue('d' as char) | new IntValue(100)          | BooleanValue.FALSE
        new CharValue('d' as char) | new IntValue(101)          | BooleanValue.TRUE
        new CharValue('d' as char) | new LongValue(100L)        | BooleanValue.FALSE
        new CharValue('d' as char) | new LongValue(101L)        | BooleanValue.TRUE
        new CharValue('d' as char) | new FloatValue(100.0f)     | BooleanValue.FALSE
        new CharValue('d' as char) | new FloatValue(101.0f)     | BooleanValue.TRUE
        new CharValue('d' as char) | new DoubleValue(100.0d)    | BooleanValue.FALSE
        new CharValue('d' as char) | new DoubleValue(101.0d)    | BooleanValue.TRUE
    }

    def 'test #first < #second = #third'() {
        when:
        def eval = first.lessThan(second)

        then:
        eval == third

        where:
        first                       | second                           | third
        // Integer
        new CharValue('d' as char) | new CharValue('a' as char) | BooleanValue.FALSE
        new CharValue('d' as char) | new CharValue('z' as char) | BooleanValue.TRUE
        new CharValue('d' as char) | new CharValue('d' as char) | BooleanValue.FALSE
        new CharValue('d' as char) | new IntValue(2)            | BooleanValue.FALSE
        new CharValue('d' as char) | new IntValue(200)          | BooleanValue.TRUE
        new CharValue('d' as char) | new IntValue(100)          | BooleanValue.FALSE
        new CharValue('d' as char) | new LongValue(2L)          | BooleanValue.FALSE
        new CharValue('d' as char) | new LongValue(200L)        | BooleanValue.TRUE
        new CharValue('d' as char) | new LongValue(100L)        | BooleanValue.FALSE
        new CharValue('d' as char) | new FloatValue(2.0f)       | BooleanValue.FALSE
        new CharValue('d' as char) | new FloatValue(200.0f)     | BooleanValue.TRUE
        new CharValue('d' as char) | new FloatValue(100.0f)     | BooleanValue.FALSE
        new CharValue('d' as char) | new DoubleValue(2.0d)      | BooleanValue.FALSE
        new CharValue('d' as char) | new DoubleValue(200.0d)    | BooleanValue.TRUE
        new CharValue('d' as char) | new DoubleValue(100.0d)    | BooleanValue.FALSE
    }

    def 'test #first <= #second = #third'() {
        when:
        def eval = first.lessThanEqual(second)

        then:
        eval == third

        where:
        first                       | second                           | third
        // Integer
        new CharValue('d' as char) | new CharValue('a' as char) | BooleanValue.FALSE
        new CharValue('d' as char) | new CharValue('z' as char) | BooleanValue.TRUE
        new CharValue('d' as char) | new CharValue('d' as char) | BooleanValue.TRUE
        new CharValue('d' as char) | new IntValue(2)            | BooleanValue.FALSE
        new CharValue('d' as char) | new IntValue(200)          | BooleanValue.TRUE
        new CharValue('d' as char) | new IntValue(100)          | BooleanValue.TRUE
        new CharValue('d' as char) | new LongValue(2L)          | BooleanValue.FALSE
        new CharValue('d' as char) | new LongValue(200L)        | BooleanValue.TRUE
        new CharValue('d' as char) | new LongValue(100L)        | BooleanValue.TRUE
        new CharValue('d' as char) | new FloatValue(2.0f)       | BooleanValue.FALSE
        new CharValue('d' as char) | new FloatValue(200.0f)     | BooleanValue.TRUE
        new CharValue('d' as char) | new FloatValue(100.0f)     | BooleanValue.TRUE
        new CharValue('d' as char) | new DoubleValue(2.0d)      | BooleanValue.FALSE
        new CharValue('d' as char) | new DoubleValue(200.0d)    | BooleanValue.TRUE
        new CharValue('d' as char) | new DoubleValue(100.0d)    | BooleanValue.TRUE
    }

    def 'test #first > #second = #third'() {
        when:
        def eval = first.greaterThan(second)

        then:
        eval == third

        where:
        first                       | second                           | third
        // Integer
        new CharValue('d' as char) | new CharValue('a' as char) | BooleanValue.TRUE
        new CharValue('d' as char) | new CharValue('z' as char) | BooleanValue.FALSE
        new CharValue('d' as char) | new CharValue('d' as char) | BooleanValue.FALSE
        new CharValue('d' as char) | new IntValue(2)            | BooleanValue.TRUE
        new CharValue('d' as char) | new IntValue(200)          | BooleanValue.FALSE
        new CharValue('d' as char) | new IntValue(100)          | BooleanValue.FALSE
        new CharValue('d' as char) | new LongValue(2L)          | BooleanValue.TRUE
        new CharValue('d' as char) | new LongValue(200L)        | BooleanValue.FALSE
        new CharValue('d' as char) | new LongValue(100L)        | BooleanValue.FALSE
        new CharValue('d' as char) | new FloatValue(2.0f)       | BooleanValue.TRUE
        new CharValue('d' as char) | new FloatValue(200.0f)     | BooleanValue.FALSE
        new CharValue('d' as char) | new FloatValue(100.0f)     | BooleanValue.FALSE
        new CharValue('d' as char) | new DoubleValue(2.0d)      | BooleanValue.TRUE
        new CharValue('d' as char) | new DoubleValue(200.0d)    | BooleanValue.FALSE
        new CharValue('d' as char) | new DoubleValue(100.0d)    | BooleanValue.FALSE
    }

    def 'test #first >= #second = #third'() {
        when:
        def eval = first.greaterThanEqual(second)

        then:
        eval == third

        where:
        first                       | second                           | third
        // Integer
        new CharValue('d' as char) | new CharValue('a' as char) | BooleanValue.TRUE
        new CharValue('d' as char) | new CharValue('z' as char) | BooleanValue.FALSE
        new CharValue('d' as char) | new CharValue('d' as char) | BooleanValue.TRUE
        new CharValue('d' as char) | new IntValue(2)            | BooleanValue.TRUE
        new CharValue('d' as char) | new IntValue(200)          | BooleanValue.FALSE
        new CharValue('d' as char) | new IntValue(100)          | BooleanValue.TRUE
        new CharValue('d' as char) | new LongValue(2L)          | BooleanValue.TRUE
        new CharValue('d' as char) | new LongValue(200L)        | BooleanValue.FALSE
        new CharValue('d' as char) | new LongValue(100L)        | BooleanValue.TRUE
        new CharValue('d' as char) | new FloatValue(2.0f)       | BooleanValue.TRUE
        new CharValue('d' as char) | new FloatValue(200.0f)     | BooleanValue.FALSE
        new CharValue('d' as char) | new FloatValue(100.0f)     | BooleanValue.TRUE
        new CharValue('d' as char) | new DoubleValue(2.0d)      | BooleanValue.TRUE
        new CharValue('d' as char) | new DoubleValue(200.0d)    | BooleanValue.FALSE
        new CharValue('d' as char) | new DoubleValue(100.0d)    | BooleanValue.TRUE
    }

    def 'test #first & #second = #third'() {
        when:
        def eval = first.bitAnd(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharValue('a' as char) | new CharValue('a' as char) | new IntValue((INT_CHAR & INT_CHAR) as Integer)
        new CharValue('a' as char) | new IntValue(2)            | new IntValue((INT_CHAR & 2) as Integer)
        new CharValue('a' as char) | new LongValue(2L)          | new LongValue((INT_CHAR & 2L) as Long)
    }

    def 'test #first | #second = #third'() {
        when:
        def eval = first.bitOr(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharValue('a' as char) | new CharValue('a' as char) | new IntValue((INT_CHAR | INT_CHAR) as Integer)
        new CharValue('a' as char) | new IntValue(2)            | new IntValue((INT_CHAR | 2) as Integer)
        new CharValue('a' as char) | new LongValue(2L)          | new LongValue((INT_CHAR | 2L) as Long)
    }

    def 'test #first ^ #second = #third'() {
        when:
        def eval = first.bitXor(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharValue('a' as char) | new CharValue('a' as char) | new IntValue((INT_CHAR ^ INT_CHAR) as Integer)
        new CharValue('a' as char) | new IntValue(2)            | new IntValue((INT_CHAR ^ 2) as Integer)
        new CharValue('a' as char) | new LongValue(2L)          | new LongValue((INT_CHAR ^ 2L) as Long)
    }

    def 'test #first << #second = #third'() {
        when:
        def eval = first.lshift(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharValue('a' as char) | new CharValue('a' as char) | new IntValue((INT_CHAR << INT_CHAR) as Integer)
        new CharValue('a' as char) | new IntValue(2)            | new IntValue((INT_CHAR << 2) as Integer)
        new CharValue('a' as char) | new LongValue(2L)          | new LongValue((INT_CHAR << 2L) as Long)
    }

    def 'test #first >> #second = #third'() {
        when:
        def eval = first.rshift(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharValue('a' as char) | new CharValue('a' as char) | new IntValue((INT_CHAR >> INT_CHAR) as Integer)
        new CharValue('a' as char) | new IntValue(2)            | new IntValue((INT_CHAR >> 2) as Integer)
        new CharValue('a' as char) | new LongValue(2L)          | new LongValue((INT_CHAR >> 2L) as Long)
    }

    def 'test #first >>> #second = #third'() {
        when:
        def eval = first.urshift(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharValue('a' as char) | new CharValue('a' as char) | new IntValue((INT_CHAR >>> INT_CHAR) as Integer)
        new CharValue('a' as char) | new IntValue(2)            | new IntValue((INT_CHAR >>> 2) as Integer)
        new CharValue('a' as char) | new LongValue(2L)          | new LongValue((INT_CHAR >>> 2L) as Long)
    }

    def 'test #first + #second = #third'() {
        when:
        def eval = first.add(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharValue('a' as char) | new CharValue('a' as char) | new IntValue((INT_CHAR + INT_CHAR) as Integer)
        new CharValue('a' as char) | new IntValue(2)            | new IntValue((INT_CHAR + 2) as Integer)
        new CharValue('a' as char) | new LongValue(2L)          | new LongValue((INT_CHAR + 2L) as Long)
        new CharValue('a' as char) | new FloatValue(2.0f)       | new FloatValue((INT_CHAR + 2.0f) as Float)
        new CharValue('a' as char) | new DoubleValue(2.0d)      | new DoubleValue((INT_CHAR + 2.0d) as Double)
    }

    def 'test #first - #second = #third'() {
        when:
        def eval = first.subtract(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharValue('a' as char) | new CharValue('a' as char) | new IntValue((INT_CHAR - INT_CHAR) as Integer)
        new CharValue('a' as char) | new IntValue(2)            | new IntValue((INT_CHAR - 2) as Integer)
        new CharValue('a' as char) | new LongValue(2L)          | new LongValue((INT_CHAR - 2L) as Long)
        new CharValue('a' as char) | new FloatValue(2.0f)       | new FloatValue((INT_CHAR - 2.0f) as Float)
        new CharValue('a' as char) | new DoubleValue(2.0d)      | new DoubleValue((INT_CHAR - 2.0d) as Double)
    }

    def 'test #first * #second = #third'() {
        when:
        def eval = first.multiply(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharValue('a' as char) | new CharValue('a' as char) | new IntValue((INT_CHAR * INT_CHAR) as Integer)
        new CharValue('a' as char) | new IntValue(2)            | new IntValue((INT_CHAR * 2) as Integer)
        new CharValue('a' as char) | new LongValue(2L)          | new LongValue((INT_CHAR * 2L) as Long)
        new CharValue('a' as char) | new FloatValue(2.0f)       | new FloatValue((INT_CHAR * 2.0f) as Float)
        new CharValue('a' as char) | new DoubleValue(2.0d)      | new DoubleValue((INT_CHAR * 2.0d) as Double)
    }

    def 'test #first / #second = #third'() {
        when:
        def eval = first.divide(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharValue('a' as char) | new CharValue('a' as char) | new IntValue((INT_CHAR / INT_CHAR) as Integer)
        new CharValue('a' as char) | new IntValue(2)            | new IntValue((INT_CHAR / 2) as Integer)
        new CharValue('a' as char) | new LongValue(2L)          | new LongValue((INT_CHAR / 2L) as Long)
        new CharValue('a' as char) | new FloatValue(2.0f)       | new FloatValue((INT_CHAR / 2.0f) as Float)
        new CharValue('a' as char) | new DoubleValue(2.0d)      | new DoubleValue((INT_CHAR / 2.0d) as Double)
    }

    def 'test #first % #second = #third'() {
        when:
        def eval = first.modulo(second)

        then:
        eval == third

        where:
        first                            | second                           | third
        // Character
        new CharValue('a' as char) | new CharValue('a' as char) | new IntValue((INT_CHAR % INT_CHAR) as Integer)
        new CharValue('a' as char) | new IntValue(2)            | new IntValue((INT_CHAR % 2) as Integer)
        new CharValue('a' as char) | new LongValue(2L)          | new LongValue((INT_CHAR % 2L) as Long)
        new CharValue('a' as char) | new FloatValue(2.0f)       | new FloatValue((INT_CHAR % 2.0f) as Float)
        new CharValue('a' as char) | new DoubleValue(2.0d)      | new DoubleValue((INT_CHAR % 2.0d) as Double)
    }

}