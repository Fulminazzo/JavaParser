package it.fulminazzo.javaparser.typechecker

import it.fulminazzo.javaparser.parser.node.values.BooleanValueLiteral
import it.fulminazzo.javaparser.parser.node.values.CharValueLiteral
import it.fulminazzo.javaparser.parser.node.values.LongValueLiteral
import it.fulminazzo.javaparser.parser.node.values.NumberValueLiteral
import it.fulminazzo.javaparser.typechecker.types.ValueType
import spock.lang.Specification

class TypeCheckerTest extends Specification {
    private static final BOOL_LIT = new BooleanValueLiteral('true')
    private static final CHAR_LIT = new CharValueLiteral('\'a\'')
    private static final NUMBER_LIT = new NumberValueLiteral('1')
    private static final LONG_LIT = new LongValueLiteral('1L')

    private TypeChecker typeChecker

    void setup() {
        this.typeChecker = new TypeChecker()
    }

    def 'test equal'() {
        given:
        def type = this.typeChecker.visitEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test not equal'() {
        given:
        def type = this.typeChecker.visitNotEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test less than'() {
        given:
        def type = this.typeChecker.visitLessThan(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test less than equal'() {
        given:
        def type = this.typeChecker.visitLessThanEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test greater than'() {
        given:
        def type = this.typeChecker.visitGreaterThan(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test greater than equal'() {
        given:
        def type = this.typeChecker.visitGreaterThanEqual(NUMBER_LIT, NUMBER_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test valid and'() {
        given:
        def type = this.typeChecker.visitAnd(BOOL_LIT, BOOL_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test invalid and'() {
        when:
        this.typeChecker.visitAnd(first, second)

        then:
        thrown(TypeCheckerException)

        where:
        first      | second
        BOOL_LIT   | NUMBER_LIT
        NUMBER_LIT | BOOL_LIT
        NUMBER_LIT | NUMBER_LIT
    }

    def 'test valid or'() {
        given:
        def type = this.typeChecker.visitOr(BOOL_LIT, BOOL_LIT)

        expect:
        type == ValueType.BOOLEAN
    }

    def 'test invalid or'() {
        when:
        this.typeChecker.visitOr(first, second)

        then:
        thrown(TypeCheckerException)

        where:
        first      | second
        BOOL_LIT   | NUMBER_LIT
        NUMBER_LIT | BOOL_LIT
        NUMBER_LIT | NUMBER_LIT
    }

    def 'test visit bit and of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitBitAnd(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        BOOL_LIT   | BOOL_LIT   | ValueType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit bit or of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitBitOr(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        BOOL_LIT   | BOOL_LIT   | ValueType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit bit xor of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitBitXor(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        BOOL_LIT   | BOOL_LIT   | ValueType.BOOLEAN
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit lshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitLShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit rshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitRShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

    def 'test visit urshift of #first and #second should return #expected'() {
        when:
        def type = this.typeChecker.visitURShift(first, second)

        then:
        type == expected

        where:
        first      | second     | expected
        CHAR_LIT   | CHAR_LIT   | ValueType.NUMBER
        NUMBER_LIT | NUMBER_LIT | ValueType.NUMBER
        LONG_LIT   | LONG_LIT   | ValueType.LONG
    }

}
