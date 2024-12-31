package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import spock.lang.Specification

class NullTypeTest extends Specification {
    private NullType type

    void setup() {
        this.type = Types.NULL_TYPE
    }

    def 'test null type should be compatible with class type #classType'() {
        expect:
        this.type.isAssignableFrom(classType)

        where:
        classType << ClassObjectType.values()
    }

    def 'test null type should not be compatible with class type #classType'() {
        expect:
        !this.type.isAssignableFrom(classType)

        where:
        classType << PrimitiveType.values()
    }

    def 'test toClassType'() {
        when:
        this.type.toClassType()

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.noClassType(NullType).message
    }

    def 'test hashCode'() {
        given:
        def code = this.type.hashCode()

        expect:
        code == NullType.hashCode()
    }

    def 'test equality'() {
        given:
        def second = Types.NULL_TYPE

        expect:
        this.type == second
    }

    def 'test inequality of #other'() {
        expect:
        !this.type.equals(other)

        where:
        other << [PrimitiveType.BOOLEAN, ValueType.STRING, null, 'NULL_TYPE']
    }

    def 'test toString'() {
        given:
        def string = this.type.toString()

        expect:
        string == 'NULL_TYPE'
    }



}