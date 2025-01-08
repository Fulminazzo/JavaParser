package it.fulminazzo.mojito.executor.values.primitivevalue;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a general {@link Number} value.
 *
 * @param <N> the type of the value
 */
abstract class NumberValue<N extends Number> extends PrimitiveValue<N> {

    /**
     * Instantiates a new Number value.
     *
     * @param value the value
     */
    public NumberValue(@NotNull N value) {
        super(value);
    }

}
