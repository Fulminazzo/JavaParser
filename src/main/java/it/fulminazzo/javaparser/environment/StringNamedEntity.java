package it.fulminazzo.javaparser.environment;

import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * An instance of {@link NamedEntity} for raw strings.
 */
class StringNamedEntity extends ObjectWrapper<String> implements NamedEntity {

    /**
     * Instantiates a new String named entity.
     *
     * @param name the name
     */
    public StringNamedEntity(final @NotNull String name) {
        super(name);
    }

    @Override
    public @NotNull String getName() {
        return this.object;
    }

}
