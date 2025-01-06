package it.fulminazzo.javaparser.typechecker.types.variables;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.ParameterTypes;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.TypeException;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.FieldContainer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * An implementation of {@link FieldContainer} for {@link Type}.
 */
public final class TypeFieldContainer extends FieldContainer<ClassType, Type, ParameterTypes> implements Type {

    /**
     * Instantiates a new Field container.
     *
     * @param type  the type
     * @param value the value
     */
    public TypeFieldContainer(@NotNull ClassType type, @NotNull Type value) {
        super(type, value);
    }

    @Override
    public @NotNull Type set(@NotNull Type newValue) {
        return newValue;
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
