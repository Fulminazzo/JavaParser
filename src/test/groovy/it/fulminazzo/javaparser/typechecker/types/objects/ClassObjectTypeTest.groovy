package it.fulminazzo.javaparser.typechecker.types.objects

import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.ValueType.*

class ClassObjectTypeTest extends Specification {

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
    
}
