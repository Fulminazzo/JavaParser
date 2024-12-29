package it.fulminazzo.javaparser.typechecker.types.primitives;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a general primitive type.
 *
 * @param <I> the type
 */
abstract class PrimitiveType<I extends Type> extends ClassType<I> {

    /**
     * Instantiates a new Primitive type.
     *
     * @param internalType the internal type
     */
    public PrimitiveType(final @NotNull I internalType) {
        super(internalType);
    }

}
