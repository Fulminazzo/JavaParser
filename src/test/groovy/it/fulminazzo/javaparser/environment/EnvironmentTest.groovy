package it.fulminazzo.javaparser.environment

import spock.lang.Specification

class EnvironmentTest extends Specification {
    private Environment<Integer> environment

    void setup() {
        this.environment = new Environment().enterScope(ScopeType.CODE_BLOCK)
    }

    void cleanup() {
        this.environment.exitScope()
    }

    def 'test declare, isDeclared, lookup and update'() {
        given:
        def varName = 'var'

        when:
        this.environment.declare(varName, 1)
        def declared = this.environment.isDeclared(varName)
        def first = this.environment.lookup(varName)
        this.environment.update(varName, 2)
        def second = this.environment.lookup(varName)

        then:
        declared
        first == 1
        second == 2
    }

    def 'test declare twice'() {
        given:
        def varName = 'var'

        when:
        this.environment.declare(varName, 1)
        this.environment.declare(varName, 2)

        then:
        thrown(ScopeException)
    }

    def 'test lookup without declare'() {
        given:
        def varName = 'var'

        when:
        this.environment.lookup(varName)

        then:
        thrown(ScopeException)
    }

}