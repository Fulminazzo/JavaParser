package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.javaparser.environment.Info;
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue;
import it.fulminazzo.javaparser.executor.values.objects.ObjectValue;
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the class of a {@link Value}.
 *
 * @param <V> the type of the value
 */
@SuppressWarnings("unchecked")
public interface ClassValue<V> extends Value<Class<V>>, Info {

    /**
     * Converts the given value to the current class.
     *
     * @param value the value
     * @return the value cast to this class
     */
    default @NotNull Value<V> cast(final @NotNull Value<?> value) {
        Object object = value.getValue();
        if (is(PrimitiveClassValue.class))
            if (is(PrimitiveClassValue.BOOLEAN))
                return (Value<V>) PrimitiveValue.of(object.equals(true));
            else {
                Number numberValue = object instanceof Number ? (Number) object : (int) (char) object;
                if (is(PrimitiveClassValue.BYTE))
                    return (Value<V>) PrimitiveValue.of(numberValue.byteValue());
                else if (is(PrimitiveClassValue.SHORT))
                    return (Value<V>) PrimitiveValue.of(numberValue.shortValue());
                else if (is(PrimitiveClassValue.CHAR))
                    return (Value<V>) PrimitiveValue.of((char) numberValue.intValue());
                else if (is(PrimitiveClassValue.INT))
                    return (Value<V>) PrimitiveValue.of(numberValue.intValue());
                else if (is(PrimitiveClassValue.LONG))
                    return (Value<V>) PrimitiveValue.of(numberValue.longValue());
                else if (is(PrimitiveClassValue.FLOAT))
                    return (Value<V>) PrimitiveValue.of(numberValue.floatValue());
                else if (is(PrimitiveClassValue.DOUBLE))
                    return (Value<V>) PrimitiveValue.of(numberValue.doubleValue());
            }
        else if (is(ObjectClassValue.class))
            if (is(ObjectClassValue.BOOLEAN))
                return (Value<V>) ObjectValue.of(object.equals(true));
            else {
                Number numberValue = object instanceof Number ? (Number) object : (int) (char) object;
                if (is(ObjectClassValue.BYTE))
                    return (Value<V>) ObjectValue.of(numberValue.byteValue());
                else if (is(ObjectClassValue.SHORT))
                    return (Value<V>) ObjectValue.of(numberValue.shortValue());
                else if (is(ObjectClassValue.CHARACTER))
                    return (Value<V>) ObjectValue.of((char) numberValue.intValue());
                else if (is(ObjectClassValue.INTEGER))
                    return (Value<V>) ObjectValue.of(numberValue.intValue());
                else if (is(ObjectClassValue.LONG))
                    return (Value<V>) ObjectValue.of(numberValue.longValue());
                else if (is(ObjectClassValue.FLOAT))
                    return (Value<V>) ObjectValue.of(numberValue.floatValue());
                else if (is(ObjectClassValue.DOUBLE))
                    return (Value<V>) ObjectValue.of(numberValue.doubleValue());
            }
        return Value.of(getValue().cast(object));
    }

    @Override
    default boolean isPrimitive() {
        return is(PrimitiveClassValue.class);
    }

    /**
     * Converts the current class value to an initialized {@link Value}.
     *
     * @return the value
     */
    default @NotNull Value<V> toValue() {
        return (Value<V>) Values.NULL_VALUE;
    }

    /**
     * Verifies that the current class value is compatible with the provided value.
     *
     * @param value the other value
     * @return true if it is
     */
    boolean compatibleWith(final @NotNull Value<?> value);

    @Override
    default boolean compatibleWith(@NotNull Object object) {
        return object instanceof Value && compatibleWith((Value<?>) object);
    }

    @Override
    default @NotNull ClassValue<Class<V>> toClassValue() {
        return (ClassValue<Class<V>>) (Object) of(Class.class);
    }

    @Override
    @NotNull Class<V> getValue();

    /**
     * Gets a new {@link ClassValue} from the given class.
     * Tries first to obtain from {@link PrimitiveClassValue}.
     * If it fails, uses the fields of {@link ObjectClassValue}.
     * Otherwise, a new value is created.
     *
     * @param <V>       the type parameter
     * @param className the class name
     * @return the class type
     * @throws ValueException the exception thrown in case the class is not found
     */
    static <V> @NotNull ClassValue<V> of(final @NotNull String className) throws ValueException {
        try {
            String lowerCase = className.toLowerCase();
            if (lowerCase.equals(className))
                return (ClassValue<V>) PrimitiveClassValue.valueOf(className.toUpperCase());
        } catch (IllegalArgumentException ignored) {
        }
        return ObjectClassValue.of(className);
    }

    /**
     * Gets a new {@link ClassValue} from the given class.
     * Tries first to obtain from {@link PrimitiveClassValue}.
     * If it fails, uses the fields of {@link ObjectClassValue}.
     * Otherwise, a new value is created.
     *
     * @param <V>   the type parameter
     * @param clazz the class
     * @return the class value
     */
    static <V> @NotNull ClassValue<V> of(final @NotNull Class<V> clazz) {
        for (PrimitiveClassValue<?> value : PrimitiveClassValue.values())
            if (value.getValue().equals(clazz)) return (ClassValue<V>) value;
        return ObjectClassValue.of(clazz);
    }

    /**
     * Prints the given string to the format of a class.
     *
     * @param output the output
     * @return the new output
     */
    static @NotNull String print(final @NotNull String output) {
        return output + ".class";
    }

}
