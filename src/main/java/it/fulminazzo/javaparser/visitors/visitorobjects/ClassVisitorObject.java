package it.fulminazzo.javaparser.visitors.visitorobjects;

import it.fulminazzo.javaparser.environment.Info;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the associated class of a {@link VisitorObject}.
 */
public interface ClassVisitorObject extends VisitorObject, Info {

    /**
     * Checks and converts the given object to the current class.
     *
     * @param object the object
     * @return the associated object of this class
     */
    @NotNull VisitorObject cast(final @NotNull VisitorObject object);

    /**
     * Creates a new object from the current class with the parameters
     * as constructor parameters.
     *
     * @param <V>        the type of the parameters
     * @param parameters the parameters
     * @return the object associated with this class
     */
    <V extends VisitorObject> @NotNull VisitorObject newObject(final @NotNull ParameterVisitorObjects<V> parameters);

    /**
     * Checks if the current class is extending the provided class.
     *
     * @param classObject the class object
     * @return true if it is
     */
    boolean isExtending(final @NotNull ClassVisitorObject classObject);

    /**
     * Verifies that the current class is compatible with the provided object.
     *
     * @param object the object
     * @return true if it is
     */
    boolean compatibleWith(final @NotNull VisitorObject object);

    @Override
    default boolean compatibleWith(final @NotNull Object object) {
        return object instanceof VisitorObject && compatibleWith((VisitorObject) object);
    }

}
