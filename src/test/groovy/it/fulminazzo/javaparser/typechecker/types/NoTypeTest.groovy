package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import spock.lang.Specification

class NoTypeTest extends Specification {
    private NoType type

    void setup() {
        this.type = Types.NO_TYPE
    }

    def 'test toClassType'() {
        when:
        this.type.toClassType()

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.noClassType(NoType).message
    }

    def 'test hashCode'() {
        given:
        def code = this.type.hashCode()

        expect:
        code == NoType.hashCode()
    }

    def 'test equality'() {
        given:
        def second = Types.NO_TYPE

        expect:
        this.type == second
    }

    def 'test inequality of #other'() {
        expect:
        !this.type.equals(other)

        where:
        other << [PrimitiveType.BOOLEAN, ValueType.STRING, null, 'NONE']
    }

    def 'test toString'() {
        given:
        def string = this.type.toString()

        expect:
        string == 'NONE'
    }



}