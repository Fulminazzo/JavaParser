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

    /**
     * Verifies that the current class type is compatible with the provided type.
     *
     * @param type the other type
     * @return true if it is
     */
    public abstract boolean compatibleWith(final @NotNull Type type);

    @Override
    public boolean compatibleWith(@NotNull Object object) {
        return object instanceof Type && compatibleWith((Type) object);
    }

}
