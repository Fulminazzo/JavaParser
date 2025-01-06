package it.fulminazzo.javaparser.parser.node.values

import spock.lang.Specification

class StringValueLiteralTest extends Specification {

    def 'test escape characters are parsed'() {
        given:
        def expected = 'Hello\tworld\r\nMy \'name\' is \"James\", ' +
                'and I love \b, \f and \\,' +
                'but I hate \\a, \\b, \\d, \\e, \\g, \\h, \\i, \\l, \\m, \\n, \\o,' +
                '\\p, \\q, \\s, \\u, \\v, \\w, \\x, \\y and \\z'

        and:
        def literal = new StringValueLiteral('\"Hello\\tworld\\r\\n' +
                'My \\\'name\\\' is \\"James\\", and I love \\b, \\f and \\\\' +
                'but I hate \\a, \\b, \\d, \\e, \\g, \\h, \\i, \\l, \\m, \\n, \\o,' +
                '\\p, \\q, \\s, \\u, \\v, \\w, \\x, \\y and \\z\"')

        expect:
        literal.rawValue == expected
    }

}