package it.fulminazzo.javaparser.typechecker.types;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

/**
 * An exception thrown by {@link Type} objects.
 */
public class TypeException extends Exception {

    /**
     * Instantiates a new Type exception.
     *
     * @param message the message
     */
    private TypeException(final @NotNull String message) {
        super(message);
    }

    /**
     * Generates a {@link TypeException} with message:
     * <i>Could not find class %clazz%</i>
     *
     * @param clazz the clazz
     * @return the type exception
     */
    public static TypeException classNotFound(final @NotNull String clazz) {
        return new TypeException("Could not find class: " + clazz);
    }

    /**
     * Generates a {@link TypeException} with message:
     * <i>Could not find field %field%</i>
     *
     * @param field the field
     * @return the type exception
     */
    public static TypeException fieldNotFound(final @NotNull String field) {
        return new TypeException("Could not find field: " + field);
    }

    /**
     * Generates a {@link TypeException} with message:
     * <i>Cannot access field %field% with access-level: %access-level%</i>
     *
     * @param field the field
     * @return the type exception
     */
    public static TypeException cannotAccessField(final @NotNull Field field) {
        return new TypeException(String.format("Cannot access field %s with access level: %s",
                field.getName(), field.getModifiers()));
    }

}
