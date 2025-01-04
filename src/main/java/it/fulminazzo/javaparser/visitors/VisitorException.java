package it.fulminazzo.javaparser.visitors;

import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by a {@link Visitor} object.
 */
public class VisitorException extends RuntimeException {

    /**
     * Instantiates a new Visitor exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    protected VisitorException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

    /**
     * Generates a {@link VisitorException} with message the one from the given {@link Throwable}.
     *
     * @param cause the cause
     * @return the visitor exception
     */
    public static @NotNull VisitorException of(final @NotNull Throwable cause) {
        return new VisitorException(cause.getMessage());
    }

    /**
     * Generates a {@link VisitorException} with message:
     * <i>%clazz% does not have a class</i>
     *
     * @param type the type
     * @return the type checker exception
     */
    public static @NotNull VisitorException noClassType(final @NotNull Class<?> type) {
        return new VisitorException("%s does not have a class", type.getSimpleName());
    }

}
