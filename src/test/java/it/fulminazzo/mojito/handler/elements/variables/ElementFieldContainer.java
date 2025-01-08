package it.fulminazzo.mojito.handler.elements.variables;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.mojito.handler.elements.ClassElement;
import it.fulminazzo.mojito.handler.elements.Element;
import it.fulminazzo.mojito.handler.elements.ParameterElements;
import it.fulminazzo.mojito.visitors.visitorobjects.variables.FieldContainer;
import org.jetbrains.annotations.NotNull;

public final class ElementFieldContainer
        extends FieldContainer<ClassElement, Element, ParameterElements>
        implements ElementContainer {

    /**
     * Instantiates a new Element field container.
     *
     * @param parent   the object where the field is contained
     * @param type     the type of the field
     * @param name     the name of the field
     * @param variable the value of the field
     */
    public ElementFieldContainer(@NotNull Element parent, @NotNull ClassElement type, @NotNull String name, @NotNull Element variable) {
        super(parent, type, name, variable);
    }

    @Override
    public @NotNull Element set(@NotNull Element newValue) {
        new Refl<>(this.container.getElement()).setFieldObject(this.name, newValue.getElement());
        return this.type.cast(newValue);
    }

}
