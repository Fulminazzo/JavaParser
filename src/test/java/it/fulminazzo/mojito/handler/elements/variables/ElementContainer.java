package it.fulminazzo.mojito.handler.elements.variables;

import it.fulminazzo.mojito.handler.elements.ClassElement;
import it.fulminazzo.mojito.handler.elements.Element;
import org.jetbrains.annotations.NotNull;

interface ElementContainer extends Element {

    @Override
    default Object getElement() {
        return getVariable().getElement();
    }

    @Override
    @NotNull
    default ClassElement toClass() {
        return getVariable().toClass();
    }

    @NotNull Element getVariable();

}
