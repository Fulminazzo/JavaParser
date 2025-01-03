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
public final class ParameterValues extends ObjectWrapper<List<Value<?>>> implements Value<List<Value<?>>>, Iterable<Value<?>> {

    /**
     * Instantiates a new Parameter values.
     *
     * @param parameters the parameters
     */
    public ParameterValues(final @NotNull List<Value<?>> parameters) {
        super(parameters);
    }

    @Override
    public @NotNull ClassValue<List<Value<?>>> toClassValue() {
        throw ExecutorException.noClassValue(getClass());
    }

    @Override
    public List<Value<?>> getValue() {
        return this.object;
    }

    /**
     * Gets a stream of the internal values.
     *
     * @return the stream
     */
    public @NotNull Stream<Value<?>> stream() {
        return this.object.stream();
    }

    @Override
    public @NotNull Iterator<Value<?>> iterator() {
        return stream().iterator();
    }

}
