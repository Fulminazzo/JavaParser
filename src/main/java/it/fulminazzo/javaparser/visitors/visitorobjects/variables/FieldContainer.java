package it.fulminazzo.javaparser.visitors.visitorobjects.variables;

import it.fulminazzo.javaparser.visitors.visitorobjects.ClassVisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.ParameterVisitorObjects;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the field of an object.
 *
 * @param <C> the type of the {@link ClassVisitorObject}
 * @param <O> the type of the {@link VisitorObject}
 * @param <P> the type of the {@link ParameterVisitorObjects}
 */
public abstract class FieldContainer<
        C extends ClassVisitorObject<C, O, P>,
        O extends VisitorObject<C, O, P>,
        P extends ParameterVisitorObjects<C, O, P>
        > extends VariableContainer<C, O, P, O> {

    /**
     * Instantiates a new Field container.
     *
     * @param parent   the object where the field is contained
     * @param type     the type of the field
     * @param name     the name of the field
     * @param variable the value of the field
     */
    public FieldContainer(final @NotNull O parent, final @NotNull C type,
                          final @NotNull String name, final @NotNull O variable) {
        super(parent, type, name, variable);
    }

}
