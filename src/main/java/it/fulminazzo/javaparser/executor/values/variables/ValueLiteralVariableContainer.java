package it.fulminazzo.javaparser.executor.values.variables;

import it.fulminazzo.javaparser.executor.ExecutorException;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.ParameterValues;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.ValueException;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.LiteralVariableContainer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@SuppressWarnings("unchecked")
public final class ValueLiteralVariableContainer<V> extends LiteralVariableContainer<ClassValue<?>, Value<?>, ParameterValues> implements Value<V> {

    @Override
    public V getValue() {
        return (V) this.variable.getValue();
    }

    @Override
    public boolean isCharacter() {
        return this.variable.isCharacter();
    }

    @Override
    public boolean isInteger() {
        return this.variable.isInteger();
    }

    @Override
    public boolean isLong() {
        return this.variable.isLong();
    }

    @Override
    public boolean isFloat() {
        return this.variable.isFloat();
    }

    @Override
    public boolean isDouble() {
        return this.variable.isDouble();
    }

    @Override
    public boolean isBoolean() {
        return this.variable.isBoolean();
    }

    @Override
    public boolean isString() {
        return this.variable.isString();
    }

    @Override
    public @NotNull ClassValue<V> checkClass() {
        return (ClassValue<V>) this.variable.checkClass();
    }

    @Override
    public @NotNull ValueFieldContainer<V> getField(@NotNull Field field) {
        return this.variable.getField(field);
    }

    @Override
    public @NotNull Value<?> invokeMethod(@NotNull Method method, @NotNull ParameterValues parameters) throws ValueException {
        return this.variable.invokeMethod(method, parameters);
    }

    @Override
    public <T extends Value<?>> @NotNull T to(@NotNull Class<T> value) {
        return this.variable.to(value);
    }

    @Override
    protected @NotNull RuntimeException exceptionWrapper(@NotNull Exception exception) {
        return ExecutorException.of(exception);
    }

}
