package it.fulminazzo.javaparser.typechecker

import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.PrimitiveType.*

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
        BYTE   | ObjectType.BYTE
        ObjectType.BYTE | BYTE
        ObjectType.BYTE | ObjectType.BYTE
        BYTE   | SHORT
        BYTE   | ObjectType.SHORT
        ObjectType.BYTE | SHORT
        ObjectType.BYTE | ObjectType.SHORT
        BYTE   | CHAR
        BYTE   | ObjectType.CHARACTER
        ObjectType.BYTE | CHAR
        ObjectType.BYTE | ObjectType.CHARACTER
        BYTE   | NUMBER
        BYTE   | ObjectType.INTEGER
        ObjectType.BYTE | NUMBER
        ObjectType.BYTE | ObjectType.INTEGER
        BYTE   | LONG
        BYTE   | ObjectType.LONG
        ObjectType.BYTE | LONG
        ObjectType.BYTE | ObjectType.LONG
        BYTE   | FLOAT
        BYTE   | ObjectType.FLOAT
        ObjectType.BYTE | FLOAT
        ObjectType.BYTE | ObjectType.FLOAT
        BYTE   | DOUBLE
        BYTE   | ObjectType.DOUBLE
        ObjectType.BYTE | DOUBLE
        ObjectType.BYTE | ObjectType.DOUBLE
        // Short
        SHORT  | BYTE
        SHORT  | ObjectType.BYTE
        ObjectType.SHORT | BYTE
        ObjectType.SHORT | ObjectType.BYTE
        SHORT  | SHORT
        SHORT  | ObjectType.SHORT
        ObjectType.SHORT | SHORT
        ObjectType.SHORT | ObjectType.SHORT
        SHORT  | CHAR
        SHORT  | ObjectType.CHARACTER
        ObjectType.SHORT | CHAR
        ObjectType.SHORT | ObjectType.CHARACTER
        SHORT  | NUMBER
        SHORT  | ObjectType.INTEGER
        ObjectType.SHORT | NUMBER
        ObjectType.SHORT | ObjectType.INTEGER
        SHORT  | LONG
        SHORT  | ObjectType.LONG
        ObjectType.SHORT | LONG
        ObjectType.SHORT | ObjectType.LONG
        SHORT  | FLOAT
        SHORT  | ObjectType.FLOAT
        ObjectType.SHORT | FLOAT
        ObjectType.SHORT | ObjectType.FLOAT
        SHORT  | DOUBLE
        SHORT  | ObjectType.DOUBLE
        ObjectType.SHORT | DOUBLE
        ObjectType.SHORT | ObjectType.DOUBLE
        // Char
        CHAR   | BYTE
        CHAR   | ObjectType.BYTE
        ObjectType.CHARACTER | BYTE
        ObjectType.CHARACTER | ObjectType.BYTE
        CHAR   | SHORT
        CHAR   | ObjectType.SHORT
        ObjectType.CHARACTER | SHORT
        ObjectType.CHARACTER | ObjectType.SHORT
        CHAR   | CHAR
        CHAR   | ObjectType.CHARACTER
        ObjectType.CHARACTER | CHAR
        ObjectType.CHARACTER | ObjectType.CHARACTER
        CHAR   | NUMBER
        CHAR   | ObjectType.INTEGER
        ObjectType.CHARACTER | NUMBER
        ObjectType.CHARACTER | ObjectType.INTEGER
        CHAR   | LONG
        CHAR   | ObjectType.LONG
        ObjectType.CHARACTER | LONG
        ObjectType.CHARACTER | ObjectType.LONG
        CHAR   | FLOAT
        CHAR   | ObjectType.FLOAT
        ObjectType.CHARACTER | FLOAT
        ObjectType.CHARACTER | ObjectType.FLOAT
        CHAR   | DOUBLE
        CHAR   | ObjectType.DOUBLE
        ObjectType.CHARACTER | DOUBLE
        ObjectType.CHARACTER | ObjectType.DOUBLE
        // Number
        NUMBER | BYTE
        NUMBER | ObjectType.BYTE
        ObjectType.INTEGER | BYTE
        ObjectType.INTEGER | ObjectType.BYTE
        NUMBER | SHORT
        NUMBER | ObjectType.SHORT
        ObjectType.INTEGER | SHORT
        ObjectType.INTEGER | ObjectType.SHORT
        NUMBER | CHAR
        NUMBER | ObjectType.CHARACTER
        ObjectType.INTEGER | CHAR
        ObjectType.INTEGER | ObjectType.CHARACTER
        NUMBER | NUMBER
        NUMBER | ObjectType.INTEGER
        ObjectType.INTEGER | NUMBER
        ObjectType.INTEGER | ObjectType.INTEGER
        NUMBER | LONG
        NUMBER | ObjectType.LONG
        ObjectType.INTEGER | LONG
        ObjectType.INTEGER | ObjectType.LONG
        NUMBER | FLOAT
        NUMBER | ObjectType.FLOAT
        ObjectType.INTEGER | FLOAT
        ObjectType.INTEGER | ObjectType.FLOAT
        NUMBER | DOUBLE
        NUMBER | ObjectType.DOUBLE
        ObjectType.INTEGER | DOUBLE
        ObjectType.INTEGER | ObjectType.DOUBLE
        // Long
        LONG   | BYTE
        LONG   | ObjectType.BYTE
        ObjectType.LONG | BYTE
        ObjectType.LONG | ObjectType.BYTE
        LONG   | SHORT
        LONG   | ObjectType.SHORT
        ObjectType.LONG | SHORT
        ObjectType.LONG | ObjectType.SHORT
        LONG   | CHAR
        LONG   | ObjectType.CHARACTER
        ObjectType.LONG | CHAR
        ObjectType.LONG | ObjectType.CHARACTER
        LONG   | NUMBER
        LONG   | ObjectType.INTEGER
        ObjectType.LONG | NUMBER
        ObjectType.LONG | ObjectType.INTEGER
        LONG   | LONG
        LONG   | ObjectType.LONG
        ObjectType.LONG | LONG
        ObjectType.LONG | ObjectType.LONG
        LONG   | FLOAT
        LONG   | ObjectType.FLOAT
        ObjectType.LONG | FLOAT
        ObjectType.LONG | ObjectType.FLOAT
        LONG   | DOUBLE
        LONG   | ObjectType.DOUBLE
        ObjectType.LONG | DOUBLE
        ObjectType.LONG | ObjectType.DOUBLE
        // Float
        FLOAT  | BYTE
        FLOAT  | ObjectType.BYTE
        ObjectType.FLOAT | BYTE
        ObjectType.FLOAT | ObjectType.BYTE
        FLOAT  | SHORT
        FLOAT  | ObjectType.SHORT
        ObjectType.FLOAT | SHORT
        ObjectType.FLOAT | ObjectType.SHORT
        FLOAT  | CHAR
        FLOAT  | ObjectType.CHARACTER
        ObjectType.FLOAT | CHAR
        ObjectType.FLOAT | ObjectType.CHARACTER
        FLOAT  | NUMBER
        FLOAT  | ObjectType.INTEGER
        ObjectType.FLOAT | NUMBER
        ObjectType.FLOAT | ObjectType.INTEGER
        FLOAT  | LONG
        FLOAT  | ObjectType.LONG
        ObjectType.FLOAT | LONG
        ObjectType.FLOAT | ObjectType.LONG
        FLOAT  | FLOAT
        FLOAT  | ObjectType.FLOAT
        ObjectType.FLOAT | FLOAT
        ObjectType.FLOAT | ObjectType.FLOAT
        FLOAT  | DOUBLE
        FLOAT  | ObjectType.DOUBLE
        ObjectType.FLOAT | DOUBLE
        ObjectType.FLOAT | ObjectType.DOUBLE
        // Double
        DOUBLE | BYTE
        DOUBLE | ObjectType.BYTE
        ObjectType.DOUBLE | BYTE
        ObjectType.DOUBLE | ObjectType.BYTE
        DOUBLE | SHORT
        DOUBLE | ObjectType.SHORT
        ObjectType.DOUBLE | SHORT
        ObjectType.DOUBLE | ObjectType.SHORT
        DOUBLE | CHAR
        DOUBLE | ObjectType.CHARACTER
        ObjectType.DOUBLE | CHAR
        ObjectType.DOUBLE | ObjectType.CHARACTER
        DOUBLE | NUMBER
        DOUBLE | ObjectType.INTEGER
        ObjectType.DOUBLE | NUMBER
        ObjectType.DOUBLE | ObjectType.INTEGER
        DOUBLE | LONG
        DOUBLE | ObjectType.LONG
        ObjectType.DOUBLE | LONG
        ObjectType.DOUBLE | ObjectType.LONG
        DOUBLE | FLOAT
        DOUBLE | ObjectType.FLOAT
        ObjectType.DOUBLE | FLOAT
        ObjectType.DOUBLE | ObjectType.FLOAT
        DOUBLE | DOUBLE
        DOUBLE | ObjectType.DOUBLE
        ObjectType.DOUBLE | DOUBLE
        ObjectType.DOUBLE | ObjectType.DOUBLE
    }

    def 'test invalid execute binary comparison: #a * #b'() {
        when:
        OperationUtils.executeBinaryComparison(a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a       | b
        STRING  | STRING
        STRING  | ObjectType.STRING
        ObjectType.STRING | STRING
        ObjectType.STRING | ObjectType.STRING
        BOOLEAN | STRING
        BOOLEAN | ObjectType.STRING
        ObjectType.BOOLEAN | STRING
        ObjectType.BOOLEAN | ObjectType.STRING
        STRING  | BOOLEAN
        STRING  | ObjectType.BOOLEAN
        ObjectType.STRING | BOOLEAN
        ObjectType.STRING | ObjectType.BOOLEAN
        BOOLEAN | BOOLEAN
        BOOLEAN | ObjectType.BOOLEAN
        ObjectType.BOOLEAN | BOOLEAN
        ObjectType.BOOLEAN | ObjectType.BOOLEAN
    }

    def 'test execute binary bit operation: #a * #b -> #c'() {
        expect:
        OperationUtils.executeBinaryBitOperation(a, b) == c

        where:
        a      | b      | c
        // Char
        CHAR    | BYTE    | NUMBER
        CHAR    | ObjectType.BYTE    | NUMBER
        ObjectType.CHARACTER | BYTE    | NUMBER
        ObjectType.CHARACTER | ObjectType.BYTE    | NUMBER
        CHAR    | SHORT   | NUMBER
        CHAR    | ObjectType.SHORT   | NUMBER
        ObjectType.CHARACTER | SHORT   | NUMBER
        ObjectType.CHARACTER | ObjectType.SHORT   | NUMBER
        CHAR    | CHAR    | NUMBER
        CHAR    | ObjectType.CHARACTER    | NUMBER
        ObjectType.CHARACTER | CHAR    | NUMBER
        ObjectType.CHARACTER | ObjectType.CHARACTER    | NUMBER
        CHAR    | NUMBER  | NUMBER
        CHAR    | ObjectType.INTEGER  | NUMBER
        ObjectType.CHARACTER | NUMBER  | NUMBER
        ObjectType.CHARACTER | ObjectType.INTEGER  | NUMBER
        CHAR    | LONG    | LONG
        CHAR    | ObjectType.LONG    | LONG
        ObjectType.CHARACTER | LONG    | LONG
        ObjectType.CHARACTER | ObjectType.LONG    | LONG
        // Number
        NUMBER  | BYTE    | NUMBER
        NUMBER  | ObjectType.BYTE    | NUMBER
        ObjectType.INTEGER | BYTE    | NUMBER
        ObjectType.INTEGER | ObjectType.BYTE    | NUMBER
        NUMBER  | SHORT   | NUMBER
        NUMBER  | ObjectType.SHORT   | NUMBER
        ObjectType.INTEGER | SHORT   | NUMBER
        ObjectType.INTEGER | ObjectType.SHORT   | NUMBER
        NUMBER  | CHAR    | NUMBER
        NUMBER  | ObjectType.CHARACTER    | NUMBER
        ObjectType.INTEGER | CHAR    | NUMBER
        ObjectType.INTEGER | ObjectType.CHARACTER    | NUMBER
        NUMBER  | NUMBER  | NUMBER
        NUMBER  | ObjectType.INTEGER  | NUMBER
        ObjectType.INTEGER | NUMBER  | NUMBER
        ObjectType.INTEGER | ObjectType.INTEGER  | NUMBER
        NUMBER  | LONG    | LONG
        NUMBER  | ObjectType.LONG    | LONG
        ObjectType.INTEGER | LONG    | LONG
        ObjectType.INTEGER | ObjectType.LONG    | LONG
        // Long
        LONG    | BYTE    | LONG
        LONG    | ObjectType.BYTE    | LONG
        ObjectType.LONG | BYTE    | LONG
        ObjectType.LONG | ObjectType.BYTE    | LONG
        LONG    | SHORT   | LONG
        LONG    | ObjectType.SHORT   | LONG
        ObjectType.LONG | SHORT   | LONG
        ObjectType.LONG | ObjectType.SHORT   | LONG
        LONG    | CHAR    | LONG
        LONG    | ObjectType.CHARACTER    | LONG
        ObjectType.LONG | CHAR    | LONG
        ObjectType.LONG | ObjectType.CHARACTER    | LONG
        LONG    | NUMBER  | LONG
        LONG    | ObjectType.INTEGER  | LONG
        ObjectType.LONG | NUMBER  | LONG
        ObjectType.LONG | ObjectType.INTEGER  | LONG
        LONG    | LONG    | LONG
        LONG    | ObjectType.LONG    | LONG
        ObjectType.LONG | LONG    | LONG
        ObjectType.LONG | ObjectType.LONG    | LONG
        // Boolean
        BOOLEAN | BOOLEAN | BOOLEAN
        BOOLEAN | ObjectType.BOOLEAN | BOOLEAN
        ObjectType.BOOLEAN | BOOLEAN | BOOLEAN
        ObjectType.BOOLEAN | ObjectType.BOOLEAN | BOOLEAN
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
        FLOAT   | ObjectType.BYTE
        ObjectType.FLOAT | BYTE
        ObjectType.FLOAT | ObjectType.BYTE
        FLOAT   | SHORT
        FLOAT   | ObjectType.SHORT
        ObjectType.FLOAT | SHORT
        ObjectType.FLOAT | ObjectType.SHORT
        FLOAT   | CHAR
        FLOAT   | ObjectType.CHARACTER
        ObjectType.FLOAT | CHAR
        ObjectType.FLOAT | ObjectType.CHARACTER
        FLOAT   | NUMBER
        FLOAT   | ObjectType.INTEGER
        ObjectType.FLOAT | NUMBER
        ObjectType.FLOAT | ObjectType.INTEGER
        FLOAT   | LONG
        FLOAT   | ObjectType.LONG
        ObjectType.FLOAT | LONG
        ObjectType.FLOAT | ObjectType.LONG
        FLOAT   | FLOAT
        FLOAT   | ObjectType.FLOAT
        ObjectType.FLOAT | FLOAT
        ObjectType.FLOAT | ObjectType.FLOAT
        FLOAT   | DOUBLE
        FLOAT   | ObjectType.DOUBLE
        ObjectType.FLOAT | DOUBLE
        ObjectType.FLOAT | ObjectType.DOUBLE
        FLOAT   | BOOLEAN
        FLOAT   | ObjectType.BOOLEAN
        ObjectType.FLOAT | BOOLEAN
        ObjectType.FLOAT | ObjectType.BOOLEAN
        // Double
        DOUBLE  | BYTE
        DOUBLE  | ObjectType.BYTE
        ObjectType.DOUBLE | BYTE
        ObjectType.DOUBLE | ObjectType.BYTE
        DOUBLE  | SHORT
        DOUBLE  | ObjectType.SHORT
        ObjectType.DOUBLE | SHORT
        ObjectType.DOUBLE | ObjectType.SHORT
        DOUBLE  | CHAR
        DOUBLE  | ObjectType.CHARACTER
        ObjectType.DOUBLE | CHAR
        ObjectType.DOUBLE | ObjectType.CHARACTER
        DOUBLE  | NUMBER
        DOUBLE  | ObjectType.INTEGER
        ObjectType.DOUBLE | NUMBER
        ObjectType.DOUBLE | ObjectType.INTEGER
        DOUBLE  | LONG
        DOUBLE  | ObjectType.LONG
        ObjectType.DOUBLE | LONG
        ObjectType.DOUBLE | ObjectType.LONG
        DOUBLE  | FLOAT
        DOUBLE  | ObjectType.FLOAT
        ObjectType.DOUBLE | FLOAT
        ObjectType.DOUBLE | ObjectType.FLOAT
        DOUBLE  | DOUBLE
        DOUBLE  | ObjectType.DOUBLE
        ObjectType.DOUBLE | DOUBLE
        ObjectType.DOUBLE | ObjectType.DOUBLE
        DOUBLE  | BOOLEAN
        DOUBLE  | ObjectType.BOOLEAN
        ObjectType.DOUBLE | BOOLEAN
        ObjectType.DOUBLE | ObjectType.BOOLEAN
        // Boolean
        BOOLEAN | BYTE
        BOOLEAN | ObjectType.BYTE
        ObjectType.BOOLEAN | BYTE
        ObjectType.BOOLEAN | ObjectType.BYTE
        BOOLEAN | SHORT
        BOOLEAN | ObjectType.SHORT
        ObjectType.BOOLEAN | SHORT
        ObjectType.BOOLEAN | ObjectType.SHORT
        BOOLEAN | CHAR
        BOOLEAN | ObjectType.CHARACTER
        ObjectType.BOOLEAN | CHAR
        ObjectType.BOOLEAN | ObjectType.CHARACTER
        BOOLEAN | NUMBER
        BOOLEAN | ObjectType.INTEGER
        ObjectType.BOOLEAN | NUMBER
        ObjectType.BOOLEAN | ObjectType.INTEGER
        BOOLEAN | LONG
        BOOLEAN | ObjectType.LONG
        ObjectType.BOOLEAN | LONG
        ObjectType.BOOLEAN | ObjectType.LONG
        BOOLEAN | FLOAT
        BOOLEAN | ObjectType.FLOAT
        ObjectType.BOOLEAN | FLOAT
        ObjectType.BOOLEAN | ObjectType.FLOAT
        BOOLEAN | DOUBLE
        BOOLEAN | ObjectType.DOUBLE
        ObjectType.BOOLEAN | DOUBLE
        ObjectType.BOOLEAN | ObjectType.DOUBLE
        BYTE    | BOOLEAN
        BYTE    | ObjectType.BOOLEAN
        ObjectType.BYTE | BOOLEAN
        ObjectType.BYTE | ObjectType.BOOLEAN
        SHORT    | BOOLEAN
        SHORT    | ObjectType.BOOLEAN
        ObjectType.SHORT | BOOLEAN
        ObjectType.SHORT | ObjectType.BOOLEAN
        CHAR    | BOOLEAN
        CHAR    | ObjectType.BOOLEAN
        ObjectType.CHARACTER | BOOLEAN
        ObjectType.CHARACTER | ObjectType.BOOLEAN
        NUMBER  | BOOLEAN
        NUMBER  | ObjectType.BOOLEAN
        ObjectType.INTEGER | BOOLEAN
        ObjectType.INTEGER | ObjectType.BOOLEAN
        LONG    | BOOLEAN
        LONG    | ObjectType.BOOLEAN
        ObjectType.LONG | BOOLEAN
        ObjectType.LONG | ObjectType.BOOLEAN
    }

    def 'test execute binary operation (non-decimal): #a * #b -> #c'() {
        expect:
        OperationUtils.executeBinaryOperation(a, b) == c

        where:
        a      | b      | c
        // Char
        CHAR   | BYTE   | NUMBER
        CHAR   | ObjectType.BYTE   | NUMBER
        ObjectType.CHARACTER | BYTE   | NUMBER
        ObjectType.CHARACTER | ObjectType.BYTE   | NUMBER
        CHAR   | SHORT  | NUMBER
        CHAR   | ObjectType.SHORT  | NUMBER
        ObjectType.CHARACTER | SHORT  | NUMBER
        ObjectType.CHARACTER | ObjectType.SHORT  | NUMBER
        CHAR   | CHAR   | NUMBER
        CHAR   | ObjectType.CHARACTER   | NUMBER
        ObjectType.CHARACTER | CHAR   | NUMBER
        ObjectType.CHARACTER | ObjectType.CHARACTER   | NUMBER
        CHAR   | NUMBER | NUMBER
        CHAR   | ObjectType.INTEGER | NUMBER
        ObjectType.CHARACTER | NUMBER | NUMBER
        ObjectType.CHARACTER | ObjectType.INTEGER | NUMBER
        CHAR   | LONG   | LONG
        CHAR   | ObjectType.LONG   | LONG
        ObjectType.CHARACTER | LONG   | LONG
        ObjectType.CHARACTER | ObjectType.LONG   | LONG
        // Number
        NUMBER | BYTE   | NUMBER
        NUMBER | ObjectType.BYTE   | NUMBER
        ObjectType.INTEGER | BYTE   | NUMBER
        ObjectType.INTEGER | ObjectType.BYTE   | NUMBER
        NUMBER | SHORT  | NUMBER
        NUMBER | ObjectType.SHORT  | NUMBER
        ObjectType.INTEGER | SHORT  | NUMBER
        ObjectType.INTEGER | ObjectType.SHORT  | NUMBER
        NUMBER | CHAR   | NUMBER
        NUMBER | ObjectType.CHARACTER   | NUMBER
        ObjectType.INTEGER | CHAR   | NUMBER
        ObjectType.INTEGER | ObjectType.CHARACTER   | NUMBER
        NUMBER | NUMBER | NUMBER
        NUMBER | ObjectType.INTEGER | NUMBER
        ObjectType.INTEGER | NUMBER | NUMBER
        ObjectType.INTEGER | ObjectType.INTEGER | NUMBER
        NUMBER | LONG   | LONG
        NUMBER | ObjectType.LONG   | LONG
        ObjectType.INTEGER | LONG   | LONG
        ObjectType.INTEGER | ObjectType.LONG   | LONG
        // Long
        LONG   | BYTE   | LONG
        LONG   | ObjectType.BYTE   | LONG
        ObjectType.LONG | BYTE   | LONG
        ObjectType.LONG | ObjectType.BYTE   | LONG
        LONG   | SHORT  | LONG
        LONG   | ObjectType.SHORT  | LONG
        ObjectType.LONG | SHORT  | LONG
        ObjectType.LONG | ObjectType.SHORT  | LONG
        LONG   | CHAR   | LONG
        LONG   | ObjectType.CHARACTER   | LONG
        ObjectType.LONG | CHAR   | LONG
        ObjectType.LONG | ObjectType.CHARACTER   | LONG
        LONG   | NUMBER | LONG
        LONG   | ObjectType.INTEGER | LONG
        ObjectType.LONG | NUMBER | LONG
        ObjectType.LONG | ObjectType.INTEGER | LONG
        LONG   | LONG   | LONG
        LONG   | ObjectType.LONG   | LONG
        ObjectType.LONG | LONG   | LONG
        ObjectType.LONG | ObjectType.LONG   | LONG
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
        FLOAT  | ObjectType.BYTE
        ObjectType.FLOAT | BYTE
        ObjectType.FLOAT | ObjectType.BYTE
        FLOAT  | SHORT
        FLOAT  | ObjectType.SHORT
        ObjectType.FLOAT | SHORT
        ObjectType.FLOAT | ObjectType.SHORT
        FLOAT  | CHAR
        FLOAT  | ObjectType.CHARACTER
        ObjectType.FLOAT | CHAR
        ObjectType.FLOAT | ObjectType.CHARACTER
        FLOAT  | NUMBER
        FLOAT  | ObjectType.INTEGER
        ObjectType.FLOAT | NUMBER
        ObjectType.FLOAT | ObjectType.INTEGER
        FLOAT  | LONG
        FLOAT  | ObjectType.LONG
        ObjectType.FLOAT | LONG
        ObjectType.FLOAT | ObjectType.LONG
        FLOAT  | FLOAT
        FLOAT  | ObjectType.FLOAT
        ObjectType.FLOAT | FLOAT
        ObjectType.FLOAT | ObjectType.FLOAT
        FLOAT  | DOUBLE
        FLOAT  | ObjectType.DOUBLE
        ObjectType.FLOAT | DOUBLE
        ObjectType.FLOAT | ObjectType.DOUBLE
        // Double
        DOUBLE | BYTE
        DOUBLE | ObjectType.BYTE
        ObjectType.DOUBLE | BYTE
        ObjectType.DOUBLE | ObjectType.BYTE
        DOUBLE | SHORT
        DOUBLE | ObjectType.SHORT
        ObjectType.DOUBLE | SHORT
        ObjectType.DOUBLE | ObjectType.SHORT
        DOUBLE | CHAR
        DOUBLE | ObjectType.CHARACTER
        ObjectType.DOUBLE | CHAR
        ObjectType.DOUBLE | ObjectType.CHARACTER
        DOUBLE | NUMBER
        DOUBLE | ObjectType.INTEGER
        ObjectType.DOUBLE | NUMBER
        ObjectType.DOUBLE | ObjectType.INTEGER
        DOUBLE | LONG
        DOUBLE | ObjectType.LONG
        ObjectType.DOUBLE | LONG
        ObjectType.DOUBLE | ObjectType.LONG
        DOUBLE | FLOAT
        DOUBLE | ObjectType.FLOAT
        ObjectType.DOUBLE | FLOAT
        ObjectType.DOUBLE | ObjectType.FLOAT
        DOUBLE | DOUBLE
        DOUBLE | ObjectType.DOUBLE
        ObjectType.DOUBLE | DOUBLE
        ObjectType.DOUBLE | ObjectType.DOUBLE
    }

    def 'test execute binary operation decimal: #a * #b -> #c'() {
        expect:
        OperationUtils.executeBinaryOperationDecimal(a, b) == c

        where:
        a      | b      | c
        // Byte
        BYTE   | BYTE   | NUMBER
        BYTE   | ObjectType.BYTE   | NUMBER
        ObjectType.BYTE | BYTE   | NUMBER
        ObjectType.BYTE | ObjectType.BYTE   | NUMBER
        BYTE   | SHORT  | NUMBER
        BYTE   | ObjectType.SHORT  | NUMBER
        ObjectType.BYTE | SHORT  | NUMBER
        ObjectType.BYTE | ObjectType.SHORT  | NUMBER
        BYTE   | CHAR   | NUMBER
        BYTE   | ObjectType.CHARACTER   | NUMBER
        ObjectType.BYTE | CHAR   | NUMBER
        ObjectType.BYTE | ObjectType.CHARACTER   | NUMBER
        BYTE   | NUMBER | NUMBER
        BYTE   | ObjectType.INTEGER | NUMBER
        ObjectType.BYTE | NUMBER | NUMBER
        ObjectType.BYTE | ObjectType.INTEGER | NUMBER
        BYTE   | LONG   | LONG
        BYTE   | ObjectType.LONG   | LONG
        ObjectType.BYTE | LONG   | LONG
        ObjectType.BYTE | ObjectType.LONG   | LONG
        BYTE   | FLOAT  | FLOAT
        BYTE   | ObjectType.FLOAT  | FLOAT
        ObjectType.BYTE | FLOAT  | FLOAT
        ObjectType.BYTE | ObjectType.FLOAT  | FLOAT
        BYTE   | DOUBLE | DOUBLE
        BYTE   | ObjectType.DOUBLE | DOUBLE
        ObjectType.BYTE | DOUBLE | DOUBLE
        ObjectType.BYTE | ObjectType.DOUBLE | DOUBLE
        // Short
        SHORT  | BYTE   | NUMBER
        SHORT  | ObjectType.BYTE   | NUMBER
        ObjectType.SHORT | BYTE   | NUMBER
        ObjectType.SHORT | ObjectType.BYTE   | NUMBER
        SHORT  | SHORT  | NUMBER
        SHORT  | ObjectType.SHORT  | NUMBER
        ObjectType.SHORT | SHORT  | NUMBER
        ObjectType.SHORT | ObjectType.SHORT  | NUMBER
        SHORT  | CHAR   | NUMBER
        SHORT  | ObjectType.CHARACTER   | NUMBER
        ObjectType.SHORT | CHAR   | NUMBER
        ObjectType.SHORT | ObjectType.CHARACTER   | NUMBER
        SHORT  | NUMBER | NUMBER
        SHORT  | ObjectType.INTEGER | NUMBER
        ObjectType.SHORT | NUMBER | NUMBER
        ObjectType.SHORT | ObjectType.INTEGER | NUMBER
        SHORT  | LONG   | LONG
        SHORT  | ObjectType.LONG   | LONG
        ObjectType.SHORT | LONG   | LONG
        ObjectType.SHORT | ObjectType.LONG   | LONG
        SHORT  | FLOAT  | FLOAT
        SHORT  | ObjectType.FLOAT  | FLOAT
        ObjectType.SHORT | FLOAT  | FLOAT
        ObjectType.SHORT | ObjectType.FLOAT  | FLOAT
        SHORT  | DOUBLE | DOUBLE
        SHORT  | ObjectType.DOUBLE | DOUBLE
        ObjectType.SHORT | DOUBLE | DOUBLE
        ObjectType.SHORT | ObjectType.DOUBLE | DOUBLE
        // Char
        CHAR   | BYTE   | NUMBER
        CHAR   | ObjectType.BYTE   | NUMBER
        ObjectType.CHARACTER | BYTE   | NUMBER
        ObjectType.CHARACTER | ObjectType.BYTE   | NUMBER
        CHAR   | SHORT  | NUMBER
        CHAR   | ObjectType.SHORT  | NUMBER
        ObjectType.CHARACTER | SHORT  | NUMBER
        ObjectType.CHARACTER | ObjectType.SHORT  | NUMBER
        CHAR   | CHAR   | NUMBER
        CHAR   | ObjectType.CHARACTER   | NUMBER
        ObjectType.CHARACTER | CHAR   | NUMBER
        ObjectType.CHARACTER | ObjectType.CHARACTER   | NUMBER
        CHAR   | NUMBER | NUMBER
        CHAR   | ObjectType.INTEGER | NUMBER
        ObjectType.CHARACTER | NUMBER | NUMBER
        ObjectType.CHARACTER | ObjectType.INTEGER | NUMBER
        CHAR   | LONG   | LONG
        CHAR   | ObjectType.LONG   | LONG
        ObjectType.CHARACTER | LONG   | LONG
        ObjectType.CHARACTER | ObjectType.LONG   | LONG
        CHAR   | FLOAT  | FLOAT
        CHAR   | ObjectType.FLOAT  | FLOAT
        ObjectType.CHARACTER | FLOAT  | FLOAT
        ObjectType.CHARACTER | ObjectType.FLOAT  | FLOAT
        CHAR   | DOUBLE | DOUBLE
        CHAR   | ObjectType.DOUBLE | DOUBLE
        ObjectType.CHARACTER | DOUBLE | DOUBLE
        ObjectType.CHARACTER | ObjectType.DOUBLE | DOUBLE
        // Number
        NUMBER | BYTE   | NUMBER
        NUMBER | ObjectType.BYTE   | NUMBER
        ObjectType.INTEGER | BYTE   | NUMBER
        ObjectType.INTEGER | ObjectType.BYTE   | NUMBER
        NUMBER | SHORT  | NUMBER
        NUMBER | ObjectType.SHORT  | NUMBER
        ObjectType.INTEGER | SHORT  | NUMBER
        ObjectType.INTEGER | ObjectType.SHORT  | NUMBER
        NUMBER | CHAR   | NUMBER
        NUMBER | ObjectType.CHARACTER   | NUMBER
        ObjectType.INTEGER | CHAR   | NUMBER
        ObjectType.INTEGER | ObjectType.CHARACTER   | NUMBER
        NUMBER | NUMBER | NUMBER
        NUMBER | ObjectType.INTEGER | NUMBER
        ObjectType.INTEGER | NUMBER | NUMBER
        ObjectType.INTEGER | ObjectType.INTEGER | NUMBER
        NUMBER | LONG   | LONG
        NUMBER | ObjectType.LONG   | LONG
        ObjectType.INTEGER | LONG   | LONG
        ObjectType.INTEGER | ObjectType.LONG   | LONG
        NUMBER | FLOAT  | FLOAT
        NUMBER | ObjectType.FLOAT  | FLOAT
        ObjectType.INTEGER | FLOAT  | FLOAT
        ObjectType.INTEGER | ObjectType.FLOAT  | FLOAT
        NUMBER | DOUBLE | DOUBLE
        NUMBER | ObjectType.DOUBLE | DOUBLE
        ObjectType.INTEGER | DOUBLE | DOUBLE
        ObjectType.INTEGER | ObjectType.DOUBLE | DOUBLE
        // Long
        LONG   | BYTE   | LONG
        LONG   | ObjectType.BYTE   | LONG
        ObjectType.LONG | BYTE   | LONG
        ObjectType.LONG | ObjectType.BYTE   | LONG
        LONG   | SHORT  | LONG
        LONG   | ObjectType.SHORT  | LONG
        ObjectType.LONG | SHORT  | LONG
        ObjectType.LONG | ObjectType.SHORT  | LONG
        LONG   | CHAR   | LONG
        LONG   | ObjectType.CHARACTER   | LONG
        ObjectType.LONG | CHAR   | LONG
        ObjectType.LONG | ObjectType.CHARACTER   | LONG
        LONG   | NUMBER | LONG
        LONG   | ObjectType.INTEGER | LONG
        ObjectType.LONG | NUMBER | LONG
        ObjectType.LONG | ObjectType.INTEGER | LONG
        LONG   | LONG   | LONG
        LONG   | ObjectType.LONG   | LONG
        ObjectType.LONG | LONG   | LONG
        ObjectType.LONG | ObjectType.LONG   | LONG
        LONG   | FLOAT  | FLOAT
        LONG   | ObjectType.FLOAT  | FLOAT
        ObjectType.LONG | FLOAT  | FLOAT
        ObjectType.LONG | ObjectType.FLOAT  | FLOAT
        LONG   | DOUBLE | DOUBLE
        LONG   | ObjectType.DOUBLE | DOUBLE
        ObjectType.LONG | DOUBLE | DOUBLE
        ObjectType.LONG | ObjectType.DOUBLE | DOUBLE
        // Float
        FLOAT  | BYTE   | FLOAT
        FLOAT  | ObjectType.BYTE   | FLOAT
        ObjectType.FLOAT | BYTE   | FLOAT
        ObjectType.FLOAT | ObjectType.BYTE   | FLOAT
        FLOAT  | SHORT  | FLOAT
        FLOAT  | ObjectType.SHORT  | FLOAT
        ObjectType.FLOAT | SHORT  | FLOAT
        ObjectType.FLOAT | ObjectType.SHORT  | FLOAT
        FLOAT  | CHAR   | FLOAT
        FLOAT  | ObjectType.CHARACTER   | FLOAT
        ObjectType.FLOAT | CHAR   | FLOAT
        ObjectType.FLOAT | ObjectType.CHARACTER   | FLOAT
        FLOAT  | NUMBER | FLOAT
        FLOAT  | ObjectType.INTEGER | FLOAT
        ObjectType.FLOAT | NUMBER | FLOAT
        ObjectType.FLOAT | ObjectType.INTEGER | FLOAT
        FLOAT  | LONG   | FLOAT
        FLOAT  | ObjectType.LONG   | FLOAT
        ObjectType.FLOAT | LONG   | FLOAT
        ObjectType.FLOAT | ObjectType.LONG   | FLOAT
        FLOAT  | FLOAT  | FLOAT
        FLOAT  | ObjectType.FLOAT  | FLOAT
        ObjectType.FLOAT | FLOAT  | FLOAT
        ObjectType.FLOAT | ObjectType.FLOAT  | FLOAT
        FLOAT  | DOUBLE | DOUBLE
        FLOAT  | ObjectType.DOUBLE | DOUBLE
        ObjectType.FLOAT | DOUBLE | DOUBLE
        ObjectType.FLOAT | ObjectType.DOUBLE | DOUBLE
        // Double
        DOUBLE | BYTE   | DOUBLE
        DOUBLE | ObjectType.BYTE   | DOUBLE
        ObjectType.DOUBLE | BYTE   | DOUBLE
        ObjectType.DOUBLE | ObjectType.BYTE   | DOUBLE
        DOUBLE | SHORT  | DOUBLE
        DOUBLE | ObjectType.SHORT  | DOUBLE
        ObjectType.DOUBLE | SHORT  | DOUBLE
        ObjectType.DOUBLE | ObjectType.SHORT  | DOUBLE
        DOUBLE | CHAR   | DOUBLE
        DOUBLE | ObjectType.CHARACTER   | DOUBLE
        ObjectType.DOUBLE | CHAR   | DOUBLE
        ObjectType.DOUBLE | ObjectType.CHARACTER   | DOUBLE
        DOUBLE | NUMBER | DOUBLE
        DOUBLE | ObjectType.INTEGER | DOUBLE
        ObjectType.DOUBLE | NUMBER | DOUBLE
        ObjectType.DOUBLE | ObjectType.INTEGER | DOUBLE
        DOUBLE | LONG   | DOUBLE
        DOUBLE | ObjectType.LONG   | DOUBLE
        ObjectType.DOUBLE | LONG   | DOUBLE
        ObjectType.DOUBLE | ObjectType.LONG   | DOUBLE
        DOUBLE | FLOAT  | DOUBLE
        DOUBLE | ObjectType.FLOAT  | DOUBLE
        ObjectType.DOUBLE | FLOAT  | DOUBLE
        ObjectType.DOUBLE | ObjectType.FLOAT  | DOUBLE
        DOUBLE | DOUBLE | DOUBLE
        DOUBLE | ObjectType.DOUBLE | DOUBLE
        ObjectType.DOUBLE | DOUBLE | DOUBLE
        ObjectType.DOUBLE | ObjectType.DOUBLE | DOUBLE
    }

    def 'test invalid execute binary operation decimal: #a * #b'() {
        when:
        OperationUtils.executeBinaryOperationDecimal(a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a       | b
        STRING  | STRING
        STRING  | ObjectType.STRING
        ObjectType.STRING | STRING
        ObjectType.STRING | ObjectType.STRING
        BOOLEAN | STRING
        BOOLEAN | ObjectType.STRING
        ObjectType.BOOLEAN | STRING
        ObjectType.BOOLEAN | ObjectType.STRING
        STRING  | BOOLEAN
        STRING  | ObjectType.BOOLEAN
        ObjectType.STRING | BOOLEAN
        ObjectType.STRING | ObjectType.BOOLEAN
        BOOLEAN | BOOLEAN
        BOOLEAN | ObjectType.BOOLEAN
        ObjectType.BOOLEAN | BOOLEAN
        ObjectType.BOOLEAN | ObjectType.BOOLEAN
    }

}
