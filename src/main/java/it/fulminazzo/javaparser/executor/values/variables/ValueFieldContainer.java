package it.fulminazzo.javaparser.executor.values.variables;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.ParameterValues;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.ValueException;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.FieldContainer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * An implementation of {@link FieldContainer} for {@link Value}.
 */
public final class ValueFieldContainer extends FieldContainer<ClassValue<?>, Value<?>, ParameterValues> implements Value<?> {

    /**
     * Instantiates a new Field container.
     *
     * @param type  the type
     * @param value the value
     */
    public ValueFieldContainer(@NotNull ClassValue<?> type, @NotNull Value<?> value) {
        super(type, value);
    }

    @Override
    public boolean isCharacter() {
        return this.value.isCharacter();
    }

    @Override
    public boolean isInteger() {
        return this.value.isInteger();
    }

    @Override
    public boolean isLong() {
        return this.value.isLong();
    }

    @Override
    public boolean isFloat() {
        return this.value.isFloat();
    }

    @Override
    public boolean isDouble() {
        return this.value.isDouble();
    }

    @Override
    public boolean isBoolean() {
        return this.value.isBoolean();
    }

    @Override
    public boolean isString() {
        return this.value.isString();
    }

    @Override
    public @NotNull ValueFieldContainer getField(@NotNull Field field) {
        return this.value.getField(field);
    }

    @Override
    public @NotNull Value<?> invokeMethod(@NotNull Method method, @NotNull ParameterValues parameters) throws ValueException {
        return this.value.invokeMethod(method, parameters);
    }

    @Override
    public <T extends Value<?>> @NotNull T to(@NotNull Class<T> value) {
        return this.value.to(value);
    }

}
