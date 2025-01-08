package it.fulminazzo.mojito.typechecker.types.variables;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.mojito.typechecker.TypeCheckerException;
import it.fulminazzo.mojito.typechecker.types.ClassType;
import it.fulminazzo.mojito.typechecker.types.ParameterTypes;
import it.fulminazzo.mojito.typechecker.types.Type;
import it.fulminazzo.mojito.typechecker.types.TypeException;
import it.fulminazzo.mojito.visitors.visitorobjects.variables.FieldContainer;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * An implementation of {@link FieldContainer} for {@link Type}.
 */
public final class TypeFieldContainer
        extends FieldContainer<ClassType, Type, ParameterTypes>
        implements TypeVariableContainer {

    /**
     * Instantiates a new Type field container.
     *
     * @param parent   the parent
     * @param type     the type
     * @param name     the name
     * @param variable the value
     */
    public TypeFieldContainer(@NotNull Type parent, @NotNull ClassType type,
                              @NotNull String name, @NotNull Type variable) {
        super(parent, type, name, variable);
    }

    @Override
    public @NotNull Type set(@NotNull Type newValue) {
        Type container = this.container;
        ClassType containerClass = container.isClassType() ? (ClassType) container : container.toClass();
        Field field = new Refl<>(containerClass.toJavaClass()).getField(this.name);
        if (Modifier.isFinal(field.getModifiers()))
            try {
                throw TypeException.cannotModifyFinalField(this.name);
            } catch (TypeException e) {
                throw TypeCheckerException.of(e);
            }
        return newValue;
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
