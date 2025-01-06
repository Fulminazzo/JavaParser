package it.fulminazzo.javaparser.typechecker.types.variables;

import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.typechecker.types.*;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.LiteralVariableContainer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    @Override
    public boolean isClassType() {
        return this.value.isClassType();
    }

    @Override
    public @NotNull Type check(Type @NotNull ... expectedTypes) {
        return this.value.check(expectedTypes);
    }

    @Override
    public @NotNull Type checkNot(Type @NotNull ... expectedTypes) {
        return this.value.checkNot(expectedTypes);
    }

    @Override
    public @NotNull Type checkAssignableFrom(@NotNull ClassType classType) {
        return this.value.checkAssignableFrom(classType);
    }

    @Override
    public @NotNull TypeFieldContainer getField(@NotNull Field field) throws TypeException {
        return this.value.getField(field);
    }

    @Override
    public @NotNull Type invokeMethod(@NotNull Method method, @NotNull ParameterTypes parameterTypes) throws TypeException {
        return this.value.invokeMethod(method, parameterTypes);
    }

}
