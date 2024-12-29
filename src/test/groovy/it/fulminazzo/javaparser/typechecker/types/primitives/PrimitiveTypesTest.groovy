package it.fulminazzo.javaparser.typechecker.types.primitives

import spock.lang.Specification

class PrimitiveTypesTest extends Specification {

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
