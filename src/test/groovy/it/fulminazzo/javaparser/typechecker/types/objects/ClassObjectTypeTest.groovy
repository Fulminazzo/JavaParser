package it.fulminazzo.javaparser.typechecker.types.objects

import it.fulminazzo.javaparser.typechecker.types.ValueType
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
        ClassObjectType.BYTE      | NUMBER
        ClassObjectType.CHARACTER | CHAR
        ClassObjectType.SHORT     | NUMBER
        ClassObjectType.INTEGER   | NUMBER
        ClassObjectType.LONG      | LONG
        ClassObjectType.FLOAT     | FLOAT
        ClassObjectType.DOUBLE    | DOUBLE
        ClassObjectType.BOOLEAN   | BOOLEAN
        ClassObjectType.STRING    | STRING
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
                CHAR, NUMBER
        ]
    }

    def 'test BYTE incompatible with #type'() {
        expect:
        !ClassObjectType.BYTE.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING
        ]
    }

    def 'test CHAR compatible with #type'() {
        expect:
        ClassObjectType.CHARACTER.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER
        ]
    }

    def 'test CHAR incompatible with #type'() {
        expect:
        !ClassObjectType.CHARACTER.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING
        ]
    }

    def 'test SHORT compatible with #type'() {
        expect:
        ClassObjectType.SHORT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER
        ]
    }

    def 'test SHORT incompatible with #type'() {
        expect:
        !ClassObjectType.SHORT.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING
        ]
    }

    def 'test INT compatible with #type'() {
        expect:
        ClassObjectType.INTEGER.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER
        ]
    }

    def 'test INT incompatible with #type'() {
        expect:
        !ClassObjectType.INTEGER.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING
        ]
    }

    def 'test LONG compatible with #type'() {
        expect:
        ClassObjectType.LONG.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG
        ]
    }

    def 'test LONG incompatible with #type'() {
        expect:
        !ClassObjectType.LONG.compatibleWith(type)

        where:
        type << [
                DOUBLE, FLOAT,
                BOOLEAN, STRING
        ]
    }

    def 'test FLOAT compatible with #type'() {
        expect:
        ClassObjectType.FLOAT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                FLOAT
        ]
    }

    def 'test FLOAT incompatible with #type'() {
        expect:
        !ClassObjectType.FLOAT.compatibleWith(type)

        where:
        type << [
                DOUBLE,
                BOOLEAN, STRING
        ]
    }

    def 'test DOUBLE compatible with #type'() {
        expect:
        ClassObjectType.DOUBLE.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                FLOAT, DOUBLE
        ]
    }

    def 'test DOUBLE incompatible with #type'() {
        expect:
        !ClassObjectType.DOUBLE.compatibleWith(type)

        where:
        type << [
                BOOLEAN, STRING
        ]
    }

    def 'test BOOLEAN compatible with #type'() {
        expect:
        ClassObjectType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                BOOLEAN
        ]
    }

    def 'test BOOLEAN incompatible with #type'() {
        expect:
        !ClassObjectType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                DOUBLE, FLOAT,
                STRING
        ]
    }

    def 'test STRING compatible with #type'() {
        expect:
        ClassObjectType.STRING.compatibleWith(type)

        where:
        type << [
                STRING
        ]
    }

    def 'test STRING incompatible with #type'() {
        expect:
        !ClassObjectType.STRING.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                DOUBLE, FLOAT,
                BOOLEAN
        ]
    }

    def 'test OBJECT compatible with #type'() {
        expect:
        ClassObjectType.OBJECT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING, ObjectType.OBJECT
        ]
    }
    
}
