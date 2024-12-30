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
     */
    private TypeCheckerException(final @NotNull String message) {
        super(message);
    }

    /**
     * Generates a new exception with an invalid type related message.
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

    /**
     * Generates a new exception with an invalid type related message.
     *
     * @param expected the expected type
     * @param actual   the actual type
     * @return the type checker exception
     */
    public static @NotNull TypeCheckerException invalidType(final @NotNull Class<? extends Type> expected,
                                                            final @NotNull Type actual) {
        return new TypeCheckerException(String.format("Invalid type received: expected type to be %s but was %s instead.",
                expected.getSimpleName(), actual.getClass().getSimpleName()));
    }

}
