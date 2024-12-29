package it.fulminazzo.javaparser.typechecker.types.values

import spock.lang.Specification

class ValueTypesTest extends Specification {

    def 'test name method of #type'() {
        when:
        def name = type.name()
        def toString = type.toString()

        then:
        name == expected
        toString == expected

        where:
        type                    | expected
        ValueTypes.BOOLEAN_TYPE | "BOOLEAN_TYPE"
        ValueTypes.CHAR_TYPE    | "CHAR_TYPE"
        ValueTypes.DOUBLE_TYPE  | "DOUBLE_TYPE"
        ValueTypes.FLOAT_TYPE   | "FLOAT_TYPE"
        ValueTypes.LONG_TYPE    | "LONG_TYPE"
        ValueTypes.NUMBER_TYPE  | "NUMBER_TYPE"
        ValueTypes.STRING_TYPE  | "STRING_TYPE"
    }

    def 'test values method'() {
        when:
        def values = ValueTypes.values()

        then:
        values == [
                ValueTypes.BOOLEAN_TYPE,
                ValueTypes.CHAR_TYPE,
                ValueTypes.DOUBLE_TYPE,
                ValueTypes.FLOAT_TYPE,
                ValueTypes.LONG_TYPE,
                ValueTypes.NUMBER_TYPE,
                ValueTypes.STRING_TYPE
        ].toArray()
    }

}
