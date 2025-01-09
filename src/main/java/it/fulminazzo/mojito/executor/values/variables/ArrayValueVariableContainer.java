package it.fulminazzo.mojito.executor.values.variables;

import it.fulminazzo.mojito.executor.values.ClassValue;
import it.fulminazzo.mojito.executor.values.ParameterValues;
import it.fulminazzo.mojito.executor.values.Value;
import it.fulminazzo.mojito.executor.values.arrays.ArrayValue;
import it.fulminazzo.mojito.visitors.visitorobjects.variables.VariableContainer;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the access to an array element with its index.
 *
 * @param <V> the type of the components
 */
public final class ArrayValueVariableContainer<V>
        extends VariableContainer<ClassValue<?>, Value<?>, ParameterValues, VariableContainer<ClassValue<?>, Value<?>, ParameterValues, ?>>
        implements ValueVariableContainer<V> {

    /**
     * Instantiates a new Array value variable container.
     *
     * @param container the actual array
     * @param type      the class value of the components
     * @param index     the index of the current component
     * @param variable  the value returned by the current component
     */
    public ArrayValueVariableContainer(@NotNull VariableContainer<ClassValue<?>, Value<?>, ParameterValues, ?> container,
                                       @NotNull ClassValue<?> type, @NotNull String index, @NotNull Value<?> variable) {
        super(container, type, index, variable);
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
