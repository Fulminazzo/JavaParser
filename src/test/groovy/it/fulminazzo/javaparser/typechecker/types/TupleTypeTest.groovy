package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import spock.lang.Specification

class TupleTypeTest extends Specification {

    def 'test toClassType should throw exception'() {
        given:
        def type = new TupleType(PrimitiveType.INT, PrimitiveType.INT)

        when:
        type.toClassType()

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.noClassType(TupleType).message
    }

}