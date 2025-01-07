package it.fulminazzo.javaparser.handler.elements.variables;

import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.handler.HandlerException;
import it.fulminazzo.javaparser.handler.elements.ClassElement;
import it.fulminazzo.javaparser.handler.elements.Element;
import it.fulminazzo.javaparser.handler.elements.ParameterElements;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.LiteralVariableContainer;
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
