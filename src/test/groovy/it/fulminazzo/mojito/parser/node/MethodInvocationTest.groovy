package it.fulminazzo.mojito.parser.node

import it.fulminazzo.mojito.parser.node.literals.Literal
import it.fulminazzo.mojito.parser.node.values.BooleanValueLiteral
import it.fulminazzo.mojito.parser.node.values.NumberValueLiteral
import spock.lang.Specification

class MethodInvocationTest extends Specification {

    def 'test method invocation print'() {
        given:
        def literals = [Literal.of('a'), new NumberValueLiteral('2'), new BooleanValueLiteral('true')]
        def methodInvocation = new MethodInvocation(literals)

        when:
        def output = methodInvocation.toString()

        then:
        output == 'MethodInvocation(Literal(a), NumberValueLiteral(2), BooleanValueLiteral(true))'
    }

}
