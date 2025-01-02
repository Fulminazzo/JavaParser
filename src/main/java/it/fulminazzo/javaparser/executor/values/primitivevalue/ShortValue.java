package it.fulminazzo.javaparser.executor.values.primitivevalue;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Short} {@link PrimitiveValue}.
 */
class ShortValue extends NumberValue<Short> {

    /**
     * Instantiates a new Short value.
     *
     * @param value the value
     */
    public ShortValue(@NotNull Short value) {
        super(value);
    }

}
