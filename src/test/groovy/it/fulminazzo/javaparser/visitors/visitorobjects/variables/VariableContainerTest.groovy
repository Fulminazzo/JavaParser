package it.fulminazzo.javaparser.visitors.visitorobjects.variables

import it.fulminazzo.javaparser.handler.elements.ClassElement
import it.fulminazzo.javaparser.handler.elements.Element
import it.fulminazzo.javaparser.handler.elements.variables.ElementVariableContainer
import spock.lang.Specification

class VariableContainerTest extends Specification {
    private Element variable
    private ElementVariableContainer container

    void setup() {
        this.variable = Mock()
        this.container = new ElementVariableContainer(null, ClassElement.of(Double),
                'variable', this.variable)
    }

}
