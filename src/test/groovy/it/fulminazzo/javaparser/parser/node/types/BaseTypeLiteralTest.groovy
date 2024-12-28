package it.fulminazzo.javaparser.parser.node.types

import it.fulminazzo.javaparser.tokenizer.TokenType
import org.jetbrains.annotations.NotNull
import spock.lang.Specification

class BaseTypeLiteralTest extends Specification {

    def 'test invalid base type literal'() {
        given:
        def rawValue = 'invalid'

        when:
        new MockBaseType(rawValue)

        then:
        thrown(LiteralException)
    }

    static class MockBaseType extends BaseTypeLiteral {

        MockBaseType(@NotNull String rawValue) throws LiteralException {
            super(rawValue, TokenType.EOF)
        }

    }

}
