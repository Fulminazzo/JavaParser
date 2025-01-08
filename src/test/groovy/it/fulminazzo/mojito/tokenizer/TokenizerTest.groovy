package it.fulminazzo.mojito.tokenizer

import spock.lang.Specification

import static it.fulminazzo.mojito.tokenizer.TokenType.*

class TokenizerTest extends Specification {

    def 'test tokenizer next'() {
        given:
        def input = '10 20.0 \'c\' \"Hello\"'.bytes
        def tokenizer = new Tokenizer(new ByteArrayInputStream(input))

        when:
        def output = []
        for (TokenType t : tokenizer) output.add(t)

        then:
        output == [NUMBER_VALUE, SPACE, DOUBLE_VALUE, SPACE,
                   CHAR_VALUE, SPACE, STRING_VALUE, EOF]
        tokenizer.lastToken() == EOF
        tokenizer.lastRead() == ''
    }

    def 'test tokenizer next spaceless'() {
        given:
        def input = '         10'.bytes
        def tokenizer = new Tokenizer(new ByteArrayInputStream(input))

        when:
        tokenizer.nextSpaceless()

        then:
        tokenizer.lastToken() == NUMBER_VALUE
        tokenizer.lastRead() == '10'
    }

}