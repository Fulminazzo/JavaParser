package it.fulminazzo.mojito.typechecker.types.arrays;

import it.fulminazzo.mojito.typechecker.TypeCheckerException;
import it.fulminazzo.mojito.typechecker.types.ClassType;
import it.fulminazzo.mojito.typechecker.types.Type;
import it.fulminazzo.mojito.typechecker.types.TypeWrapper;
import it.fulminazzo.mojito.typechecker.types.objects.ObjectClassType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

/**
 * Represents the class type for an array.
 */
public class ArrayClassType extends TypeWrapper implements ClassType {

    /**
     * Instantiates a new Array class type.
     *
     * @param componentType the component type
     */
    public ArrayClassType(@NotNull ClassType componentType) {
        super(componentType);
    }

    /**
     * Gets the components class type.
     *
     * @return the class type
     */
    public @NotNull ClassType getComponentType() {
        return (ClassType) this.object;
    }

    @Override
    public @NotNull Type cast(@NotNull Type type) {
        ArrayType arrayType = type.check(ArrayType.class);
        Type componentType = arrayType.getComponentType();
        if (componentType.isPrimitive() || componentType.toClass().is(ObjectClassType.class)) {
            if (!getComponentType().is(componentType.toClass()))
                throw TypeCheckerException.invalidCast(this, type);
        }
        return toType();
    }

    @Override
    public @NotNull Type toType() {
        return new ArrayType(getComponentType().toType());
    }

    @Override
    public @NotNull Class<?> toJavaClass() {
        return Array.newInstance(getComponentType().toJavaClass(), 0).getClass();
    }

    @Override
    public boolean compatibleWith(@NotNull Type type) {
        return type instanceof ArrayType && ((ArrayType) type).getInternalType().isAssignableFrom((ClassType) getInternalType());
    }

    @Override
    public @NotNull String toString() {
        return getInternalType().toString().replace(".class", "[].class");
    }

}
