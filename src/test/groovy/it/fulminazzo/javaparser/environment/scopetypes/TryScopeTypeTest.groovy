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

}