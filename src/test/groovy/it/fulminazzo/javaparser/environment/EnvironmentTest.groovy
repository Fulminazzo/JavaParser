package it.fulminazzo.javaparser.environment

import org.jetbrains.annotations.NotNull
import spock.lang.Specification

class EnvironmentTest extends Specification implements Scoped {
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
        def e = thrown(ScopeException)
        e.message == alreadyDeclaredVariable(varName).message
    }

    def 'test lookup without declare'() {
        given:
        def varName = 'var'

        when:
        this.environment.lookup(varName)

        then:
        def e = thrown(ScopeException)
        e.message == noSuchVariable(varName).message
    }

    def 'test update without declare'() {
        given:
        def varName = 'var'

        when:
        this.environment.update(varName, 1)

        then:
        def e = thrown(ScopeException)
        e.message == noSuchVariable(varName).message
    }

    // Unused methods
    @Override
    Optional search(@NotNull String name) {
        return null
    }

    @Override
    void declare(@NotNull String name, @NotNull Object value) throws ScopeException {

    }

    @Override
    void update(@NotNull String name, @NotNull Object value) throws ScopeException {

    }

    @Override
    ScopeType scopeType() {
        return null
    }

}