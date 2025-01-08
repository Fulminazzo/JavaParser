package it.fulminazzo.mojito.visitors.visitorobjects;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.mojito.environment.Info;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the associated class of a {@link VisitorObject}.
 *
 * @param <C> the type of the {@link ClassVisitorObject}
 * @param <O> the type of the {@link VisitorObject}
 * @param <P> the type of the {@link ParameterVisitorObjects}
 */
@SuppressWarnings("unchecked")
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
     * Verifies that the current class is compatible with the provided object.
     *
     * @param object the object
     * @return true if it is
     */
    boolean compatibleWith(final @NotNull O object);

    @SuppressWarnings("unchecked")
    @Override
    default boolean compatibleWith(final @NotNull Object object) {
        return object instanceof VisitorObject<?, ?, ?> && compatibleWith((O) object);
    }

    /**
     * Creates a new object from the current class with the parameters
     * as constructor parameters.
     *
     * @param parameters the parameters
     * @return the object associated with this class
     * @throws VisitorObjectException the exception thrown in case of errors
     */
    default @NotNull O newObject(final @NotNull P parameters) throws VisitorObjectException {
        final String methodName = "<init>";
        final C classVisitorObject = (C) this;
        try {
            Class<?> javaClass = classVisitorObject.toJavaClass();
            // Lookup constructors from parameters count
            @NotNull List<Constructor<?>> constructors = Arrays.stream(javaClass.getDeclaredConstructors())
                    .filter(c -> VisitorObjectUtils.verifyExecutable(parameters, c))
                    .collect(Collectors.toList());
            if (constructors.isEmpty()) throw new IllegalArgumentException();

            Refl<?> refl = new Refl<>(ReflectionUtils.class);
            Class<?> @NotNull [] parametersTypes = parameters.toJavaClassArray();

            for (Constructor<?> constructor : constructors) {
                // For each one, validate its parameters
                if (Boolean.TRUE.equals(refl.invokeMethod("validateParameters",
                        new Class[]{Class[].class, Executable.class},
                        parametersTypes, constructor)))
                    return newObject(constructor, parameters);
            }

            throw typesMismatch(classVisitorObject, constructors.get(0), parameters);
        } catch (IllegalArgumentException e) {
            throw methodNotFound(classVisitorObject, methodName, parameters);
        }
    }

    /**
     * Creates a new object from the current class with
     * the given constructor and parameters.
     *
     * @param parameters the parameters
     * @return the object associated with this class
     * @throws VisitorObjectException the exception thrown in case of errors
     */
    @NotNull O newObject(final @NotNull Constructor<?> constructor,
                         final @NotNull P parameters) throws VisitorObjectException;

    /**
     * Converts the current class visitor object to its associated class.
     *
     * @return the class
     */
    @NotNull Class<?> toJavaClass();

    /**
     * Converts the current class object to the appropriate {@link VisitorObject}.
     *
     * @return the object
     */
    @NotNull O toObject();

}
