package it.fulminazzo.javaparser.typechecker.types.variables;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.ParameterTypes;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.TypeException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Support interface for classes of this package.
 */
interface TypeVariableContainer extends Type {

    @Override
    default boolean isClassType() {
        return getVariable().isClassType();
    }

    @Override
    default @NotNull Type check(Type @NotNull ... expectedTypes) {
        return getVariable().check(expectedTypes);
    }

    @Override
    default @NotNull Type checkNot(Type @NotNull ... expectedTypes) {
        return getVariable().checkNot(expectedTypes);
    }

    @Override
    default @NotNull Type checkAssignableFrom(@NotNull ClassType classType) {
        return getVariable().checkAssignableFrom(classType);
    }

    @Override
    default @NotNull TypeFieldContainer getField(@NotNull Field field) throws TypeException {
        return getVariable().getField(field);
    }

    @Override
    default @NotNull Type invokeMethod(@NotNull Method method, @NotNull ParameterTypes parameterTypes) throws TypeException {
        return getVariable().invokeMethod(method, parameterTypes);
    }

    /**
     * Gets the variable.
     *
     * @return the variable
     */
    @NotNull Type getVariable();
    
}
