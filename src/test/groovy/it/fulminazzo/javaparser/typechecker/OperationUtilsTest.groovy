package it.fulminazzo.javaparser.typechecker

import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.ValueType.*

class OperationUtilsTest extends Specification {

    def 'test execute object comparison: #a * #b'() {
        expect:
        OperationUtils.executeObjectComparison(a, b)

        where:
        a       | b
        // String
        STRING  | STRING
        // Boolean
        BOOLEAN | BOOLEAN
        // Byte
        BYTE    | BYTE
        BYTE    | SHORT
        BYTE    | CHAR
        BYTE    | NUMBER
        BYTE    | LONG
        BYTE    | FLOAT
        BYTE    | DOUBLE
        // Short
        SHORT   | BYTE
        SHORT   | SHORT
        SHORT   | CHAR
        SHORT   | NUMBER
        SHORT   | LONG
        SHORT   | FLOAT
        SHORT   | DOUBLE
        // Char
        CHAR    | BYTE
        CHAR    | SHORT
        CHAR    | CHAR
        CHAR    | NUMBER
        CHAR    | LONG
        CHAR    | FLOAT
        CHAR    | DOUBLE
        // Number
        NUMBER  | BYTE
        NUMBER  | SHORT
        NUMBER  | CHAR
        NUMBER  | NUMBER
        NUMBER  | LONG
        NUMBER  | FLOAT
        NUMBER  | DOUBLE
        // Long
        LONG    | BYTE
        LONG    | SHORT
        LONG    | CHAR
        LONG    | NUMBER
        LONG    | LONG
        LONG    | FLOAT
        LONG    | DOUBLE
        // Float
        FLOAT   | BYTE
        FLOAT   | SHORT
        FLOAT   | CHAR
        FLOAT   | NUMBER
        FLOAT   | LONG
        FLOAT   | FLOAT
        FLOAT   | DOUBLE
        // Double
        DOUBLE  | BYTE
        DOUBLE  | SHORT
        DOUBLE  | CHAR
        DOUBLE  | NUMBER
        DOUBLE  | LONG
        DOUBLE  | FLOAT
        DOUBLE  | DOUBLE
        // Custom
        STRING | ObjectType.OBJECT
        ObjectType.OBJECT | STRING
        ObjectType.OBJECT | ObjectType.OBJECT
    }

    def 'test invalid execute object comparison: #a * #b'() {
        when:
        OperationUtils.executeObjectComparison(a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a       | b
        // String
        STRING  | BYTE
        STRING  | SHORT
        STRING  | CHAR
        STRING  | NUMBER
        STRING  | LONG
        STRING  | FLOAT
        STRING  | DOUBLE
        STRING  | BOOLEAN
        // Boolean
        BOOLEAN | BYTE
        BOOLEAN | SHORT
        BOOLEAN | CHAR
        BOOLEAN | NUMBER
        BOOLEAN | LONG
        BOOLEAN | FLOAT
        BOOLEAN | DOUBLE
        BOOLEAN | STRING
        // Byte
        BYTE    | BOOLEAN
        BYTE    | STRING
        // Short
        SHORT   | BOOLEAN
        SHORT   | STRING
        // Char
        CHAR    | BOOLEAN
        CHAR    | STRING
        // Number
        NUMBER  | BOOLEAN
        NUMBER  | STRING
        // Long
        LONG    | BOOLEAN
        LONG    | STRING
        // Float
        FLOAT   | BOOLEAN
        FLOAT   | STRING
        // Double
        DOUBLE  | BOOLEAN
        DOUBLE  | STRING
    }

    def 'test execute binary comparison: #a * #b'() {
        expect:
        OperationUtils.executeBinaryComparison(a, b)

        where:
        a      | b
        // Byte
        BYTE   | BYTE
        BYTE   | SHORT
        BYTE   | CHAR
        BYTE   | NUMBER
        BYTE   | LONG
        BYTE   | FLOAT
        BYTE   | DOUBLE
        // Short
        SHORT  | BYTE
        SHORT  | SHORT
        SHORT  | CHAR
        SHORT  | NUMBER
        SHORT  | LONG
        SHORT  | FLOAT
        SHORT  | DOUBLE
        // Char
        CHAR   | BYTE
        CHAR   | SHORT
        CHAR   | CHAR
        CHAR   | NUMBER
        CHAR   | LONG
        CHAR   | FLOAT
        CHAR   | DOUBLE
        // Number
        NUMBER | BYTE
        NUMBER | SHORT
        NUMBER | CHAR
        NUMBER | NUMBER
        NUMBER | LONG
        NUMBER | FLOAT
        NUMBER | DOUBLE
        // Long
        LONG   | BYTE
        LONG   | SHORT
        LONG   | CHAR
        LONG   | NUMBER
        LONG   | LONG
        LONG   | FLOAT
        LONG   | DOUBLE
        // Float
        FLOAT  | BYTE
        FLOAT  | SHORT
        FLOAT  | CHAR
        FLOAT  | NUMBER
        FLOAT  | LONG
        FLOAT  | FLOAT
        FLOAT  | DOUBLE
        // Double
        DOUBLE | BYTE
        DOUBLE | SHORT
        DOUBLE | CHAR
        DOUBLE | NUMBER
        DOUBLE | LONG
        DOUBLE | FLOAT
        DOUBLE | DOUBLE
    }

    def 'test invalid execute binary comparison: #a * #b'() {
        when:
        OperationUtils.executeBinaryComparison(a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a       | b
        STRING  | STRING
        BOOLEAN | STRING
        STRING  | BOOLEAN
        BOOLEAN | BOOLEAN
    }

    def 'test execute binary bit operation: #a * #b -> #c'() {
        expect:
        OperationUtils.executeBinaryBitOperation(a, b) == c

        where:
        a      | b      | c
        // Char
        CHAR    | BYTE    | NUMBER
        CHAR    | SHORT   | NUMBER
        CHAR    | CHAR    | NUMBER
        CHAR    | NUMBER  | NUMBER
        CHAR    | LONG    | LONG
        // Number
        NUMBER  | BYTE    | NUMBER
        NUMBER  | SHORT   | NUMBER
        NUMBER  | CHAR    | NUMBER
        NUMBER  | NUMBER  | NUMBER
        NUMBER  | LONG    | LONG
        // Long
        LONG    | BYTE    | LONG
        LONG    | SHORT   | LONG
        LONG    | CHAR    | LONG
        LONG    | NUMBER  | LONG
        LONG    | LONG    | LONG
        // Boolean
        BOOLEAN | BOOLEAN | BOOLEAN
    }

    def 'test invalid execute binary bit operation: #a * #b'() {
        when:
        OperationUtils.executeBinaryBitOperation(a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a       | b
        // Float
        FLOAT   | BYTE
        FLOAT   | SHORT
        FLOAT   | CHAR
        FLOAT   | NUMBER
        FLOAT   | LONG
        FLOAT   | FLOAT
        FLOAT   | DOUBLE
        FLOAT   | BOOLEAN
        // Double
        DOUBLE  | BYTE
        DOUBLE  | SHORT
        DOUBLE  | CHAR
        DOUBLE  | NUMBER
        DOUBLE  | LONG
        DOUBLE  | FLOAT
        DOUBLE  | DOUBLE
        DOUBLE  | BOOLEAN
        // Boolean
        BOOLEAN | BYTE
        BOOLEAN | SHORT
        BOOLEAN | CHAR
        BOOLEAN | NUMBER
        BOOLEAN | LONG
        BOOLEAN | FLOAT
        BOOLEAN | DOUBLE
        BYTE    | BOOLEAN
        SHORT    | BOOLEAN
        CHAR    | BOOLEAN
        NUMBER  | BOOLEAN
        LONG    | BOOLEAN
    }

    def 'test execute binary operation (non-decimal): #a * #b -> #c'() {
        expect:
        OperationUtils.executeBinaryOperation(a, b) == c

        where:
        a      | b      | c
        // Char
        CHAR   | BYTE   | NUMBER
        CHAR   | SHORT  | NUMBER
        CHAR   | CHAR   | NUMBER
        CHAR   | NUMBER | NUMBER
        CHAR   | LONG   | LONG
        // Number
        NUMBER | BYTE   | NUMBER
        NUMBER | SHORT  | NUMBER
        NUMBER | CHAR   | NUMBER
        NUMBER | NUMBER | NUMBER
        NUMBER | LONG   | LONG
        // Long
        LONG   | BYTE   | LONG
        LONG   | SHORT  | LONG
        LONG   | CHAR   | LONG
        LONG   | NUMBER | LONG
        LONG   | LONG   | LONG
    }

    def 'test invalid execute binary operation (non-decimal): #a * #b'() {
        when:
        OperationUtils.executeBinaryOperation(a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a      | b
        // Float
        FLOAT  | BYTE
        FLOAT  | SHORT
        FLOAT  | CHAR
        FLOAT  | NUMBER
        FLOAT  | LONG
        FLOAT  | FLOAT
        FLOAT  | DOUBLE
        // Double
        DOUBLE | BYTE
        DOUBLE | SHORT
        DOUBLE | CHAR
        DOUBLE | NUMBER
        DOUBLE | LONG
        DOUBLE | FLOAT
        DOUBLE | DOUBLE
    }

    def 'test execute binary operation decimal: #a * #b -> #c'() {
        expect:
        OperationUtils.executeBinaryOperationDecimal(a, b) == c

        where:
        a      | b      | c
        // Byte
        BYTE   | BYTE   | NUMBER
        BYTE   | SHORT  | NUMBER
        BYTE   | CHAR   | NUMBER
        BYTE   | NUMBER | NUMBER
        BYTE   | LONG   | LONG
        BYTE   | FLOAT  | FLOAT
        BYTE   | DOUBLE | DOUBLE
        // Short
        SHORT  | BYTE   | NUMBER
        SHORT  | SHORT  | NUMBER
        SHORT  | CHAR   | NUMBER
        SHORT  | NUMBER | NUMBER
        SHORT  | LONG   | LONG
        SHORT  | FLOAT  | FLOAT
        SHORT  | DOUBLE | DOUBLE
        // Char
        CHAR   | BYTE   | NUMBER
        CHAR   | SHORT  | NUMBER
        CHAR   | CHAR   | NUMBER
        CHAR   | NUMBER | NUMBER
        CHAR   | LONG   | LONG
        CHAR   | FLOAT  | FLOAT
        CHAR   | DOUBLE | DOUBLE
        // Number
        NUMBER | BYTE   | NUMBER
        NUMBER | SHORT  | NUMBER
        NUMBER | CHAR   | NUMBER
        NUMBER | NUMBER | NUMBER
        NUMBER | LONG   | LONG
        NUMBER | FLOAT  | FLOAT
        NUMBER | DOUBLE | DOUBLE
        // Long
        LONG   | BYTE   | LONG
        LONG   | SHORT  | LONG
        LONG   | CHAR   | LONG
        LONG   | NUMBER | LONG
        LONG   | LONG   | LONG
        LONG   | FLOAT  | FLOAT
        LONG   | DOUBLE | DOUBLE
        // Float
        FLOAT  | BYTE   | FLOAT
        FLOAT  | SHORT  | FLOAT
        FLOAT  | CHAR   | FLOAT
        FLOAT  | NUMBER | FLOAT
        FLOAT  | LONG   | FLOAT
        FLOAT  | FLOAT  | FLOAT
        FLOAT  | DOUBLE | DOUBLE
        // Double
        DOUBLE | BYTE   | DOUBLE
        DOUBLE | SHORT  | DOUBLE
        DOUBLE | CHAR   | DOUBLE
        DOUBLE | NUMBER | DOUBLE
        DOUBLE | LONG   | DOUBLE
        DOUBLE | FLOAT  | DOUBLE
        DOUBLE | DOUBLE | DOUBLE
    }

    def 'test invalid execute binary operation decimal: #a * #b'() {
        when:
        OperationUtils.executeBinaryOperationDecimal(a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a       | b
        STRING  | STRING
        BOOLEAN | STRING
        STRING  | BOOLEAN
        BOOLEAN | BOOLEAN
    }

}
