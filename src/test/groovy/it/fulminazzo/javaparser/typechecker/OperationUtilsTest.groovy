package it.fulminazzo.javaparser.typechecker

import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.ValueType.*

class OperationUtilsTest extends Specification {

    def 'test execute binary operation (non-decimal): #a * #b -> #c'() {
        expect:
        OperationUtils.executeBinaryOperation(a, b) == c

        where:
        a      | b      | c
        // Char
        CHAR   | CHAR   | NUMBER
        CHAR   | NUMBER | NUMBER
        CHAR   | LONG   | LONG
        // Number
        NUMBER | CHAR   | NUMBER
        NUMBER | NUMBER | NUMBER
        NUMBER | LONG   | LONG
        // Long
        LONG   | CHAR   | LONG
        LONG   | NUMBER | LONG
        LONG   | LONG   | LONG
    }

    def 'test execute binary operation decimal: #a * #b -> #c'() {
        expect:
        OperationUtils.executeBinaryOperationDecimal(a, b) == c

        where:
        a      | b      | c
        // Char
        CHAR   | CHAR   | NUMBER
        CHAR   | NUMBER | NUMBER
        CHAR   | LONG   | LONG
        CHAR   | FLOAT  | FLOAT
        CHAR   | DOUBLE | DOUBLE
        // Number
        NUMBER | CHAR   | NUMBER
        NUMBER | NUMBER | NUMBER
        NUMBER | LONG   | LONG
        NUMBER | FLOAT  | FLOAT
        NUMBER | DOUBLE | DOUBLE
        // Long
        LONG   | CHAR   | LONG
        LONG   | NUMBER | LONG
        LONG   | LONG   | LONG
        LONG   | FLOAT  | FLOAT
        LONG   | DOUBLE | DOUBLE
        // Double
        DOUBLE | CHAR   | DOUBLE
        DOUBLE | NUMBER | DOUBLE
        DOUBLE | LONG   | DOUBLE
        DOUBLE | FLOAT  | DOUBLE
        DOUBLE | DOUBLE | DOUBLE
        // Float
        FLOAT  | CHAR   | FLOAT
        FLOAT  | NUMBER | FLOAT
        FLOAT  | LONG   | LONG
        FLOAT  | FLOAT  | FLOAT
        FLOAT  | DOUBLE | DOUBLE
    }

    def 'test checkType invalid'() {
        when:
        OperationUtils.checkType(DOUBLE, STRING, BOOLEAN, FLOAT)

        then:
        def e = thrown(TypeCheckerException)
        e.getMessage() == TypeCheckerException.invalidType(STRING, DOUBLE).message

    }

    def 'test checkType empty'() {
        when:
        OperationUtils.checkType(DOUBLE)

        then:
        thrown(IllegalArgumentException)
    }

}
