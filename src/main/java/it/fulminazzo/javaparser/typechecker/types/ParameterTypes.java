package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.visitors.visitorobjects.ParameterVisitorObjects;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents the list of parameters {@link Type}s required during the invocation of a method or constructor.
 */
public final class ParameterTypes extends ParameterVisitorObjects<ClassType, Type, ParameterTypes> implements Type {

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
