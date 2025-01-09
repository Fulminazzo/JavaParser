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
     * @param parent the holder of this field
     * @param type   the class type of the field
     * @param name   the name of the field
     * @param variable  the type of the field
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

}
