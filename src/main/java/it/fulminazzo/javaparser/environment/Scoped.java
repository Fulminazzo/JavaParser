package it.fulminazzo.javaparser.environment;

import org.jetbrains.annotations.NotNull;

/**
 * An object that can hold values in the form (name, value) pair.
 *
 * @param <T> the type of the values
 */
interface Scoped<T> {

    /**
     * Throws an exception for a variable not declared.
     *
     * @param name the name of the variable
     * @throws ScopeException the scope exception
     */
    default void noSuchVariable(final @NotNull String name) throws ScopeException {
        throw new ScopeException("No such variable: " + name);
    }

    /**
     * Throws an exception for a variable already declared.
     *
     * @param name the name of the variable
     * @throws ScopeException the scope exception
     */
    default void alreadyDeclaredVariable(final @NotNull String name) throws ScopeException {
        throw new ScopeException("Variable already declared: " + name);
    }

}
