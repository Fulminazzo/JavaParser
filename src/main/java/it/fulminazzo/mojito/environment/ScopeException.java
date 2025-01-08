package it.fulminazzo.mojito.environment;

import it.fulminazzo.mojito.environment.scopetypes.ScopeType;
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
     * Generates a {@link ScopeException} with message:
     * <i>No such variable: %name%</i>
     *
     * @param name the name of the variable
     * @return the scope exception
     */
    public static @NotNull ScopeException noSuchVariable(final @NotNull NamedEntity name) {
        return new ScopeException("No such variable: " + name.getName());
    }

    /**
     * Generates a {@link ScopeException} with message:
     * <i>Variable already declared: %name%</i>
     *
     * @param name the name of the variable
     * @return the scope exception
     */
    public static @NotNull ScopeException alreadyDeclaredVariable(final @NotNull NamedEntity name) {
        return new ScopeException("Variable already declared: " + name.getName());
    }

    /**
     * Generates two {@link ScopeException} based on the passed {@link ScopeType}s:
     * <ul>
     *     <li>if it is empty, <i>Cannot compare current scope type with no types provided</i>;</li>
     *     <li>else, <i>Current scope does not match any of the expected types: %scopeTypes%</i></li>
     * </ul>
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

    /**
     * Generates a {@link ScopeException} with message:
     * <i>Cannot assign %value% to %info%</i>
     *
     * @param <T>   the type of the value
     * @param value the value
     * @param info  the info associated with the value
     * @return the scope exception
     */
    public static <T> @NotNull ScopeException cannotAssignValue(final @NotNull T value,
                                                                final @NotNull Info info) {
        return new ScopeException(String.format("Cannot assign %s to %s", value, info));
    }

}
