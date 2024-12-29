package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.environment.Info;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the class of a {@link Type}.
 *
 * @param <I> the type
 */
public interface ClassType<I extends Type> extends Type, Info {

    /**
     * Gets internal type.
     *
     * @return the internal type
     */
    I getInternalType();

    /**
     * Verifies that the current class type is compatible with the provided type.
     *
     * @param type the other type
     * @return true if it is
     */
    boolean compatibleWith(final @NotNull Type type);

    @Override
    default boolean compatibleWith(@NotNull Object object) {
        return object instanceof Type && compatibleWith((Type) object);
    }

}
