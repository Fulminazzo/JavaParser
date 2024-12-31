package it.fulminazzo.javaparser.environment

import it.fulminazzo.javaparser.utils.TestUtils
import spock.lang.Specification

class ScopedTest extends Specification {

    def 'verify Scoped has check method for ScopeType #scopeType'() {
        given:
        def methodName = "check${TestUtils.convertEnumName(scopeType)}"

        when:
        def method = Scoped.class.getDeclaredMethods()
                .findAll { it.name == methodName }
                .find { it.parameterCount == 0 }

        then:
        if (method == null)
            TestUtils.generateMethod(Scoped.class, scopeType,
                    s -> [
                            "Checks that the scope type is {@link ${ScopeType.simpleName}#${s}}.",
                            '',
                            '@return this object',
                            "@throws ${ScopeException.simpleName} thrown if the current scope type does not match",
                    ],
                    'default', "@NotNull ${Scoped.simpleName}<T>",
                    s -> "check${s}",
                    ScopeException,
                    e -> ["return checkScopeType(${ScopeType.simpleName}.${e});"])
        method != null

        where:
        scopeType << ScopeType.values().findAll { it != ScopeType.MAIN }
    }

}
