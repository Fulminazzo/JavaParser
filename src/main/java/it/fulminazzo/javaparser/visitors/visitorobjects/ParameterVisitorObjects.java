package it.fulminazzo.javaparser.visitors.visitorobjects;

import it.fulminazzo.javaparser.visitors.VisitorException;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents the list of parameters required during the invocation
 * of a method or the initialization of an object.
 *
 * @param <V> the type of the parameters
 */
public abstract class ParameterVisitorObjects<V extends VisitorObject> extends ObjectWrapper<List<V>> implements VisitorObject, Iterable<V> {

    /**
     * Instantiates a new Parameter visitor object.
     *
     * @param parameters the parameters
     */
    public ParameterVisitorObjects(final @NotNull List<V> parameters) {
        super(parameters);
    }

    /**
     * Gets the number of parameters.
     *
     * @return the number of parameters
     */
    public int size() {
        return this.object.size();
    }

    @Override
    public @NotNull ClassVisitorObject toClass() {
        throw VisitorException.noClassType(getClass());
    }

    /**
     * Gets a stream of the internal objects.
     *
     * @return the stream
     */
    public @NotNull Stream<V> stream() {
        return this.object.stream();
    }

    @Override
    public @NotNull Iterator<V> iterator() {
        return stream().iterator();
    }

}
