package it.fulminazzo.javaparser.parser.node.types

import spock.lang.Specification


class LiteralTest extends Specification {

    def 'test literal split'() {
        given:
        def string = 'hello.dear.world'
        def literal = new Literal(string)

        when:
        def split = literal.splitLastDot()

        then:
        split.getKey() == new Literal('hello.dear')
        split.getValue() == new Literal('world')
    }

}