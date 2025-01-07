package it.fulminazzo.javaparser.parser.node.literals

import spock.lang.Specification

class ArrayLiteralTest extends Specification {

    def 'test getLiteral should throw exception'() {
        given:
        def literal = new ArrayLiteral(Literal.of('a'))

        when:
        literal.literal

        then:
        thrown(IllegalArgumentException)
    }

}
