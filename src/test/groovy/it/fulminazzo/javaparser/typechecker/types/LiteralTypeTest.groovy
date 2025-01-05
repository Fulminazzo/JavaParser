package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.TypeCheckerException
import spock.lang.Specification

class LiteralTypeTest extends Specification {
    private LiteralType literal

    void setup() {
        this.literal = new LiteralType('world')
    }

    def 'test getLiteral'() {
        when:
        def literal = this.literal.getLiteral()

        then:
        literal == 'world'
    }

    def 'test toClass'() {
        when:
        this.literal.toClass()

        then:
        def e = thrown(TypeCheckerException)
        e.message == TypeCheckerException.noClassType(LiteralType).message
    }

}
