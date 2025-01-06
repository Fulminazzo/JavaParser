package it.fulminazzo.javaparser.executor.values.variables;

import it.fulminazzo.fulmicollection.objects.Refl;
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
public final class ValueFieldContainer<V> extends FieldContainer<ClassValue<?>, Value<?>, ParameterValues>
        implements Value<V>, ValueVariableContainer<V> {

    /**
     * Instantiates a new Field container.
     *
     * @param type  the type
     * @param value the value
     */
    public ValueFieldContainer(@NotNull Value<?> parent, @NotNull ClassValue<?> type,
                               @NotNull String name, @NotNull Value<?> value) {
        super(parent, type, name, value);
    }

    @Override
    public @NotNull Value<?> set(@NotNull Value<?> newValue) {
        Refl<?> refl = new Refl<>(this.container.getValue());
        refl.setFieldObject(this.name, newValue.getValue());
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
