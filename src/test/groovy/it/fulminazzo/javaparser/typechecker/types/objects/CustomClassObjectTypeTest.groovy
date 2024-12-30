package it.fulminazzo.javaparser.typechecker.types.objects

import it.fulminazzo.javaparser.typechecker.types.PrimitiveType
import spock.lang.Specification

class CustomClassObjectTypeTest extends Specification {

    def 'test conversion of types'() {
        given:
        def type = ObjectType.of(Map)

        when:
        def classType = type.toClassType()
        def newType = classType.toType()

        then:
        newType == type
        classType == new CustomClassObjectType(ObjectType.of(Map))
    }

    def 'test compatibleWith'() {
        given:
        def className = getClass().canonicalName
        def type = ObjectType.of(className)
        def classType = new CustomClassObjectType(type)

        expect:
        classType.compatibleWith(type)
    }

    def 'test incompatible with #type'() {
        given:
        // Bad practice, just for testing purposes
        def classType = new CustomClassObjectType(ObjectType.OBJECT)

        expect:
        !classType.compatibleWith(type)

        where:
        type << [
                PrimitiveType.values(),
                ObjectType.of(getClass().canonicalName)
        ].flatten()
    }

    def 'test equality'() {
        given:
        def className = getClass().canonicalName
        def type = ObjectType.of(className)
        def first = new CustomClassObjectType(type)
        def second = new CustomClassObjectType(type)

        expect:
        first == second
        first.hashCode() == second.hashCode()
    }

    def 'test inequality'() {
        given:
        def className = getClass().canonicalName
        def classType = new CustomClassObjectType(ObjectType.of(className))

        expect:
        !classType.equals(type)

        where:
        type << [
                null,
                PrimitiveType.values(),
                ClassObjectType.values(),
                new CustomClassObjectType(ObjectType.OBJECT)
        ].flatten()
    }

}
