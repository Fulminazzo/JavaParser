package it.fulminazzo.javaparser.typechecker.types;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * An exception thrown by {@link Type} objects.
 */
public class TypeException extends Exception {

    /**
     * Instantiates a new Type exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    private TypeException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

    /**
     * Generates a {@link TypeException} with message:
     * <i>Could not find class: %clazz%</i>
     *
     * @param clazz the clazz
     * @return the type exception
     */
    public static @NotNull TypeException classNotFound(final @NotNull String clazz) {
        return new TypeException("Could not find class: " + clazz);
    }

    /**
     * Generates a {@link TypeException} with message:
     * <i>Could not find field '%field%' in type %type%</i>
     *
     * @param type  the type
     * @param field the field
     * @return the type exception
     */
    public static @NotNull TypeException fieldNotFound(final @NotNull ClassType type,
                                                       final @NotNull String field) {
        return new TypeException(String.format("Could not find field '%s' in type %s",
                field, type));
    }

    /**
     * Generates a {@link TypeException} with message:
     * <i>Type %type% cannot access field '%field%' with access-level '%access-level%'</i>
     *
     * @param type  the type
     * @param field the field
     * @return the type exception
     */
    public static @NotNull TypeException cannotAccessField(final @NotNull ClassType type,
                                                           final @NotNull Field field) {
        return new TypeException(String.format("Type %s cannot access field '%s' with access level '%s'",
                type, field.getName(), getVisibilityModifier(field)));
    }

    /**
     * Generates a {@link TypeException} with message:
     * <i>Type %type% cannot access non-static field '%field%' from a static context</i>
     *
     * @param type  the type
     * @param field the field
     * @return the type exception
     */
    public static @NotNull TypeException cannotAccessStaticField(final @NotNull ClassType type,
                                                                 final @NotNull String field) {
        return new TypeException(String.format("Type %s cannot access non-static field '%s' from a static context",
                type, field));
    }

    /**
     * Gets the visibility modifier from the given field in a string format.
     *
     * @param field the field
     * @return the visibility modifier
     */
    static @NotNull String getVisibilityModifier(final @NotNull Field field) {
        int modifiers = field.getModifiers();
        if (Modifier.isProtected(modifiers)) return "protected";
        else if (Modifier.isPublic(modifiers)) return "public";
        else if (Modifier.isPrivate(modifiers)) return "private";
        else return "package";
    }

}
