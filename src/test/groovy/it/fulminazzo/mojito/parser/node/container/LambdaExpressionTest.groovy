package it.fulminazzo.mojito.parser.node.container

import it.fulminazzo.mojito.parser.node.literals.Literal
import it.fulminazzo.mojito.parser.node.statements.Return
import it.fulminazzo.mojito.parser.node.values.BooleanValueLiteral
import spock.lang.Specification

class LambdaExpressionTest extends Specification {
    private static final OBJECT = new BooleanValueLiteral('true')

    def 'test #expression toString should return #expected'() {
        when:
        def output = expression.toString()

        then:
        output == expected

        where:
        expression                                                                        | expected
        new LambdaExpression(OBJECT)                                                      | LambdaExpression.simpleName +
                "([] -> ${new CodeBlock(new Return(OBJECT))})"
        new LambdaExpression(Literal.of('a'), OBJECT)                                     | LambdaExpression.simpleName +
                "([${Literal.of('a')}] -> ${new CodeBlock(new Return(OBJECT))})"
        new LambdaExpression([Literal.of('a'), Literal.of('b')], OBJECT)                  | LambdaExpression.simpleName +
                "([${Literal.of('a')}, ${Literal.of('b')}] -> ${new CodeBlock(new Return(OBJECT))})"
        new LambdaExpression([Literal.of('a'), Literal.of('b'), Literal.of('c')], OBJECT) | LambdaExpression.simpleName +
                "([${Literal.of('a')}, ${Literal.of('b')}, ${Literal.of('c')}] -> ${new CodeBlock(new Return(OBJECT))})"
    }

}
