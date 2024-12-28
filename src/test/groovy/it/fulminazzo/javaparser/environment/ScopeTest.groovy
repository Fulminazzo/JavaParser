package it.fulminazzo.javaparser.environment

import spock.lang.Specification

class ScopeTest extends Specification {
    private Scope<String> scope

    void setup() {
        this.scope = new Scope<>(ScopeType.CODE_BLOCK)
    }

    def 'declare should throw ScopeException on already declared variable'() {
        when:
        this.scope.declare(new WrapperInfo<>(String), 'var', '1')
        this.scope.declare(new WrapperInfo<>(String), 'var', '1')

        then:
        def e = thrown(ScopeException)
        e.getMessage() == this.scope.alreadyDeclaredVariable('var').message
    }

    def 'update should throw ScopeException on not declared variable'() {
        when:
        this.scope.update('var', '1')

        then:
        def e = thrown(ScopeException)
        e.getMessage() == this.scope.noSuchVariable('var').message
    }

}
