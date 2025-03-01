package it.fulminazzo.mojito.parser.node.values

import it.fulminazzo.mojito.parser.node.NodeException
import spock.lang.Specification

class CharValueLiteralTest extends Specification {

    def 'test escaped #charName should be parsed'() {
        when:
        def literal = new CharValueLiteral("'${character}'")

        and:
        def value = literal.rawValue

        then:
        value.charAt(0) == expected

        where:
        charName          | character | expected
        'tab'             | '\\t'     | '\t' as char
        'carriage return' | '\\r'     | '\r' as char
        'new line'        | '\\n'     | '\n' as char
        'format'          | '\\f'     | '\f' as char
        'backspace'       | '\\b'     | '\b' as char
        'single quote'    | '\\\''    | '\'' as char
        'double quote'    | '\\\"'    | '\"' as char
        'backslash'       | '\\\\'    | '\\' as char
    }

    def 'test new char value literal from #character should throw exception'() {
        when:
        new CharValueLiteral(character)

        then:
        thrown(NodeException)

        where:
        character << ['\'a', 'a\'', '\'invalid\'', 'invalid']
    }

}

