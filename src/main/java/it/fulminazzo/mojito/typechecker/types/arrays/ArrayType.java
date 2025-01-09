package it.fulminazzo.mojito.typechecker.types.arrays;

import it.fulminazzo.mojito.typechecker.types.*;
import it.fulminazzo.mojito.typechecker.types.variables.TypeFieldContainer;
import it.fulminazzo.mojito.visitors.visitorobjects.VisitorObjectException;
import it.fulminazzo.mojito.visitors.visitorobjects.variables.FieldContainer;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a general array {@link Type}.
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
     * @return the components type
     */
    public @NotNull Type getComponentsType() {
        return this.object;
    }

    @Override
    public @NotNull FieldContainer<ClassType, Type, ParameterTypes> getField(@NotNull String fieldName) throws VisitorObjectException {
        if (fieldName.equals("length"))
            return new TypeFieldContainer(this, PrimitiveClassType.INT, "length", PrimitiveType.INT);
        else throw TypeException.fieldNotFound(toClass(), fieldName);
    }

    @Override
    public @NotNull ClassType toClass() {
        return new ArrayClassType(getComponentsType().toClass());
    }

}
