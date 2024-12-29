package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.fulmicollection.objects.Refl;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Represents a general class which provides various <code>static final</code> fields of the same type.
 * It provides useful methods for the lookup and retrieval of certain fields, similar to {@link Enum}.
 */
public abstract class EnumType extends TypeImpl {

    /**
     * Returns the name of the current object based on its field name.
     *
     * @return the name
     */
    public @NotNull String name() {
        Refl<?> refl = new Refl<>(getClass());
        for (final Field f : refl.getFields(f -> f.getType().isAssignableFrom(getClass()) &&
                Modifier.isStatic(f.getModifiers()))) {
            if (equals(refl.getFieldObject(f))) return f.getName();
        }
        throw new IllegalStateException("Unreachable code");
    }

    /**
     * Returns all the static fields of the given type.
     *
     * @param <E>             the type of the fields
     * @param fieldsContainer the class where the fields are stored
     * @param returnType      the type of the returned fields
     * @return an array containing all the fields
     */
    public static <E extends EnumType> E @NotNull [] values(final @NotNull Class<?> fieldsContainer,
                                                            final @NotNull Class<E> returnType) {

    }

    /**
     * Gets the most appropriate field with the given name.
     * Uses {@link #values(Class, Class)} and then {@link #name()}.
     * Throws {@link IllegalArgumentException} in case of error.
     *
     * @param <E>             the type of the field
     * @param fieldsContainer the class where the field is stored
     * @param returnType      the type of the returned fields
     * @param name            the name of the field
     * @return the field if found
     */
    public static <E extends EnumType> @NotNull E valueOf(final @NotNull Class<?> fieldsContainer,
                                                          final @NotNull Class<E> returnType,
                                                          final @NotNull String name) {
        for (final E value : values(fieldsContainer, returnType))
            if (value.name().equals(name))
                return value;
        throw new IllegalArgumentException(String.format("No enum constant %s.%s",
                fieldsContainer.getCanonicalName(), name));
    }


}
