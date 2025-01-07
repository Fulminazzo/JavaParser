package it.fulminazzo.javaparser.handler.elements.variables;

import it.fulminazzo.javaparser.handler.elements.ClassElement;
import it.fulminazzo.javaparser.handler.elements.Element;
import it.fulminazzo.javaparser.handler.elements.ParameterElements;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.VariableContainer;
import org.jetbrains.annotations.NotNull;

public final class ElementVariableContainer
        extends VariableContainer<ClassElement, Element, ParameterElements, Element>
        implements ElementContainer {

    /**
     * Instantiates a new Element variable container.
     *
     * @param container the container
     * @param type      the type
     * @param name      the name
     * @param variable  the value
     */
    public ElementVariableContainer(@NotNull Element container, @NotNull ClassElement type,
                                    @NotNull String name, @NotNull Element variable) {
        super(container, type, name, variable);
    }

    @Override
    public @NotNull Element set(@NotNull Element newValue) {
        return newValue;
    }

}
