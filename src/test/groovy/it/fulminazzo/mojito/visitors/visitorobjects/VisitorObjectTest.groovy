package it.fulminazzo.mojito.visitors.visitorobjects

import it.fulminazzo.mojito.handler.elements.ClassElement
import it.fulminazzo.mojito.handler.elements.Element
import it.fulminazzo.mojito.handler.elements.ParameterElements
import org.jetbrains.annotations.NotNull
import spock.lang.Specification

class VisitorObjectTest extends Specification {

    def 'test #object isAssignableFrom #clazz should return #expected'() {
        when:
        def actual = object.isAssignableFrom(clazz)

        then:
        actual == expected

        where:
        object           | clazz                    | expected
        Element.of(1.0d) | ClassElement.of(Double)  | true
        Element.of(1.0d) | ClassElement.of(Integer) | false
    }

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
        private final T element

        PrimitiveElement(T element) {
            this.element = element
        }

        @NotNull
        @Override
        Element toWrapper() {
            return Element.of(this.element)
        }

        @Override
        boolean isPrimitive() {
            return true
        }

        @Override
        Object getElement() {
            return this.element
        }

        @Override
        @NotNull
        ClassElement toClass() {
            return ClassElement.of(this.element.getClass())
        }

    }

}
