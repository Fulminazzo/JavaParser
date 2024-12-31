package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
     * Returns the number of parameters.
     *
     * @return the number of parameters
     */
    public int size() {
        return this.object.size();
    }

    @Override
    public @NotNull ClassType toClassType() {
        throw TypeCheckerException.noClassType(getClass());
    }

    @Override
    public @NotNull Iterator<ClassType> iterator() {
        return this.object.iterator();
    }

}
