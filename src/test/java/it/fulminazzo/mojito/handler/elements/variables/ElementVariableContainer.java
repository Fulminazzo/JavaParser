package it.fulminazzo.mojito.handler.elements.variables;

import it.fulminazzo.mojito.handler.elements.ClassElement;
import it.fulminazzo.mojito.handler.elements.Element;
import it.fulminazzo.mojito.handler.elements.ParameterElements;
import it.fulminazzo.mojito.visitors.visitorobjects.variables.VariableContainer;
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
