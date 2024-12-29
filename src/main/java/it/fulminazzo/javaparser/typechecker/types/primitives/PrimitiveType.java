package it.fulminazzo.javaparser.typechecker.types.primitives;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Contains all the primitive {@link Type} allowed by the language.
 *
 * @param <I> the type
 */
public abstract class PrimitiveType<I extends Type> extends ClassType<I> {

    /**
     * Instantiates a new Primitive type.
     *
     * @param internalType the internal type
     */
    PrimitiveType(final @NotNull I internalType) {
        super(internalType);
    }

}
