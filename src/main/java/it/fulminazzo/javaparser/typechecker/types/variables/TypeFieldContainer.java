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
     * @param parent   the parent
     * @param type     the type
     * @param variable the value
     */
    public TypeFieldContainer(@NotNull Type parent, @NotNull ClassType type,
                              @NotNull String name, @NotNull Type variable) {
        super(parent, type, name, variable);
    }

    @Override
    public @NotNull Type set(@NotNull Type newValue) {
        return newValue;
    }

    @Override
    public boolean isClassType() {
        return this.variable.isClassType();
    }

    @Override
    public @NotNull Type check(Type @NotNull ... expectedTypes) {
        return this.variable.check(expectedTypes);
    }

    @Override
    public @NotNull Type checkNot(Type @NotNull ... expectedTypes) {
        return this.variable.checkNot(expectedTypes);
    }

    @Override
    public @NotNull Type checkAssignableFrom(@NotNull ClassType classType) {
        return this.variable.checkAssignableFrom(classType);
    }

    @Override
    public @NotNull TypeFieldContainer getField(@NotNull Field field) throws TypeException {
        return this.variable.getField(field);
    }

    @Override
    public @NotNull Type invokeMethod(@NotNull Method method, @NotNull ParameterTypes parameterTypes) throws TypeException {
        return this.variable.invokeMethod(method, parameterTypes);
    }

}
