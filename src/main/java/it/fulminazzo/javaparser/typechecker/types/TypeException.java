package it.fulminazzo.javaparser.typechecker.types;

import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by {@link Type} objects.
 */
public class TypeException extends Exception {

    /**
     * Instantiates a new Type exception.
     *
     * @param message the message
     */
    public TypeException(final @NotNull String message) {
        super(message);
    }

}
