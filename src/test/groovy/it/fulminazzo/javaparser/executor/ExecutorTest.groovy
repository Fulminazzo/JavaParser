package it.fulminazzo.javaparser.executor


import it.fulminazzo.javaparser.executor.values.primitivevalue.BooleanValue
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue
import it.fulminazzo.javaparser.parser.node.values.*
import spock.lang.Specification

class ExecutorTest extends Specification {
    private static final BOOL_LIT_TRUE = new BooleanValueLiteral('true')
    private static final BOOL_LIT_FALSE = new BooleanValueLiteral('false')
    private static final CHAR_LIT = new CharValueLiteral('\'a\'')
    private static final NUMBER_LIT = new NumberValueLiteral('1')
    private static final LONG_LIT = new LongValueLiteral('2L')
    private static final FLOAT_LIT = new FloatValueLiteral('3.0f')
    private static final DOUBLE_LIT = new DoubleValueLiteral('4.0d')
    private static final STRING_LIT = new StringValueLiteral('\"Hello, world!\"')

    private Executor executor

    void setup() {
        this.executor = new Executor(getClass())
    }

    def 'test equal'() {
        given:
        def result = this.executor.visitEqual(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        NUMBER_LIT | NUMBER_LIT | BooleanValue.TRUE
        CHAR_LIT   | NUMBER_LIT | BooleanValue.FALSE
    }

    def 'test not equal'() {
        given:
        def result = this.executor.visitNotEqual(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        NUMBER_LIT | NUMBER_LIT | BooleanValue.TRUE
        CHAR_LIT   | NUMBER_LIT | BooleanValue.FALSE
    }

    def 'test less than'() {
        given:
        def result = this.executor.visitLessThan(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | BooleanValue.FALSE
        NUMBER_LIT | LONG_LIT   | BooleanValue.TRUE
        NUMBER_LIT | NUMBER_LIT | BooleanValue.FALSE
    }

    def 'test less than equal'() {
        given:
        def result = this.executor.visitLessThanEqual(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | BooleanValue.FALSE
        NUMBER_LIT | LONG_LIT   | BooleanValue.TRUE
        NUMBER_LIT | NUMBER_LIT | BooleanValue.TRUE
    }

    def 'test greater than'() {
        given:
        def result = this.executor.visitGreaterThan(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | BooleanValue.TRUE
        NUMBER_LIT | LONG_LIT   | BooleanValue.FALSE
        NUMBER_LIT | NUMBER_LIT | BooleanValue.FALSE
    }

    def 'test greater than equal'() {
        given:
        def result = this.executor.visitGreaterThanEqual(first, second)

        expect:
        result == expected

        where:
        first      | second     | expected
        LONG_LIT   | NUMBER_LIT | BooleanValue.TRUE
        NUMBER_LIT | LONG_LIT   | BooleanValue.FALSE
        NUMBER_LIT | NUMBER_LIT | BooleanValue.TRUE
    }

    def 'test and'() {
        given:
        def result = this.executor.visitAnd(first, second)

        expect:
        result == expected

        where:
        first          | second         | expected
        BOOL_LIT_TRUE  | BOOL_LIT_TRUE  | BooleanValue.TRUE
        BOOL_LIT_FALSE | BOOL_LIT_TRUE  | BooleanValue.FALSE
        BOOL_LIT_TRUE  | BOOL_LIT_FALSE | BooleanValue.FALSE
        BOOL_LIT_FALSE | BOOL_LIT_FALSE | BooleanValue.FALSE
    }

    def 'test or'() {
        given:
        def result = this.executor.visitOr(first, second)

        expect:
        result == expected

        where:
        first          | second         | expected
        BOOL_LIT_TRUE  | BOOL_LIT_TRUE  | BooleanValue.TRUE
        BOOL_LIT_FALSE | BOOL_LIT_TRUE  | BooleanValue.TRUE
        BOOL_LIT_TRUE  | BOOL_LIT_FALSE | BooleanValue.TRUE
        BOOL_LIT_FALSE | BOOL_LIT_FALSE | BooleanValue.FALSE
    }

    def 'test bit and'() {
        given:
        def result = this.executor.visitBitAnd(first, second)

        expect:
        result == expected

        where:
        first         | second         | expected
        BOOL_LIT_TRUE | BOOL_LIT_FALSE | PrimitiveValue.of(true & false)
        NUMBER_LIT    | NUMBER_LIT     | PrimitiveValue.of(1 & 1)
    }

    def 'test bit or'() {
        given:
        def result = this.executor.visitBitOr(first, second)

        expect:
        result == expected

        where:
        first         | second         | expected
        BOOL_LIT_TRUE | BOOL_LIT_FALSE | PrimitiveValue.of(true | false)
        NUMBER_LIT    | NUMBER_LIT     | PrimitiveValue.of(1 | 1)
    }

    def 'test bit xor'() {
        given:
        def result = this.executor.visitBitXor(first, second)

        expect:
        result == expected

        where:
        first         | second         | expected
        BOOL_LIT_TRUE | BOOL_LIT_FALSE | PrimitiveValue.of(true ^ false)
        NUMBER_LIT    | NUMBER_LIT     | PrimitiveValue.of(1 ^ 1)
    }

}