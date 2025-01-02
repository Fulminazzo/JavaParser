package it.fulminazzo.javaparser.executor


import it.fulminazzo.javaparser.executor.values.primitivevalue.BooleanValue
import it.fulminazzo.javaparser.parser.node.values.*
import spock.lang.Specification

class ExecutorTest extends Specification {
    private static final BOOL_LIT = new BooleanValueLiteral('true')
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

}