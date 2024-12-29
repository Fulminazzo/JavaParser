package it.fulminazzo.javaparser.typechecker.types.primitives

import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.values.ValueTypes.*;

class PrimitiveTypesTest extends Specification {

    def 'test BYTE compatible with #type'() {
        expect:
        PrimitiveTypes.BYTE.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE,
        ]
    }

    def 'test BYTE incompatible with #type'() {
        expect:
        !PrimitiveTypes.BYTE.compatibleWith(type)

        where:
        type << [
                LONG_TYPE,
                DOUBLE_TYPE, FLOAT_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test CHAR compatible with #type'() {
        expect:
        PrimitiveTypes.CHAR.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE
        ]
    }

    def 'test CHAR incompatible with #type'() {
        expect:
        !PrimitiveTypes.CHAR.compatibleWith(type)

        where:
        type << [
                LONG_TYPE,
                DOUBLE_TYPE, FLOAT_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test SHORT compatible with #type'() {
        expect:
        PrimitiveTypes.SHORT.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE,
        ]
    }

    def 'test SHORT incompatible with #type'() {
        expect:
        !PrimitiveTypes.SHORT.compatibleWith(type)

        where:
        type << [
                LONG_TYPE,
                DOUBLE_TYPE, FLOAT_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test INT compatible with #type'() {
        expect:
        PrimitiveTypes.INT.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE,
        ]
    }

    def 'test INT incompatible with #type'() {
        expect:
        !PrimitiveTypes.INT.compatibleWith(type)

        where:
        type << [
                LONG_TYPE,
                DOUBLE_TYPE, FLOAT_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test LONG compatible with #type'() {
        expect:
        PrimitiveTypes.LONG.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE, LONG_TYPE,
        ]
    }

    def 'test LONG incompatible with #type'() {
        expect:
        !PrimitiveTypes.LONG.compatibleWith(type)

        where:
        type << [
                DOUBLE_TYPE, FLOAT_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test FLOAT compatible with #type'() {
        expect:
        PrimitiveTypes.FLOAT.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE, LONG_TYPE,
                FLOAT_TYPE,
        ]
    }

    def 'test FLOAT incompatible with #type'() {
        expect:
        !PrimitiveTypes.FLOAT.compatibleWith(type)

        where:
        type << [
                DOUBLE_TYPE,
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test DOUBLE compatible with #type'() {
        expect:
        PrimitiveTypes.DOUBLE.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE, LONG_TYPE,
                FLOAT_TYPE, DOUBLE_TYPE,
        ]
    }

    def 'test DOUBLE incompatible with #type'() {
        expect:
        !PrimitiveTypes.DOUBLE.compatibleWith(type)

        where:
        type << [
                BOOLEAN_TYPE, STRING_TYPE
        ]
    }

    def 'test BOOLEAN compatible with #type'() {
        expect:
        PrimitiveTypes.BOOLEAN.compatibleWith(type)

        where:
        type << [
                BOOLEAN_TYPE
        ]
    }

    def 'test BOOLEAN incompatible with #type'() {
        expect:
        !PrimitiveTypes.BOOLEAN.compatibleWith(type)

        where:
        type << [
                CHAR_TYPE, NUMBER_TYPE, LONG_TYPE,
                DOUBLE_TYPE, FLOAT_TYPE,
                STRING_TYPE
        ]
    }

    def 'test name method of #type'() {
        when:
        def name = type.name()
        def toString = type.toString()

        then:
        name == expected
        toString == expected

        where:
        type                   | expected
        PrimitiveTypes.BYTE    | "BYTE"
        PrimitiveTypes.CHAR    | "CHAR"
        PrimitiveTypes.SHORT   | "SHORT"
        PrimitiveTypes.INT     | "INT"
        PrimitiveTypes.LONG    | "LONG"
        PrimitiveTypes.FLOAT   | "FLOAT"
        PrimitiveTypes.DOUBLE  | "DOUBLE"
        PrimitiveTypes.BOOLEAN | "BOOLEAN"
    }

    def 'test values method'() {
        when:
        def values = PrimitiveTypes.values()

        then:
        values == [
                PrimitiveTypes.BYTE,
                PrimitiveTypes.CHAR,
                PrimitiveTypes.SHORT,
                PrimitiveTypes.INT,
                PrimitiveTypes.LONG,
                PrimitiveTypes.FLOAT,
                PrimitiveTypes.DOUBLE,
                PrimitiveTypes.BOOLEAN,
        ].toArray()
    }

    def 'test value of #name'() {
        when:
        def actual = PrimitiveTypes.valueOf(name)

        then:
        actual == expected

        where:
        expected | name
        PrimitiveTypes.BOOLEAN | "BOOLEAN"
        PrimitiveTypes.CHAR    | "CHAR"
        PrimitiveTypes.DOUBLE  | "DOUBLE"
        PrimitiveTypes.FLOAT   | "FLOAT"
        PrimitiveTypes.LONG    | "LONG"
        PrimitiveTypes.INT     | "INT"
        PrimitiveTypes.SHORT   | "SHORT"
        PrimitiveTypes.BYTE    | "BYTE"
    }

}
