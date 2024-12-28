package it.fulminazzo.javaparser.parser.node.literals


import spock.lang.Specification


class LiteralTest extends Specification {

    def 'test split of #string should return: (#key, #value)'() {
        given:
        def literal = new Literal(string)

        when:
        def split = literal.splitLastDot()

        then:
        split.getKey() == key
        split.getValue() == value

        where:
        string             | key                       | value
        'hello.dear.world' | new Literal('hello.dear') | new Literal('world')
        'hello'            | new Literal('hello')      | null
    }

}