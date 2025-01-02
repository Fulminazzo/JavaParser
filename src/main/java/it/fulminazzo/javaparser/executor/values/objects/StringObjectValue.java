package it.fulminazzo.javaparser.executor.values.objects;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a {@link String} {@link ObjectValue}, with support for special operations.
 */
class StringObjectValue extends ObjectWrapper<String> implements Value<String> {

    /**
     * Instantiates a new String object value.
     *
     * @param string the string
     */
    StringObjectValue(final @NotNull String string) {
        super(string);
    }

    @Override
    public @NotNull ClassValue<String> toClassValue() {
        return ObjectClassValue.STRING;
    }

    @Override
    public @NotNull String getValue() {
        return this.object;
    }

}
