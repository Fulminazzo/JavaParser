package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.javaparser.tokenizer.TokenType;
import org.jetbrains.annotations.NotNull;

/**
 * An exception thrown by {@link Value} objects.
 */
public class ValueRuntimeException extends RuntimeException {

    /**
     * Instantiates a new Value runtime exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    private ValueRuntimeException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

    /**
     * Generates a {@link ValueRuntimeException} with message:
     * <i>Value %value% is not a valid primitive type</i>
     *
     * @param value the value
     * @return the value runtime exception
     */
    public static @NotNull ValueRuntimeException invalidPrimitiveValue(final @NotNull Object value) {
        return new ValueRuntimeException("Value %s is not a valid primitive type", value);
    }

    /**
     * Generates a {@link ValueRuntimeException} with message:
     * <i>Operator '%operator%' cannot be applied to '%left%', '%right%'</i>
     *
     * @param operator the operator
     * @param left     the left
     * @param right    the right
     * @return the value runtime exception
     */
    public static @NotNull ValueRuntimeException unsupportedOperation(final @NotNull TokenType operator,
                                                                      final @NotNull Value<?> left,
                                                                      final @NotNull Value<?> right) {
        return new ValueRuntimeException("Operator '%s' cannot be applied to '%s', '%s'",
                operator.regex().replace("\\", ""), left, right);
    }

}
