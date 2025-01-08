package it.fulminazzo.mojito.visitors

import it.fulminazzo.mojito.environment.MockEnvironment
import it.fulminazzo.mojito.handler.Handler
import it.fulminazzo.mojito.handler.HandlerException
import it.fulminazzo.mojito.handler.elements.ClassElement
import it.fulminazzo.mojito.handler.elements.Element
import it.fulminazzo.mojito.TestClass
import spock.lang.Specification

class VisitorLiteralTest extends Specification {
    private Visitor visitor
    private MockEnvironment environment

    void setup() {
        this.visitor = new Handler(new TestClass())
        this.environment = this.visitor.environment as MockEnvironment
    }

    def 'test visitLiteralImpl of #value should return #expected'() {
        given:
        this.environment.declare(ClassElement.of(Integer), 'i', Element.of(1))

        when:
        def element = this.visitor.visitLiteralImpl(value)

        then:
        element == expected

        where:
        value                                       | expected
        'i'                                         | Element.of(1)
        'System'                                    | ClassElement.of(System)
        'System.out'                                | Element.of(System.out)
        "${SystemWrapper.canonicalName}.system.out" | Element.of(SystemWrapper.system.out)
    }

    def 'test visitLiteralImpl of #value should throw exception #exception'() {
        when:
        this.visitor.visitLiteralImpl(value)

        then:
        def e = thrown(exception.class)
        e.message == exception.message

        where:
        value                 | exception
        'System.inexisting'   | new HandlerException(Element.of(null).fieldNotFound(ClassElement.of(System), 'inexisting'))
        'System.not.existing' | new HandlerException(Element.of(null).fieldNotFound(ClassElement.of(System), 'not'))
        'not.existing'        | new Handler(null).cannotResolveSymbol(value)
    }

    class SystemWrapper {
        static System system = new System()

    }

}
