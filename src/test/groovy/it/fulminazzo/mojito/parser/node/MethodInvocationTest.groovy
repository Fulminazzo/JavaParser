package it.fulminazzo.mojito.parser.node

import it.fulminazzo.mojito.parser.node.types.BooleanLiteral
import it.fulminazzo.mojito.parser.node.types.Literal
import it.fulminazzo.mojito.parser.node.types.NumberLiteral
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