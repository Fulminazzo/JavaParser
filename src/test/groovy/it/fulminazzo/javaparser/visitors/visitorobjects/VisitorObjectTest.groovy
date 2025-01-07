package it.fulminazzo.javaparser.visitors.visitorobjects

import it.fulminazzo.javaparser.handler.elements.ClassElement
import it.fulminazzo.javaparser.handler.elements.Element
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
