package it.fulminazzo.mojito.parser.node

import it.fulminazzo.mojito.tokenizer.TokenType
import org.jetbrains.annotations.NotNull
import spock.lang.Specification

class TokenizedNodeTest extends Specification {

    def 'test invalid tokenized note'() {
        given:
        def rawValue = 'invalid'

        when:
        new MockBaseType(rawValue)

        then:
        thrown(NodeException)
    }

    static class MockBaseType extends TokenizedNode {

        MockBaseType(@NotNull String rawValue) throws NodeException {
            super(rawValue, TokenType.EOF)
        }

    }

}
