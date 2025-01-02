package it.fulminazzo.javaparser.executor.values.objects;

import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link String} {@link PrimitiveValue}.
 */
class StringObjectValue extends PrimitiveValue<String> {

    /**
     * Instantiates a new String value.
     *
     * @param value the value
     */
    public StringObjectValue(@NotNull String value) {
        super(value);
    }

}
