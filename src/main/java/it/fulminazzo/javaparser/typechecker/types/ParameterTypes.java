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
public final class ParameterTypes implements Type {

    /**
     * Instantiates a new Parameter types.
     *
     * @param parameters the parameters
     */
    public ParameterTypes(final @NotNull List<Type> parameters) {
        super(new LinkedList<>(parameters));
    }

    @Override
    public @NotNull ClassType toClass() {
        throw TypeCheckerException.noClassType(getClass());
    }

}
