package it.fulminazzo.javaparser.environment.scopetypes

import it.fulminazzo.javaparser.environment.ScopeException
import spock.lang.Specification

class ScopeTypeTest extends Specification {

    def 'test try scope'() {
        given:
        def classes = [IllegalArgumentException, ScopeException]

        and:
        def expected = new TryScopeType(classes.stream())
        def expectedClasses = new LinkedHashSet(classes)

        when:
        def actual = ScopeType.tryScope(classes.stream())

        then:
        actual == expected
        actual.getCaughtExceptions() == expectedClasses
    }

}