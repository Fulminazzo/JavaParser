package it.fulminazzo.javaparser.environment;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * An object that can hold values in the form (name, value) pair.
 *
 * @param <T> the type of the values
 */
interface Scoped<T> {

    /**
     * Searches for the given variable name.
     * If none is found, returns an empty {@link Optional}.
     *
     * @param name the name
     * @return the optional containing the value
     */
    @NotNull Optional<T> find(@NotNull final String name);

    /**
     * Finds the variable with the given name and returns its value.
     *
     * @param name the name of the variable
     * @return the value of the variable
     * @throws ScopeException thrown if the variable is not declared
     */
    default @NotNull T lookup(@NotNull String name) throws ScopeException {
        return find(name).orElseThrow(() -> noSuchVariable(name));
    }

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
     * Returns an exception for a variable not declared.
     *
     * @param name the name of the variable
     * @return the scope exception
     */
    default @NotNull ScopeException noSuchVariable(final @NotNull String name) {
        return new ScopeException("No such variable: " + name);
    }

    /**
     * Returns an exception for a variable already declared.
     *
     * @param name the name of the variable
     * @return the scope exception
     */
    default @NotNull ScopeException alreadyDeclaredVariable(final @NotNull String name) {
        return new ScopeException("Variable already declared: " + name);
    }

    /**
     * Returns the scope type of the current scope.
     *
     * @return the scope type
     */
    @NotNull ScopeType scopeType();

    /**
     * Checks that the scope type is the one given.
     *
     * @param scopeType the scope type
     * @return this object
     * @throws ScopeException thrown if the current scope type does not match
     */
    default Scoped<T> checkScopeType(final @NotNull ScopeType scopeType) throws ScopeException {
        if (!scopeType().equals(scopeType))
            throw new ScopeException("Scope type mismatch: " + scopeType() + " != " + scopeType);
        return this;
    }

    /**
     * Checks that the scope type is {@link ScopeType#CODE_BLOCK}.
     *
     * @return this object
     * @throws ScopeException thrown if the current scope type does not match
     */
    default Scoped<T> checkCodeBlock() throws ScopeException {
        return checkScopeType(ScopeType.CODE_BLOCK);
    }

    /**
     * Checks that the scope type is {@link ScopeType#SWITCH}.
     *
     * @return this object
     * @throws ScopeException thrown if the current scope type does not match
     */
    default Scoped<T> checkSwitch() throws ScopeException {
        return checkScopeType(ScopeType.SWITCH);
    }

    /**
     * Checks that the scope type is {@link ScopeType#CASE}.
     *
     * @return this object
     * @throws ScopeException thrown if the current scope type does not match
     */
    default Scoped<T> checkCase() throws ScopeException {
        return checkScopeType(ScopeType.CASE);
    }

    /**
     * Checks that the scope type is {@link ScopeType#FOR}.
     *
     * @return this object
     * @throws ScopeException thrown if the current scope type does not match
     */
    default Scoped<T> checkFor() throws ScopeException {
        return checkScopeType(ScopeType.FOR);
    }

    /**
     * Checks that the scope type is {@link ScopeType#WHILE}.
     *
     * @return this object
     * @throws ScopeException thrown if the current scope type does not match
     */
    default Scoped<T> checkWhile() throws ScopeException {
        return checkScopeType(ScopeType.WHILE);
    }

    /**
     * Checks that the scope type is {@link ScopeType#DO}.
     *
     * @return this object
     * @throws ScopeException thrown if the current scope type does not match
     */
    default Scoped<T> checkDo() throws ScopeException {
        return checkScopeType(ScopeType.DO);
    }

}
