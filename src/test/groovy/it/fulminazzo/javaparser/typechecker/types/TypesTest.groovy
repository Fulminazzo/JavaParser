package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectClassType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

class TypesTest extends Specification {
    private Type type

    void setup() {
        this.type = new Types.SingletonType('TEST_TYPE')
    }

    def 'test null type should be compatible with class type #classType'() {
        given:
        def type = Types.NULL_TYPE

        expect:
        type.isAssignableFrom(classType)

        where:
        classType << ObjectClassType.values()
    }

    def 'test null type should not be compatible with class type #classType'() {
        given:
        def type = Types.NULL_TYPE

        expect:
        !type.isAssignableFrom(classType)

        where:
        classType << PrimitiveClassType.values()
    }

    def 'test SingletonType toClassType'() {
        when:
        this.type.toClassType()

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.noClassType(Types.SingletonType).message
    }

    def 'test SingletonType hashCode'() {
        given:
        def code = this.type.hashCode()
        int expected = Types.SingletonType.hashCode() ^ 'TEST_TYPE'.hashCode()

        expect:
        code == expected
    }

    def 'test SingletonType equality'() {
        given:
        def second = new Types.SingletonType('TEST_TYPE')

        expect:
        this.type == second
    }

    def 'test SingletonType should not be equal to #other'() {
        expect:
        !this.type.equals(other)

        where:
        other << [PrimitiveClassType.BOOLEAN, ObjectType.STRING, null,
                  new Types.SingletonType('MOCK'), 'TEST_TYPE']
    }

    def 'test SingletonType toString'() {
        given:
        def string = this.type.toString()

        expect:
        string == 'TEST_TYPE'
    }



}