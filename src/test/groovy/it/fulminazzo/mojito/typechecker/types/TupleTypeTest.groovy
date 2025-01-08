package it.fulminazzo.mojito.typechecker.types

import it.fulminazzo.mojito.typechecker.TypeCheckerException
import spock.lang.Specification

class TupleTypeTest extends Specification {

    def 'test toClass should throw exception'() {
        given:
        def type = new TupleType(PrimitiveClassType.INT, PrimitiveClassType.INT)

        when:
        type.toClass()

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.noClassType(TupleType).message
    }

}
