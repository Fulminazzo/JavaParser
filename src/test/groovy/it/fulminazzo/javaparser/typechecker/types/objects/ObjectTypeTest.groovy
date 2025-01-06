package it.fulminazzo.javaparser.typechecker.types.objects

import it.fulminazzo.javaparser.typechecker.types.PrimitiveClassType
import it.fulminazzo.javaparser.typechecker.types.Type
import it.fulminazzo.javaparser.typechecker.types.TypeException
import spock.lang.Specification

class ObjectTypeTest extends Specification {

    def 'test value #actual from values function did not match #expected'() {
        expect:
        expected == actual

        where:
        expected << [
                ObjectType.BYTE, ObjectType.SHORT, ObjectType.CHARACTER,
                ObjectType.INTEGER, ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN, ObjectType.STRING,
                ObjectType.OBJECT
        ].toArray(ObjectType[])
        actual << ObjectType.values()
    }

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
        def first = ObjectType.of('Map')

        expect:
        first != second

        where:
        second << [
                ObjectType.of('Object'),
                PrimitiveClassType.BYTE,
                ObjectType.STRING
        ]
    }

    def 'test toString of type #raw'() {
        given:
        def type = ObjectType.of(raw)

        when:
        def string = type.toString()

        then:
        string == Type.print(expected)

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
        ObjectType.of(className)

        then:
        def ex = thrown(TypeException)
        ex.message.contains(className)
    }

}
