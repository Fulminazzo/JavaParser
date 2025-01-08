package it.fulminazzo.mojito.handler.elements

import spock.lang.Specification

class ElementImplTest extends Specification {

    def 'test two elements with element #element should equal'() {
        given:
        def first = Element.of(element)
        def second = Element.of(element)

        expect:
        first == second

        where:
        element << [1, 2L, 3.0, 4.0d, 5.0f, true, false, 'a' as char, 'Hello, world!', this]
    }

}
