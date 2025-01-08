package it.fulminazzo.mojito.environment;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an object with a name.
 */
public interface NamedEntity {

    /**
     * Gets the name of this object.
     *
     * @return the name
     */
    @NotNull String getName();

    /**
     * Gets an instance of {@link NamedEntity} from the given name.
     *
     * @param name the name
     * @return the named entity
     */
    static @NotNull NamedEntity of(@NotNull String name) {
        return new StringNamedEntity(name);
    }


}
