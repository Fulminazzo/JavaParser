package it.fulminazzo.mojito.typechecker.types.objects

import it.fulminazzo.mojito.typechecker.TypeCheckerException
import it.fulminazzo.mojito.typechecker.types.PrimitiveClassType
import it.fulminazzo.mojito.typechecker.types.PrimitiveType
import it.fulminazzo.mojito.typechecker.types.Type
import it.fulminazzo.mojito.typechecker.types.TypeException
import spock.lang.Specification

class ObjectTypeTest extends Specification {

    def 'test #object toPrimitive should return #expected'() {
        when:
        def actual = object.toPrimitive()

        then:
        actual == expected

        where:
        object               | expected
        ObjectType.BYTE      | PrimitiveType.BYTE
        ObjectType.SHORT     | PrimitiveType.SHORT
        ObjectType.CHARACTER | PrimitiveType.CHAR
        ObjectType.INTEGER   | PrimitiveType.INT
        ObjectType.LONG      | PrimitiveType.LONG
        ObjectType.FLOAT     | PrimitiveType.FLOAT
        ObjectType.DOUBLE    | PrimitiveType.DOUBLE
        ObjectType.BOOLEAN   | PrimitiveType.BOOLEAN
    }

    def 'test toPrimitive of invalid should throw exception'() {
        when:
        object.toPrimitive()

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.noPrimitive(object).message

        where:
        object << [ObjectType.STRING, ObjectType.OBJECT, ObjectType.of(Map)]
    }

    def 'test value #actual from values function did not match #expected'() {
        expect:
        expected == actual

        where:
        expected << [
                ObjectType.BYTE, ObjectType.SHORT, ObjectType.CHARACTER,
                ObjectType.INTEGER, ObjectType.LONG, ObjectType.FLOAT,
                ObjectType.DOUBLE, ObjectType.BOOLEAN, ObjectType.STRING,
                ObjectType.OBJECT,
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
                ObjectType.STRING,
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
        raw                      | expected
        'String'                 | 'String'
        'java.lang.String'       | 'String'
        'Map'                    | 'Map'
        'java.util.Map'          | 'Map'
        getClass().canonicalName | getClass().canonicalName
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
