package it.fulminazzo.javaparser.typechecker.types.variables;

import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.ParameterTypes;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.Types;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.LiteralVariableContainer;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of {@link LiteralVariableContainer} with {@link Type}.
 */
public final class TypeLiteralVariableContainer extends LiteralVariableContainer<ClassType, Type, ParameterTypes> implements Type {

    /**
     * Instantiates a new Type literal variable container.
     *
     * @param environment the environment
     * @param name        the name
     */
    public TypeLiteralVariableContainer(@NotNull Environment<Type> environment, @NotNull String name) {
        super(environment, Types.NULL_TYPE.checkClass(), name, Types.NULL_TYPE);
    }

    /**
     * Instantiates a new type literal variable container.
     *
     * @param environment the environment
     * @param type        the type
     * @param name        the name
     * @param value       the value
     */
    public TypeLiteralVariableContainer(@NotNull Environment<Type> environment, @NotNull ClassType type,
                                        @NotNull String name, @NotNull Type value) {
        super(environment, type, name, value);
    }

    @Override
    protected @NotNull RuntimeException exceptionWrapper(@NotNull Exception exception) {
        return TypeCheckerException.of(exception);
    }

}
