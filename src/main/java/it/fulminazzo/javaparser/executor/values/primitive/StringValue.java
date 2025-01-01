package it.fulminazzo.javaparser.executor.values.primitive;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link String} {@link PrimitiveValue}.
 */
class StringValue extends PrimitiveValue<String> {

    /**
     * Instantiates a new String value.
     *
     * @param value the value
     */
    public StringValue(@NotNull String value) {
        super(value);
    }

}
