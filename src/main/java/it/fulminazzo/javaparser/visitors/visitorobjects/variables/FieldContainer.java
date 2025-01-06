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
        > extends VariableContainer<C, O, P> {

    /**
     * Instantiates a new Field container.
     *
     * @param type  the type
     * @param value the value
     */
    public FieldContainer(@NotNull C type, @NotNull O value) {
        super(type, value);
    }

}
