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
    ArrayClassType(final @NotNull ClassType componentType) {
        super(componentType);
    }

    /**
     * Gets the components class type.
     *
     * @return the class type
     */
    public @NotNull ClassType getComponentsType() {
        return (ClassType) this.object;
    }

    @Override
    public @NotNull Type cast(@NotNull Type type) {
        ArrayType arrayType = type.check(ArrayType.class);
        Type componentType = arrayType.getComponentType();
        if (componentType.isPrimitive() || componentType.toClass().is(ObjectClassType.class)) {
            if (!getComponentsType().is(componentType.toClass()))
                throw TypeCheckerException.invalidCast(this, type);
        }
        return toType();
    }

    @Override
    public boolean compatibleWith(@NotNull Type type) {
        return type instanceof ArrayType && ((ArrayType) type).getInternalType().isAssignableFrom((ClassType) getInternalType());
    }

    @Override
    public @NotNull Type toType() {
        return new ArrayType(getComponentsType().toType());
    }

    @Override
    public @NotNull Class<?> toJavaClass() {
        return Array.newInstance(getComponentsType().toJavaClass(), 0).getClass();
    }

    @Override
    public @NotNull String toString() {
        return getInternalType().toString().replace(".class", "[].class");
    }

    /**
     * Instantiates a new {@link ArrayClassType} from the given {@link ClassType}.
     *
     * @param classType the class type of the components
     * @return the array class type
     */
    public static @NotNull ArrayClassType of(final @NotNull ClassType classType) {
        return new ArrayClassType(classType);
    }

}
