package it.fulminazzo.mojito.typechecker.types.variables;

import it.fulminazzo.mojito.environment.Environment;
import it.fulminazzo.mojito.typechecker.TypeCheckerException;
import it.fulminazzo.mojito.typechecker.types.ClassType;
import it.fulminazzo.mojito.typechecker.types.ParameterTypes;
import it.fulminazzo.mojito.typechecker.types.Type;
import it.fulminazzo.mojito.typechecker.types.Types;
import it.fulminazzo.mojito.visitors.visitorobjects.variables.LiteralVariableContainer;
import org.jetbrains.annotations.NotNull;

/**
 * An implementation of {@link LiteralVariableContainer} with {@link Type}.
 */
public final class TypeLiteralVariableContainer
        extends LiteralVariableContainer<ClassType, Type, ParameterTypes>
        implements TypeVariableContainer {

    /**
     * Instantiates a new Type literal variable container.
     *
     * @param environment the environment
     * @param name        the name of the variable
     */
    public TypeLiteralVariableContainer(@NotNull Environment<Type> environment, @NotNull String name) {
        super(environment, Types.NULL_TYPE.checkClass(), name, Types.NULL_TYPE);
    }

    /**
     * Instantiates a new Type literal variable container.
     *
     * @param environment the environment
     * @param type        the class type of the variable
     * @param name        the name of the variable
     * @param variable    the type of the variable
     */
    public TypeLiteralVariableContainer(@NotNull Environment<Type> environment, @NotNull ClassType type,
                                        @NotNull String name, @NotNull Type variable) {
        super(environment, type, name, variable);
    }

    @Override
    protected @NotNull RuntimeException exceptionWrapper(@NotNull Exception exception) {
        return TypeCheckerException.of(exception);
    }

}
