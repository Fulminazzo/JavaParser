package it.fulminazzo.javaparser.typechecker;

import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by the {@link it.fulminazzo.javaparser.typechecker.TypeChecker}.
 */
public class TypeCheckerException extends RuntimeException {

    /**
     * Instantiates a new Type checker exception.
     *
     * @param message the message
     */
    private TypeCheckerException(final @NotNull String message) {
        super(message);
    }

}
