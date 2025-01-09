package it.fulminazzo.mojito.executor.values.variables;

import it.fulminazzo.mojito.environment.Environment;
import it.fulminazzo.mojito.executor.ExecutorException;
import it.fulminazzo.mojito.executor.values.ClassValue;
import it.fulminazzo.mojito.executor.values.ParameterValues;
import it.fulminazzo.mojito.executor.values.Value;
import it.fulminazzo.mojito.executor.values.Values;
import it.fulminazzo.mojito.visitors.visitorobjects.variables.LiteralVariableContainer;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of {@link LiteralVariableContainer} with {@link Value}.
 *
 * @param <V> the type of the value
 */
public final class ValueLiteralVariableContainer<V>
        extends LiteralVariableContainer<ClassValue<?>, Value<?>, ParameterValues>
        implements ValueVariableContainer<V> {

    /**
     * Instantiates a new Value literal variable container.
     *
     * @param environment the environment
     * @param name        the name of the variable
     */
    public ValueLiteralVariableContainer(@NotNull Environment<Value<?>> environment,
                                         @NotNull String name) {
        super(environment, (ClassValue<?>) Values.NULL_VALUE, name, Values.NULL_VALUE);
    }

    /**
     * Instantiates a new Value literal variable container.
     *
     * @param environment the environment
     * @param type        the class value of the variable
     * @param name        the name of the variable
     * @param variable    the value of the variable
     */
    public ValueLiteralVariableContainer(@NotNull Environment<Value<?>> environment, @NotNull ClassValue<?> type,
                                         @NotNull String name, @NotNull Value<?> variable) {
        super(environment, type, name, variable);
    }

    @Override
    protected @NotNull RuntimeException exceptionWrapper(@NotNull Exception exception) {
        return ExecutorException.of(exception);
    }

    @Override
    public @NotNull ClassValue<V> checkClass() {
        return ValueVariableContainer.super.checkClass();
    }

}
