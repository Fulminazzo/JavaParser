package it.fulminazzo.javaparser.typechecker.types;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a general type parsed by the {@link it.fulminazzo.javaparser.typechecker.TypeChecker}.
 */
public interface Type {

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
