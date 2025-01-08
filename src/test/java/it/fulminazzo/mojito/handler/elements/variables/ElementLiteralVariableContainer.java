package it.fulminazzo.mojito.handler.elements.variables;

import it.fulminazzo.mojito.environment.Environment;
import it.fulminazzo.mojito.handler.HandlerException;
import it.fulminazzo.mojito.handler.elements.ClassElement;
import it.fulminazzo.mojito.handler.elements.Element;
import it.fulminazzo.mojito.handler.elements.ParameterElements;
import it.fulminazzo.mojito.visitors.visitorobjects.variables.LiteralVariableContainer;
import org.jetbrains.annotations.NotNull;

public final class ElementLiteralVariableContainer
        extends LiteralVariableContainer<ClassElement, Element, ParameterElements>
        implements ElementContainer {

    /**
     * Instantiates a new Element literal variable container.
     *
     * @param environment the environment
     * @param type        the type
     * @param name        the name
     * @param value       the value
     */
    public ElementLiteralVariableContainer(@NotNull Environment<Element> environment, @NotNull ClassElement type,
                                           @NotNull String name, @NotNull Element value) {
        super(environment, type, name, value);
    }

    @Override
    protected @NotNull RuntimeException exceptionWrapper(@NotNull Exception exception) {
        return new HandlerException(exception);
    }

}
