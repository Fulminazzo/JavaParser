package it.fulminazzo.javaparser.parser.node.arrays

import it.fulminazzo.javaparser.parser.node.values.Literal
import it.fulminazzo.javaparser.parser.node.values.NumberLiteral
import spock.lang.Specification

class DynamicArrayTest extends Specification {
    private DynamicArray array

    void setup() {
        this.array = new DynamicArray(new Literal('int'), Arrays.asList(
                new NumberLiteral('1'),
                new NumberLiteral('2'),
                new NumberLiteral('3'),
        ))
    }

    def 'test size method'() {
        when:
        def size = this.array.size()

        then:
        size == 3
    }

    def 'test toString'() {
        when:
        def output = this.array.toString()

        then:
        output == 'DynamicArray(Literal(int), {NumberLiteral(1), NumberLiteral(2), NumberLiteral(3)})'
    }

}
