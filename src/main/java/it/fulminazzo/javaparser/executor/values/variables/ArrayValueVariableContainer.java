package it.fulminazzo.javaparser.executor.values.variables;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.ParameterValues;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.VariableContainer;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unchecked")
public class ArrayValueVariableContainer<V>
        extends VariableContainer<ClassValue<?>, Value<?>, ParameterValues>
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
        return null;
    }

}
