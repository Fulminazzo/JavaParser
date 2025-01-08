package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.javaparser.visitors.visitorobjects.ParameterVisitorObjects;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the list of {@link Value}s required during the invocation of a method or constructor.
 */
public final class ParameterValues extends ParameterVisitorObjects<ClassValue<?>, Value<?>, ParameterValues> implements Value<List<Value<?>>> {

    /**
     * Instantiates a new Parameter values.
     *
     * @param parameters the parameters
     */
    public ParameterValues(final @NotNull List<Value<?>> parameters) {
        super(parameters);
    }

    @Override
    public List<Value<?>> getValue() {
        return this.object;
    }

}
