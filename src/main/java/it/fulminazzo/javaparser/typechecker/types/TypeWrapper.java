package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a wrapper for {@link Type} objects.
 */
public abstract class TypeWrapper extends ObjectWrapper<Type> {

    /**
     * Instantiates a new Type wrapper.
     *
     * @param internalType the internal type
     */
    public TypeWrapper(final @NotNull Type internalType) {
        super(internalType);
    }

    /**
     * Gets internal type.
     *
     * @return the internal type
     */
    public Type getInternalType() {
        return this.object;
    }

}
