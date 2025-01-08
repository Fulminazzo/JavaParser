package it.fulminazzo.mojito.environment.scopetypes

import it.fulminazzo.mojito.environment.ScopeException
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
        actual.caughtExceptions == expectedClasses
    }

}