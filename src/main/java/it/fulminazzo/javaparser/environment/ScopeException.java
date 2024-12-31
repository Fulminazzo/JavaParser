package it.fulminazzo.javaparser.environment;

import org.jetbrains.annotations.NotNull;

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

}
