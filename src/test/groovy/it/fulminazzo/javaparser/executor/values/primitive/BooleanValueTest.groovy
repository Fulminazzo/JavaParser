package it.fulminazzo.javaparser.executor.values.primitive

import spock.lang.Specification

import static it.fulminazzo.javaparser.executor.values.primitive.BooleanValue.*

class BooleanValueTest extends Specification {

    def 'test #first == #second = #third'() {
        when:
        def eval = first.equal(second)

        then:
        eval == third

        where:
        first | second | third
        TRUE  | TRUE   | TRUE
        TRUE  | FALSE  | FALSE
        FALSE | TRUE   | FALSE
        FALSE | FALSE  | TRUE
    }

    def 'test #first != #second = #third'() {
        when:
        def eval = first.notEqual(second)

        then:
        eval == third

        where:
        first | second | third
        TRUE  | TRUE   | FALSE
        TRUE  | FALSE  | TRUE
        FALSE | TRUE   | TRUE
        FALSE | FALSE  | FALSE
    }

    def 'test #first && #second = #third'() {
        when:
        def eval = first.and(second)

        then:
        eval == third

        where:
        first | second | third
        TRUE  | TRUE   | of(true  && true)
        TRUE  | FALSE  | of(true  && false)
        FALSE | TRUE   | of(false && true)
        FALSE | FALSE  | of(false && false)
    }

    def 'test #first || #second = #third'() {
        when:
        def eval = first.or(second)

        then:
        eval == third

        where:
        first | second | third
        TRUE  | TRUE   | of(true  || true)
        TRUE  | FALSE  | of(true  || false)
        FALSE | TRUE   | of(false || true)
        FALSE | FALSE  | of(false || false)
    }

    def 'test #first & #second = #third'() {
        when:
        def eval = first.bitAnd(second)

        then:
        eval == third

        where:
        first | second | third
        TRUE  | TRUE   | of(true  & true)
        TRUE  | FALSE  | of(true  & false)
        FALSE | TRUE   | of(false & true)
        FALSE | FALSE  | of(false & false)
    }

    def 'test #first | #second = #third'() {
        when:
        def eval = first.bitOr(second)

        then:
        eval == third

        where:
        first | second | third
        TRUE  | TRUE   | of(true  | true)
        TRUE  | FALSE  | of(true  | false)
        FALSE | TRUE   | of(false | true)
        FALSE | FALSE  | of(false | false)
    }

    def 'test #first ^ #second = #third'() {
        when:
        def eval = first.bitXor(second)

        then:
        eval == third

        where:
        first | second | third
        TRUE  | TRUE   | of(true  ^ true)
        TRUE  | FALSE  | of(true  ^ false)
        FALSE | TRUE   | of(false ^ true)
        FALSE | FALSE  | of(false ^ false)
    }

    def 'test !#value = #expected'() {
        when:
        def eval = value.not()

        then:
        eval == expected

        where:
        value | expected
        TRUE  | FALSE
        FALSE | TRUE
    }

    def 'test #value toString = #expected'() {
        when:
        def eval = value.toString()

        then:
        eval == expected

        where:
        value | expected
        TRUE  | 'TRUE'
        FALSE | 'FALSE'
    }

}