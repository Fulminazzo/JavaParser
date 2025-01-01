package it.fulminazzo.javaparser.environment.scopetypes

import it.fulminazzo.javaparser.environment.ScopeException
import spock.lang.Specification

class TryScopeTypeTest extends Specification {
    private TryScopeType type

    void setup() {
        this.type = new TryScopeType([
                IllegalArgumentException, IllegalStateException,
                IllegalAccessException, ScopeException
        ].stream())
    }

    def 'test name and toString should be equal'() {
        expect:
        this.type.toString() == this.type.name()
    }

    def 'test try scope type should be equal to #other'() {
        expect:
        this.type == other

        where:
        other << [
                new TryScopeType([
                        IllegalArgumentException, IllegalStateException,
                        IllegalAccessException, ScopeException
                ].stream()),
                new TryScopeType([
                        ScopeException, IllegalAccessException,
                        IllegalStateException, IllegalArgumentException
                ].stream()),
                new TryScopeType([
                        ScopeException, IllegalStateException,
                        IllegalAccessException, IllegalArgumentException
                ].stream()),
        ]
    }

}