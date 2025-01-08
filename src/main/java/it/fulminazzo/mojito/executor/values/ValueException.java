package it.fulminazzo.mojito.executor.values;

import it.fulminazzo.mojito.visitors.visitorobjects.VisitorObjectException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Executable;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * An exception thrown by {@link Value} objects.
 */
public class ValueException extends VisitorObjectException {

    /**
     * Instantiates a new Value exception.
     *
     * @param message the message
     * @param args    the arguments to add in the message format
     */
    private ValueException(final @NotNull String message, final Object @NotNull ... args) {
        super(String.format(message, args));
    }

    /**
     * Generates a {@link ValueException} with message:
     * <i>Could not find class '%clazz%'</i>
     *
     * @param clazz the clazz
     * @return the value exception
     */
    public static @NotNull ValueException classNotFound(final @NotNull String clazz) {
        return new ValueException("Could not find class '%s'", clazz);
    }

    /**
     * Generates a {@link ValueException} with message:
     * <i>Could not find field '%field%' in value %value%</i>
     *
     * @param value the value
     * @param field the field
     * @return the value exception
     */
    public static @NotNull ValueException fieldNotFound(final @NotNull ClassValue<?> value,
                                                        final @NotNull String field) {
        return new ValueException("Could not find field '%s' in value %s", field, value);
    }

    /**
     * Generates a {@link ValueException} with message:
     * <i>Could not find method %method_format% in value %value%</i>
     *
     * @param value           the value
     * @param method          the method
     * @param parameterValues the parameter values
     * @return the value exception
     */
    public static @NotNull ValueException methodNotFound(final @NotNull ClassValue<?> value,
                                                         final @NotNull String method,
                                                         final @NotNull ParameterValues parameterValues) {
        return new ValueException("Could not find method %s in value %s", formatMethod(method, parameterValues), value);
    }

    /**
     * Generates a {@link ValueException} with message:
     * <i>Types mismatch: cannot apply parameters %parameter_values_format% to method %method_format% in value %value%</i>
     *
     * @param value           the value
     * @param method          the method
     * @param parameterValues the parameter values
     * @return the value exception
     */
    public static @NotNull ValueException valuesMismatch(final @NotNull ClassValue<?> value,
                                                         final @NotNull Executable method,
                                                         final @NotNull ParameterValues parameterValues) {
        return new ValueException("Types mismatch: cannot apply parameters %s to method %s in value %s",
                formatParameters(parameterValues), formatMethod(method), value);
    }

    /**
     * Formats the given method and parameter values to a string.
     *
     * @param method the method
     * @return the string
     */
    static @NotNull String formatMethod(final @NotNull Executable method) {
        return formatMethod(method.getName(), new ParameterValues(Arrays.stream(method.getParameterTypes())
                .map(ClassValue::of)
                .collect(Collectors.toList())));
    }

}
