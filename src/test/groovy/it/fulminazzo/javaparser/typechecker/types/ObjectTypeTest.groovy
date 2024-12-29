package it.fulminazzo.javaparser.typechecker.types

import spock.lang.Specification

class ObjectTypeTest extends Specification {

    def 'test equality'() {
        given:
        def first = ObjectType.of('String')
        def second = ObjectType.of('String')

        expect:
        first == second
    }

    def 'test inequality'() {
        given:
        def first = ObjectType.of('String')

        expect:
        first != second

        where:
        second << [
                ObjectType.of('Object'),
                PrimitiveType.BYTE,
                ValueType.STRING
        ]
    }

    def 'test toString of type #raw'() {
        given:
        def type = ObjectType.of(raw)

        when:
        def string = type.toString()

        then:
        string == "Type(${expected})"

        where:
        raw                | expected
        'String'           | 'String'
        'java.lang.String' | 'String'
        'Map'              | 'Map'
        'java.util.Map'    | 'Map'
        'it.fulminazzo.javaparser.typechecker.types.ObjectTypeTest' |
                'it.fulminazzo.javaparser.typechecker.types.ObjectTypeTest'
    }

}
