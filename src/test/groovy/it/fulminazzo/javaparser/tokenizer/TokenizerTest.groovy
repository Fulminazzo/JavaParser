package it.fulminazzo.javaparser.tokenizer

import spock.lang.Specification

import static it.fulminazzo.javaparser.tokenizer.TokenType.*

class TokenizerTest extends Specification {

    def 'test tokenizer hasNext method exception'() {
        given:
        def input = Mock(ByteArrayInputStream)
        input.available() >> {
            throw new IOException('Closed stream')
        }
        def tokenizer = new Tokenizer(input)

        when:
        tokenizer.hasNext()

        then:
        thrown(TokenizerException)
    }

    def 'test tokenizer read until next line'() {
        given:
        def input = 'This should be totally ignored\n10'.bytes
        def tokenizer = new Tokenizer(new ByteArrayInputStream(input))

        when:
        tokenizer.readUntilNextLine()

        then:
        tokenizer.lastToken() == NUMBER_VALUE
        tokenizer.lastRead() == '10'
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

    def 'test tokenizer next method exception'() {
        given:
        def input = Mock(ByteArrayInputStream)
        input.available() >> {
            throw new IOException('Closed stream')
        }
        def tokenizer = new Tokenizer(input)

        when:
        tokenizer.next()

        then:
        thrown(TokenizerException)
    }

}