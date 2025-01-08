package it.fulminazzo.mojito;

import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by {@link Runner}.
 */
final class RunnerException extends RuntimeException {

    /**
     * Instantiates a new Runner exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    RunnerException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

    /**
     * Instantiates a new Runner exception.
     *
     * @param message the message
     * @param cause   the throwable that caused the exception
     */
    RunnerException(final @NotNull String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Generates a {@link RunnerException} with message:
     * <i>Could not find file: %filePath%</i>
     *
     * @param filePath the path of the file
     * @return the executor exception
     */
    public static @NotNull RunnerException cannotFindFile(final @NotNull String filePath) {
        return new RunnerException("Could not find file: %s", filePath);
    }

    /**
     * Generates a {@link RunnerException} with message:
     * <i>A %cause% occurred during execution:</i>
     * <br>
     * with the actual exception wrapped.
     *
     * @param cause   the cause
     * @return runner exception
     */
    public static @NotNull RunnerException of(final Throwable cause) {
        return new RunnerException(String.format("A %s occurred during execution", cause.getClass().getSimpleName()), cause);
    }

}
