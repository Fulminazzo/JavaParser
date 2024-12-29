package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.environment.Info;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the class of a {@link Type}.
 *
 * @param <I> the type
 */
public abstract class ClassType<I extends Type> implements Type, Info<I> {
    protected final @NotNull I internalType;

    /**
     * Instantiates a new Class type.
     *
     * @param internalType the internal type
     */
    public ClassType(final @NotNull I internalType) {
        this.internalType = internalType;
    }

}
