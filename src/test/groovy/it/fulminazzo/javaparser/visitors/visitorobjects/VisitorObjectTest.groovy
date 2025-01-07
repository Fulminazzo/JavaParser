package it.fulminazzo.javaparser.visitors.visitorobjects

import it.fulminazzo.javaparser.handler.elements.ClassElement
import it.fulminazzo.javaparser.handler.elements.Element
import it.fulminazzo.javaparser.handler.elements.ParameterElements
import org.jetbrains.annotations.NotNull
import spock.lang.Specification

class VisitorObjectTest extends Specification {

    def 'test getField of primitive'() {
        given:
        def element = new PrimitiveElement(1)

        when:
        def val = element.getField('MAX_VALUE')

        then:
        val.variable.element == Integer.MAX_VALUE
    }

    def 'test invokeMethod of primitive'() {
        given:
        def element = new PrimitiveElement(1)

        when:
        def val = element.invokeMethod('toString', new ParameterElements([]))

        then:
        val.element == '1'
    }

    static class PrimitiveElement<T> implements Element {
        private final T element;

        PrimitiveElement(T element) {
            this.element = element;
        }

        @Override
        Element toWrapper() {
            return Element.of(this.element)
        }

        @Override
        boolean isPrimitive() {
            return true;
        }

        @Override
        Object getElement() {
            return this.element;
        }

        @Override
        @NotNull ClassElement toClass() {
            return ClassElement.of(this.element.getClass());
        }

    }

}
