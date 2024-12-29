package it.fulminazzo.javaparser.typechecker.types.objects

import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.ValueType.*

class ClassObjectTypeTest extends Specification {

    def 'test BYTE compatible with #type'() {
        expect:
        ClassObjectType.BYTE.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
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

    def 'test CHARACTER compatible with #type'() {
        expect:
        ClassObjectType.CHARACTER.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER
        ]
    }

    def 'test CHARACTER incompatible with #type'() {
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
                CHAR, NUMBER,
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

    def 'test INTEGER compatible with #type'() {
        expect:
        ClassObjectType.INTEGER.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
        ]
    }

    def 'test INTEGER incompatible with #type'() {
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
                CHAR, NUMBER, LONG,
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
                FLOAT,
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
                FLOAT, DOUBLE,
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
    
}
