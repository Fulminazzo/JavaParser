package it.fulminazzo.javaparser.environment;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An exception thrown by {@link Scoped} objects.
 */
public class ScopeException extends Exception {

    /**
     * Instantiates a new Scope exception.
     *
     * @param message the message
     */
    private ScopeException(final @NotNull String message) {
        super(message);
    }

    /**
     * Returns an exception for a variable not declared.
     *
     * @param name the name of the variable
     * @return the scope exception
     */
    public static @NotNull ScopeException noSuchVariable(final @NotNull String name) {
        return new ScopeException("No such variable: " + name);
    }

    /**
     * Returns an exception for a variable already declared.
     *
     * @param name the name of the variable
     * @return the scope exception
     */
    public static @NotNull ScopeException alreadyDeclaredVariable(final @NotNull String name) {
        return new ScopeException("Variable already declared: " + name);
    }

    /**
     * Returns an exception for a wrong scope type.
     *
     * @param scopeTypes the scope types
     * @return the scope exception
     */
    public static @NotNull ScopeException scopeTypeMismatch(final ScopeType @NotNull [] scopeTypes) {
        return new ScopeException(scopeTypes.length == 0 ?
                "Cannot compare current scope type with no types provided" :
                "Current scope does not match any of the expected types: " +
                        Arrays.stream(scopeTypes).map(ScopeType::name).collect(Collectors.joining(", "))
        );
    }

    public static <T> @NotNull ScopeException cannotAssignValue(final @NotNull T value,
                                                                final @NotNull Info info) {
        return new ScopeException(String.format("Cannot assign %s to %s", value, info));
    }

}
