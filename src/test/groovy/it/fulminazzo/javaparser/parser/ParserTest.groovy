package it.fulminazzo.javaparser.parser

import it.fulminazzo.javaparser.tokenizer.TokenType
import spock.lang.Specification

class ParserTest extends Specification {
    private Parser parser

    void setup() {
        this.parser = new Parser() { }
    }

    def 'test match method exception'() {
        given:
        this.parser.setInput('mock input')

        when:
        this.parser.match(TokenType.DIVIDE)

        then:
        thrown(ParserException)
    }

}
