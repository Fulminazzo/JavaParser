package it.fulminazzo.mojito.handler.elements;

import it.fulminazzo.mojito.visitors.visitorobjects.ParameterVisitorObjects;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ParameterElements extends ParameterVisitorObjects<ClassElement, Element, ParameterElements> implements Element {

    /**
     * Instantiates a new Parameter elements.
     *
     * @param parameters the parameters
     */
    public ParameterElements(@NotNull List<Element> parameters) {
        super(parameters);
    }

    @Override
    public Object getElement() {
        return this.object;
    }

}
