package it.fulminazzo.javaparser.executor.values.variables;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.ParameterValues;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.arrays.ArrayValue;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.VariableContainer;
import org.jetbrains.annotations.NotNull;

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
    public @NotNull ClassValue<V> checkClass() {
        return ValueVariableContainer.super.checkClass();
    }

}
