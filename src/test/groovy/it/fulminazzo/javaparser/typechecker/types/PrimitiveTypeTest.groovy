package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.ValueType.*

class PrimitiveTypeTest extends Specification {

    def 'test cast of #cast to #type should return #cast'() {
        when:
        def actual = cast.cast(type)

        then:
        actual == cast.toType()

        where:
        cast                   | type
        // Byte
        PrimitiveType.BYTE     | BYTE
        PrimitiveType.BYTE     | ObjectType.BYTE
        PrimitiveType.BYTE     | SHORT
        PrimitiveType.BYTE     | CHAR
        PrimitiveType.BYTE     | NUMBER
        PrimitiveType.BYTE     | LONG
        PrimitiveType.BYTE     | FLOAT
        PrimitiveType.BYTE     | DOUBLE
        // Short
        PrimitiveType.SHORT    | BYTE
        PrimitiveType.SHORT    | ObjectType.BYTE
        PrimitiveType.SHORT    | SHORT
        PrimitiveType.SHORT    | ObjectType.SHORT
        PrimitiveType.SHORT    | CHAR
        PrimitiveType.SHORT    | NUMBER
        PrimitiveType.SHORT    | LONG
        PrimitiveType.SHORT    | FLOAT
        PrimitiveType.SHORT    | DOUBLE
        // Character
        PrimitiveType.CHAR     | BYTE
        PrimitiveType.CHAR     | SHORT
        PrimitiveType.CHAR     | CHAR
        PrimitiveType.CHAR     | ObjectType.CHARACTER
        PrimitiveType.CHAR     | NUMBER
        PrimitiveType.CHAR     | LONG
        PrimitiveType.CHAR     | FLOAT
        PrimitiveType.CHAR     | DOUBLE
        // Integer
        PrimitiveType.INT      | BYTE
        PrimitiveType.INT      | ObjectType.BYTE
        PrimitiveType.INT      | SHORT
        PrimitiveType.INT      | ObjectType.SHORT
        PrimitiveType.INT      | CHAR
        PrimitiveType.INT      | ObjectType.CHARACTER
        PrimitiveType.INT      | NUMBER
        PrimitiveType.INT      | ObjectType.INTEGER
        PrimitiveType.INT      | LONG
        PrimitiveType.INT      | FLOAT
        PrimitiveType.INT      | DOUBLE
        // Long
        PrimitiveType.LONG     | BYTE
        PrimitiveType.LONG     | ObjectType.BYTE
        PrimitiveType.LONG     | SHORT
        PrimitiveType.LONG     | ObjectType.SHORT
        PrimitiveType.LONG     | CHAR
        PrimitiveType.LONG     | ObjectType.CHARACTER
        PrimitiveType.LONG     | NUMBER
        PrimitiveType.LONG     | ObjectType.INTEGER
        PrimitiveType.LONG     | LONG
        PrimitiveType.LONG     | ObjectType.LONG
        PrimitiveType.LONG     | FLOAT
        PrimitiveType.LONG     | DOUBLE
        // Float
        PrimitiveType.FLOAT    | BYTE
        PrimitiveType.FLOAT    | ObjectType.BYTE
        PrimitiveType.FLOAT    | SHORT
        PrimitiveType.FLOAT    | ObjectType.SHORT
        PrimitiveType.FLOAT    | CHAR
        PrimitiveType.FLOAT    | ObjectType.CHARACTER
        PrimitiveType.FLOAT    | NUMBER
        PrimitiveType.FLOAT    | ObjectType.INTEGER
        PrimitiveType.FLOAT    | LONG
        PrimitiveType.FLOAT    | ObjectType.LONG
        PrimitiveType.FLOAT    | FLOAT
        PrimitiveType.FLOAT    | ObjectType.FLOAT
        PrimitiveType.FLOAT    | DOUBLE
        // Double
        PrimitiveType.DOUBLE   | BYTE
        PrimitiveType.DOUBLE   | ObjectType.BYTE
        PrimitiveType.DOUBLE   | SHORT
        PrimitiveType.DOUBLE   | ObjectType.SHORT
        PrimitiveType.DOUBLE   | CHAR
        PrimitiveType.DOUBLE   | ObjectType.CHARACTER
        PrimitiveType.DOUBLE   | NUMBER
        PrimitiveType.DOUBLE   | ObjectType.INTEGER
        PrimitiveType.DOUBLE   | LONG
        PrimitiveType.DOUBLE   | ObjectType.LONG
        PrimitiveType.DOUBLE   | FLOAT
        PrimitiveType.DOUBLE   | ObjectType.FLOAT
        PrimitiveType.DOUBLE   | DOUBLE
        PrimitiveType.DOUBLE   | ObjectType.DOUBLE
        // Boolean
        PrimitiveType.BOOLEAN  | BOOLEAN
        PrimitiveType.BOOLEAN  | ObjectType.BOOLEAN
    }

    def 'test invalid cast of #cast to #type'() {
        when:
        cast.cast(type)

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidCast(cast, type).message

        where:
        cast                   | type
        // Byte
        PrimitiveType.BYTE     | ObjectType.SHORT
        PrimitiveType.BYTE     | ObjectType.CHARACTER
        PrimitiveType.BYTE     | ObjectType.INTEGER
        PrimitiveType.BYTE     | ObjectType.LONG
        PrimitiveType.BYTE     | ObjectType.FLOAT
        PrimitiveType.BYTE     | ObjectType.DOUBLE
        PrimitiveType.BYTE     | BOOLEAN
        PrimitiveType.BYTE     | ObjectType.BOOLEAN
        PrimitiveType.BYTE     | STRING
        PrimitiveType.BYTE     | ObjectType.STRING
        PrimitiveType.BYTE     | ObjectType.OBJECT
        // Short
        PrimitiveType.SHORT    | ObjectType.CHARACTER
        PrimitiveType.SHORT    | ObjectType.INTEGER
        PrimitiveType.SHORT    | ObjectType.LONG
        PrimitiveType.SHORT    | ObjectType.FLOAT
        PrimitiveType.SHORT    | ObjectType.DOUBLE
        PrimitiveType.SHORT    | BOOLEAN
        PrimitiveType.SHORT    | ObjectType.BOOLEAN
        PrimitiveType.SHORT    | STRING
        PrimitiveType.SHORT    | ObjectType.STRING
        PrimitiveType.SHORT    | ObjectType.OBJECT
        // Character
        PrimitiveType.CHAR     | ObjectType.BYTE
        PrimitiveType.CHAR     | ObjectType.SHORT
        PrimitiveType.CHAR     | ObjectType.INTEGER
        PrimitiveType.CHAR     | ObjectType.LONG
        PrimitiveType.CHAR     | ObjectType.FLOAT
        PrimitiveType.CHAR     | ObjectType.DOUBLE
        PrimitiveType.CHAR     | BOOLEAN
        PrimitiveType.CHAR     | ObjectType.BOOLEAN
        PrimitiveType.CHAR     | STRING
        PrimitiveType.CHAR     | ObjectType.STRING
        PrimitiveType.CHAR     | ObjectType.OBJECT
        // Integer
        PrimitiveType.INT      | ObjectType.LONG
        PrimitiveType.INT      | ObjectType.FLOAT
        PrimitiveType.INT      | ObjectType.DOUBLE
        PrimitiveType.INT      | BOOLEAN
        PrimitiveType.INT      | ObjectType.BOOLEAN
        PrimitiveType.INT      | STRING
        PrimitiveType.INT      | ObjectType.STRING
        PrimitiveType.INT      | ObjectType.OBJECT
        // Long
        PrimitiveType.LONG     | ObjectType.FLOAT
        PrimitiveType.LONG     | ObjectType.DOUBLE
        PrimitiveType.LONG     | BOOLEAN
        PrimitiveType.LONG     | ObjectType.BOOLEAN
        PrimitiveType.LONG     | STRING
        PrimitiveType.LONG     | ObjectType.STRING
        PrimitiveType.LONG     | ObjectType.OBJECT
        // Float
        PrimitiveType.FLOAT    | ObjectType.DOUBLE
        PrimitiveType.FLOAT    | BOOLEAN
        PrimitiveType.FLOAT    | ObjectType.BOOLEAN
        PrimitiveType.FLOAT    | STRING
        PrimitiveType.FLOAT    | ObjectType.STRING
        PrimitiveType.FLOAT    | ObjectType.OBJECT
        // Double
        PrimitiveType.DOUBLE   | BOOLEAN
        PrimitiveType.DOUBLE   | ObjectType.BOOLEAN
        PrimitiveType.DOUBLE   | STRING
        PrimitiveType.DOUBLE   | ObjectType.STRING
        PrimitiveType.DOUBLE   | ObjectType.OBJECT
        // Boolean
        PrimitiveType.BOOLEAN  | BYTE
        PrimitiveType.BOOLEAN  | ObjectType.BYTE
        PrimitiveType.BOOLEAN  | SHORT
        PrimitiveType.BOOLEAN  | ObjectType.SHORT
        PrimitiveType.BOOLEAN  | CHAR
        PrimitiveType.BOOLEAN  | ObjectType.CHARACTER
        PrimitiveType.BOOLEAN  | NUMBER
        PrimitiveType.BOOLEAN  | ObjectType.INTEGER
        PrimitiveType.BOOLEAN  | LONG
        PrimitiveType.BOOLEAN  | ObjectType.LONG
        PrimitiveType.BOOLEAN  | FLOAT
        PrimitiveType.BOOLEAN  | ObjectType.FLOAT
        PrimitiveType.BOOLEAN  | DOUBLE
        PrimitiveType.BOOLEAN  | ObjectType.DOUBLE
        PrimitiveType.BOOLEAN  | STRING
        PrimitiveType.BOOLEAN  | ObjectType.STRING
        PrimitiveType.BOOLEAN  | ObjectType.OBJECT
    }

    def 'test #type toType should return #expected'() {
        given:
        def actual = type.toType()

        expect:
        actual == expected

        where:
        type                  | expected
        PrimitiveType.BYTE    | BYTE
        PrimitiveType.CHAR    | CHAR
        PrimitiveType.SHORT   | SHORT
        PrimitiveType.INT     | NUMBER
        PrimitiveType.LONG    | LONG
        PrimitiveType.FLOAT   | FLOAT
        PrimitiveType.DOUBLE  | DOUBLE
        PrimitiveType.BOOLEAN | BOOLEAN
    }

    def 'test check invalid #types'() {
        when:
        DOUBLE.check(types)

        then:
        def e = thrown(TypeCheckerException)
        e.getMessage() == TypeCheckerException.invalidType(STRING, DOUBLE).message

        where:
        types << [
                STRING,
                new Type[]{STRING, BOOLEAN, FLOAT}
        ]
    }

    def 'test check empty'() {
        when:
        DOUBLE.check()

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == "Cannot compare type ${DOUBLE} with no types"
    }

    def 'test BYTE compatible with #type'() {
        expect:
        PrimitiveType.BYTE.compatibleWith(type)

        where:
        type << [
                BYTE,
                ObjectType.BYTE
        ]
    }

    def 'test BYTE incompatible with #type'() {
        expect:
        !PrimitiveType.BYTE.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test CHAR compatible with #type'() {
        expect:
        PrimitiveType.CHAR.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
                ObjectType.CHARACTER
        ]
    }

    def 'test CHAR incompatible with #type'() {
        expect:
        !PrimitiveType.CHAR.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                ObjectType.BYTE,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test SHORT compatible with #type'() {
        expect:
        PrimitiveType.SHORT.compatibleWith(type)

        where:
        type << [
                BYTE, SHORT,
                ObjectType.BYTE, ObjectType.SHORT
        ]
    }

    def 'test SHORT incompatible with #type'() {
        expect:
        !PrimitiveType.SHORT.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                ObjectType.CHARACTER, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test INT compatible with #type'() {
        expect:
        PrimitiveType.INT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER
        ]
    }

    def 'test INT incompatible with #type'() {
        expect:
        !PrimitiveType.INT.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test LONG compatible with #type'() {
        expect:
        PrimitiveType.LONG.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG
        ]
    }

    def 'test LONG incompatible with #type'() {
        expect:
        !PrimitiveType.LONG.compatibleWith(type)

        where:
        type << [
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test FLOAT compatible with #type'() {
        expect:
        PrimitiveType.FLOAT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                FLOAT,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT
        ]
    }

    def 'test FLOAT incompatible with #type'() {
        expect:
        !PrimitiveType.FLOAT.compatibleWith(type)

        where:
        type << [
                DOUBLE,
                BOOLEAN, STRING,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test DOUBLE compatible with #type'() {
        expect:
        PrimitiveType.DOUBLE.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                FLOAT, DOUBLE,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE
        ]
    }

    def 'test DOUBLE incompatible with #type'() {
        expect:
        !PrimitiveType.DOUBLE.compatibleWith(type)

        where:
        type << [
                BOOLEAN, STRING,
                ObjectType.BOOLEAN, ObjectType.STRING
        ]
    }

    def 'test BOOLEAN compatible with #type'() {
        expect:
        PrimitiveType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                BOOLEAN, ObjectType.BOOLEAN
        ]
    }

    def 'test BOOLEAN incompatible with #type'() {
        expect:
        !PrimitiveType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                DOUBLE, FLOAT,
                STRING,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.STRING
        ]
    }

    def 'test toString of #type'() {
        given:
        def string = type.toString()

        expect:
        string == ClassType.print(type.name().toLowerCase())

        where:
        type << PrimitiveType.values()
    }

}
