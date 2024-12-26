package it.fulminazzo.javaparser.environment;

import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by {@link Scoped} objects.
 */
class ScopeException extends RuntimeException {

    /**
     * Instantiates a new Scope exception.
     *
     * @param message the message
     */
    public ScopeException(final @NotNull String message) {
        super(message);
    }

}
