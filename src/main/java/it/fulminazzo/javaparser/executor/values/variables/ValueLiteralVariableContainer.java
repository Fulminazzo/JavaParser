package it.fulminazzo.javaparser.executor.values.variables;

import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.executor.ExecutorException;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.*;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.LiteralVariableContainer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The type Value literal variable container.
 *
 * @param <V> the type of the value
 */
public final class ValueLiteralVariableContainer<V>
        extends LiteralVariableContainer<ClassValue<?>, Value<?>, ParameterValues>
        implements ValueVariableContainer<V> {

    /**
     * Instantiates a new value literal variable container.
     *
     * @param environment the environment
     * @param name        the name
     */
    public ValueLiteralVariableContainer(@NotNull Environment<Value<?>> environment,
                                         @NotNull String name) {
        super(environment, (ClassValue<?>) Values.NULL_VALUE, name, Values.NULL_VALUE);
    }

    /**
     * Instantiates a new value literal variable container.
     *
     * @param environment the environment
     * @param type        the type
     * @param name        the name
     * @param value       the value
     */
    public ValueLiteralVariableContainer(@NotNull Environment<Value<?>> environment, @NotNull ClassValue<?> type,
                                         @NotNull String name, @NotNull Value<?> value) {
        super(environment, type, name, value);
    }

    @Override
    protected @NotNull RuntimeException exceptionWrapper(@NotNull Exception exception) {
        return ExecutorException.of(exception);
    }

    @Override
    public V getValue() {
        return ValueVariableContainer.super.getValue();
    }

    @Override
    public boolean isCharacter() {
        return ValueVariableContainer.super.isCharacter();
    }

    @Override
    public boolean isInteger() {
        return ValueVariableContainer.super.isInteger();
    }

    @Override
    public boolean isLong() {
        return ValueVariableContainer.super.isLong();
    }

    @Override
    public boolean isFloat() {
        return ValueVariableContainer.super.isFloat();
    }

    @Override
    public boolean isDouble() {
        return ValueVariableContainer.super.isDouble();
    }

    @Override
    public boolean isBoolean() {
        return ValueVariableContainer.super.isBoolean();
    }

    @Override
    public boolean isString() {
        return ValueVariableContainer.super.isString();
    }

    @Override
    public @NotNull ClassValue<V> checkClass() {
        return ValueVariableContainer.super.checkClass();
    }

    @Override
    public @NotNull ValueFieldContainer<V> getField(@NotNull Field field) {
        return ValueVariableContainer.super.getField(field);
    }

    @Override
    public @NotNull Value<?> invokeMethod(@NotNull Method method, @NotNull ParameterValues parameters) throws ValueException {
        return ValueVariableContainer.super.invokeMethod(method, parameters);
    }

}
