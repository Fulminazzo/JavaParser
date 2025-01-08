package it.fulminazzo.javaparser

import it.fulminazzo.javaparser.tokenizer.TokenType
import spock.lang.Specification

class MojitoTest extends Specification {

    def 'test parseVariables of #arguments should throw exception with message: #expected'() {
        when:
        Mojito.parseVariables(arguments.toArray(new String[arguments.size()]), 1)

        then:
        def e = thrown(RunnerException)
        e.message == expected

        where:
        arguments                                | expected
        ['code', 'invalid_variable']             | 'Error for variable \'invalid_variable\' at index 1: Expected \'key:value\' pair'
        ['code', 'a1:true', 'invalid key:value'] | 'Error for variable \'invalid key:value\' at index 2: ' +
                "Invalid value invalid key for token${TokenType.LITERAL}(${TokenType.LITERAL.regex()})"
        ['code', 'a1:true', 'a2:false', 'key:;'] | 'Error for variable \'invalid_variable\' at index 3: ' +
                'No value was returned from key'
    }

}
