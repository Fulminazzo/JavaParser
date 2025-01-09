package it.fulminazzo.mojito.typechecker.types.arrays;

import it.fulminazzo.mojito.typechecker.types.*;
import it.fulminazzo.mojito.typechecker.types.variables.TypeFieldContainer;
import it.fulminazzo.mojito.visitors.visitorobjects.VisitorObjectException;
import it.fulminazzo.mojito.visitors.visitorobjects.variables.FieldContainer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an initialized array.
 */
@Getter
public class ArrayType extends TypeWrapper implements Type {

    /**
     * Instantiates a new Array type.
     *
     * @param componentType the component type
     */
    public ArrayType(@NotNull Type componentType) {
        super(componentType);
    }

    /**
     * Gets the components type.
     *
     * @return the type
     */
    public @NotNull Type getComponentType() {
        return this.object;
    }

    @Override
    public @NotNull FieldContainer<ClassType, Type, ParameterTypes> getField(@NotNull String fieldName) throws VisitorObjectException {
        if (fieldName.equals("length"))
            return new TypeFieldContainer(this, PrimitiveClassType.INT, "length", PrimitiveType.INT);
        else return Type.super.getField(fieldName);
    }

    @Override
    public @NotNull ClassType toClass() {
        return new ArrayClassType(getComponentType().toClass());
    }

}
