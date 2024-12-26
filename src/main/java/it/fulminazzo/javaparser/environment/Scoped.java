package it.fulminazzo.javaparser.environment;

import org.jetbrains.annotations.NotNull;

/**
 * An object that can hold values in the form (name, value) pair.
 *
 * @param <T> the type of the values
 */
interface Scoped<T> {

    /**
     * Finds the variable with the given name and returns its value.
     *
     * @param name the name of the variable
     * @return the value of the variable
     * @throws ScopeException thrown if the variable is not declared
     */
    @NotNull T lookup(@NotNull String name) throws ScopeException;

    /**
     * Defines a variable with the given name and value.
     *
     * @param name  the name
     * @param value the value
     * @throws ScopeException thrown if the variable is already declared
     */
    void define(@NotNull String name, @NotNull T value) throws ScopeException;

    /**
     * Updates the value of a variable.
     *
     * @param name  the name of the variable
     * @param value the new value
     * @throws ScopeException thrown if the variable is not declared
     */
    void update(@NotNull String name, @NotNull T value) throws ScopeException;

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
