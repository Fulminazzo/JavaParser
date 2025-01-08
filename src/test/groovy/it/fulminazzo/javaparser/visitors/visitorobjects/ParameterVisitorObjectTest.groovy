package it.fulminazzo.javaparser.visitors.visitorobjects

import it.fulminazzo.javaparser.TestClass
import it.fulminazzo.javaparser.handler.HandlerException
import it.fulminazzo.javaparser.handler.elements.Element
import it.fulminazzo.javaparser.handler.elements.ParameterElements
import spock.lang.Specification

class ParameterVisitorObjectTest extends Specification {
    private ParameterElements parameters

    void setup() {
        this.parameters = new ParameterElements([
                Element.of(1), Element.of(2.0d), Element.of(3.0f),
                Element.of('Hello'), Element.of(new TestClass()), Element.of(null),
        ])
    }

    def 'test toJavaClassArray'() {
        given:
        def expected = new Class[]{Integer, Double, Float, String, TestClass, null}

        when:
        def actual = this.parameters.toJavaClassArray()

        then:
        actual == expected
    }

    def 'test toClass should throw'() {
        when:
        this.parameters.toClass()

        then:
        def e = thrown(HandlerException)
        e.message == this.parameters.noClassType(ParameterElements).message
    }

}
