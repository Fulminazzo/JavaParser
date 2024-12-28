package it.fulminazzo.javaparser.tokenizer

import spock.lang.Specification

import static it.fulminazzo.javaparser.tokenizer.TokenType.*

class TokenizerTest extends Specification {

    static Tokenizer generateTokenizer(String input) {
        return new Tokenizer(new ByteArrayInputStream(input.bytes))
    }

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

    def 'simulate reading of empty comment'() {
        given:
        def tokenizer = generateTokenizer('//\n10')

        when:
        def initialToken = tokenizer.nextSpaceless()
        def finalToken = tokenizer.readUntilNextLine()

        then:
        initialToken == COMMENT_INLINE
        finalToken == NUMBER_VALUE
        tokenizer.lastRead() == '10'
    }

    def 'test tokenizer read until next line'() {
        given:
        def tokenizer = generateTokenizer('This should be totally ignored\n10')

        when:
        tokenizer.readUntilNextLine()

        then:
        tokenizer.lastToken() == NUMBER_VALUE
        tokenizer.lastRead() == '10'
    }

    def 'test tokenizer read until next line method exception'() {
        given:
        def input = Mock(ByteArrayInputStream)
        input.available() >> {
            throw new IOException('Closed stream')
        }
        def tokenizer = new Tokenizer(input)

        when:
        tokenizer.readUntilNextLine()

        then:
        thrown(TokenizerException)
    }

    def 'test tokenizer next spaceless'() {
        given:
        def tokenizer = generateTokenizer('         10')

        when:
        tokenizer.nextSpaceless()

        then:
        tokenizer.lastToken() == NUMBER_VALUE
        tokenizer.lastRead() == '10'
        tokenizer.nextSpaceless() == EOF
    }

    def 'test tokenizer invalid dot'() {
        given:
        def tokenizer = generateTokenizer('!.')

        when:
        tokenizer.next()
        def output = tokenizer.lastToken()

        then:
        output == NOT
    }

    def 'test tokenizer next'() {
        given:
        def tokenizer = generateTokenizer('10 20.0 \'c\' \"Hello\"')

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

    def 'test line and column methods reading code: #code'() {
        given:
        def tokenizer = generateTokenizer(code)

        when:
        tokenizer.nextSpaceless()

        then:
        tokenizer.line() == line
        tokenizer.column() == column

        where:
        line | column | code
        -1   | -1     | ''
        1    | 1      | '1'
        1    | 6      | 'return'
        2    | 5      | '    \nbreak\n'
    }

}