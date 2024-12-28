package it.fulminazzo.javaparser.parser.node

import it.fulminazzo.javaparser.parser.node.values.BooleanLiteral
import it.fulminazzo.javaparser.parser.node.literals.Literal
import it.fulminazzo.javaparser.parser.node.values.NumberLiteral
import spock.lang.Specification


class MethodInvocationTest extends Specification {

    def 'test method invocation print'() {
        given:
        def literals = [new Literal('a'), new NumberLiteral('2'), new BooleanLiteral('true')]
        def methodInvocation = new MethodInvocation(literals)

        when:
        def output = methodInvocation.toString()

        then:
        output == 'MethodInvocation(Literal(a), NumberLiteral(2), BooleanLiteral(true))'
    }

}