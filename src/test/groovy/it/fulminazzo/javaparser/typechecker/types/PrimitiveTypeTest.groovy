package it.fulminazzo.javaparser.typechecker.types


import spock.lang.Specification

import static ValueType.*;

class PrimitiveTypeTest extends Specification {

    def 'test BYTE compatible with #type'() {
        expect:
        PrimitiveType.BYTE.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
        ]
    }

    def 'test BYTE incompatible with #type'() {
        expect:
        !PrimitiveType.BYTE.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING
        ]
    }

    def 'test CHAR compatible with #type'() {
        expect:
        PrimitiveType.CHAR.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER
        ]
    }

    def 'test CHAR incompatible with #type'() {
        expect:
        !PrimitiveType.CHAR.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING
        ]
    }

    def 'test SHORT compatible with #type'() {
        expect:
        PrimitiveType.SHORT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
        ]
    }

    def 'test SHORT incompatible with #type'() {
        expect:
        !PrimitiveType.SHORT.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING
        ]
    }

    def 'test INT compatible with #type'() {
        expect:
        PrimitiveType.INT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER,
        ]
    }

    def 'test INT incompatible with #type'() {
        expect:
        !PrimitiveType.INT.compatibleWith(type)

        where:
        type << [
                LONG,
                DOUBLE, FLOAT,
                BOOLEAN, STRING
        ]
    }

    def 'test LONG compatible with #type'() {
        expect:
        PrimitiveType.LONG.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
        ]
    }

    def 'test LONG incompatible with #type'() {
        expect:
        !PrimitiveType.LONG.compatibleWith(type)

        where:
        type << [
                DOUBLE, FLOAT,
                BOOLEAN, STRING
        ]
    }

    def 'test FLOAT compatible with #type'() {
        expect:
        PrimitiveType.FLOAT.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                FLOAT,
        ]
    }

    def 'test FLOAT incompatible with #type'() {
        expect:
        !PrimitiveType.FLOAT.compatibleWith(type)

        where:
        type << [
                DOUBLE,
                BOOLEAN, STRING
        ]
    }

    def 'test DOUBLE compatible with #type'() {
        expect:
        PrimitiveType.DOUBLE.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                FLOAT, DOUBLE,
        ]
    }

    def 'test DOUBLE incompatible with #type'() {
        expect:
        !PrimitiveType.DOUBLE.compatibleWith(type)

        where:
        type << [
                BOOLEAN, STRING
        ]
    }

    def 'test BOOLEAN compatible with #type'() {
        expect:
        PrimitiveType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                BOOLEAN
        ]
    }

    def 'test BOOLEAN incompatible with #type'() {
        expect:
        !PrimitiveType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                CHAR, NUMBER, LONG,
                DOUBLE, FLOAT,
                STRING
        ]
    }

}
