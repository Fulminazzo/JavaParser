package it.fulminazzo.javaparser.executor.values.variables;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.ParameterValues;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.ValueException;
import it.fulminazzo.javaparser.executor.values.arrays.ArrayValue;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.VariableContainer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Represents the access to an array element with its index.
 *
 * @param <V> the type of the value
 */
public final class ArrayValueVariableContainer<V>
        extends VariableContainer<ClassValue<?>, Value<?>, ParameterValues, VariableContainer<ClassValue<?>, Value<?>, ParameterValues, ?>>
        implements ValueVariableContainer<V> {

    /**
     * Instantiates a new Variable container.
     *
     * @param container the container
     * @param type      the type
     * @param name      the name
     * @param variable  the value
     */
    public ArrayValueVariableContainer(@NotNull VariableContainer<ClassValue<?>, Value<?>, ParameterValues, ?> container,
                                       @NotNull ClassValue<?> type, @NotNull String name, @NotNull Value<?> variable) {
        super(container, type, name, variable);
    }

    @Override
    public @NotNull Value<?> set(@NotNull Value<?> newValue) {
        int index = Integer.parseInt(this.name);
        ArrayValue<?> array = (ArrayValue<?>) this.container.getVariable();
        array.set(index, newValue);
        this.container.set(array);
        return newValue;
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
