package it.fulminazzo.javaparser.typechecker.types.objects


import it.fulminazzo.fulmicollection.utils.StringUtils
import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.ClassType
import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.ValueType.*

class ClassObjectTypeTest extends Specification {

    def 'test cast of #cast to #type should return #cast'() {
        when:
        def actual = cast.cast(type)

        then:
        actual == cast.toType()

        where:
        cast                      | type
        // Byte
        ClassObjectType.BYTE      | BYTE
        ClassObjectType.BYTE      | ObjectType.BYTE
        // Short
        ClassObjectType.SHORT     | SHORT
        ClassObjectType.SHORT     | ObjectType.SHORT
        // Character
        ClassObjectType.CHARACTER | CHAR
        ClassObjectType.CHARACTER | ObjectType.CHARACTER
        // Integer
        ClassObjectType.INTEGER   | NUMBER
        ClassObjectType.INTEGER   | ObjectType.INTEGER
        // Long
        ClassObjectType.LONG      | LONG
        ClassObjectType.LONG      | ObjectType.LONG
        // Float
        ClassObjectType.FLOAT     | FLOAT
        ClassObjectType.FLOAT     | ObjectType.FLOAT
        // Double
        ClassObjectType.DOUBLE    | DOUBLE
        ClassObjectType.DOUBLE    | ObjectType.DOUBLE
        // Boolean
        ClassObjectType.BOOLEAN   | BOOLEAN
        ClassObjectType.BOOLEAN   | ObjectType.BOOLEAN
        // String
        ClassObjectType.STRING    | STRING
        ClassObjectType.STRING    | ObjectType.STRING
        // Object
        ClassObjectType.OBJECT    | NUMBER
        ClassObjectType.OBJECT    | ObjectType.BYTE
        ClassObjectType.OBJECT    | ObjectType.SHORT
        ClassObjectType.OBJECT    | ObjectType.INTEGER
        ClassObjectType.OBJECT    | CHAR
        ClassObjectType.OBJECT    | ObjectType.CHARACTER
        ClassObjectType.OBJECT    | LONG
        ClassObjectType.OBJECT    | ObjectType.LONG
        ClassObjectType.OBJECT    | FLOAT
        ClassObjectType.OBJECT    | ObjectType.FLOAT
        ClassObjectType.OBJECT    | DOUBLE
        ClassObjectType.OBJECT    | ObjectType.DOUBLE
        ClassObjectType.OBJECT    | BOOLEAN
        ClassObjectType.OBJECT    | ObjectType.BOOLEAN
        ClassObjectType.OBJECT    | STRING
        ClassObjectType.OBJECT    | ObjectType.STRING
        ClassObjectType.OBJECT    | ObjectType.OBJECT
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
        ClassObjectType.BYTE      | SHORT
        ClassObjectType.BYTE      | ObjectType.SHORT
        ClassObjectType.BYTE      | CHAR
        ClassObjectType.BYTE      | ObjectType.CHARACTER
        ClassObjectType.BYTE      | NUMBER
        ClassObjectType.BYTE      | ObjectType.INTEGER
        ClassObjectType.BYTE      | LONG
        ClassObjectType.BYTE      | ObjectType.LONG
        ClassObjectType.BYTE      | FLOAT
        ClassObjectType.BYTE      | ObjectType.FLOAT
        ClassObjectType.BYTE      | DOUBLE
        ClassObjectType.BYTE      | ObjectType.DOUBLE
        ClassObjectType.BYTE      | BOOLEAN
        ClassObjectType.BYTE      | ObjectType.BOOLEAN
        ClassObjectType.BYTE      | STRING
        ClassObjectType.BYTE      | ObjectType.STRING
        ClassObjectType.BYTE      | ObjectType.OBJECT
        // Short
        ClassObjectType.SHORT     | BYTE
        ClassObjectType.SHORT     | ObjectType.BYTE
        ClassObjectType.SHORT     | CHAR
        ClassObjectType.SHORT     | ObjectType.CHARACTER
        ClassObjectType.SHORT     | NUMBER
        ClassObjectType.SHORT     | ObjectType.INTEGER
        ClassObjectType.SHORT     | LONG
        ClassObjectType.SHORT     | ObjectType.LONG
        ClassObjectType.SHORT     | FLOAT
        ClassObjectType.SHORT     | ObjectType.FLOAT
        ClassObjectType.SHORT     | DOUBLE
        ClassObjectType.SHORT     | ObjectType.DOUBLE
        ClassObjectType.SHORT     | BOOLEAN
        ClassObjectType.SHORT     | ObjectType.BOOLEAN
        ClassObjectType.SHORT     | STRING
        ClassObjectType.SHORT     | ObjectType.STRING
        ClassObjectType.SHORT     | ObjectType.OBJECT
        // Character
        ClassObjectType.CHARACTER | BYTE
        ClassObjectType.CHARACTER | ObjectType.BYTE
        ClassObjectType.CHARACTER | SHORT
        ClassObjectType.CHARACTER | ObjectType.SHORT
        ClassObjectType.CHARACTER | NUMBER
        ClassObjectType.CHARACTER | ObjectType.INTEGER
        ClassObjectType.CHARACTER | LONG
        ClassObjectType.CHARACTER | ObjectType.LONG
        ClassObjectType.CHARACTER | FLOAT
        ClassObjectType.CHARACTER | ObjectType.FLOAT
        ClassObjectType.CHARACTER | DOUBLE
        ClassObjectType.CHARACTER | ObjectType.DOUBLE
        ClassObjectType.CHARACTER | BOOLEAN
        ClassObjectType.CHARACTER | ObjectType.BOOLEAN
        ClassObjectType.CHARACTER | STRING
        ClassObjectType.CHARACTER | ObjectType.STRING
        ClassObjectType.CHARACTER | ObjectType.OBJECT
        // Integer
        ClassObjectType.INTEGER   | BYTE
        ClassObjectType.INTEGER   | ObjectType.BYTE
        ClassObjectType.INTEGER   | SHORT
        ClassObjectType.INTEGER   | ObjectType.SHORT
        ClassObjectType.INTEGER   | CHAR
        ClassObjectType.INTEGER   | ObjectType.CHARACTER
        ClassObjectType.INTEGER   | LONG
        ClassObjectType.INTEGER   | ObjectType.LONG
        ClassObjectType.INTEGER   | FLOAT
        ClassObjectType.INTEGER   | ObjectType.FLOAT
        ClassObjectType.INTEGER   | DOUBLE
        ClassObjectType.INTEGER   | ObjectType.DOUBLE
        ClassObjectType.INTEGER   | BOOLEAN
        ClassObjectType.INTEGER   | ObjectType.BOOLEAN
        ClassObjectType.INTEGER   | STRING
        ClassObjectType.INTEGER   | ObjectType.STRING
        ClassObjectType.INTEGER   | ObjectType.OBJECT
        // Long
        ClassObjectType.LONG      | BYTE
        ClassObjectType.LONG      | ObjectType.BYTE
        ClassObjectType.LONG      | SHORT
        ClassObjectType.LONG      | ObjectType.SHORT
        ClassObjectType.LONG      | CHAR
        ClassObjectType.LONG      | ObjectType.CHARACTER
        ClassObjectType.LONG      | NUMBER
        ClassObjectType.LONG      | ObjectType.INTEGER
        ClassObjectType.LONG      | FLOAT
        ClassObjectType.LONG      | ObjectType.FLOAT
        ClassObjectType.LONG      | DOUBLE
        ClassObjectType.LONG      | ObjectType.DOUBLE
        ClassObjectType.LONG      | BOOLEAN
        ClassObjectType.LONG      | ObjectType.BOOLEAN
        ClassObjectType.LONG      | STRING
        ClassObjectType.LONG      | ObjectType.STRING
        ClassObjectType.LONG      | ObjectType.OBJECT
        // Float
        ClassObjectType.FLOAT     | BYTE
        ClassObjectType.FLOAT     | ObjectType.BYTE
        ClassObjectType.FLOAT     | SHORT
        ClassObjectType.FLOAT     | ObjectType.SHORT
        ClassObjectType.FLOAT     | CHAR
        ClassObjectType.FLOAT     | ObjectType.CHARACTER
        ClassObjectType.FLOAT     | NUMBER
        ClassObjectType.FLOAT     | ObjectType.INTEGER
        ClassObjectType.FLOAT     | LONG
        ClassObjectType.FLOAT     | ObjectType.LONG
        ClassObjectType.FLOAT     | DOUBLE
        ClassObjectType.FLOAT     | ObjectType.DOUBLE
        ClassObjectType.FLOAT     | BOOLEAN
        ClassObjectType.FLOAT     | ObjectType.BOOLEAN
        ClassObjectType.FLOAT     | STRING
        ClassObjectType.FLOAT     | ObjectType.STRING
        ClassObjectType.FLOAT     | ObjectType.OBJECT
        // Double
        ClassObjectType.DOUBLE    | BYTE
        ClassObjectType.DOUBLE    | ObjectType.BYTE
        ClassObjectType.DOUBLE    | SHORT
        ClassObjectType.DOUBLE    | ObjectType.SHORT
        ClassObjectType.DOUBLE    | CHAR
        ClassObjectType.DOUBLE    | ObjectType.CHARACTER
        ClassObjectType.DOUBLE    | NUMBER
        ClassObjectType.DOUBLE    | ObjectType.INTEGER
        ClassObjectType.DOUBLE    | LONG
        ClassObjectType.DOUBLE    | ObjectType.LONG
        ClassObjectType.DOUBLE    | FLOAT
        ClassObjectType.DOUBLE    | ObjectType.FLOAT
        ClassObjectType.DOUBLE    | BOOLEAN
        ClassObjectType.DOUBLE    | ObjectType.BOOLEAN
        ClassObjectType.DOUBLE    | STRING
        ClassObjectType.DOUBLE    | ObjectType.STRING
        ClassObjectType.DOUBLE    | ObjectType.OBJECT
        // Boolean
        ClassObjectType.BOOLEAN   | BYTE
        ClassObjectType.BOOLEAN   | ObjectType.BYTE
        ClassObjectType.BOOLEAN   | SHORT
        ClassObjectType.BOOLEAN   | ObjectType.SHORT
        ClassObjectType.BOOLEAN   | CHAR
        ClassObjectType.BOOLEAN   | ObjectType.CHARACTER
        ClassObjectType.BOOLEAN   | NUMBER
        ClassObjectType.BOOLEAN   | ObjectType.INTEGER
        ClassObjectType.BOOLEAN   | LONG
        ClassObjectType.BOOLEAN   | ObjectType.LONG
        ClassObjectType.BOOLEAN   | FLOAT
        ClassObjectType.BOOLEAN   | ObjectType.FLOAT
        ClassObjectType.BOOLEAN   | DOUBLE
        ClassObjectType.BOOLEAN   | ObjectType.DOUBLE
        ClassObjectType.BOOLEAN   | STRING
        ClassObjectType.BOOLEAN   | ObjectType.STRING
        ClassObjectType.BOOLEAN   | ObjectType.OBJECT
        // String
        ClassObjectType.STRING    | BYTE
        ClassObjectType.STRING    | ObjectType.BYTE
        ClassObjectType.STRING    | SHORT
        ClassObjectType.STRING    | ObjectType.SHORT
        ClassObjectType.STRING    | CHAR
        ClassObjectType.STRING    | ObjectType.CHARACTER
        ClassObjectType.STRING    | NUMBER
        ClassObjectType.STRING    | ObjectType.INTEGER
        ClassObjectType.STRING    | LONG
        ClassObjectType.STRING    | ObjectType.LONG
        ClassObjectType.STRING    | FLOAT
        ClassObjectType.STRING    | ObjectType.FLOAT
        ClassObjectType.STRING    | DOUBLE
        ClassObjectType.STRING    | ObjectType.DOUBLE
        ClassObjectType.STRING    | BOOLEAN
        ClassObjectType.STRING    | ObjectType.BOOLEAN
        ClassObjectType.STRING    | ObjectType.OBJECT
    }

    def 'test #type toType should return #expected'() {
        given:
        def actual = type.toType()

        expect:
        actual == expected

        where:
        type                      | expected
        ClassObjectType.BYTE      | ObjectType.BYTE
        ClassObjectType.CHARACTER | ObjectType.CHARACTER
        ClassObjectType.SHORT     | ObjectType.SHORT
        ClassObjectType.INTEGER   | ObjectType.INTEGER
        ClassObjectType.LONG      | ObjectType.LONG
        ClassObjectType.FLOAT     | ObjectType.FLOAT
        ClassObjectType.DOUBLE    | ObjectType.DOUBLE
        ClassObjectType.BOOLEAN   | ObjectType.BOOLEAN
        ClassObjectType.STRING    | ObjectType.STRING
        ClassObjectType.OBJECT    | ObjectType.OBJECT
    }

    def 'test #type toJavaClass should return #clazz'() {
        given:
        def actual = type.toJavaClass()

        expect:
        actual == clazz

        where:
        type                      | clazz
        ClassObjectType.BYTE      | Byte.class
        ClassObjectType.CHARACTER | Character.class
        ClassObjectType.SHORT     | Short.class
        ClassObjectType.INTEGER   | Integer.class
        ClassObjectType.LONG      | Long.class
        ClassObjectType.FLOAT     | Float.class
        ClassObjectType.DOUBLE    | Double.class
        ClassObjectType.BOOLEAN   | Boolean.class
        ClassObjectType.STRING    | String.class
        ClassObjectType.OBJECT    | Object.class
    }

    def 'test toString of #type should return #expected'() {
        given:
        def string = type.toString()

        expect:
        string == expected

        where:
        type << ClassObjectType.values()
        expected << ClassObjectType.values().collect {
            "ClassType(${StringUtils.capitalize(it.name())})"
        }
    }

    def 'test BYTE compatible with #type'() {
        expect:
        ClassObjectType.BYTE.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
                ObjectType.BYTE
        ]
    }

    def 'test BYTE incompatible with #type'() {
        expect:
        !ClassObjectType.BYTE.compatibleWith(type)

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
        ClassObjectType.CHARACTER.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
                ObjectType.CHARACTER
        ]
    }

    def 'test CHAR incompatible with #type'() {
        expect:
        !ClassObjectType.CHARACTER.compatibleWith(type)

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
        ClassObjectType.SHORT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
                ObjectType.BYTE, ObjectType.SHORT
        ]
    }

    def 'test SHORT incompatible with #type'() {
        expect:
        !ClassObjectType.SHORT.compatibleWith(type)

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
        ClassObjectType.INTEGER.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER
        ]
    }

    def 'test INT incompatible with #type'() {
        expect:
        !ClassObjectType.INTEGER.compatibleWith(type)

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
        ClassObjectType.LONG.compatibleWith(type)

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
        !ClassObjectType.LONG.compatibleWith(type)

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
        ClassObjectType.FLOAT.compatibleWith(type)

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
        !ClassObjectType.FLOAT.compatibleWith(type)

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
        ClassObjectType.DOUBLE.compatibleWith(type)

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
        !ClassObjectType.DOUBLE.compatibleWith(type)

        where:
        type << [
                BOOLEAN, STRING,
                ObjectType.BOOLEAN, ObjectType.STRING
        ]
    }

    def 'test BOOLEAN compatible with #type'() {
        expect:
        ClassObjectType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                BOOLEAN, ObjectType.BOOLEAN
        ]
    }

    def 'test BOOLEAN incompatible with #type'() {
        expect:
        !ClassObjectType.BOOLEAN.compatibleWith(type)

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

    def 'test STRING compatible with #type'() {
        expect:
        ClassObjectType.STRING.compatibleWith(type)

        where:
        type << [
                STRING, ObjectType.STRING
        ]
    }

    def 'test STRING incompatible with #type'() {
        expect:
        !ClassObjectType.STRING.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                DOUBLE, FLOAT,
                BOOLEAN,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN
        ]
    }

    def 'test OBJECT compatible with #type'() {
        expect:
        ClassObjectType.OBJECT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING,
                ObjectType.BYTE, ObjectType.CHARACTER,
                ObjectType.SHORT, ObjectType.INTEGER,
                ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN,
                ObjectType.STRING, ObjectType.OBJECT
        ]
    }

    def 'test toString of #type'() {
        given:
        def string = type.toString()

        expect:
        string == ClassType.print(StringUtils.capitalize(type.name()))

        where:
        type << ClassObjectType.values()
    }
    
}
