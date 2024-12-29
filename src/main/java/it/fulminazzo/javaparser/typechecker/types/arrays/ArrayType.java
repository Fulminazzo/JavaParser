package it.fulminazzo.javaparser.typechecker.types.arrays;

import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.TypeWrapper;
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

}
