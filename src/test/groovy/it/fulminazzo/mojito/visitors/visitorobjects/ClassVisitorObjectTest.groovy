package it.fulminazzo.mojito.visitors.visitorobjects

import it.fulminazzo.mojito.handler.elements.ClassElement
import spock.lang.Specification

class ClassVisitorObjectTest extends Specification {

    def 'test class visitor object should only be compatible with a visitor object instance'() {
        expect:
        !ClassElement.of(String).compatibleWith(null)
    }

}
