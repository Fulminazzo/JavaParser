package it.fulminazzo.javaparser.visitors;

import it.fulminazzo.javaparser.tokenizer.TokenType;
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

    /**
     * Generates a {@link VisitorException} with message:
     * <i>Operator '%operator%' cannot be applied to '%left%', '%right%'</i>
     *
     * @param operator the operator
     * @param left     the left operand
     * @param right    the right operand
     * @return the visitor exception
     */
    public static @NotNull VisitorException unsupportedOperation(final @NotNull TokenType operator,
                                                                 final @NotNull Object left,
                                                                 final @NotNull Object right) {
        return new VisitorException("Operator '%s' cannot be applied to '%s', '%s'",
                operator.regex().replace("\\", ""), left, right);
    }

    /**
     * Generates a {@link VisitorException} with message:
     * <i>Operator '%operator%' cannot be applied to '%operand%'</i>
     *
     * @param operator the operator
     * @param operand  the operand
     * @return the visitor exception
     */
    public static @NotNull VisitorException unsupportedOperation(final @NotNull TokenType operator,
                                                                 final @NotNull Object operand) {
        return new VisitorException("Operator '%s' cannot be applied to '%s'",
                operator.regex().replace("\\", ""), operand);
    }

}
