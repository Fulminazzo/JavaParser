package it.fulminazzo.javaparser.visitors.visitorobjects;

import it.fulminazzo.javaparser.environment.Info;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the associated class of a {@link VisitorObject}.
 *
 * @param <C> the type of the {@link ClassVisitorObject}
 * @param <O> the type of the {@link VisitorObject}
 * @param <P> the type of the {@link ParameterVisitorObjects}
 */
public interface ClassVisitorObject<
        C extends ClassVisitorObject<C, O, P>,
        O extends VisitorObject<C, O, P>,
        P extends ParameterVisitorObjects<C, O, P>
        >
        extends VisitorObject<C, O, P>, Info {

    /**
     * Checks and converts the given object to the current class.
     *
     * @param object the object
     * @return the associated object of this class
     */
    @NotNull O cast(final @NotNull O object);

    /**
     * Creates a new object from the current class with the parameters
     * as constructor parameters.
     *
     * @param parameters the parameters
     * @return the object associated with this class
     */
    @NotNull O newObject(final @NotNull P parameters);

    /**
     * Checks if the current class is extending the provided class.
     *
     * @param classObject the class object
     * @return true if it is
     */
    boolean isExtending(final @NotNull C classObject);

    /**
     * Verifies that the current class is compatible with the provided object.
     *
     * @param object the object
     * @return true if it is
     */
    boolean compatibleWith(final @NotNull O object);

    @SuppressWarnings("unchecked")
    @Override
    default boolean compatibleWith(final @NotNull Object object) {
        return object.getClass().equals(getClass()) && compatibleWith((O) object);
    }

}
