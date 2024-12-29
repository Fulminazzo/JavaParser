package it.fulminazzo.javaparser.typechecker.types


import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.values.ValueTypes.*;

class PrimitiveTypeTest extends Specification {

    def 'test BYTE compatible with #type'() {
        expect:
        PrimitiveType.BYTE.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE,
        ]
    }

    def 'test BYTE incompatible with #type'() {
        expect:
        !PrimitiveType.BYTE.compatibleWith(type)

        where:
        type << [
                LONG_TYPE,
                DOUBLE_TYPE, FLOAT_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test CHAR compatible with #type'() {
        expect:
        PrimitiveType.CHAR.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE
        ]
    }

    def 'test CHAR incompatible with #type'() {
        expect:
        !PrimitiveType.CHAR.compatibleWith(type)

        where:
        type << [
                LONG_TYPE,
                DOUBLE_TYPE, FLOAT_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test SHORT compatible with #type'() {
        expect:
        PrimitiveType.SHORT.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE,
        ]
    }

    def 'test SHORT incompatible with #type'() {
        expect:
        !PrimitiveType.SHORT.compatibleWith(type)

        where:
        type << [
                LONG_TYPE,
                DOUBLE_TYPE, FLOAT_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test INT compatible with #type'() {
        expect:
        PrimitiveType.INT.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE,
        ]
    }

    def 'test INT incompatible with #type'() {
        expect:
        !PrimitiveType.INT.compatibleWith(type)

        where:
        type << [
                LONG_TYPE,
                DOUBLE_TYPE, FLOAT_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test LONG compatible with #type'() {
        expect:
        PrimitiveType.LONG.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE, LONG_TYPE,
        ]
    }

    def 'test LONG incompatible with #type'() {
        expect:
        !PrimitiveType.LONG.compatibleWith(type)

        where:
        type << [
                DOUBLE_TYPE, FLOAT_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test FLOAT compatible with #type'() {
        expect:
        PrimitiveType.FLOAT.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE, LONG_TYPE,
                FLOAT_TYPE,
        ]
    }

    def 'test FLOAT incompatible with #type'() {
        expect:
        !PrimitiveType.FLOAT.compatibleWith(type)

        where:
        type << [
                DOUBLE_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test DOUBLE compatible with #type'() {
        expect:
        PrimitiveType.DOUBLE.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE, LONG_TYPE,
                FLOAT_TYPE, DOUBLE_TYPE,
        ]
    }

    def 'test DOUBLE incompatible with #type'() {
        expect:
        !PrimitiveType.DOUBLE.compatibleWith(type)

        where:
        type << [
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test BOOLEAN compatible with #type'() {
        expect:
        PrimitiveType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                BOOLEAN_TYPE
        ]
    }

    def 'test BOOLEAN incompatible with #type'() {
        expect:
        !PrimitiveType.BOOLEAN.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE, LONG_TYPE,
                DOUBLE_TYPE, FLOAT_TYPE,
                STRING_TYPE
        ]
    }

}
