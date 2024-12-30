package it.fulminazzo.javaparser.typechecker;

import it.fulminazzo.javaparser.typechecker.types.Type;
import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by the {@link it.fulminazzo.javaparser.typechecker.TypeChecker}.
 */
public class TypeCheckerException extends RuntimeException {

    /**
     * Instantiates a new Type checker exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    private TypeCheckerException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

    /**
     * Generates a new exception with message:
     * <i>Invalid type received: expected %expected% but got %actual% instead.</i>
     *
     * @param expected the expected type
     * @param actual   the actual type
     * @return the type checker exception
     */
    public static @NotNull TypeCheckerException invalidType(final @NotNull Type expected,
                                                            final @NotNull Type actual) {
        return new TypeCheckerException(String.format("Invalid type received: expected %s but got %s instead.",
                expected, actual));
    }

}
