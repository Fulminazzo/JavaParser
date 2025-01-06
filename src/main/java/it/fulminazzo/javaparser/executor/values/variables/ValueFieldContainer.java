package it.fulminazzo.javaparser.executor.values.variables;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.ParameterValues;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.FieldContainer;
import org.jetbrains.annotations.NotNull;

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

}
