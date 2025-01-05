package it.fulminazzo.javaparser.executor;

import it.fulminazzo.javaparser.executor.values.Value;
import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by the {@link it.fulminazzo.javaparser.executor.Executor}.
 */
public class ExecutorException extends RuntimeException {

    /**
     * Instantiates a new Executor exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    private ExecutorException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

    /**
     * Generates a {@link ExecutorException} with message the one from the given {@link Throwable}.
     *
     * @param cause the cause
     * @return the executor exception
     */
    public static @NotNull ExecutorException of(final @NotNull Throwable cause) {
        return new ExecutorException(cause.getMessage());
    }

    /**
     * Generates a {@link ExecutorException} with message:
     * <i>Cannot resolve symbol '%symbol%'</i>
     *
     * @param symbol the symbol
     * @return the type checker exception
     */
    public static @NotNull ExecutorException cannotResolveSymbol(final @NotNull String symbol) {
        return new ExecutorException("Cannot resolve symbol '%s'", symbol);
    }

    /**
     * Generates a {@link ExecutorException} with message:
     * <i>%clazz% does not have a {@link ClassValue}</i>
     *
     * @param <V>   the type parameter
     * @param value the value
     * @return the executor exception
     */
    public static <V extends Value<?>> @NotNull ExecutorException noClassValue(final @NotNull Class<V> value) {
        return new ExecutorException("%s does not have a %s",
                value.getSimpleName(), ClassValue.class.getSimpleName());
    }

    /**
     * Generates a {@link ExecutorException} with message:
     * <i>%clazz% does not have a {@link ClassValue}</i>
     *
     * @param type the type
     * @return the executor exception
     */
    public static @NotNull ExecutorException noClassType(final @NotNull Class<?> type) {
        return new ExecutorException("%s does not have a %s",
                type.getSimpleName(), ClassValue.class.getSimpleName());
    }

    /**
     * Generates a {@link ExecutorException} with message:
     * <i>Not implemented</i>
     *
     * @return the executor exception
     */
    public static @NotNull ExecutorException notImplemented() {
        return new ExecutorException("Not implemented");
    }

}
