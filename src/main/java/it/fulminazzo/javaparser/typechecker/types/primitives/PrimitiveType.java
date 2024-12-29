package it.fulminazzo.javaparser.typechecker.types.primitives;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.EnumType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.values.ValueTypes;
import org.jetbrains.annotations.NotNull;

/**
 * Contains all the primitive {@link Type} allowed by the language.
 *
 * @param <I> the type
 */
abstract class PrimitiveType<I extends Type> extends ClassType<I> implements EnumType {

    /**
     * Instantiates a new Primitive type.
     *
     * @param internalType the internal type
     */
    PrimitiveType(final @NotNull I internalType) {
        super(internalType);
    }

    @Override
    public @NotNull String name() {
        return name(ValueTypes.class);
    }

    @Override
    public String toString() {
        return name();
    }

}
