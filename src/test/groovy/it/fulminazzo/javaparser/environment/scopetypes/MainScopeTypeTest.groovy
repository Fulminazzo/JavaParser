package it.fulminazzo.javaparser.environment.scopetypes

import spock.lang.Specification

class MainScopeTypeTest extends Specification {
    private MainScopeType type

    void setup() {
        this.type = new MainScopeType()
    }

    def 'test name and toString should be equal'() {
        expect:
        this.type.toString() == this.type.name()
    }

    def 'test equal to other main scope type'() {
        given:
        def other = new MainScopeType()

        expect:
        this.type == other
        this.type.hashCode() == other.hashCode()
    }

    def 'test main scope type should not be equal to #other'() {
        expect:
        !this.type.equals(other)

        where:
        other << [
                null,
                BaseScopeType.values(),
                new ScopeType() {

                    @Override
                    String name() {
                        return "MAIN"
                    }

                },
                new ScopeType() {

                    @Override
                    String name() {
                        return "MAIN"
                    }

                    @Override
                    boolean equals(Object o) {
                        return true
                    }

                    @Override
                    int hashCode() {
                        return MainScopeType.hashCode()
                    }

                    @Override
                    String toString() {
                        return name()
                    }

                },
        ].flatten()
    }

}