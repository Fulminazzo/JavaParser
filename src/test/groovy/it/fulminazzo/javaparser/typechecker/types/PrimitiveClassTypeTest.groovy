package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

import static PrimitiveType.*

class PrimitiveClassTypeTest extends Specification {

    def 'test cast of #cast to #type should return #cast'() {
        when:
        def actual = cast.cast(type)

        then:
        actual == cast.toType()

        where:
        cast                       | type
        // Byte
        PrimitiveClassType.BYTE    | BYTE
        PrimitiveClassType.BYTE    | ObjectType.BYTE
        PrimitiveClassType.BYTE    | SHORT
        PrimitiveClassType.BYTE    | CHAR
        PrimitiveClassType.BYTE    | NUMBER
        PrimitiveClassType.BYTE    | LONG
        PrimitiveClassType.BYTE    | FLOAT
        PrimitiveClassType.BYTE    | DOUBLE
        // Short
        PrimitiveClassType.SHORT   | BYTE
        PrimitiveClassType.SHORT   | ObjectType.BYTE
        PrimitiveClassType.SHORT   | SHORT
        PrimitiveClassType.SHORT   | ObjectType.SHORT
        PrimitiveClassType.SHORT   | CHAR
        PrimitiveClassType.SHORT   | NUMBER
        PrimitiveClassType.SHORT   | LONG
        PrimitiveClassType.SHORT   | FLOAT
        PrimitiveClassType.SHORT   | DOUBLE
        // Character
        PrimitiveClassType.CHAR    | BYTE
        PrimitiveClassType.CHAR    | SHORT
        PrimitiveClassType.CHAR    | CHAR
        PrimitiveClassType.CHAR    | ObjectType.CHARACTER
        PrimitiveClassType.CHAR    | NUMBER
        PrimitiveClassType.CHAR    | LONG
        PrimitiveClassType.CHAR    | FLOAT
        PrimitiveClassType.CHAR    | DOUBLE
        // Integer
        PrimitiveClassType.INT     | BYTE
        PrimitiveClassType.INT     | ObjectType.BYTE
        PrimitiveClassType.INT     | SHORT
        PrimitiveClassType.INT     | ObjectType.SHORT
        PrimitiveClassType.INT     | CHAR
        PrimitiveClassType.INT     | ObjectType.CHARACTER
        PrimitiveClassType.INT     | NUMBER
        PrimitiveClassType.INT     | ObjectType.INTEGER
        PrimitiveClassType.INT     | LONG
        PrimitiveClassType.INT     | FLOAT
        PrimitiveClassType.INT     | DOUBLE
        // Long
        PrimitiveClassType.LONG    | BYTE
        PrimitiveClassType.LONG    | ObjectType.BYTE
        PrimitiveClassType.LONG    | SHORT
        PrimitiveClassType.LONG    | ObjectType.SHORT
        PrimitiveClassType.LONG    | CHAR
        PrimitiveClassType.LONG    | ObjectType.CHARACTER
        PrimitiveClassType.LONG    | NUMBER
        PrimitiveClassType.LONG    | ObjectType.INTEGER
        PrimitiveClassType.LONG    | LONG
        PrimitiveClassType.LONG    | ObjectType.LONG
        PrimitiveClassType.LONG    | FLOAT
        PrimitiveClassType.LONG    | DOUBLE
        // Float
        PrimitiveClassType.FLOAT   | BYTE
        PrimitiveClassType.FLOAT   | ObjectType.BYTE
        PrimitiveClassType.FLOAT   | SHORT
        PrimitiveClassType.FLOAT   | ObjectType.SHORT
        PrimitiveClassType.FLOAT   | CHAR
        PrimitiveClassType.FLOAT   | ObjectType.CHARACTER
        PrimitiveClassType.FLOAT   | NUMBER
        PrimitiveClassType.FLOAT   | ObjectType.INTEGER
        PrimitiveClassType.FLOAT   | LONG
        PrimitiveClassType.FLOAT   | ObjectType.LONG
        PrimitiveClassType.FLOAT   | FLOAT
        PrimitiveClassType.FLOAT   | ObjectType.FLOAT
        PrimitiveClassType.FLOAT   | DOUBLE
        // Double
        PrimitiveClassType.DOUBLE  | BYTE
        PrimitiveClassType.DOUBLE  | ObjectType.BYTE
        PrimitiveClassType.DOUBLE  | SHORT
        PrimitiveClassType.DOUBLE  | ObjectType.SHORT
        PrimitiveClassType.DOUBLE  | CHAR
        PrimitiveClassType.DOUBLE  | ObjectType.CHARACTER
        PrimitiveClassType.DOUBLE  | NUMBER
        PrimitiveClassType.DOUBLE  | ObjectType.INTEGER
        PrimitiveClassType.DOUBLE  | LONG
        PrimitiveClassType.DOUBLE  | ObjectType.LONG
        PrimitiveClassType.DOUBLE  | FLOAT
        PrimitiveClassType.DOUBLE  | ObjectType.FLOAT
        PrimitiveClassType.DOUBLE  | DOUBLE
        PrimitiveClassType.DOUBLE  | ObjectType.DOUBLE
        // Boolean
        PrimitiveClassType.BOOLEAN | BOOLEAN
        PrimitiveClassType.BOOLEAN | ObjectType.BOOLEAN
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
        PrimitiveClassType.BYTE    | ObjectType.SHORT
        PrimitiveClassType.BYTE    | ObjectType.CHARACTER
        PrimitiveClassType.BYTE    | ObjectType.INTEGER
        PrimitiveClassType.BYTE    | ObjectType.LONG
        PrimitiveClassType.BYTE    | ObjectType.FLOAT
        PrimitiveClassType.BYTE    | ObjectType.DOUBLE
        PrimitiveClassType.BYTE    | BOOLEAN
        PrimitiveClassType.BYTE    | ObjectType.BOOLEAN
        PrimitiveClassType.BYTE    | ObjectType.STRING
        PrimitiveClassType.BYTE    | ObjectType.OBJECT
        // Short
        PrimitiveClassType.SHORT   | ObjectType.CHARACTER
        PrimitiveClassType.SHORT   | ObjectType.INTEGER
        PrimitiveClassType.SHORT   | ObjectType.LONG
        PrimitiveClassType.SHORT   | ObjectType.FLOAT
        PrimitiveClassType.SHORT   | ObjectType.DOUBLE
        PrimitiveClassType.SHORT   | BOOLEAN
        PrimitiveClassType.SHORT   | ObjectType.BOOLEAN
        PrimitiveClassType.SHORT   | ObjectType.STRING
        PrimitiveClassType.SHORT   | ObjectType.OBJECT
        // Character
        PrimitiveClassType.CHAR    | ObjectType.BYTE
        PrimitiveClassType.CHAR    | ObjectType.SHORT
        PrimitiveClassType.CHAR    | ObjectType.INTEGER
        PrimitiveClassType.CHAR    | ObjectType.LONG
        PrimitiveClassType.CHAR    | ObjectType.FLOAT
        PrimitiveClassType.CHAR    | ObjectType.DOUBLE
        PrimitiveClassType.CHAR    | BOOLEAN
        PrimitiveClassType.CHAR    | ObjectType.BOOLEAN
        PrimitiveClassType.CHAR    | ObjectType.STRING
        PrimitiveClassType.CHAR    | ObjectType.OBJECT
        // Integer
        PrimitiveClassType.INT     | ObjectType.LONG
        PrimitiveClassType.INT     | ObjectType.FLOAT
        PrimitiveClassType.INT     | ObjectType.DOUBLE
        PrimitiveClassType.INT     | BOOLEAN
        PrimitiveClassType.INT     | ObjectType.BOOLEAN
        PrimitiveClassType.INT     | ObjectType.STRING
        PrimitiveClassType.INT     | ObjectType.OBJECT
        // Long
        PrimitiveClassType.LONG    | ObjectType.FLOAT
        PrimitiveClassType.LONG    | ObjectType.DOUBLE
        PrimitiveClassType.LONG    | BOOLEAN
        PrimitiveClassType.LONG    | ObjectType.BOOLEAN
        PrimitiveClassType.LONG    | ObjectType.STRING
        PrimitiveClassType.LONG    | ObjectType.OBJECT
        // Float
        PrimitiveClassType.FLOAT   | ObjectType.DOUBLE
        PrimitiveClassType.FLOAT   | BOOLEAN
        PrimitiveClassType.FLOAT   | ObjectType.BOOLEAN
        PrimitiveClassType.FLOAT   | ObjectType.STRING
        PrimitiveClassType.FLOAT   | ObjectType.OBJECT
        // Double
        PrimitiveClassType.DOUBLE  | BOOLEAN
        PrimitiveClassType.DOUBLE  | ObjectType.BOOLEAN
        PrimitiveClassType.DOUBLE  | ObjectType.STRING
        PrimitiveClassType.DOUBLE  | ObjectType.OBJECT
        // Boolean
        PrimitiveClassType.BOOLEAN | BYTE
        PrimitiveClassType.BOOLEAN | ObjectType.BYTE
        PrimitiveClassType.BOOLEAN | SHORT
        PrimitiveClassType.BOOLEAN | ObjectType.SHORT
        PrimitiveClassType.BOOLEAN | CHAR
        PrimitiveClassType.BOOLEAN | ObjectType.CHARACTER
        PrimitiveClassType.BOOLEAN | NUMBER
        PrimitiveClassType.BOOLEAN | ObjectType.INTEGER
        PrimitiveClassType.BOOLEAN | LONG
        PrimitiveClassType.BOOLEAN | ObjectType.LONG
        PrimitiveClassType.BOOLEAN | FLOAT
        PrimitiveClassType.BOOLEAN | ObjectType.FLOAT
        PrimitiveClassType.BOOLEAN | DOUBLE
        PrimitiveClassType.BOOLEAN | ObjectType.DOUBLE
        PrimitiveClassType.BOOLEAN | ObjectType.STRING
        PrimitiveClassType.BOOLEAN | ObjectType.OBJECT
    }

    def 'test #type toType should return #expected'() {
        given:
        def actual = type.toType()

        expect:
        actual == expected

        where:
        type                  | expected
        PrimitiveClassType.BYTE    | BYTE
        PrimitiveClassType.CHAR    | CHAR
        PrimitiveClassType.SHORT   | SHORT
        PrimitiveClassType.INT     | NUMBER
        PrimitiveClassType.LONG    | LONG
        PrimitiveClassType.FLOAT   | FLOAT
        PrimitiveClassType.DOUBLE  | DOUBLE
        PrimitiveClassType.BOOLEAN | BOOLEAN
    }

    def 'test check invalid #types'() {
        when:
        DOUBLE.check(types)

        then:
        def e = thrown(TypeCheckerException)
        e.getMessage() == TypeCheckerException.invalidType(ObjectType.STRING, DOUBLE).message

        where:
        types << [
                ObjectType.STRING,
                new Type[]{ObjectType.STRING, BOOLEAN, FLOAT}
        ]
    }

    def 'test check empty'() {
        when:
        DOUBLE.check()

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == "Cannot compare type ${DOUBLE} with no types"
    }

    def 'test #classType should be compatible with #type'() {
        expect:
        classType.compatibleWith(type)

        where:
        classType                   | type
        // byte
        PrimitiveClassType.BYTE     | BYTE
        PrimitiveClassType.BYTE     | ObjectType.BYTE
        PrimitiveClassType.BYTE     | NUMBER
        // short
        PrimitiveClassType.SHORT    | BYTE
        PrimitiveClassType.SHORT    | ObjectType.BYTE
        PrimitiveClassType.SHORT    | SHORT
        PrimitiveClassType.SHORT    | ObjectType.SHORT
        PrimitiveClassType.SHORT    | NUMBER
        // char
        PrimitiveClassType.CHAR     | CHAR
        PrimitiveClassType.CHAR     | ObjectType.CHARACTER
        PrimitiveClassType.CHAR     | NUMBER
        // int
        PrimitiveClassType.INT      | BYTE
        PrimitiveClassType.INT      | ObjectType.BYTE
        PrimitiveClassType.INT      | SHORT
        PrimitiveClassType.INT      | ObjectType.SHORT
        PrimitiveClassType.INT      | CHAR
        PrimitiveClassType.INT      | ObjectType.CHARACTER
        PrimitiveClassType.INT      | NUMBER
        PrimitiveClassType.INT      | ObjectType.INTEGER
        // long
        PrimitiveClassType.LONG     | BYTE
        PrimitiveClassType.LONG     | ObjectType.BYTE
        PrimitiveClassType.LONG     | SHORT
        PrimitiveClassType.LONG     | ObjectType.SHORT
        PrimitiveClassType.LONG     | CHAR
        PrimitiveClassType.LONG     | ObjectType.CHARACTER
        PrimitiveClassType.LONG     | NUMBER
        PrimitiveClassType.LONG     | ObjectType.INTEGER
        PrimitiveClassType.LONG     | LONG
        PrimitiveClassType.LONG     | ObjectType.LONG
        // float
        PrimitiveClassType.FLOAT    | BYTE
        PrimitiveClassType.FLOAT    | ObjectType.BYTE
        PrimitiveClassType.FLOAT    | SHORT
        PrimitiveClassType.FLOAT    | ObjectType.SHORT
        PrimitiveClassType.FLOAT    | CHAR
        PrimitiveClassType.FLOAT    | ObjectType.CHARACTER
        PrimitiveClassType.FLOAT    | NUMBER
        PrimitiveClassType.FLOAT    | ObjectType.INTEGER
        PrimitiveClassType.FLOAT    | LONG
        PrimitiveClassType.FLOAT    | ObjectType.LONG
        PrimitiveClassType.FLOAT    | FLOAT
        PrimitiveClassType.FLOAT    | ObjectType.FLOAT
        // double
        PrimitiveClassType.DOUBLE   | BYTE
        PrimitiveClassType.DOUBLE   | ObjectType.BYTE
        PrimitiveClassType.DOUBLE   | SHORT
        PrimitiveClassType.DOUBLE   | ObjectType.SHORT
        PrimitiveClassType.DOUBLE   | CHAR
        PrimitiveClassType.DOUBLE   | ObjectType.CHARACTER
        PrimitiveClassType.DOUBLE   | NUMBER
        PrimitiveClassType.DOUBLE   | ObjectType.INTEGER
        PrimitiveClassType.DOUBLE   | LONG
        PrimitiveClassType.DOUBLE   | ObjectType.LONG
        PrimitiveClassType.DOUBLE   | FLOAT
        PrimitiveClassType.DOUBLE   | ObjectType.FLOAT
        PrimitiveClassType.DOUBLE   | DOUBLE
        PrimitiveClassType.DOUBLE   | ObjectType.DOUBLE
        // boolean
        PrimitiveClassType.BOOLEAN  | BOOLEAN
        PrimitiveClassType.BOOLEAN  | ObjectType.BOOLEAN
    }

    def 'test #classType should not be compatible with #type'() {
        expect:
        !classType.compatibleWith(type)

        where:
        classType                   | type
        // byte
        PrimitiveClassType.BYTE     | SHORT
        PrimitiveClassType.BYTE     | ObjectType.SHORT
        PrimitiveClassType.BYTE     | CHAR
        PrimitiveClassType.BYTE     | ObjectType.CHARACTER
        PrimitiveClassType.BYTE     | ObjectType.INTEGER
        PrimitiveClassType.BYTE     | LONG
        PrimitiveClassType.BYTE     | ObjectType.LONG
        PrimitiveClassType.BYTE     | FLOAT
        PrimitiveClassType.BYTE     | ObjectType.FLOAT
        PrimitiveClassType.BYTE     | DOUBLE
        PrimitiveClassType.BYTE     | ObjectType.DOUBLE
        PrimitiveClassType.BYTE     | BOOLEAN
        PrimitiveClassType.BYTE     | ObjectType.BOOLEAN
        // short
        PrimitiveClassType.SHORT    | CHAR
        PrimitiveClassType.SHORT    | ObjectType.CHARACTER
        PrimitiveClassType.SHORT    | ObjectType.INTEGER
        PrimitiveClassType.SHORT    | LONG
        PrimitiveClassType.SHORT    | ObjectType.LONG
        PrimitiveClassType.SHORT    | FLOAT
        PrimitiveClassType.SHORT    | ObjectType.FLOAT
        PrimitiveClassType.SHORT    | DOUBLE
        PrimitiveClassType.SHORT    | ObjectType.DOUBLE
        PrimitiveClassType.SHORT    | BOOLEAN
        PrimitiveClassType.SHORT    | ObjectType.BOOLEAN
        // char
        PrimitiveClassType.CHAR     | BYTE
        PrimitiveClassType.CHAR     | ObjectType.BYTE
        PrimitiveClassType.CHAR     | SHORT
        PrimitiveClassType.CHAR     | ObjectType.SHORT
        PrimitiveClassType.CHAR     | ObjectType.INTEGER
        PrimitiveClassType.CHAR     | LONG
        PrimitiveClassType.CHAR     | ObjectType.LONG
        PrimitiveClassType.CHAR     | FLOAT
        PrimitiveClassType.CHAR     | ObjectType.FLOAT
        PrimitiveClassType.CHAR     | DOUBLE
        PrimitiveClassType.CHAR     | ObjectType.DOUBLE
        PrimitiveClassType.CHAR     | BOOLEAN
        PrimitiveClassType.CHAR     | ObjectType.BOOLEAN
        // int
        PrimitiveClassType.INT      | LONG
        PrimitiveClassType.INT      | ObjectType.LONG
        PrimitiveClassType.INT      | FLOAT
        PrimitiveClassType.INT      | ObjectType.FLOAT
        PrimitiveClassType.INT      | DOUBLE
        PrimitiveClassType.INT      | ObjectType.DOUBLE
        PrimitiveClassType.INT      | BOOLEAN
        PrimitiveClassType.INT      | ObjectType.BOOLEAN
        // long
        PrimitiveClassType.LONG     | FLOAT
        PrimitiveClassType.LONG     | ObjectType.FLOAT
        PrimitiveClassType.LONG     | DOUBLE
        PrimitiveClassType.LONG     | ObjectType.DOUBLE
        PrimitiveClassType.LONG     | BOOLEAN
        PrimitiveClassType.LONG     | ObjectType.BOOLEAN
        // float
        PrimitiveClassType.FLOAT    | DOUBLE
        PrimitiveClassType.FLOAT    | ObjectType.DOUBLE
        PrimitiveClassType.FLOAT    | BOOLEAN
        PrimitiveClassType.FLOAT    | ObjectType.BOOLEAN
        // double
        PrimitiveClassType.DOUBLE   | BOOLEAN
        PrimitiveClassType.DOUBLE   | ObjectType.BOOLEAN
        // boolean
        PrimitiveClassType.BOOLEAN  | BYTE
        PrimitiveClassType.BOOLEAN  | ObjectType.BYTE
        PrimitiveClassType.BOOLEAN  | SHORT
        PrimitiveClassType.BOOLEAN  | ObjectType.SHORT
        PrimitiveClassType.BOOLEAN  | CHAR
        PrimitiveClassType.BOOLEAN  | ObjectType.CHARACTER
        PrimitiveClassType.BOOLEAN  | NUMBER
        PrimitiveClassType.BOOLEAN  | ObjectType.INTEGER
        PrimitiveClassType.BOOLEAN  | LONG
        PrimitiveClassType.BOOLEAN  | ObjectType.LONG
        PrimitiveClassType.BOOLEAN  | FLOAT
        PrimitiveClassType.BOOLEAN  | ObjectType.FLOAT
        PrimitiveClassType.BOOLEAN  | DOUBLE
        PrimitiveClassType.BOOLEAN  | ObjectType.DOUBLE
    }

    def 'test toString of #type'() {
        given:
        def string = type.toString()

        expect:
        string == ClassType.print(type.name().toLowerCase())

        where:
        type << PrimitiveClassType.values()
    }

}
