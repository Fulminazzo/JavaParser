package it.fulminazzo.javaparser.executor.values.objects;

import it.fulminazzo.fulmicollection.objects.EnumObject;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.PrimitiveClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
public final class ObjectClassValue<V> extends EnumObject implements ClassValue<V> {
    private final @NotNull Class<V> value;
    private final @Nullable PrimitiveClassValue<V> associatedValue;

    private ObjectClassValue(final @NotNull Class<V> value) {
        this(value, null);
    }

    private ObjectClassValue(final @NotNull Class<V> value,
                             final @Nullable PrimitiveClassValue<V> associatedValue) {
        this.value = value;
        this.associatedValue = associatedValue;
    }

    @Override
    public boolean compatibleWith(@NotNull Value<?> value) {
        //TODO: Null
        if (this.associatedValue != null) return this.associatedValue.compatibleWith(value);
        else {
            // Either STRING or OBJECT
            if (this.equals(STRING)) return STRING.is(value);
            else return true;
        }
    }

}
