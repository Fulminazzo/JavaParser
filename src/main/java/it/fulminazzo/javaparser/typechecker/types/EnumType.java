package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.fulmicollection.objects.Refl;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a general class which provides various <code>static final</code> fields of the same type.
 * It provides useful methods for the lookup and retrieval of certain fields, similar to {@link Enum}.
 */
public interface EnumType extends Type {

    /**
     * Returns the name of the current object based on its field name.
     *
     * @return the name
     */
    @NotNull String name();

    /**
     * Returns the name of the current object based on its field name.
     *
     * @param fieldsContainer the class where the field is stored
     * @return the name
     */
    default @NotNull String name(final @NotNull Class<?> fieldsContainer) {
        Refl<?> refl = new Refl<>(fieldsContainer);
        for (final Field f : getFields(fieldsContainer))
            if (equals(refl.getFieldObject(f))) return f.getName();
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
    @SuppressWarnings("unchecked")
    static <E extends EnumType> E @NotNull [] values(final @NotNull Class<?> fieldsContainer,
                                                     final @NotNull Class<E> returnType) {
        Refl<?> refl = new Refl<>(fieldsContainer);
        List<E> values = new ArrayList<>();
        for (final Field f : getFields(fieldsContainer))
            values.add(refl.getFieldObject(f));
        return values.toArray((E[]) Array.newInstance(returnType, values.size()));
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
    static <E extends EnumType> @NotNull E valueOf(final @NotNull Class<?> fieldsContainer,
                                                   final @NotNull Class<E> returnType,
                                                   final @NotNull String name) {
        for (final E value : values(fieldsContainer, returnType))
            if (value.name().equals(name))
                return value;
        throw new IllegalArgumentException(String.format("No enum constant %s.%s",
                fieldsContainer.getCanonicalName(), name));
    }

    /**
     * Gets the fields from the container class.
     *
     * @param fieldsContainer the class where the field is stored
     * @return the fields
     */
    default @NotNull List<Field> getFields(final @NotNull Class<?> fieldsContainer) {
        Refl<?> refl = new Refl<>(fieldsContainer);
        return refl.getFields(f -> Modifier.isStatic(f.getModifiers()) &&
                (f.getType().isAssignableFrom(getClass()) || getClass().isAssignableFrom(f.getType())));
    }


}
