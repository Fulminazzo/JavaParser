package it.fulminazzo.javaparser.typechecker.types.arrays;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.TypeWrapper;
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

    @Override
    public Class<?> toJavaClass() {
        Class<?> componentClass = ((ClassType) this.object).toJavaClass();
        return Array.newInstance(componentClass, 0).getClass();
    }

    @Override
    public boolean compatibleWith(@NotNull Type type) {
        return type instanceof ArrayType && ((ArrayType) type).getInternalType().isAssignableFrom((ClassType) getInternalType());
    }

}
