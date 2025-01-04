package it.fulminazzo.javaparser.environment;

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

}
