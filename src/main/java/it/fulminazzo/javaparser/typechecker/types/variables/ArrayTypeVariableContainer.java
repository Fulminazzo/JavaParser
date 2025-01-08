package it.fulminazzo.javaparser.typechecker.types.variables;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.ParameterTypes;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.TypeException;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.VariableContainer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Represents the access to an array element with its index.
 */
public final class ArrayTypeVariableContainer
        extends VariableContainer<ClassType, Type, ParameterTypes, VariableContainer<ClassType, Type, ParameterTypes, ?>>
        implements TypeVariableContainer {

    /**
     * Instantiates a new Array type variable container.
     *
     * @param container the container
     * @param type      the type
     * @param index     the index
     * @param variable  the value
     */
    public ArrayTypeVariableContainer(@NotNull VariableContainer<ClassType, Type, ParameterTypes, ?> container,
                                      @NotNull ClassType type, @NotNull String index, @NotNull Type variable) {
        super(container, type, index, variable);
    }

    @Override
    public @NotNull Type set(@NotNull Type newValue) {
        return this.type.cast(newValue);
    }

    @Override
    public boolean isClassType() {
        return TypeVariableContainer.super.isClassType();
    }

    @Override
    public @NotNull Type check(Type @NotNull ... expectedTypes) {
        return TypeVariableContainer.super.check(expectedTypes);
    }

    @Override
    public @NotNull Type checkNot(Type @NotNull ... expectedTypes) {
        return TypeVariableContainer.super.checkNot(expectedTypes);
    }

    @Override
    public @NotNull Type checkAssignableFrom(@NotNull ClassType classType) {
        return TypeVariableContainer.super.checkAssignableFrom(classType);
    }

    @Override
    public @NotNull TypeFieldContainer getField(@NotNull Field field) throws TypeException {
        return TypeVariableContainer.super.getField(field);
    }

    @Override
    public @NotNull Type invokeMethod(@NotNull Method method, @NotNull ParameterTypes parameterTypes) throws TypeException {
        return TypeVariableContainer.super.invokeMethod(method, parameterTypes);
    }

}
