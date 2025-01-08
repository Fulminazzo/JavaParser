package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.tokenizer.TokenType
import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.PrimitiveType.*

class OperationUtilsTest extends Specification {

    def 'test execute object comparison: #a * #b'() {
        expect:
        OperationUtils.executeObjectComparison(TokenType.EQUAL, a, b)

        where:
        a                 | b
        // String
        ObjectType.STRING | ObjectType.STRING
        // Boolean
        BOOLEAN           | BOOLEAN
        // Byte
        BYTE              | BYTE
        BYTE              | SHORT
        BYTE              | CHAR
        BYTE              | INT
        BYTE              | LONG
        BYTE              | FLOAT
        BYTE              | DOUBLE
        // Short
        SHORT             | BYTE
        SHORT             | SHORT
        SHORT             | CHAR
        SHORT             | INT
        SHORT             | LONG
        SHORT             | FLOAT
        SHORT             | DOUBLE
        // Char
        CHAR              | BYTE
        CHAR              | SHORT
        CHAR              | CHAR
        CHAR              | INT
        CHAR              | LONG
        CHAR              | FLOAT
        CHAR              | DOUBLE
        // Number
        INT               | BYTE
        INT               | SHORT
        INT               | CHAR
        INT               | INT
        INT               | LONG
        INT               | FLOAT
        INT               | DOUBLE
        // Long
        LONG              | BYTE
        LONG              | SHORT
        LONG              | CHAR
        LONG              | INT
        LONG              | LONG
        LONG              | FLOAT
        LONG              | DOUBLE
        // Float
        FLOAT             | BYTE
        FLOAT             | SHORT
        FLOAT             | CHAR
        FLOAT             | INT
        FLOAT             | LONG
        FLOAT             | FLOAT
        FLOAT             | DOUBLE
        // Double
        DOUBLE            | BYTE
        DOUBLE            | SHORT
        DOUBLE            | CHAR
        DOUBLE            | INT
        DOUBLE            | LONG
        DOUBLE            | FLOAT
        DOUBLE            | DOUBLE
        // Custom
        ObjectType.STRING | ObjectType.OBJECT
        ObjectType.OBJECT | ObjectType.STRING
        ObjectType.OBJECT | ObjectType.OBJECT
    }

    def 'test invalid execute object comparison: #a * #b'() {
        when:
        OperationUtils.executeObjectComparison(TokenType.EQUAL, a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a                 | b
        // String
        ObjectType.STRING | BYTE
        ObjectType.STRING | SHORT
        ObjectType.STRING | CHAR
        ObjectType.STRING | INT
        ObjectType.STRING | LONG
        ObjectType.STRING | FLOAT
        ObjectType.STRING | DOUBLE
        ObjectType.STRING | BOOLEAN
        // Boolean
        BOOLEAN           | BYTE
        BOOLEAN           | SHORT
        BOOLEAN           | CHAR
        BOOLEAN           | INT
        BOOLEAN           | LONG
        BOOLEAN           | FLOAT
        BOOLEAN           | DOUBLE
        BOOLEAN           | ObjectType.STRING
        // Byte
        BYTE              | BOOLEAN
        BYTE              | ObjectType.STRING
        // Short
        SHORT             | BOOLEAN
        SHORT             | ObjectType.STRING
        // Char
        CHAR              | BOOLEAN
        CHAR              | ObjectType.STRING
        // Number
        INT               | BOOLEAN
        INT               | ObjectType.STRING
        // Long
        LONG              | BOOLEAN
        LONG              | ObjectType.STRING
        // Float
        FLOAT             | BOOLEAN
        FLOAT             | ObjectType.STRING
        // Double
        DOUBLE            | BOOLEAN
        DOUBLE            | ObjectType.STRING
    }

    def 'test execute binary comparison: #a * #b'() {
        expect:
        OperationUtils.executeBinaryComparison(TokenType.LESS_THAN, a, b)

        where:
        a                    | b
        // Byte
        BYTE                 | BYTE
        BYTE                 | ObjectType.BYTE
        ObjectType.BYTE      | BYTE
        ObjectType.BYTE      | ObjectType.BYTE
        BYTE                 | SHORT
        BYTE                 | ObjectType.SHORT
        ObjectType.BYTE      | SHORT
        ObjectType.BYTE      | ObjectType.SHORT
        BYTE                 | CHAR
        BYTE                 | ObjectType.CHARACTER
        ObjectType.BYTE      | CHAR
        ObjectType.BYTE      | ObjectType.CHARACTER
        BYTE                 | INT
        BYTE                 | ObjectType.INTEGER
        ObjectType.BYTE      | INT
        ObjectType.BYTE      | ObjectType.INTEGER
        BYTE                 | LONG
        BYTE                 | ObjectType.LONG
        ObjectType.BYTE      | LONG
        ObjectType.BYTE      | ObjectType.LONG
        BYTE                 | FLOAT
        BYTE                 | ObjectType.FLOAT
        ObjectType.BYTE      | FLOAT
        ObjectType.BYTE      | ObjectType.FLOAT
        BYTE                 | DOUBLE
        BYTE                 | ObjectType.DOUBLE
        ObjectType.BYTE      | DOUBLE
        ObjectType.BYTE      | ObjectType.DOUBLE
        // Short
        SHORT                | BYTE
        SHORT                | ObjectType.BYTE
        ObjectType.SHORT     | BYTE
        ObjectType.SHORT     | ObjectType.BYTE
        SHORT                | SHORT
        SHORT                | ObjectType.SHORT
        ObjectType.SHORT     | SHORT
        ObjectType.SHORT     | ObjectType.SHORT
        SHORT                | CHAR
        SHORT                | ObjectType.CHARACTER
        ObjectType.SHORT     | CHAR
        ObjectType.SHORT     | ObjectType.CHARACTER
        SHORT                | INT
        SHORT                | ObjectType.INTEGER
        ObjectType.SHORT     | INT
        ObjectType.SHORT     | ObjectType.INTEGER
        SHORT                | LONG
        SHORT                | ObjectType.LONG
        ObjectType.SHORT     | LONG
        ObjectType.SHORT     | ObjectType.LONG
        SHORT                | FLOAT
        SHORT                | ObjectType.FLOAT
        ObjectType.SHORT     | FLOAT
        ObjectType.SHORT     | ObjectType.FLOAT
        SHORT                | DOUBLE
        SHORT                | ObjectType.DOUBLE
        ObjectType.SHORT     | DOUBLE
        ObjectType.SHORT     | ObjectType.DOUBLE
        // Char
        CHAR                 | BYTE
        CHAR                 | ObjectType.BYTE
        ObjectType.CHARACTER | BYTE
        ObjectType.CHARACTER | ObjectType.BYTE
        CHAR                 | SHORT
        CHAR                 | ObjectType.SHORT
        ObjectType.CHARACTER | SHORT
        ObjectType.CHARACTER | ObjectType.SHORT
        CHAR                 | CHAR
        CHAR                 | ObjectType.CHARACTER
        ObjectType.CHARACTER | CHAR
        ObjectType.CHARACTER | ObjectType.CHARACTER
        CHAR                 | INT
        CHAR                 | ObjectType.INTEGER
        ObjectType.CHARACTER | INT
        ObjectType.CHARACTER | ObjectType.INTEGER
        CHAR                 | LONG
        CHAR                 | ObjectType.LONG
        ObjectType.CHARACTER | LONG
        ObjectType.CHARACTER | ObjectType.LONG
        CHAR                 | FLOAT
        CHAR                 | ObjectType.FLOAT
        ObjectType.CHARACTER | FLOAT
        ObjectType.CHARACTER | ObjectType.FLOAT
        CHAR                 | DOUBLE
        CHAR                 | ObjectType.DOUBLE
        ObjectType.CHARACTER | DOUBLE
        ObjectType.CHARACTER | ObjectType.DOUBLE
        // Number
        INT                  | BYTE
        INT                  | ObjectType.BYTE
        ObjectType.INTEGER   | BYTE
        ObjectType.INTEGER   | ObjectType.BYTE
        INT                  | SHORT
        INT                  | ObjectType.SHORT
        ObjectType.INTEGER   | SHORT
        ObjectType.INTEGER   | ObjectType.SHORT
        INT                  | CHAR
        INT                  | ObjectType.CHARACTER
        ObjectType.INTEGER   | CHAR
        ObjectType.INTEGER   | ObjectType.CHARACTER
        INT                  | INT
        INT                  | ObjectType.INTEGER
        ObjectType.INTEGER   | INT
        ObjectType.INTEGER   | ObjectType.INTEGER
        INT                  | LONG
        INT                  | ObjectType.LONG
        ObjectType.INTEGER   | LONG
        ObjectType.INTEGER   | ObjectType.LONG
        INT                  | FLOAT
        INT                  | ObjectType.FLOAT
        ObjectType.INTEGER   | FLOAT
        ObjectType.INTEGER   | ObjectType.FLOAT
        INT                  | DOUBLE
        INT                  | ObjectType.DOUBLE
        ObjectType.INTEGER   | DOUBLE
        ObjectType.INTEGER   | ObjectType.DOUBLE
        // Long
        LONG                 | BYTE
        LONG                 | ObjectType.BYTE
        ObjectType.LONG      | BYTE
        ObjectType.LONG      | ObjectType.BYTE
        LONG                 | SHORT
        LONG                 | ObjectType.SHORT
        ObjectType.LONG      | SHORT
        ObjectType.LONG      | ObjectType.SHORT
        LONG                 | CHAR
        LONG                 | ObjectType.CHARACTER
        ObjectType.LONG      | CHAR
        ObjectType.LONG      | ObjectType.CHARACTER
        LONG                 | INT
        LONG                 | ObjectType.INTEGER
        ObjectType.LONG      | INT
        ObjectType.LONG      | ObjectType.INTEGER
        LONG                 | LONG
        LONG                 | ObjectType.LONG
        ObjectType.LONG      | LONG
        ObjectType.LONG      | ObjectType.LONG
        LONG                 | FLOAT
        LONG                 | ObjectType.FLOAT
        ObjectType.LONG      | FLOAT
        ObjectType.LONG      | ObjectType.FLOAT
        LONG                 | DOUBLE
        LONG                 | ObjectType.DOUBLE
        ObjectType.LONG      | DOUBLE
        ObjectType.LONG      | ObjectType.DOUBLE
        // Float
        FLOAT                | BYTE
        FLOAT                | ObjectType.BYTE
        ObjectType.FLOAT     | BYTE
        ObjectType.FLOAT     | ObjectType.BYTE
        FLOAT                | SHORT
        FLOAT                | ObjectType.SHORT
        ObjectType.FLOAT     | SHORT
        ObjectType.FLOAT     | ObjectType.SHORT
        FLOAT                | CHAR
        FLOAT                | ObjectType.CHARACTER
        ObjectType.FLOAT     | CHAR
        ObjectType.FLOAT     | ObjectType.CHARACTER
        FLOAT                | INT
        FLOAT                | ObjectType.INTEGER
        ObjectType.FLOAT     | INT
        ObjectType.FLOAT     | ObjectType.INTEGER
        FLOAT                | LONG
        FLOAT                | ObjectType.LONG
        ObjectType.FLOAT     | LONG
        ObjectType.FLOAT     | ObjectType.LONG
        FLOAT                | FLOAT
        FLOAT                | ObjectType.FLOAT
        ObjectType.FLOAT     | FLOAT
        ObjectType.FLOAT     | ObjectType.FLOAT
        FLOAT                | DOUBLE
        FLOAT                | ObjectType.DOUBLE
        ObjectType.FLOAT     | DOUBLE
        ObjectType.FLOAT     | ObjectType.DOUBLE
        // Double
        DOUBLE               | BYTE
        DOUBLE               | ObjectType.BYTE
        ObjectType.DOUBLE    | BYTE
        ObjectType.DOUBLE    | ObjectType.BYTE
        DOUBLE               | SHORT
        DOUBLE               | ObjectType.SHORT
        ObjectType.DOUBLE    | SHORT
        ObjectType.DOUBLE    | ObjectType.SHORT
        DOUBLE               | CHAR
        DOUBLE               | ObjectType.CHARACTER
        ObjectType.DOUBLE    | CHAR
        ObjectType.DOUBLE    | ObjectType.CHARACTER
        DOUBLE               | INT
        DOUBLE               | ObjectType.INTEGER
        ObjectType.DOUBLE    | INT
        ObjectType.DOUBLE    | ObjectType.INTEGER
        DOUBLE               | LONG
        DOUBLE               | ObjectType.LONG
        ObjectType.DOUBLE    | LONG
        ObjectType.DOUBLE    | ObjectType.LONG
        DOUBLE               | FLOAT
        DOUBLE               | ObjectType.FLOAT
        ObjectType.DOUBLE    | FLOAT
        ObjectType.DOUBLE    | ObjectType.FLOAT
        DOUBLE               | DOUBLE
        DOUBLE               | ObjectType.DOUBLE
        ObjectType.DOUBLE    | DOUBLE
        ObjectType.DOUBLE    | ObjectType.DOUBLE
    }

    def 'test invalid execute binary comparison: #a * #b'() {
        when:
        OperationUtils.executeBinaryComparison(TokenType.LESS_THAN, a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a                  | b
        ObjectType.STRING  | ObjectType.STRING
        ObjectType.STRING  | ObjectType.STRING
        ObjectType.STRING  | ObjectType.STRING
        ObjectType.STRING  | ObjectType.STRING
        BOOLEAN            | ObjectType.STRING
        BOOLEAN            | ObjectType.STRING
        ObjectType.BOOLEAN | ObjectType.STRING
        ObjectType.BOOLEAN | ObjectType.STRING
        ObjectType.STRING  | BOOLEAN
        ObjectType.STRING  | ObjectType.BOOLEAN
        ObjectType.STRING  | BOOLEAN
        ObjectType.STRING  | ObjectType.BOOLEAN
        BOOLEAN            | BOOLEAN
        BOOLEAN            | ObjectType.BOOLEAN
        ObjectType.BOOLEAN | BOOLEAN
        ObjectType.BOOLEAN | ObjectType.BOOLEAN
    }

    def 'test execute binary bit operation: #a * #b -> #c'() {
        expect:
        OperationUtils.executeBinaryBitOperation(TokenType.BIT_AND, a, b) == c

        where:
        a                    | b                    | c
        // Char
        CHAR                 | BYTE                 | INT
        CHAR                 | ObjectType.BYTE      | INT
        ObjectType.CHARACTER | BYTE                 | INT
        ObjectType.CHARACTER | ObjectType.BYTE      | INT
        CHAR                 | SHORT                | INT
        CHAR                 | ObjectType.SHORT     | INT
        ObjectType.CHARACTER | SHORT                | INT
        ObjectType.CHARACTER | ObjectType.SHORT     | INT
        CHAR                 | CHAR                 | INT
        CHAR                 | ObjectType.CHARACTER | INT
        ObjectType.CHARACTER | CHAR                 | INT
        ObjectType.CHARACTER | ObjectType.CHARACTER | INT
        CHAR                 | INT                  | INT
        CHAR                 | ObjectType.INTEGER   | INT
        ObjectType.CHARACTER | INT                  | INT
        ObjectType.CHARACTER | ObjectType.INTEGER   | INT
        CHAR                 | LONG                 | LONG
        CHAR                 | ObjectType.LONG      | LONG
        ObjectType.CHARACTER | LONG                 | LONG
        ObjectType.CHARACTER | ObjectType.LONG      | LONG
        // Number
        INT                  | BYTE                 | INT
        INT                  | ObjectType.BYTE      | INT
        ObjectType.INTEGER   | BYTE                 | INT
        ObjectType.INTEGER   | ObjectType.BYTE      | INT
        INT                  | SHORT                | INT
        INT                  | ObjectType.SHORT     | INT
        ObjectType.INTEGER   | SHORT                | INT
        ObjectType.INTEGER   | ObjectType.SHORT     | INT
        INT                  | CHAR                 | INT
        INT                  | ObjectType.CHARACTER | INT
        ObjectType.INTEGER   | CHAR                 | INT
        ObjectType.INTEGER   | ObjectType.CHARACTER | INT
        INT                  | INT                  | INT
        INT                  | ObjectType.INTEGER   | INT
        ObjectType.INTEGER   | INT                  | INT
        ObjectType.INTEGER   | ObjectType.INTEGER   | INT
        INT                  | LONG                 | LONG
        INT                  | ObjectType.LONG      | LONG
        ObjectType.INTEGER   | LONG                 | LONG
        ObjectType.INTEGER   | ObjectType.LONG      | LONG
        // Long
        LONG                 | BYTE                 | LONG
        LONG                 | ObjectType.BYTE      | LONG
        ObjectType.LONG      | BYTE                 | LONG
        ObjectType.LONG      | ObjectType.BYTE      | LONG
        LONG                 | SHORT                | LONG
        LONG                 | ObjectType.SHORT     | LONG
        ObjectType.LONG      | SHORT                | LONG
        ObjectType.LONG      | ObjectType.SHORT     | LONG
        LONG                 | CHAR                 | LONG
        LONG                 | ObjectType.CHARACTER | LONG
        ObjectType.LONG      | CHAR                 | LONG
        ObjectType.LONG      | ObjectType.CHARACTER | LONG
        LONG                 | INT                  | LONG
        LONG                 | ObjectType.INTEGER   | LONG
        ObjectType.LONG      | INT                  | LONG
        ObjectType.LONG      | ObjectType.INTEGER   | LONG
        LONG                 | LONG                 | LONG
        LONG                 | ObjectType.LONG      | LONG
        ObjectType.LONG      | LONG                 | LONG
        ObjectType.LONG      | ObjectType.LONG      | LONG
        // Boolean
        BOOLEAN              | BOOLEAN              | BOOLEAN
        BOOLEAN              | ObjectType.BOOLEAN   | BOOLEAN
        ObjectType.BOOLEAN   | BOOLEAN              | BOOLEAN
        ObjectType.BOOLEAN   | ObjectType.BOOLEAN   | BOOLEAN
    }

    def 'test invalid execute binary bit operation: #a * #b'() {
        when:
        OperationUtils.executeBinaryBitOperation(TokenType.BIT_AND, a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a                    | b
        // Float
        FLOAT                | BYTE
        FLOAT                | ObjectType.BYTE
        ObjectType.FLOAT     | BYTE
        ObjectType.FLOAT     | ObjectType.BYTE
        FLOAT                | SHORT
        FLOAT                | ObjectType.SHORT
        ObjectType.FLOAT     | SHORT
        ObjectType.FLOAT     | ObjectType.SHORT
        FLOAT                | CHAR
        FLOAT                | ObjectType.CHARACTER
        ObjectType.FLOAT     | CHAR
        ObjectType.FLOAT     | ObjectType.CHARACTER
        FLOAT                | INT
        FLOAT                | ObjectType.INTEGER
        ObjectType.FLOAT     | INT
        ObjectType.FLOAT     | ObjectType.INTEGER
        FLOAT                | LONG
        FLOAT                | ObjectType.LONG
        ObjectType.FLOAT     | LONG
        ObjectType.FLOAT     | ObjectType.LONG
        FLOAT                | FLOAT
        FLOAT                | ObjectType.FLOAT
        ObjectType.FLOAT     | FLOAT
        ObjectType.FLOAT     | ObjectType.FLOAT
        FLOAT                | DOUBLE
        FLOAT                | ObjectType.DOUBLE
        ObjectType.FLOAT     | DOUBLE
        ObjectType.FLOAT     | ObjectType.DOUBLE
        FLOAT                | BOOLEAN
        FLOAT                | ObjectType.BOOLEAN
        ObjectType.FLOAT     | BOOLEAN
        ObjectType.FLOAT     | ObjectType.BOOLEAN
        // Double
        DOUBLE               | BYTE
        DOUBLE               | ObjectType.BYTE
        ObjectType.DOUBLE    | BYTE
        ObjectType.DOUBLE    | ObjectType.BYTE
        DOUBLE               | SHORT
        DOUBLE               | ObjectType.SHORT
        ObjectType.DOUBLE    | SHORT
        ObjectType.DOUBLE    | ObjectType.SHORT
        DOUBLE               | CHAR
        DOUBLE               | ObjectType.CHARACTER
        ObjectType.DOUBLE    | CHAR
        ObjectType.DOUBLE    | ObjectType.CHARACTER
        DOUBLE               | INT
        DOUBLE               | ObjectType.INTEGER
        ObjectType.DOUBLE    | INT
        ObjectType.DOUBLE    | ObjectType.INTEGER
        DOUBLE               | LONG
        DOUBLE               | ObjectType.LONG
        ObjectType.DOUBLE    | LONG
        ObjectType.DOUBLE    | ObjectType.LONG
        DOUBLE               | FLOAT
        DOUBLE               | ObjectType.FLOAT
        ObjectType.DOUBLE    | FLOAT
        ObjectType.DOUBLE    | ObjectType.FLOAT
        DOUBLE               | DOUBLE
        DOUBLE               | ObjectType.DOUBLE
        ObjectType.DOUBLE    | DOUBLE
        ObjectType.DOUBLE    | ObjectType.DOUBLE
        DOUBLE               | BOOLEAN
        DOUBLE               | ObjectType.BOOLEAN
        ObjectType.DOUBLE    | BOOLEAN
        ObjectType.DOUBLE    | ObjectType.BOOLEAN
        // Boolean
        BOOLEAN              | BYTE
        BOOLEAN              | ObjectType.BYTE
        ObjectType.BOOLEAN   | BYTE
        ObjectType.BOOLEAN   | ObjectType.BYTE
        BOOLEAN              | SHORT
        BOOLEAN              | ObjectType.SHORT
        ObjectType.BOOLEAN   | SHORT
        ObjectType.BOOLEAN   | ObjectType.SHORT
        BOOLEAN              | CHAR
        BOOLEAN              | ObjectType.CHARACTER
        ObjectType.BOOLEAN   | CHAR
        ObjectType.BOOLEAN   | ObjectType.CHARACTER
        BOOLEAN              | INT
        BOOLEAN              | ObjectType.INTEGER
        ObjectType.BOOLEAN   | INT
        ObjectType.BOOLEAN   | ObjectType.INTEGER
        BOOLEAN              | LONG
        BOOLEAN              | ObjectType.LONG
        ObjectType.BOOLEAN   | LONG
        ObjectType.BOOLEAN   | ObjectType.LONG
        BOOLEAN              | FLOAT
        BOOLEAN              | ObjectType.FLOAT
        ObjectType.BOOLEAN   | FLOAT
        ObjectType.BOOLEAN   | ObjectType.FLOAT
        BOOLEAN              | DOUBLE
        BOOLEAN              | ObjectType.DOUBLE
        ObjectType.BOOLEAN   | DOUBLE
        ObjectType.BOOLEAN   | ObjectType.DOUBLE
        BYTE                 | BOOLEAN
        BYTE                 | ObjectType.BOOLEAN
        ObjectType.BYTE      | BOOLEAN
        ObjectType.BYTE      | ObjectType.BOOLEAN
        SHORT                | BOOLEAN
        SHORT                | ObjectType.BOOLEAN
        ObjectType.SHORT     | BOOLEAN
        ObjectType.SHORT     | ObjectType.BOOLEAN
        CHAR                 | BOOLEAN
        CHAR                 | ObjectType.BOOLEAN
        ObjectType.CHARACTER | BOOLEAN
        ObjectType.CHARACTER | ObjectType.BOOLEAN
        INT                  | BOOLEAN
        INT                  | ObjectType.BOOLEAN
        ObjectType.INTEGER   | BOOLEAN
        ObjectType.INTEGER   | ObjectType.BOOLEAN
        LONG                 | BOOLEAN
        LONG                 | ObjectType.BOOLEAN
        ObjectType.LONG      | BOOLEAN
        ObjectType.LONG      | ObjectType.BOOLEAN
    }

    def 'test execute binary operation (non-decimal): #a * #b -> #c'() {
        expect:
        OperationUtils.executeBinaryOperation(TokenType.LSHIFT, a, b) == c

        where:
        a                    | b                    | c
        // Char
        CHAR                 | BYTE                 | INT
        CHAR                 | ObjectType.BYTE      | INT
        ObjectType.CHARACTER | BYTE                 | INT
        ObjectType.CHARACTER | ObjectType.BYTE      | INT
        CHAR                 | SHORT                | INT
        CHAR                 | ObjectType.SHORT     | INT
        ObjectType.CHARACTER | SHORT                | INT
        ObjectType.CHARACTER | ObjectType.SHORT     | INT
        CHAR                 | CHAR                 | INT
        CHAR                 | ObjectType.CHARACTER | INT
        ObjectType.CHARACTER | CHAR                 | INT
        ObjectType.CHARACTER | ObjectType.CHARACTER | INT
        CHAR                 | INT                  | INT
        CHAR                 | ObjectType.INTEGER   | INT
        ObjectType.CHARACTER | INT                  | INT
        ObjectType.CHARACTER | ObjectType.INTEGER   | INT
        CHAR                 | LONG                 | LONG
        CHAR                 | ObjectType.LONG      | LONG
        ObjectType.CHARACTER | LONG                 | LONG
        ObjectType.CHARACTER | ObjectType.LONG      | LONG
        // Number
        INT                  | BYTE                 | INT
        INT                  | ObjectType.BYTE      | INT
        ObjectType.INTEGER   | BYTE                 | INT
        ObjectType.INTEGER   | ObjectType.BYTE      | INT
        INT                  | SHORT                | INT
        INT                  | ObjectType.SHORT     | INT
        ObjectType.INTEGER   | SHORT                | INT
        ObjectType.INTEGER   | ObjectType.SHORT     | INT
        INT                  | CHAR                 | INT
        INT                  | ObjectType.CHARACTER | INT
        ObjectType.INTEGER   | CHAR                 | INT
        ObjectType.INTEGER   | ObjectType.CHARACTER | INT
        INT                  | INT                  | INT
        INT                  | ObjectType.INTEGER   | INT
        ObjectType.INTEGER   | INT                  | INT
        ObjectType.INTEGER   | ObjectType.INTEGER   | INT
        INT                  | LONG                 | LONG
        INT                  | ObjectType.LONG      | LONG
        ObjectType.INTEGER   | LONG                 | LONG
        ObjectType.INTEGER   | ObjectType.LONG      | LONG
        // Long
        LONG                 | BYTE                 | LONG
        LONG                 | ObjectType.BYTE      | LONG
        ObjectType.LONG      | BYTE                 | LONG
        ObjectType.LONG      | ObjectType.BYTE      | LONG
        LONG                 | SHORT                | LONG
        LONG                 | ObjectType.SHORT     | LONG
        ObjectType.LONG      | SHORT                | LONG
        ObjectType.LONG      | ObjectType.SHORT     | LONG
        LONG                 | CHAR                 | LONG
        LONG                 | ObjectType.CHARACTER | LONG
        ObjectType.LONG      | CHAR                 | LONG
        ObjectType.LONG      | ObjectType.CHARACTER | LONG
        LONG                 | INT                  | LONG
        LONG                 | ObjectType.INTEGER   | LONG
        ObjectType.LONG      | INT                  | LONG
        ObjectType.LONG      | ObjectType.INTEGER   | LONG
        LONG                 | LONG                 | LONG
        LONG                 | ObjectType.LONG      | LONG
        ObjectType.LONG      | LONG                 | LONG
        ObjectType.LONG      | ObjectType.LONG      | LONG
    }

    def 'test invalid execute binary operation (non-decimal): #a * #b'() {
        when:
        OperationUtils.executeBinaryOperation(TokenType.LSHIFT, a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a                 | b
        // Float
        FLOAT             | BYTE
        FLOAT             | ObjectType.BYTE
        ObjectType.FLOAT  | BYTE
        ObjectType.FLOAT  | ObjectType.BYTE
        FLOAT             | SHORT
        FLOAT             | ObjectType.SHORT
        ObjectType.FLOAT  | SHORT
        ObjectType.FLOAT  | ObjectType.SHORT
        FLOAT             | CHAR
        FLOAT             | ObjectType.CHARACTER
        ObjectType.FLOAT  | CHAR
        ObjectType.FLOAT  | ObjectType.CHARACTER
        FLOAT             | INT
        FLOAT             | ObjectType.INTEGER
        ObjectType.FLOAT  | INT
        ObjectType.FLOAT  | ObjectType.INTEGER
        FLOAT             | LONG
        FLOAT             | ObjectType.LONG
        ObjectType.FLOAT  | LONG
        ObjectType.FLOAT  | ObjectType.LONG
        FLOAT             | FLOAT
        FLOAT             | ObjectType.FLOAT
        ObjectType.FLOAT  | FLOAT
        ObjectType.FLOAT  | ObjectType.FLOAT
        FLOAT             | DOUBLE
        FLOAT             | ObjectType.DOUBLE
        ObjectType.FLOAT  | DOUBLE
        ObjectType.FLOAT  | ObjectType.DOUBLE
        // Double
        DOUBLE            | BYTE
        DOUBLE            | ObjectType.BYTE
        ObjectType.DOUBLE | BYTE
        ObjectType.DOUBLE | ObjectType.BYTE
        DOUBLE            | SHORT
        DOUBLE            | ObjectType.SHORT
        ObjectType.DOUBLE | SHORT
        ObjectType.DOUBLE | ObjectType.SHORT
        DOUBLE            | CHAR
        DOUBLE            | ObjectType.CHARACTER
        ObjectType.DOUBLE | CHAR
        ObjectType.DOUBLE | ObjectType.CHARACTER
        DOUBLE            | INT
        DOUBLE            | ObjectType.INTEGER
        ObjectType.DOUBLE | INT
        ObjectType.DOUBLE | ObjectType.INTEGER
        DOUBLE            | LONG
        DOUBLE            | ObjectType.LONG
        ObjectType.DOUBLE | LONG
        ObjectType.DOUBLE | ObjectType.LONG
        DOUBLE            | FLOAT
        DOUBLE            | ObjectType.FLOAT
        ObjectType.DOUBLE | FLOAT
        ObjectType.DOUBLE | ObjectType.FLOAT
        DOUBLE            | DOUBLE
        DOUBLE            | ObjectType.DOUBLE
        ObjectType.DOUBLE | DOUBLE
        ObjectType.DOUBLE | ObjectType.DOUBLE
    }

    def 'test execute binary operation decimal: #a * #b -> #c'() {
        expect:
        OperationUtils.executeBinaryOperationDecimal(TokenType.ADD, a, b) == c

        where:
        a                    | b                    | c
        // Byte
        BYTE                 | BYTE                 | INT
        BYTE                 | ObjectType.BYTE      | INT
        ObjectType.BYTE      | BYTE                 | INT
        ObjectType.BYTE      | ObjectType.BYTE      | INT
        BYTE                 | SHORT                | INT
        BYTE                 | ObjectType.SHORT     | INT
        ObjectType.BYTE      | SHORT                | INT
        ObjectType.BYTE      | ObjectType.SHORT     | INT
        BYTE                 | CHAR                 | INT
        BYTE                 | ObjectType.CHARACTER | INT
        ObjectType.BYTE      | CHAR                 | INT
        ObjectType.BYTE      | ObjectType.CHARACTER | INT
        BYTE                 | INT                  | INT
        BYTE                 | ObjectType.INTEGER   | INT
        ObjectType.BYTE      | INT                  | INT
        ObjectType.BYTE      | ObjectType.INTEGER   | INT
        BYTE                 | LONG                 | LONG
        BYTE                 | ObjectType.LONG      | LONG
        ObjectType.BYTE      | LONG                 | LONG
        ObjectType.BYTE      | ObjectType.LONG      | LONG
        BYTE                 | FLOAT                | FLOAT
        BYTE                 | ObjectType.FLOAT     | FLOAT
        ObjectType.BYTE      | FLOAT                | FLOAT
        ObjectType.BYTE      | ObjectType.FLOAT     | FLOAT
        BYTE                 | DOUBLE               | DOUBLE
        BYTE                 | ObjectType.DOUBLE    | DOUBLE
        ObjectType.BYTE      | DOUBLE               | DOUBLE
        ObjectType.BYTE      | ObjectType.DOUBLE    | DOUBLE
        // Short
        SHORT                | BYTE                 | INT
        SHORT                | ObjectType.BYTE      | INT
        ObjectType.SHORT     | BYTE                 | INT
        ObjectType.SHORT     | ObjectType.BYTE      | INT
        SHORT                | SHORT                | INT
        SHORT                | ObjectType.SHORT     | INT
        ObjectType.SHORT     | SHORT                | INT
        ObjectType.SHORT     | ObjectType.SHORT     | INT
        SHORT                | CHAR                 | INT
        SHORT                | ObjectType.CHARACTER | INT
        ObjectType.SHORT     | CHAR                 | INT
        ObjectType.SHORT     | ObjectType.CHARACTER | INT
        SHORT                | INT                  | INT
        SHORT                | ObjectType.INTEGER   | INT
        ObjectType.SHORT     | INT                  | INT
        ObjectType.SHORT     | ObjectType.INTEGER   | INT
        SHORT                | LONG                 | LONG
        SHORT                | ObjectType.LONG      | LONG
        ObjectType.SHORT     | LONG                 | LONG
        ObjectType.SHORT     | ObjectType.LONG      | LONG
        SHORT                | FLOAT                | FLOAT
        SHORT                | ObjectType.FLOAT     | FLOAT
        ObjectType.SHORT     | FLOAT                | FLOAT
        ObjectType.SHORT     | ObjectType.FLOAT     | FLOAT
        SHORT                | DOUBLE               | DOUBLE
        SHORT                | ObjectType.DOUBLE    | DOUBLE
        ObjectType.SHORT     | DOUBLE               | DOUBLE
        ObjectType.SHORT     | ObjectType.DOUBLE    | DOUBLE
        // Char
        CHAR                 | BYTE                 | INT
        CHAR                 | ObjectType.BYTE      | INT
        ObjectType.CHARACTER | BYTE                 | INT
        ObjectType.CHARACTER | ObjectType.BYTE      | INT
        CHAR                 | SHORT                | INT
        CHAR                 | ObjectType.SHORT     | INT
        ObjectType.CHARACTER | SHORT                | INT
        ObjectType.CHARACTER | ObjectType.SHORT     | INT
        CHAR                 | CHAR                 | INT
        CHAR                 | ObjectType.CHARACTER | INT
        ObjectType.CHARACTER | CHAR                 | INT
        ObjectType.CHARACTER | ObjectType.CHARACTER | INT
        CHAR                 | INT                  | INT
        CHAR                 | ObjectType.INTEGER   | INT
        ObjectType.CHARACTER | INT                  | INT
        ObjectType.CHARACTER | ObjectType.INTEGER   | INT
        CHAR                 | LONG                 | LONG
        CHAR                 | ObjectType.LONG      | LONG
        ObjectType.CHARACTER | LONG                 | LONG
        ObjectType.CHARACTER | ObjectType.LONG      | LONG
        CHAR                 | FLOAT                | FLOAT
        CHAR                 | ObjectType.FLOAT     | FLOAT
        ObjectType.CHARACTER | FLOAT                | FLOAT
        ObjectType.CHARACTER | ObjectType.FLOAT     | FLOAT
        CHAR                 | DOUBLE               | DOUBLE
        CHAR                 | ObjectType.DOUBLE    | DOUBLE
        ObjectType.CHARACTER | DOUBLE               | DOUBLE
        ObjectType.CHARACTER | ObjectType.DOUBLE    | DOUBLE
        // Number
        INT                  | BYTE                 | INT
        INT                  | ObjectType.BYTE      | INT
        ObjectType.INTEGER   | BYTE                 | INT
        ObjectType.INTEGER   | ObjectType.BYTE      | INT
        INT                  | SHORT                | INT
        INT                  | ObjectType.SHORT     | INT
        ObjectType.INTEGER   | SHORT                | INT
        ObjectType.INTEGER   | ObjectType.SHORT     | INT
        INT                  | CHAR                 | INT
        INT                  | ObjectType.CHARACTER | INT
        ObjectType.INTEGER   | CHAR                 | INT
        ObjectType.INTEGER   | ObjectType.CHARACTER | INT
        INT                  | INT                  | INT
        INT                  | ObjectType.INTEGER   | INT
        ObjectType.INTEGER   | INT                  | INT
        ObjectType.INTEGER   | ObjectType.INTEGER   | INT
        INT                  | LONG                 | LONG
        INT                  | ObjectType.LONG      | LONG
        ObjectType.INTEGER   | LONG                 | LONG
        ObjectType.INTEGER   | ObjectType.LONG      | LONG
        INT                  | FLOAT                | FLOAT
        INT                  | ObjectType.FLOAT     | FLOAT
        ObjectType.INTEGER   | FLOAT                | FLOAT
        ObjectType.INTEGER   | ObjectType.FLOAT     | FLOAT
        INT                  | DOUBLE               | DOUBLE
        INT                  | ObjectType.DOUBLE    | DOUBLE
        ObjectType.INTEGER   | DOUBLE               | DOUBLE
        ObjectType.INTEGER   | ObjectType.DOUBLE    | DOUBLE
        // Long
        LONG                 | BYTE                 | LONG
        LONG                 | ObjectType.BYTE      | LONG
        ObjectType.LONG      | BYTE                 | LONG
        ObjectType.LONG      | ObjectType.BYTE      | LONG
        LONG                 | SHORT                | LONG
        LONG                 | ObjectType.SHORT     | LONG
        ObjectType.LONG      | SHORT                | LONG
        ObjectType.LONG      | ObjectType.SHORT     | LONG
        LONG                 | CHAR                 | LONG
        LONG                 | ObjectType.CHARACTER | LONG
        ObjectType.LONG      | CHAR                 | LONG
        ObjectType.LONG      | ObjectType.CHARACTER | LONG
        LONG                 | INT                  | LONG
        LONG                 | ObjectType.INTEGER   | LONG
        ObjectType.LONG      | INT                  | LONG
        ObjectType.LONG      | ObjectType.INTEGER   | LONG
        LONG                 | LONG                 | LONG
        LONG                 | ObjectType.LONG      | LONG
        ObjectType.LONG      | LONG                 | LONG
        ObjectType.LONG      | ObjectType.LONG      | LONG
        LONG                 | FLOAT                | FLOAT
        LONG                 | ObjectType.FLOAT     | FLOAT
        ObjectType.LONG      | FLOAT                | FLOAT
        ObjectType.LONG      | ObjectType.FLOAT     | FLOAT
        LONG                 | DOUBLE               | DOUBLE
        LONG                 | ObjectType.DOUBLE    | DOUBLE
        ObjectType.LONG      | DOUBLE               | DOUBLE
        ObjectType.LONG      | ObjectType.DOUBLE    | DOUBLE
        // Float
        FLOAT                | BYTE                 | FLOAT
        FLOAT                | ObjectType.BYTE      | FLOAT
        ObjectType.FLOAT     | BYTE                 | FLOAT
        ObjectType.FLOAT     | ObjectType.BYTE      | FLOAT
        FLOAT                | SHORT                | FLOAT
        FLOAT                | ObjectType.SHORT     | FLOAT
        ObjectType.FLOAT     | SHORT                | FLOAT
        ObjectType.FLOAT     | ObjectType.SHORT     | FLOAT
        FLOAT                | CHAR                 | FLOAT
        FLOAT                | ObjectType.CHARACTER | FLOAT
        ObjectType.FLOAT     | CHAR                 | FLOAT
        ObjectType.FLOAT     | ObjectType.CHARACTER | FLOAT
        FLOAT                | INT                  | FLOAT
        FLOAT                | ObjectType.INTEGER   | FLOAT
        ObjectType.FLOAT     | INT                  | FLOAT
        ObjectType.FLOAT     | ObjectType.INTEGER   | FLOAT
        FLOAT                | LONG                 | FLOAT
        FLOAT                | ObjectType.LONG      | FLOAT
        ObjectType.FLOAT     | LONG                 | FLOAT
        ObjectType.FLOAT     | ObjectType.LONG      | FLOAT
        FLOAT                | FLOAT                | FLOAT
        FLOAT                | ObjectType.FLOAT     | FLOAT
        ObjectType.FLOAT     | FLOAT                | FLOAT
        ObjectType.FLOAT     | ObjectType.FLOAT     | FLOAT
        FLOAT                | DOUBLE               | DOUBLE
        FLOAT                | ObjectType.DOUBLE    | DOUBLE
        ObjectType.FLOAT     | DOUBLE               | DOUBLE
        ObjectType.FLOAT     | ObjectType.DOUBLE    | DOUBLE
        // Double
        DOUBLE               | BYTE                 | DOUBLE
        DOUBLE               | ObjectType.BYTE      | DOUBLE
        ObjectType.DOUBLE    | BYTE                 | DOUBLE
        ObjectType.DOUBLE    | ObjectType.BYTE      | DOUBLE
        DOUBLE               | SHORT                | DOUBLE
        DOUBLE               | ObjectType.SHORT     | DOUBLE
        ObjectType.DOUBLE    | SHORT                | DOUBLE
        ObjectType.DOUBLE    | ObjectType.SHORT     | DOUBLE
        DOUBLE               | CHAR                 | DOUBLE
        DOUBLE               | ObjectType.CHARACTER | DOUBLE
        ObjectType.DOUBLE    | CHAR                 | DOUBLE
        ObjectType.DOUBLE    | ObjectType.CHARACTER | DOUBLE
        DOUBLE               | INT                  | DOUBLE
        DOUBLE               | ObjectType.INTEGER   | DOUBLE
        ObjectType.DOUBLE    | INT                  | DOUBLE
        ObjectType.DOUBLE    | ObjectType.INTEGER   | DOUBLE
        DOUBLE               | LONG                 | DOUBLE
        DOUBLE               | ObjectType.LONG      | DOUBLE
        ObjectType.DOUBLE    | LONG                 | DOUBLE
        ObjectType.DOUBLE    | ObjectType.LONG      | DOUBLE
        DOUBLE               | FLOAT                | DOUBLE
        DOUBLE               | ObjectType.FLOAT     | DOUBLE
        ObjectType.DOUBLE    | FLOAT                | DOUBLE
        ObjectType.DOUBLE    | ObjectType.FLOAT     | DOUBLE
        DOUBLE               | DOUBLE               | DOUBLE
        DOUBLE               | ObjectType.DOUBLE    | DOUBLE
        ObjectType.DOUBLE    | DOUBLE               | DOUBLE
        ObjectType.DOUBLE    | ObjectType.DOUBLE    | DOUBLE
    }

    def 'test invalid execute binary operation decimal: #a * #b'() {
        when:
        OperationUtils.executeBinaryOperationDecimal(TokenType.ADD, a, b)

        then:
        thrown(TypeCheckerException)

        where:
        a                  | b
        ObjectType.STRING  | ObjectType.STRING
        ObjectType.STRING  | ObjectType.STRING
        ObjectType.STRING  | ObjectType.STRING
        ObjectType.STRING  | ObjectType.STRING
        BOOLEAN            | ObjectType.STRING
        BOOLEAN            | ObjectType.STRING
        ObjectType.BOOLEAN | ObjectType.STRING
        ObjectType.BOOLEAN | ObjectType.STRING
        ObjectType.STRING  | BOOLEAN
        ObjectType.STRING  | ObjectType.BOOLEAN
        ObjectType.STRING  | BOOLEAN
        ObjectType.STRING  | ObjectType.BOOLEAN
        BOOLEAN            | BOOLEAN
        BOOLEAN            | ObjectType.BOOLEAN
        ObjectType.BOOLEAN | BOOLEAN
        ObjectType.BOOLEAN | ObjectType.BOOLEAN
    }

    def 'test invalid execute unary operation boolean: #a'() {
        when:
        OperationUtils.executeUnaryOperationBoolean(TokenType.NOT, a)

        then:
        thrown(TypeCheckerException)

        where:
        a << [
                values(),
                ObjectType.values(),
                ObjectType.of(TestClass)
        ].flatten().findAll { !OperationUtils.isBoolean(it) }
    }

}
