package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.javaparser.executor.ExecutorException;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents the list of {@link Value}s required during the invocation of a method or constructor.
 */
public final class ParameterValues implements Value<List<Value<?>>> {

    /**
     * Instantiates a new Parameter values.
     *
     * @param parameters the parameters
     */
    public ParameterValues(final @NotNull List<Value<?>> parameters) {
        super(parameters);
    }

    @Override
    public @NotNull ClassValue<List<Value<?>>> toClass() {
        throw ExecutorException.noClassValue(getClass());
    }

    @Override
    public List<Value<?>> getValue() {
        return this.object;
    }

}
