package it.fulminazzo.javaparser.typechecker.types;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a wrapper for {@link Type} objects.
 */
public abstract class TypeWrapper {
    protected final Type internalType;

    /**
     * Instantiates a new Type wrapper.
     *
     * @param internalType the internal type
     */
    public TypeWrapper(final @NotNull Type internalType) {
        this.internalType = internalType;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() + this.internalType.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && getClass().equals(o.getClass()) && this.internalType.equals(((TypeWrapper) o).internalType);
    }

}
