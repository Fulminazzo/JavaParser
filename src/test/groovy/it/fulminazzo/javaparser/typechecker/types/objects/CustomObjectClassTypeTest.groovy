package it.fulminazzo.javaparser.typechecker.types.objects

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.ClassType
import it.fulminazzo.javaparser.typechecker.types.PrimitiveClassType
import it.fulminazzo.javaparser.typechecker.types.PrimitiveType
import spock.lang.Specification

class CustomObjectClassTypeTest extends Specification {

    def 'test cast of #cast to #type should return #cast'() {
        when:
        def actual = cast.cast(type)

        then:
        actual == cast.toType()

        where:
        cast                                                 | type
        new CustomObjectClassType(ObjectType.of(Collection)) | ObjectType.of(List)
        new CustomObjectClassType(ObjectType.of(List))       | ObjectType.of(List)
        new CustomObjectClassType(ObjectType.of(LinkedList)) | ObjectType.of(List)
        new CustomObjectClassType(ObjectType.of(ArrayList))  | ObjectType.of(List)
    }

    def 'test invalid cast of custom class to #type'() {
        given:
        def cast = new CustomObjectClassType(ObjectType.of(Collection))

        when:
        cast.cast(type)

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.invalidCast(cast, type).message

        where:
        type << [
                PrimitiveType.values(),
                ObjectType.values().findAll { it != ObjectType.OBJECT },
                ObjectType.of(Map),
                ObjectType.of(HashMap),
                ObjectType.of(Exception),
        ].flatten()
    }

    def 'test conversion of types'() {
        given:
        def type = ObjectType.of(Map)

        when:
        def classType = type.toClass()
        def newType = classType.toType()

        then:
        newType == type
        classType == new CustomObjectClassType(ObjectType.of(Map))
    }

    def 'test compatibleWith'() {
        given:
        def className = getClass().canonicalName
        def type = ObjectType.of(className)
        def classType = new CustomObjectClassType(type)

        expect:
        classType.compatibleWith(type)
    }

    def 'test incompatible with #type'() {
        given:
        // Bad practice, just for testing purposes
        def classType = new CustomObjectClassType(ObjectType.STRING)

        expect:
        !classType.compatibleWith(type)

        where:
        type << [
                PrimitiveClassType.values(),
                [ObjectType.BYTE, ObjectType.SHORT, ObjectType.OBJECT],
                ObjectType.of(getClass().canonicalName),
        ].flatten()
    }

    def 'test equality'() {
        given:
        def className = getClass().canonicalName
        def type = ObjectType.of(className)
        def first = new CustomObjectClassType(type)
        def second = new CustomObjectClassType(type)

        expect:
        first == second
        first.hashCode() == second.hashCode()
    }

    def 'test inequality'() {
        given:
        def className = getClass().canonicalName
        def classType = new CustomObjectClassType(ObjectType.of(className))

        expect:
        !classType.equals(type)

        where:
        type << [
                null,
                PrimitiveClassType.values(),
                ObjectClassType.values(),
                new CustomObjectClassType(ObjectType.STRING),
        ].flatten()
    }

    def 'test toString of type #type'() {
        given:
        def string = type.toString()

        expect:
        string == ClassType.print(expected)

        where:
        expected                 | type
        'String'                 | new CustomObjectClassType(ObjectType.STRING)
        getClass().canonicalName | new CustomObjectClassType(ObjectType.of(getClass()))
    }

}
