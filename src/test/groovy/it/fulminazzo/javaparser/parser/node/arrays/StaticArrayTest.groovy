package it.fulminazzo.javaparser.parser.node.arrays

import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.values.NumberLiteral
import spock.lang.Specification

class StaticArrayTest extends Specification {

    def 'test size method'() {
        given:
        def array = new StaticArray(new Literal('int'), new NumberLiteral('3'))

        when:
        def size = array.size()

        then:
        size == 3
    }

}
