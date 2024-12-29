package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import org.jetbrains.annotations.NotNull;

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
        return this instanceof ValueType;
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

}
