package it.fulminazzo.mojito.parser

import it.fulminazzo.mojito.tokenizer.TokenType
import spock.lang.Specification

class ParserTest extends Specification {
    private Parser parser

    void setup() {
        this.parser = new Parser() { }
    }

    def 'test match method exception'() {
        given:
        this.parser.input = 'mock input'

        when:
        this.parser.match(TokenType.DIVIDE)

        then:
        thrown(ParserException)
    }

}
