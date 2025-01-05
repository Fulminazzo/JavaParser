package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents the list of parameters {@link Type}s required during the invocation of a method or constructor.
 */
public final class ParameterTypes extends ObjectWrapper<List<ClassType>> implements Type, Iterable<ClassType> {

    /**
     * Instantiates a new Parameter types.
     *
     * @param parameters the parameters
     */
    public ParameterTypes(final @NotNull List<ClassType> parameters) {
        super(new LinkedList<>(parameters));
    }

    /**
     * Converts this class to an array of Java {@link Class}es.
     *
     * @return the classes
     */
    public Class<?> @NotNull [] toJavaClassArray() {
        return this.object.stream().map(ClassType::toJavaClass).toArray(Class[]::new);
    }

    /**
     * Returns the number of parameters.
     *
     * @return the number of parameters
     */
    public int size() {
        return this.object.size();
    }

    @Override
    public @NotNull ClassType toClass() {
        throw TypeCheckerException.noClassType(getClass());
    }

    /**
     * Gets a stream of the internal classes.
     *
     * @return the stream
     */
    public @NotNull Stream<ClassType> stream() {
        return this.object.stream();
    }

    @Override
    public @NotNull Iterator<ClassType> iterator() {
        return stream().iterator();
    }

}
