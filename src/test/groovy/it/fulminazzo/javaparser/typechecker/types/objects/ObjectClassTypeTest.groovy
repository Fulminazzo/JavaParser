package it.fulminazzo.javaparser.typechecker.types.objects

import it.fulminazzo.fulmicollection.utils.StringUtils
import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.ClassType
import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.PrimitiveType.*

class ObjectClassTypeTest extends Specification {

    def 'test cast of #cast to #type should return #cast'() {
        when:
        def actual = cast.cast(type)

        then:
        actual == cast.toType()

        where:
        cast                      | type
        // Byte
        ObjectClassType.BYTE      | BYTE
        ObjectClassType.BYTE      | ObjectType.BYTE
        // Short
        ObjectClassType.SHORT     | SHORT
        ObjectClassType.SHORT     | ObjectType.SHORT
        // Character
        ObjectClassType.CHARACTER | CHAR
        ObjectClassType.CHARACTER | ObjectType.CHARACTER
        // Integer
        ObjectClassType.INTEGER   | NUMBER
        ObjectClassType.INTEGER   | ObjectType.INTEGER
        // Long
        ObjectClassType.LONG      | LONG
        ObjectClassType.LONG      | ObjectType.LONG
        // Float
        ObjectClassType.FLOAT     | FLOAT
        ObjectClassType.FLOAT     | ObjectType.FLOAT
        // Double
        ObjectClassType.DOUBLE    | DOUBLE
        ObjectClassType.DOUBLE    | ObjectType.DOUBLE
        // Boolean
        ObjectClassType.BOOLEAN   | BOOLEAN
        ObjectClassType.BOOLEAN   | ObjectType.BOOLEAN
        // String
        ObjectClassType.STRING    | ObjectType.STRING
        // Object
        ObjectClassType.OBJECT    | NUMBER
        ObjectClassType.OBJECT    | ObjectType.BYTE
        ObjectClassType.OBJECT    | ObjectType.SHORT
        ObjectClassType.OBJECT    | ObjectType.INTEGER
        ObjectClassType.OBJECT    | CHAR
        ObjectClassType.OBJECT    | ObjectType.CHARACTER
        ObjectClassType.OBJECT    | LONG
        ObjectClassType.OBJECT    | ObjectType.LONG
        ObjectClassType.OBJECT    | FLOAT
        ObjectClassType.OBJECT    | ObjectType.FLOAT
        ObjectClassType.OBJECT    | DOUBLE
        ObjectClassType.OBJECT    | ObjectType.DOUBLE
        ObjectClassType.OBJECT    | BOOLEAN
        ObjectClassType.OBJECT    | ObjectType.BOOLEAN
        ObjectClassType.OBJECT    | ObjectType.STRING
        ObjectClassType.OBJECT    | ObjectType.OBJECT
    }

    def 'test invalid cast of #cast to #type'() {
        when:
        cast.cast(type)

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidCast(cast, type).message

        where:
        cast                      | type
        // Byte
        ObjectClassType.BYTE      | SHORT
        ObjectClassType.BYTE      | ObjectType.SHORT
        ObjectClassType.BYTE      | CHAR
        ObjectClassType.BYTE      | ObjectType.CHARACTER
        ObjectClassType.BYTE      | NUMBER
        ObjectClassType.BYTE      | ObjectType.INTEGER
        ObjectClassType.BYTE      | LONG
        ObjectClassType.BYTE      | ObjectType.LONG
        ObjectClassType.BYTE      | FLOAT
        ObjectClassType.BYTE      | ObjectType.FLOAT
        ObjectClassType.BYTE      | DOUBLE
        ObjectClassType.BYTE      | ObjectType.DOUBLE
        ObjectClassType.BYTE      | BOOLEAN
        ObjectClassType.BYTE      | ObjectType.BOOLEAN
        ObjectClassType.BYTE      | ObjectType.STRING
        ObjectClassType.BYTE      | ObjectType.OBJECT
        // Short
        ObjectClassType.SHORT     | BYTE
        ObjectClassType.SHORT     | ObjectType.BYTE
        ObjectClassType.SHORT     | CHAR
        ObjectClassType.SHORT     | ObjectType.CHARACTER
        ObjectClassType.SHORT     | NUMBER
        ObjectClassType.SHORT     | ObjectType.INTEGER
        ObjectClassType.SHORT     | LONG
        ObjectClassType.SHORT     | ObjectType.LONG
        ObjectClassType.SHORT     | FLOAT
        ObjectClassType.SHORT     | ObjectType.FLOAT
        ObjectClassType.SHORT     | DOUBLE
        ObjectClassType.SHORT     | ObjectType.DOUBLE
        ObjectClassType.SHORT     | BOOLEAN
        ObjectClassType.SHORT     | ObjectType.BOOLEAN
        ObjectClassType.SHORT     | ObjectType.STRING
        ObjectClassType.SHORT     | ObjectType.OBJECT
        // Character
        ObjectClassType.CHARACTER | BYTE
        ObjectClassType.CHARACTER | ObjectType.BYTE
        ObjectClassType.CHARACTER | SHORT
        ObjectClassType.CHARACTER | ObjectType.SHORT
        ObjectClassType.CHARACTER | NUMBER
        ObjectClassType.CHARACTER | ObjectType.INTEGER
        ObjectClassType.CHARACTER | LONG
        ObjectClassType.CHARACTER | ObjectType.LONG
        ObjectClassType.CHARACTER | FLOAT
        ObjectClassType.CHARACTER | ObjectType.FLOAT
        ObjectClassType.CHARACTER | DOUBLE
        ObjectClassType.CHARACTER | ObjectType.DOUBLE
        ObjectClassType.CHARACTER | BOOLEAN
        ObjectClassType.CHARACTER | ObjectType.BOOLEAN
        ObjectClassType.CHARACTER | ObjectType.STRING
        ObjectClassType.CHARACTER | ObjectType.OBJECT
        // Integer
        ObjectClassType.INTEGER   | BYTE
        ObjectClassType.INTEGER   | ObjectType.BYTE
        ObjectClassType.INTEGER   | SHORT
        ObjectClassType.INTEGER   | ObjectType.SHORT
        ObjectClassType.INTEGER   | CHAR
        ObjectClassType.INTEGER   | ObjectType.CHARACTER
        ObjectClassType.INTEGER   | LONG
        ObjectClassType.INTEGER   | ObjectType.LONG
        ObjectClassType.INTEGER   | FLOAT
        ObjectClassType.INTEGER   | ObjectType.FLOAT
        ObjectClassType.INTEGER   | DOUBLE
        ObjectClassType.INTEGER   | ObjectType.DOUBLE
        ObjectClassType.INTEGER   | BOOLEAN
        ObjectClassType.INTEGER   | ObjectType.BOOLEAN
        ObjectClassType.INTEGER   | ObjectType.STRING
        ObjectClassType.INTEGER   | ObjectType.OBJECT
        // Long
        ObjectClassType.LONG      | BYTE
        ObjectClassType.LONG      | ObjectType.BYTE
        ObjectClassType.LONG      | SHORT
        ObjectClassType.LONG      | ObjectType.SHORT
        ObjectClassType.LONG      | CHAR
        ObjectClassType.LONG      | ObjectType.CHARACTER
        ObjectClassType.LONG      | NUMBER
        ObjectClassType.LONG      | ObjectType.INTEGER
        ObjectClassType.LONG      | FLOAT
        ObjectClassType.LONG      | ObjectType.FLOAT
        ObjectClassType.LONG      | DOUBLE
        ObjectClassType.LONG      | ObjectType.DOUBLE
        ObjectClassType.LONG      | BOOLEAN
        ObjectClassType.LONG      | ObjectType.BOOLEAN
        ObjectClassType.LONG      | ObjectType.STRING
        ObjectClassType.LONG      | ObjectType.OBJECT
        // Float
        ObjectClassType.FLOAT     | BYTE
        ObjectClassType.FLOAT     | ObjectType.BYTE
        ObjectClassType.FLOAT     | SHORT
        ObjectClassType.FLOAT     | ObjectType.SHORT
        ObjectClassType.FLOAT     | CHAR
        ObjectClassType.FLOAT     | ObjectType.CHARACTER
        ObjectClassType.FLOAT     | NUMBER
        ObjectClassType.FLOAT     | ObjectType.INTEGER
        ObjectClassType.FLOAT     | LONG
        ObjectClassType.FLOAT     | ObjectType.LONG
        ObjectClassType.FLOAT     | DOUBLE
        ObjectClassType.FLOAT     | ObjectType.DOUBLE
        ObjectClassType.FLOAT     | BOOLEAN
        ObjectClassType.FLOAT     | ObjectType.BOOLEAN
        ObjectClassType.FLOAT     | ObjectType.STRING
        ObjectClassType.FLOAT     | ObjectType.OBJECT
        // Double
        ObjectClassType.DOUBLE    | BYTE
        ObjectClassType.DOUBLE    | ObjectType.BYTE
        ObjectClassType.DOUBLE    | SHORT
        ObjectClassType.DOUBLE    | ObjectType.SHORT
        ObjectClassType.DOUBLE    | CHAR
        ObjectClassType.DOUBLE    | ObjectType.CHARACTER
        ObjectClassType.DOUBLE    | NUMBER
        ObjectClassType.DOUBLE    | ObjectType.INTEGER
        ObjectClassType.DOUBLE    | LONG
        ObjectClassType.DOUBLE    | ObjectType.LONG
        ObjectClassType.DOUBLE    | FLOAT
        ObjectClassType.DOUBLE    | ObjectType.FLOAT
        ObjectClassType.DOUBLE    | BOOLEAN
        ObjectClassType.DOUBLE    | ObjectType.BOOLEAN
        ObjectClassType.DOUBLE    | ObjectType.STRING
        ObjectClassType.DOUBLE    | ObjectType.OBJECT
        // Boolean
        ObjectClassType.BOOLEAN   | BYTE
        ObjectClassType.BOOLEAN   | ObjectType.BYTE
        ObjectClassType.BOOLEAN   | SHORT
        ObjectClassType.BOOLEAN   | ObjectType.SHORT
        ObjectClassType.BOOLEAN   | CHAR
        ObjectClassType.BOOLEAN   | ObjectType.CHARACTER
        ObjectClassType.BOOLEAN   | NUMBER
        ObjectClassType.BOOLEAN   | ObjectType.INTEGER
        ObjectClassType.BOOLEAN   | LONG
        ObjectClassType.BOOLEAN   | ObjectType.LONG
        ObjectClassType.BOOLEAN   | FLOAT
        ObjectClassType.BOOLEAN   | ObjectType.FLOAT
        ObjectClassType.BOOLEAN   | DOUBLE
        ObjectClassType.BOOLEAN   | ObjectType.DOUBLE
        ObjectClassType.BOOLEAN   | ObjectType.STRING
        ObjectClassType.BOOLEAN   | ObjectType.OBJECT
        // String
        ObjectClassType.STRING    | BYTE
        ObjectClassType.STRING    | ObjectType.BYTE
        ObjectClassType.STRING    | SHORT
        ObjectClassType.STRING    | ObjectType.SHORT
        ObjectClassType.STRING    | CHAR
        ObjectClassType.STRING    | ObjectType.CHARACTER
        ObjectClassType.STRING    | NUMBER
        ObjectClassType.STRING    | ObjectType.INTEGER
        ObjectClassType.STRING    | LONG
        ObjectClassType.STRING    | ObjectType.LONG
        ObjectClassType.STRING    | FLOAT
        ObjectClassType.STRING    | ObjectType.FLOAT
        ObjectClassType.STRING    | DOUBLE
        ObjectClassType.STRING    | ObjectType.DOUBLE
        ObjectClassType.STRING    | BOOLEAN
        ObjectClassType.STRING    | ObjectType.BOOLEAN
        ObjectClassType.STRING    | ObjectType.OBJECT
    }

    def 'test #type toType should return #expected'() {
        given:
        def actual = type.toType()

        expect:
        actual == expected

        where:
        type                      | expected
        ObjectClassType.BYTE      | ObjectType.BYTE
        ObjectClassType.CHARACTER | ObjectType.CHARACTER
        ObjectClassType.SHORT     | ObjectType.SHORT
        ObjectClassType.INTEGER   | ObjectType.INTEGER
        ObjectClassType.LONG      | ObjectType.LONG
        ObjectClassType.FLOAT     | ObjectType.FLOAT
        ObjectClassType.DOUBLE    | ObjectType.DOUBLE
        ObjectClassType.BOOLEAN   | ObjectType.BOOLEAN
        ObjectClassType.STRING    | ObjectType.STRING
        ObjectClassType.OBJECT    | ObjectType.OBJECT
    }

    def 'test #type toJavaClass should return #clazz'() {
        given:
        def actual = type.toJavaClass()

        expect:
        actual == clazz

        where:
        type                      | clazz
        ObjectClassType.BYTE      | Byte.class
        ObjectClassType.CHARACTER | Character.class
        ObjectClassType.SHORT     | Short.class
        ObjectClassType.INTEGER   | Integer.class
        ObjectClassType.LONG      | Long.class
        ObjectClassType.FLOAT     | Float.class
        ObjectClassType.DOUBLE    | Double.class
        ObjectClassType.BOOLEAN   | Boolean.class
        ObjectClassType.STRING    | String.class
        ObjectClassType.OBJECT    | Object.class
    }

    def 'test toString of #type'() {
        given:
        def string = type.toString()

        expect:
        string == ClassType.print(StringUtils.capitalize(type.name()))

        where:
        type << ObjectClassType.values()
    }

    def 'test BYTE compatible with #type'() {
        expect:
        ObjectClassType.BYTE.compatibleWith(type)

        where:
        type << [
                BYTE,
                ObjectType.BYTE
        ]
    }

    def 'test BYTE incompatible with #type'() {
        expect:
        !ObjectClassType.BYTE.compatibleWith(type)

        where:
        type << [
                CHAR,
                SHORT,
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN,
                ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test CHAR compatible with #type'() {
        expect:
        ObjectClassType.CHARACTER.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
                ObjectType.CHARACTER
        ]
    }

    def 'test CHAR incompatible with #type'() {
        expect:
        !ObjectClassType.CHARACTER.compatibleWith(type)

        where:
        type << [
                BYTE, SHORT, LONG,
                DOUBLE, FLOAT,
                BOOLEAN,
                ObjectType.BYTE,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test SHORT compatible with #type'() {
        expect:
        ObjectClassType.SHORT.compatibleWith(type)

        where:
        type << [
                BYTE, SHORT,
                ObjectType.BYTE, ObjectType.SHORT
        ]
    }

    def 'test SHORT incompatible with #type'() {
        expect:
        !ObjectClassType.SHORT.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN,
                ObjectType.CHARACTER, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test INT compatible with #type'() {
        expect:
        ObjectClassType.INTEGER.compatibleWith(type)

        where:
        type << [
                BYTE, SHORT,
                CHAR, NUMBER,
                ObjectType.BYTE, ObjectType.SHORT,
                ObjectType.CHARACTER, ObjectType.INTEGER
        ]
    }

    def 'test INT incompatible with #type'() {
        expect:
        !ObjectClassType.INTEGER.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test LONG compatible with #type'() {
        expect:
        ObjectClassType.LONG.compatibleWith(type)

        where:
        type << [
                BYTE, SHORT,
                CHAR, NUMBER, LONG,
                ObjectType.BYTE, ObjectType.SHORT,
                ObjectType.CHARACTER, ObjectType.INTEGER,
                ObjectType.LONG
        ]
    }

    def 'test LONG incompatible with #type'() {
        expect:
        !ObjectClassType.LONG.compatibleWith(type)

        where:
        type << [
                DOUBLE, FLOAT,
                BOOLEAN,
                ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test FLOAT compatible with #type'() {
        expect:
        ObjectClassType.FLOAT.compatibleWith(type)

        where:
        type << [
                BYTE, SHORT,
                CHAR, NUMBER, LONG,
                FLOAT,
                ObjectType.BYTE, ObjectType.SHORT,
                ObjectType.CHARACTER, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT
        ]
    }

    def 'test FLOAT incompatible with #type'() {
        expect:
        !ObjectClassType.FLOAT.compatibleWith(type)

        where:
        type << [
                DOUBLE,
                BOOLEAN,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING
        ]
    }

    def 'test DOUBLE compatible with #type'() {
        expect:
        ObjectClassType.DOUBLE.compatibleWith(type)

        where:
        type << [
                BYTE, SHORT,
                CHAR, NUMBER, LONG,
                FLOAT, DOUBLE,
                ObjectType.BYTE, ObjectType.SHORT,
                ObjectType.CHARACTER, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE
        ]
    }

    def 'test DOUBLE incompatible with #type'() {
        expect:
        !ObjectClassType.DOUBLE.compatibleWith(type)

        where:
        type << [
                BOOLEAN,
                ObjectType.BOOLEAN, ObjectType.STRING
        ]
    }

    def 'test BOOLEAN compatible with #type'() {
        expect:
        ObjectClassType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                BOOLEAN, ObjectType.BOOLEAN
        ]
    }

    def 'test BOOLEAN incompatible with #type'() {
        expect:
        !ObjectClassType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                BYTE, SHORT,
                CHAR, NUMBER, LONG,
                DOUBLE, FLOAT,
               
                ObjectType.BYTE, ObjectType.SHORT,
                ObjectType.CHARACTER, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.STRING
        ]
    }

    def 'test STRING compatible with #type'() {
        expect:
        ObjectClassType.STRING.compatibleWith(type)

        where:
        type << [
                ObjectType.STRING
        ]
    }

    def 'test STRING incompatible with #type'() {
        expect:
        !ObjectClassType.STRING.compatibleWith(type)

        where:
        type << [
                BYTE, SHORT,
                CHAR, NUMBER, LONG,
                DOUBLE, FLOAT,
                BOOLEAN,
                ObjectType.BYTE, ObjectType.SHORT,
                ObjectType.CHARACTER, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN
        ]
    }

    def 'test OBJECT compatible with #type'() {
        expect:
        ObjectClassType.OBJECT.compatibleWith(type)

        where:
        type << [
                BYTE, SHORT, LONG,
                DOUBLE, FLOAT,
                BOOLEAN,
                ObjectType.BYTE, ObjectType.SHORT,
                ObjectType.CHARACTER, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING, ObjectType.OBJECT
        ]
    }
    
}
