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
        def second = ObjectType.of('Object')

        expect:
        first != second
    }

}
