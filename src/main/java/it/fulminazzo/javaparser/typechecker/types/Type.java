package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Represents a general type parsed by the {@link it.fulminazzo.javaparser.typechecker.TypeChecker}.
 */
public interface Type {

    /**
     * Checks whether the current type is a basic value of Java (from {@link ValueType}).
     *
     * @return true if it is
     */
    default boolean isValue() {
        return is(ValueType.class);
    }

    /**
     * Checks whether the current type is a {@link ClassType}.
     *
     * @return true if it is
     */
    default boolean isClassType() {
        return is(ClassType.class);
    }

    /**
     * Checks whether the current type is of the one specified.
     *
     * @param <T>  the type
     * @param type the class of the type
     * @return true if it is
     */
    default <T extends Type> boolean is(final Class<T> type) {
        return getClass().isAssignableFrom(type);
    }

    /**
     * Checks whether the current type is equal to any of the ones given.
     *
     * @param types the types
     * @return true if it is for at least one of them
     */
    default boolean is(final Type @NotNull ... types) {
        return Arrays.asList(types).contains(this);
    }

    /**
     * Checks that the current type is of the specified one.
     * Throws {@link TypeCheckerException} in case it is not.
     *
     * @param <T>  the class of the type
     * @param type the expected type
     * @return the current type cast to the expected one
     */
    default <T extends Type> T check(final @NotNull T type) {
        if (is(type)) return type;
        else throw TypeCheckerException.invalidType(type, this);
    }

    /**
     * Checks whether the current type is equal to one of the expected ones.
     * Throws a {@link TypeCheckerException} in case no match is found.
     *
     * @param expectedTypes the expected types
     * @return this type
     */
    default Type check(final Type @NotNull ... expectedTypes) {
        if (expectedTypes.length == 0)
            throw new IllegalArgumentException(String.format("Cannot compare type %s with no types", this));
        for (Type expectedType : expectedTypes)
            if (is(expectedType)) return this;
        throw TypeCheckerException.invalidType(expectedTypes[0], this);
    }

    /**
     * Checks whether the current type extends the given {@link ClassType}.
     *
     * @param classType the class type
     * @return true if it is
     */
    default boolean isAssignableFrom(final @NotNull ClassType classType) {
        return classType.compatibleWith(this);
    }

    /**
     * Searches the given field in the class of {@link #toClassType()}.
     * Then, returns the declared type of the field in form of {@link ClassType}.
     *
     * @param fieldName the field name
     * @return the type of the field
     * @throws TypeException thrown in case the field could not be found or could not be accessed
     * (only <code>public</code> modifier allowed and <code>static</code> fields from static context)
     */
    default @NotNull ClassType getField(final @NotNull String fieldName) throws TypeException {
        ClassType classType = isClassType() ? (ClassType) this : toClassType();
        try {
            Class<?> javaClass = classType.toJavaClass();
            Field field = javaClass.getDeclaredField(fieldName);
            if (!Modifier.isPublic(field.getModifiers())) throw TypeException.cannotAccessField(classType, field);
            else if (isClassType() && !Modifier.isStatic(field.getModifiers()))
                throw TypeException.cannotAccessStaticField(classType, fieldName);
            return ClassType.of(field.getType());
        } catch (NoSuchFieldException e) {
            throw TypeException.fieldNotFound(classType, fieldName);
        }
    }

    /**
     * Gets the class type associated with the current type.
     *
     * @return the class type
     */
    @NotNull ClassType toClassType();

}
