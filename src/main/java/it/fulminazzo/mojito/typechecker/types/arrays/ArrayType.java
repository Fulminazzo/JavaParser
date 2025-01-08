package it.fulminazzo.mojito.typechecker.types.arrays;

import it.fulminazzo.mojito.typechecker.types.ClassType;
import it.fulminazzo.mojito.typechecker.types.Type;
import it.fulminazzo.mojito.typechecker.types.TypeWrapper;
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
    public @NotNull ClassType toClass() {
        return new ArrayClassType(getComponentType().toClass());
    }

}
