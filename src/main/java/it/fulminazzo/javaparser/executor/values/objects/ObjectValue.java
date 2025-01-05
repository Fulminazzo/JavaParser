package it.fulminazzo.javaparser.executor.values.objects;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.ValueException;
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

/**
 * Represents an {@link Object} value, declared from its value.
 * It acts as a wrapper for values.
 *
 * @param <V> the type parameter
 */
@SuppressWarnings("unchecked")
public class ObjectValue<V> extends ObjectWrapper<V> implements Value<V> {
    private static final String[] IMPLIED_PACKAGES = new String[]{
            String.class.getPackage().getName(),
            Map.class.getPackage().getName(),
            IOException.class.getPackage().getName()
    };

    /**
     * Instantiates a new Object value.
     *
     * @param object the object
     */
    ObjectValue(final @NotNull V object) {
        super(object);
    }

    @Override
    public boolean isCharacter() {
        return getValue() instanceof Character;
    }

    @Override
    public boolean isInteger() {
        return getValue() instanceof Integer;
    }

    @Override
    public boolean isLong() {
        return getValue() instanceof Long;
    }

    @Override
    public boolean isFloat() {
        return getValue() instanceof Float;
    }

    @Override
    public boolean isDouble() {
        return getValue() instanceof Double;
    }

    @Override
    public boolean isBoolean() {
        return getValue() instanceof Boolean;
    }

    @Override
    public @NotNull V getValue() {
        return this.object;
    }

    @Override
    public <T extends Value<?>> @NotNull T to(@NotNull Class<T> value) {
        if (PrimitiveValue.class.isAssignableFrom(value))
            if (ReflectionUtils.isWrapper(getValue().getClass()))
                return (T) toPrimitive();
        return Value.super.to(value);
    }

    @Override
    public @NotNull PrimitiveValue<V> toPrimitive() {
        V value = getValue();
        Class<?> primitiveClass = ReflectionUtils.getPrimitiveClass(value.getClass());
        if (!primitiveClass.equals(value.getClass())) return PrimitiveValue.of(value);
        else return Value.super.toPrimitive();
    }

    @Override
    public @NotNull ClassValue<V> toClass() {
        return (ClassValue<V>) ClassValue.of(getValue().getClass());
    }

    @Override
    public String toString() {
        Class<?> valueClass = getValue().getClass();
        for (String packageName : IMPLIED_PACKAGES) {
            if (valueClass.getPackage().getName().equals(packageName)) {
                String className = valueClass.getSimpleName();
                if (shouldBeRenamed(valueClass)) className += "Wrapper";
                return String.format("%sValue(%s)", className, getValue());
            }
        }
        return super.toString();
    }

    private boolean shouldBeRenamed(final @NotNull Class<?> clazz) {
        if (ReflectionUtils.isWrapper(clazz)) {
            for (Class<?> c : new Class[]{Integer.class, Character.class, String.class})
                if (c.equals(clazz)) return false;
            return true;
        } else return false;
    }

    /**
     * Gets the class name from the given class.
     * If its package is present in {@link #IMPLIED_PACKAGES},
     * it will be stripped.
     *
     * @param clazz the class
     * @return the name
     */
    public static @NotNull String getClassName(final @NotNull Class<?> clazz) {
        String className = clazz.getCanonicalName();
        for (String impliedPackage : IMPLIED_PACKAGES) {
            impliedPackage += ".";
            if (className.startsWith(impliedPackage)) {
                className = className.substring(impliedPackage.length());
                break;
            }
        }
        return className;
    }

    /**
     * Obtains an instance of {@link ObjectValue} from the given object.
     * If the object is a {@link String}, then {@link StringObjectValue} will be returned.
     *
     * @param <V>    the type of the object
     * @param object the object
     * @return the object value
     */
    public static <V> ObjectValue<V> of(final @NotNull V object) {
        if (object instanceof String) return (ObjectValue<V>) new StringObjectValue((String) object);
        else return new ObjectValue<>(object);
    }

    /**
     * Searches for a class matching the given class name.
     * If no match is found, it tries to append the packages in
     * {@link #IMPLIED_PACKAGES}.
     * If still nothing is found, a {@link ValueException} is thrown.
     *
     * @param className the class name
     * @return the class
     * @throws ValueException the exception thrown in case the class is not found
     */
    static <V> @NotNull Class<V> getClass(final @NotNull String className) throws ValueException {
        try {
            return ReflectionUtils.getClass(className);
        } catch (IllegalArgumentException e) {
            for (String impliedPackage : IMPLIED_PACKAGES) {
                String name = impliedPackage + "." + className;
                try {
                    return ReflectionUtils.getClass(name);
                } catch (IllegalArgumentException ignored) {}
            }
            throw ValueException.classNotFound(className);
        }
    }

}
