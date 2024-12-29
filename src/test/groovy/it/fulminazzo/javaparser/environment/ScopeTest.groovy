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

    def 'update should throw ScopeException on invalid variable'() {
        when:
        this.scope.declare(new WrapperInfo<>(String), 'var', '1')
        this.scope.update('var', 10)

        then:
        def e = thrown(ScopeException)
        e.getMessage() == 'Cannot assign 10 to WrapperInfo(String)'
    }

    def 'test object data equality'() {
        given:
        def data = new Scope.ObjectData(new WrapperInfo<>(String), 'var')

        expect:
        data == new Scope.ObjectData(new WrapperInfo<>(String), 'var')
    }

    def 'test object data should not be equal to #other'() {
        given:
        def data = new Scope.ObjectData(new WrapperInfo<>(String), 'var')

        expect:
        data != other

        where:
        other << [
                new Scope.ObjectData(new WrapperInfo<>(Integer), 'var'),
                new Scope.ObjectData(new WrapperInfo<>(String), 'other'),
                new Scope.ObjectData(new WrapperInfo<>(Integer), 'other'),
                new WrapperInfo<>(Integer)
        ]
    }

    def 'test object data toString'() {
        given:
        def data = new Scope.ObjectData(new WrapperInfo<>(String), 'var')

        expect:
        data.toString() == "${Scope.ObjectData.simpleName}(${new WrapperInfo<>(String).toString()}, var)"
    }

}
