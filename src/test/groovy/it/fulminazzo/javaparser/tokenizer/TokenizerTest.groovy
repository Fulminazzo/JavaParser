package it.fulminazzo.javaparser.tokenizer

import spock.lang.Specification

import static it.fulminazzo.javaparser.tokenizer.TokenType.*

class TokenizerTest extends Specification {

    def "test tokenizer read"() {
        given:
        def input = "10 20.0 'c' \"Hello\"".bytes
        def tokenizer = new Tokenizer(new ByteArrayInputStream(input))

        when:
        def output = []
        for (TokenType t : tokenizer) output.add(t)

        then:
        output == [NUMBER_VALUE, SPACE, DOUBLE_VALUE, SPACE,
                   CHAR_VALUE, SPACE, STRING_VALUE, EOF]
        tokenizer.lastToken() == EOF
        tokenizer.lastRead() == ""
    }

}