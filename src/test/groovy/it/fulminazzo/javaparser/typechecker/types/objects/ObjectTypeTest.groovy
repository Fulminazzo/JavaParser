package it.fulminazzo.javaparser.typechecker.types.objects

import it.fulminazzo.javaparser.typechecker.types.PrimitiveType
import it.fulminazzo.javaparser.typechecker.types.TypeException
import it.fulminazzo.javaparser.typechecker.types.ValueType
import spock.lang.Specification

class ObjectTypeTest extends Specification {

    def 'test equality'() {
        given:
        def first = ObjectType.of('String')
        def second = ObjectType.of('String')

        expect:
        first == second
        first.hashCode() == second.hashCode()
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
        raw                           | expected
        'String'                      | 'String'
        'java.lang.String'            | 'String'
        'Map'                         | 'Map'
        'java.util.Map'               | 'Map'
        getClass().getCanonicalName() | getClass().getCanonicalName()
    }

    def 'test of method invalid class'() {
        when:
        def className = 'unknown'
        def type = ObjectType.of(className)

        then:
        def ex = thrown(TypeException)
        ex.message.contains(className)
    }

}
