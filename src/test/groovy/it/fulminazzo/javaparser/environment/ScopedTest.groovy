package it.fulminazzo.javaparser.environment

import it.fulminazzo.fulmicollection.utils.StringUtils
import spock.lang.Specification

class ScopedTest extends Specification {

    def 'verify Scoped has check method for ScopeType #scopeType'() {
        given:
        def methodName = "check${StringUtils.capitalize(scopeType.name())}"
                .replace('_', '')

        when:
        def method = Scoped.class.getDeclaredMethods()
                .findAll { it.name == methodName }
                .find { it.parameterCount == 0 }

        then:
        method != null

        where:
        scopeType << ScopeType
    }

}