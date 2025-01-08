package it.fulminazzo.mojito.executor.values.objects

import it.fulminazzo.mojito.executor.values.primitivevalue.BooleanValue
import it.fulminazzo.mojito.executor.values.primitivevalue.PrimitiveValue
import spock.lang.Specification

class WrapperObjectValueTest extends Specification {
    private static final int INT_CHAR = (int) 'a'

    def 'test #first < #second = #third'() {
        when:
        def eval = first.lessThan(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('d' as char) | ObjectValue.of('a' as char) | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of('z' as char) | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of('d' as char) | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(2)           | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(200)         | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(100)         | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(2L)          | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(200L)        | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(100L)        | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(2.0f)        | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(200.0f)      | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(100.0f)      | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(2.0d)        | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(200.0d)      | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(100.0d)      | BooleanValue.FALSE
        // Integer
        ObjectValue.of(100)         | ObjectValue.of('a' as char) | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of('z' as char) | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of('d' as char) | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(2)           | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(200)         | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(100)         | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(2L)          | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(200L)        | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(100L)        | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(2.0f)        | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(200.0f)      | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(100.0f)      | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(2.0d)        | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(200.0d)      | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(100.0d)      | BooleanValue.FALSE
        // Long
        ObjectValue.of(100L)        | ObjectValue.of('a' as char) | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of('z' as char) | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of('d' as char) | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(2)           | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(200)         | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(100)         | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(2L)          | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(200L)        | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(100L)        | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(2.0f)        | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(200.0f)      | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(100.0f)      | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(2.0d)        | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(200.0d)      | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(100.0d)      | BooleanValue.FALSE
        // Float
        ObjectValue.of(100.0f)      | ObjectValue.of('a' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of('z' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of('d' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(2)           | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(200)         | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(100)         | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(2L)          | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(200L)        | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(100L)        | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(2.0f)        | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(200.0f)      | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(100.0f)      | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(2.0d)        | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(200.0d)      | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(100.0d)      | BooleanValue.FALSE
        // Double
        ObjectValue.of(100.0d)      | ObjectValue.of('a' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of('z' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of('d' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(2)           | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(200)         | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(100)         | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(2L)          | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(200L)        | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(100L)        | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(2.0f)        | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(200.0f)      | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(100.0f)      | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(2.0d)        | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(200.0d)      | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(100.0d)      | BooleanValue.FALSE
    }

    def 'test #first <= #second = #third'() {
        when:
        def eval = first.lessThanEqual(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Integer
        ObjectValue.of('d' as char) | ObjectValue.of('a' as char) | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of('z' as char) | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of('d' as char) | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(2)           | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(200)         | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(100)         | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(2L)          | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(200L)        | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(100L)        | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(2.0f)        | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(200.0f)      | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(100.0f)      | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(2.0d)        | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(200.0d)      | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(100.0d)      | BooleanValue.TRUE
        // Integer
        ObjectValue.of(100)         | ObjectValue.of('a' as char) | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of('z' as char) | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of('d' as char) | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(2)           | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(200)         | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(100)         | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(2L)          | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(200L)        | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(100L)        | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(2.0f)        | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(200.0f)      | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(100.0f)      | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(2.0d)        | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(200.0d)      | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(100.0d)      | BooleanValue.TRUE
        // Long
        ObjectValue.of(100L)        | ObjectValue.of('a' as char) | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of('z' as char) | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of('d' as char) | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(2)           | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(200)         | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(100)         | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(2L)          | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(200L)        | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(100L)        | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(2.0f)        | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(200.0f)      | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(100.0f)      | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(2.0d)        | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(200.0d)      | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(100.0d)      | BooleanValue.TRUE
        // Float
        ObjectValue.of(100.0f)      | ObjectValue.of('a' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of('z' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of('d' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(2)           | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(200)         | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(100)         | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(2L)          | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(200L)        | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(100L)        | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(2.0f)        | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(200.0f)      | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(100.0f)      | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(2.0d)        | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(200.0d)      | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(100.0d)      | BooleanValue.TRUE
        // Double
        ObjectValue.of(100.0d)      | ObjectValue.of('a' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of('z' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of('d' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(2)           | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(200)         | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(100)         | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(2L)          | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(200L)        | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(100L)        | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(2.0f)        | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(200.0f)      | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(100.0f)      | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(2.0d)        | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(200.0d)      | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(100.0d)      | BooleanValue.TRUE
    }

    def 'test #first > #second = #third'() {
        when:
        def eval = first.greaterThan(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Integer
        ObjectValue.of('d' as char) | ObjectValue.of('a' as char) | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of('z' as char) | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of('d' as char) | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(2)           | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(200)         | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(100)         | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(2L)          | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(200L)        | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(100L)        | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(2.0f)        | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(200.0f)      | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(100.0f)      | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(2.0d)        | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(200.0d)      | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(100.0d)      | BooleanValue.FALSE
        // Integer
        ObjectValue.of(100)         | ObjectValue.of('a' as char) | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of('z' as char) | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of('d' as char) | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(2)           | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(200)         | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(100)         | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(2L)          | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(200L)        | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(100L)        | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(2.0f)        | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(200.0f)      | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(100.0f)      | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(2.0d)        | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(200.0d)      | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(100.0d)      | BooleanValue.FALSE
        // Long
        ObjectValue.of(100L)        | ObjectValue.of('a' as char) | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of('z' as char) | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of('d' as char) | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(2)           | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(200)         | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(100)         | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(2L)          | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(200L)        | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(100L)        | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(2.0f)        | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(200.0f)      | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(100.0f)      | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(2.0d)        | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(200.0d)      | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(100.0d)      | BooleanValue.FALSE
        // Float
        ObjectValue.of(100.0f)      | ObjectValue.of('a' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of('z' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of('d' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(2)           | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(200)         | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(100)         | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(2L)          | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(200L)        | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(100L)        | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(2.0f)        | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(200.0f)      | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(100.0f)      | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(2.0d)        | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(200.0d)      | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(100.0d)      | BooleanValue.FALSE
        // Double
        ObjectValue.of(100.0d)      | ObjectValue.of('a' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of('z' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of('d' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(2)           | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(200)         | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(100)         | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(2L)          | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(200L)        | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(100L)        | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(2.0f)        | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(200.0f)      | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(100.0f)      | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(2.0d)        | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(200.0d)      | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(100.0d)      | BooleanValue.FALSE
    }

    def 'test #first >= #second = #third'() {
        when:
        def eval = first.greaterThanEqual(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('d' as char) | ObjectValue.of('a' as char) | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of('z' as char) | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of('d' as char) | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(2)           | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(200)         | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(100)         | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(2L)          | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(200L)        | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(100L)        | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(2.0f)        | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(200.0f)      | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(100.0f)      | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(2.0d)        | BooleanValue.TRUE
        ObjectValue.of('d' as char) | ObjectValue.of(200.0d)      | BooleanValue.FALSE
        ObjectValue.of('d' as char) | ObjectValue.of(100.0d)      | BooleanValue.TRUE
        // Integer
        ObjectValue.of(100)         | ObjectValue.of('a' as char) | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of('z' as char) | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of('d' as char) | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(2)           | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(200)         | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(100)         | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(2L)          | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(200L)        | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(100L)        | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(2.0f)        | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(200.0f)      | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(100.0f)      | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(2.0d)        | BooleanValue.TRUE
        ObjectValue.of(100)         | ObjectValue.of(200.0d)      | BooleanValue.FALSE
        ObjectValue.of(100)         | ObjectValue.of(100.0d)      | BooleanValue.TRUE
        // Long
        ObjectValue.of(100L)        | ObjectValue.of('a' as char) | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of('z' as char) | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of('d' as char) | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(2)           | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(200)         | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(100)         | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(2L)          | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(200L)        | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(100L)        | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(2.0f)        | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(200.0f)      | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(100.0f)      | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(2.0d)        | BooleanValue.TRUE
        ObjectValue.of(100L)        | ObjectValue.of(200.0d)      | BooleanValue.FALSE
        ObjectValue.of(100L)        | ObjectValue.of(100.0d)      | BooleanValue.TRUE
        // Float
        ObjectValue.of(100.0f)      | ObjectValue.of('a' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of('z' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of('d' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(2)           | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(200)         | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(100)         | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(2L)          | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(200L)        | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(100L)        | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(2.0f)        | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(200.0f)      | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(100.0f)      | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(2.0d)        | BooleanValue.TRUE
        ObjectValue.of(100.0f)      | ObjectValue.of(200.0d)      | BooleanValue.FALSE
        ObjectValue.of(100.0f)      | ObjectValue.of(100.0d)      | BooleanValue.TRUE
        // Double
        ObjectValue.of(100.0d)      | ObjectValue.of('a' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of('z' as char) | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of('d' as char) | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(2)           | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(200)         | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(100)         | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(2L)          | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(200L)        | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(100L)        | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(2.0f)        | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(200.0f)      | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(100.0f)      | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(2.0d)        | BooleanValue.TRUE
        ObjectValue.of(100.0d)      | ObjectValue.of(200.0d)      | BooleanValue.FALSE
        ObjectValue.of(100.0d)      | ObjectValue.of(100.0d)      | BooleanValue.TRUE
    }

    def 'test #first & #second = #third'() {
        when:
        def eval = first.bitAnd(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('a' as char) | ObjectValue.of('a' as char) | PrimitiveValue.of((INT_CHAR & INT_CHAR) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2)           | PrimitiveValue.of((INT_CHAR & 2) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2L)          | PrimitiveValue.of((INT_CHAR & 2L) as Long)
        // Integer
        ObjectValue.of(4)           | ObjectValue.of('a' as char) | PrimitiveValue.of((4 & INT_CHAR) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2)           | PrimitiveValue.of((4 & 2) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2L)          | PrimitiveValue.of((4 & 2L) as Long)
        // Long
        ObjectValue.of(4L)          | ObjectValue.of('a' as char) | PrimitiveValue.of((4L & INT_CHAR) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2)           | PrimitiveValue.of((4L & 2) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2L)          | PrimitiveValue.of((4L & 2L) as Long)
    }

    def 'test #first | #second = #third'() {
        when:
        def eval = first.bitOr(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('a' as char) | ObjectValue.of('a' as char) | PrimitiveValue.of((INT_CHAR | INT_CHAR) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2)           | PrimitiveValue.of((INT_CHAR | 2) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2L)          | PrimitiveValue.of((INT_CHAR | 2L) as Long)
        // Integer
        ObjectValue.of(4)           | ObjectValue.of('a' as char) | PrimitiveValue.of((4 | INT_CHAR) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2)           | PrimitiveValue.of((4 | 2) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2L)          | PrimitiveValue.of((4 | 2L) as Long)
        // Long
        ObjectValue.of(4L)          | ObjectValue.of('a' as char) | PrimitiveValue.of((4L | INT_CHAR) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2)           | PrimitiveValue.of((4L | 2) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2L)          | PrimitiveValue.of((4L | 2L) as Long)
    }

    def 'test #first ^ #second = #third'() {
        when:
        def eval = first.bitXor(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('a' as char) | ObjectValue.of('a' as char) | PrimitiveValue.of((INT_CHAR ^ INT_CHAR) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2)           | PrimitiveValue.of((INT_CHAR ^ 2) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2L)          | PrimitiveValue.of((INT_CHAR ^ 2L) as Long)
        // Integer
        ObjectValue.of(4)           | ObjectValue.of('a' as char) | PrimitiveValue.of((4 ^ INT_CHAR) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2)           | PrimitiveValue.of((4 ^ 2) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2L)          | PrimitiveValue.of((4 ^ 2L) as Long)
        // Long
        ObjectValue.of(4L)          | ObjectValue.of('a' as char) | PrimitiveValue.of((4L ^ INT_CHAR) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2)           | PrimitiveValue.of((4L ^ 2) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2L)          | PrimitiveValue.of((4L ^ 2L) as Long)
    }

    def 'test #first << #second = #third'() {
        when:
        def eval = first.lshift(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('a' as char) | ObjectValue.of('a' as char) | PrimitiveValue.of((INT_CHAR << INT_CHAR) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2)           | PrimitiveValue.of((INT_CHAR << 2) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2L)          | PrimitiveValue.of((INT_CHAR << 2L) as Long)
        // Integer
        ObjectValue.of(4)           | ObjectValue.of('a' as char) | PrimitiveValue.of((4 << INT_CHAR) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2)           | PrimitiveValue.of((4 << 2) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2L)          | PrimitiveValue.of((4 << 2L) as Long)
        // Long
        ObjectValue.of(4L)          | ObjectValue.of('a' as char) | PrimitiveValue.of((4L << INT_CHAR) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2)           | PrimitiveValue.of((4L << 2) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2L)          | PrimitiveValue.of((4L << 2L) as Long)
    }

    def 'test #first >> #second = #third'() {
        when:
        def eval = first.rshift(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('a' as char) | ObjectValue.of('a' as char) | PrimitiveValue.of((INT_CHAR >> INT_CHAR) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2)           | PrimitiveValue.of((INT_CHAR >> 2) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2L)          | PrimitiveValue.of((INT_CHAR >> 2L) as Long)
        // Integer
        ObjectValue.of(4)           | ObjectValue.of('a' as char) | PrimitiveValue.of((4 >> INT_CHAR) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2)           | PrimitiveValue.of((4 >> 2) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2L)          | PrimitiveValue.of((4 >> 2L) as Long)
        // Long
        ObjectValue.of(4L)          | ObjectValue.of('a' as char) | PrimitiveValue.of((4L >> INT_CHAR) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2)           | PrimitiveValue.of((4L >> 2) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2L)          | PrimitiveValue.of((4L >> 2L) as Long)
    }

    def 'test #first >>> #second = #third'() {
        when:
        def eval = first.urshift(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('a' as char) | ObjectValue.of('a' as char) | PrimitiveValue.of((INT_CHAR >>> INT_CHAR) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2)           | PrimitiveValue.of((INT_CHAR >>> 2) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2L)          | PrimitiveValue.of((INT_CHAR >>> 2L) as Long)
        // Integer
        ObjectValue.of(4)           | ObjectValue.of('a' as char) | PrimitiveValue.of((4 >>> INT_CHAR) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2)           | PrimitiveValue.of((4 >>> 2) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2L)          | PrimitiveValue.of((4 >>> 2L) as Long)
        // Long
        ObjectValue.of(4L)          | ObjectValue.of('a' as char) | PrimitiveValue.of((4L >>> INT_CHAR) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2)           | PrimitiveValue.of((4L >>> 2) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2L)          | PrimitiveValue.of((4L >>> 2L) as Long)
    }

    def 'test #first + #second = #third'() {
        when:
        def eval = first.add(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('a' as char) | ObjectValue.of('a' as char) | PrimitiveValue.of((INT_CHAR + INT_CHAR) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2)           | PrimitiveValue.of((INT_CHAR + 2) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2L)          | PrimitiveValue.of((INT_CHAR + 2L) as Long)
        ObjectValue.of('a' as char) | ObjectValue.of(2.0f)        | PrimitiveValue.of((INT_CHAR + 2.0f) as Float)
        ObjectValue.of('a' as char) | ObjectValue.of(2.0d)        | PrimitiveValue.of((INT_CHAR + 2.0d) as Double)
        // Integer
        ObjectValue.of(4)           | ObjectValue.of('a' as char) | PrimitiveValue.of((4 + INT_CHAR) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2)           | PrimitiveValue.of((4 + 2) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2L)          | PrimitiveValue.of((4 + 2L) as Long)
        ObjectValue.of(4)           | ObjectValue.of(2.0f)        | PrimitiveValue.of((4 + 2.0f) as Float)
        ObjectValue.of(4)           | ObjectValue.of(2.0d)        | PrimitiveValue.of((4 + 2.0d) as Double)
        // Long
        ObjectValue.of(4L)          | ObjectValue.of('a' as char) | PrimitiveValue.of((4L + INT_CHAR) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2)           | PrimitiveValue.of((4L + 2) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2L)          | PrimitiveValue.of((4L + 2L) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2.0f)        | PrimitiveValue.of((4L + 2.0f) as Float)
        ObjectValue.of(4L)          | ObjectValue.of(2.0d)        | PrimitiveValue.of((4L + 2.0d) as Double)
        // Float
        ObjectValue.of(4.0f)        | ObjectValue.of('a' as char) | PrimitiveValue.of((4.0f + INT_CHAR) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2)           | PrimitiveValue.of((4.0f + 2) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2L)          | PrimitiveValue.of((4.0f + 2L) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2.0f)        | PrimitiveValue.of((4.0f + 2.0f) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2.0d)        | PrimitiveValue.of((4.0f + 2.0d) as Double)
        // Double
        ObjectValue.of(4.0d)        | ObjectValue.of('a' as char) | PrimitiveValue.of((4.0d + INT_CHAR) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2)           | PrimitiveValue.of((4.0d + 2) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2L)          | PrimitiveValue.of((4.0d + 2L) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2.0f)        | PrimitiveValue.of((4.0d + 2.0f) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2.0d)        | PrimitiveValue.of((4.0d + 2.0d) as Double)
    }

    def 'test #first - #second = #third'() {
        when:
        def eval = first.subtract(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('a' as char) | ObjectValue.of('a' as char) | PrimitiveValue.of((INT_CHAR - INT_CHAR) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2)           | PrimitiveValue.of((INT_CHAR - 2) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2L)          | PrimitiveValue.of((INT_CHAR - 2L) as Long)
        ObjectValue.of('a' as char) | ObjectValue.of(2.0f)        | PrimitiveValue.of((INT_CHAR - 2.0f) as Float)
        ObjectValue.of('a' as char) | ObjectValue.of(2.0d)        | PrimitiveValue.of((INT_CHAR - 2.0d) as Double)
        // Integer
        ObjectValue.of(4)           | ObjectValue.of('a' as char) | PrimitiveValue.of((4 - INT_CHAR) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2)           | PrimitiveValue.of((4 - 2) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2L)          | PrimitiveValue.of((4 - 2L) as Long)
        ObjectValue.of(4)           | ObjectValue.of(2.0f)        | PrimitiveValue.of((4 - 2.0f) as Float)
        ObjectValue.of(4)           | ObjectValue.of(2.0d)        | PrimitiveValue.of((4 - 2.0d) as Double)
        // Long
        ObjectValue.of(4L)          | ObjectValue.of('a' as char) | PrimitiveValue.of((4L - INT_CHAR) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2)           | PrimitiveValue.of((4L - 2) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2L)          | PrimitiveValue.of((4L - 2L) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2.0f)        | PrimitiveValue.of((4L - 2.0f) as Float)
        ObjectValue.of(4L)          | ObjectValue.of(2.0d)        | PrimitiveValue.of((4L - 2.0d) as Double)
        // Float
        ObjectValue.of(4.0f)        | ObjectValue.of('a' as char) | PrimitiveValue.of((4.0f - INT_CHAR) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2)           | PrimitiveValue.of((4.0f - 2) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2L)          | PrimitiveValue.of((4.0f - 2L) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2.0f)        | PrimitiveValue.of((4.0f - 2.0f) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2.0d)        | PrimitiveValue.of((4.0f - 2.0d) as Double)
        // Double
        ObjectValue.of(4.0d)        | ObjectValue.of('a' as char) | PrimitiveValue.of((4.0d - INT_CHAR) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2)           | PrimitiveValue.of((4.0d - 2) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2L)          | PrimitiveValue.of((4.0d - 2L) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2.0f)        | PrimitiveValue.of((4.0d - 2.0f) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2.0d)        | PrimitiveValue.of((4.0d - 2.0d) as Double)
    }

    def 'test #first * #second = #third'() {
        when:
        def eval = first * second

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('a' as char) | ObjectValue.of('a' as char) | PrimitiveValue.of((INT_CHAR * INT_CHAR) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2)           | PrimitiveValue.of((INT_CHAR * 2) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2L)          | PrimitiveValue.of((INT_CHAR * 2L) as Long)
        ObjectValue.of('a' as char) | ObjectValue.of(2.0f)        | PrimitiveValue.of((INT_CHAR * 2.0f) as Float)
        ObjectValue.of('a' as char) | ObjectValue.of(2.0d)        | PrimitiveValue.of((INT_CHAR * 2.0d) as Double)
        // Integer
        ObjectValue.of(4)           | ObjectValue.of('a' as char) | PrimitiveValue.of((4 * INT_CHAR) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2)           | PrimitiveValue.of((4 * 2) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2L)          | PrimitiveValue.of((4 * 2L) as Long)
        ObjectValue.of(4)           | ObjectValue.of(2.0f)        | PrimitiveValue.of((4 * 2.0f) as Float)
        ObjectValue.of(4)           | ObjectValue.of(2.0d)        | PrimitiveValue.of((4 * 2.0d) as Double)
        // Long
        ObjectValue.of(4L)          | ObjectValue.of('a' as char) | PrimitiveValue.of((4L * INT_CHAR) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2)           | PrimitiveValue.of((4L * 2) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2L)          | PrimitiveValue.of((4L * 2L) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2.0f)        | PrimitiveValue.of((4L * 2.0f) as Float)
        ObjectValue.of(4L)          | ObjectValue.of(2.0d)        | PrimitiveValue.of((4L * 2.0d) as Double)
        // Float
        ObjectValue.of(4.0f)        | ObjectValue.of('a' as char) | PrimitiveValue.of((4.0f * INT_CHAR) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2)           | PrimitiveValue.of((4.0f * 2) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2L)          | PrimitiveValue.of((4.0f * 2L) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2.0f)        | PrimitiveValue.of((4.0f * 2.0f) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2.0d)        | PrimitiveValue.of((4.0f * 2.0d) as Double)
        // Double
        ObjectValue.of(4.0d)        | ObjectValue.of('a' as char) | PrimitiveValue.of((4.0d * INT_CHAR) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2)           | PrimitiveValue.of((4.0d * 2) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2L)          | PrimitiveValue.of((4.0d * 2L) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2.0f)        | PrimitiveValue.of((4.0d * 2.0f) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2.0d)        | PrimitiveValue.of((4.0d * 2.0d) as Double)
    }

    def 'test #first / #second = #third'() {
        when:
        def eval = first.divide(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('a' as char) | ObjectValue.of('a' as char) | PrimitiveValue.of((INT_CHAR / INT_CHAR) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2)           | PrimitiveValue.of((INT_CHAR / 2) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2L)          | PrimitiveValue.of((INT_CHAR / 2L) as Long)
        ObjectValue.of('a' as char) | ObjectValue.of(2.0f)        | PrimitiveValue.of((INT_CHAR / 2.0f) as Float)
        ObjectValue.of('a' as char) | ObjectValue.of(2.0d)        | PrimitiveValue.of((INT_CHAR / 2.0d) as Double)
        // Integer
        ObjectValue.of(4)           | ObjectValue.of('a' as char) | PrimitiveValue.of((4 / INT_CHAR) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2)           | PrimitiveValue.of((4 / 2) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2L)          | PrimitiveValue.of((4 / 2L) as Long)
        ObjectValue.of(4)           | ObjectValue.of(2.0f)        | PrimitiveValue.of((4 / 2.0f) as Float)
        ObjectValue.of(4)           | ObjectValue.of(2.0d)        | PrimitiveValue.of((4 / 2.0d) as Double)
        // Long
        ObjectValue.of(4L)          | ObjectValue.of('a' as char) | PrimitiveValue.of((4 / INT_CHAR) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2)           | PrimitiveValue.of((4 / 2) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2L)          | PrimitiveValue.of((4 / 2L) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2.0f)        | PrimitiveValue.of((4 / 2.0f) as Float)
        ObjectValue.of(4L)          | ObjectValue.of(2.0d)        | PrimitiveValue.of((4 / 2.0d) as Double)
        // Float
        ObjectValue.of(4.0f)        | ObjectValue.of('a' as char) | PrimitiveValue.of((4.0f / INT_CHAR) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2)           | PrimitiveValue.of((4.0f / 2) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2L)          | PrimitiveValue.of((4.0f / 2L) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2.0f)        | PrimitiveValue.of((4.0f / 2.0f) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2.0d)        | PrimitiveValue.of((4.0f / 2.0d) as Double)
        // Double
        ObjectValue.of(4.0d)        | ObjectValue.of('a' as char) | PrimitiveValue.of((4.0d / INT_CHAR) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2)           | PrimitiveValue.of((4.0d / 2) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2L)          | PrimitiveValue.of((4.0d / 2L) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2.0f)        | PrimitiveValue.of((4.0d / 2.0f) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2.0d)        | PrimitiveValue.of((4.0d / 2.0d) as Double)
    }

    def 'test #first % #second = #third'() {
        when:
        def eval = first.modulo(second)

        then:
        eval == third

        where:
        first                       | second                      | third
        // Character
        ObjectValue.of('a' as char) | ObjectValue.of('a' as char) | PrimitiveValue.of((INT_CHAR % INT_CHAR) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2)           | PrimitiveValue.of((INT_CHAR % 2) as Integer)
        ObjectValue.of('a' as char) | ObjectValue.of(2L)          | PrimitiveValue.of((INT_CHAR % 2L) as Long)
        ObjectValue.of('a' as char) | ObjectValue.of(2.0f)        | PrimitiveValue.of((INT_CHAR % 2.0f) as Float)
        ObjectValue.of('a' as char) | ObjectValue.of(2.0d)        | PrimitiveValue.of((INT_CHAR % 2.0d) as Double)
        // Integer
        ObjectValue.of(4)           | ObjectValue.of('a' as char) | PrimitiveValue.of((4 % INT_CHAR) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2)           | PrimitiveValue.of((4 % 2) as Integer)
        ObjectValue.of(4)           | ObjectValue.of(2L)          | PrimitiveValue.of((4 % 2L) as Long)
        ObjectValue.of(4)           | ObjectValue.of(2.0f)        | PrimitiveValue.of((4 % 2.0f) as Float)
        ObjectValue.of(4)           | ObjectValue.of(2.0d)        | PrimitiveValue.of((4 % 2.0d) as Double)
        // Long
        ObjectValue.of(4L)          | ObjectValue.of('a' as char) | PrimitiveValue.of((4L % INT_CHAR) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2)           | PrimitiveValue.of((4L % 2) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2L)          | PrimitiveValue.of((4L % 2L) as Long)
        ObjectValue.of(4L)          | ObjectValue.of(2.0f)        | PrimitiveValue.of((4L % 2.0f) as Float)
        ObjectValue.of(4L)          | ObjectValue.of(2.0d)        | PrimitiveValue.of((4L % 2.0d) as Double)
        // Float
        ObjectValue.of(4.0f)        | ObjectValue.of('a' as char) | PrimitiveValue.of((4.0f % INT_CHAR) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2)           | PrimitiveValue.of((4.0f % 2) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2L)          | PrimitiveValue.of((4.0f % 2L) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2.0f)        | PrimitiveValue.of((4.0f % 2.0f) as Float)
        ObjectValue.of(4.0f)        | ObjectValue.of(2.0d)        | PrimitiveValue.of((4.0f % 2.0d) as Double)
        // Double
        ObjectValue.of(4.0d)        | ObjectValue.of('a' as char) | PrimitiveValue.of((4.0d % INT_CHAR) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2)           | PrimitiveValue.of((4.0d % 2) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2L)          | PrimitiveValue.of((4.0d % 2L) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2.0f)        | PrimitiveValue.of((4.0d % 2.0f) as Double)
        ObjectValue.of(4.0d)        | ObjectValue.of(2.0d)        | PrimitiveValue.of((4.0d % 2.0d) as Double)
    }

    def 'test -#first = #expected'() {
        when:
        def eval = first.minus()

        then:
        eval == expected

        where:
        first                       | expected
        // Integer
        ObjectValue.of(4)           | PrimitiveValue.of((-4) as Integer)
        // Character
        ObjectValue.of('a' as char) | PrimitiveValue.of((-97) as Integer)
        // Long
        ObjectValue.of(4L)          | PrimitiveValue.of((-4L) as Long)
        // Float
        ObjectValue.of(4.0f)        | PrimitiveValue.of((-4.0f) as Float)
        // Double
        ObjectValue.of(4.0d)        | PrimitiveValue.of((-4.0d) as Double)
    }

}