package it.fulminazzo.javaparser.executor.values.primitivevalue;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link Byte} {@link PrimitiveValue}.
 */
class ByteValue extends NumberValue<Byte> {

    /**
     * Instantiates a new Byte value.
     *
     * @param value the value
     */
    public ByteValue(@NotNull Byte value) {
        super(value);
    }

}
