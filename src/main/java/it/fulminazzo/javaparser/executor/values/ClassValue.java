package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.environment.Info;
import it.fulminazzo.javaparser.executor.values.arrays.ArrayClassValue;
import it.fulminazzo.javaparser.executor.values.objects.ObjectClassValue;
import it.fulminazzo.javaparser.executor.values.objects.ObjectValue;
import it.fulminazzo.javaparser.executor.values.primitivevalue.PrimitiveValue;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static sun.nio.cs.Surrogate.is;

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
    @Override
    default @NotNull Value<?> cast(final @NotNull Value<?> value) {
        Object object = value.getValue();
        if (object == null) return Values.NULL_VALUE;
        else if (is(PrimitiveClassValue.class))
            if (is(PrimitiveClassValue.BOOLEAN))
                return PrimitiveValue.of(object.equals(true));
            else {
                Number numberValue = object instanceof Number ? (Number) object : (int) (char) object;
                if (is(PrimitiveClassValue.BYTE))
                    return PrimitiveValue.of(numberValue.byteValue());
                else if (is(PrimitiveClassValue.SHORT))
                    return PrimitiveValue.of(numberValue.shortValue());
                else if (is(PrimitiveClassValue.CHAR))
                    return PrimitiveValue.of((char) numberValue.intValue());
                else if (is(PrimitiveClassValue.INT))
                    return PrimitiveValue.of(numberValue.intValue());
                else if (is(PrimitiveClassValue.LONG))
                    return PrimitiveValue.of(numberValue.longValue());
                else if (is(PrimitiveClassValue.FLOAT))
                    return PrimitiveValue.of(numberValue.floatValue());
                else if (is(PrimitiveClassValue.DOUBLE))
                    return PrimitiveValue.of(numberValue.doubleValue());
            }
        else if (is(ObjectClassValue.class))
            if (is(ObjectClassValue.OBJECT))
                return ObjectValue.of(object);
            else if (is(ObjectClassValue.STRING))
                return ObjectValue.of((String) object);
            else if (is(ObjectClassValue.BOOLEAN))
                return ObjectValue.of(object.equals(true));
            else {
                Number numberValue = object instanceof Number ? (Number) object : (int) (char) object;
                if (is(ObjectClassValue.BYTE))
                    return ObjectValue.of(numberValue.byteValue());
                else if (is(ObjectClassValue.SHORT))
                    return ObjectValue.of(numberValue.shortValue());
                else if (is(ObjectClassValue.CHARACTER))
                    return ObjectValue.of((char) numberValue.intValue());
                else if (is(ObjectClassValue.INTEGER))
                    return ObjectValue.of(numberValue.intValue());
                else if (is(ObjectClassValue.LONG))
                    return ObjectValue.of(numberValue.longValue());
                else if (is(ObjectClassValue.FLOAT))
                    return ObjectValue.of(numberValue.floatValue());
                else if (is(ObjectClassValue.DOUBLE))
                    return ObjectValue.of(numberValue.doubleValue());
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

    @Override
    default @NotNull Value<?> newObject(final @NotNull Constructor<?> constructor,
                                        final @NotNull ParameterValues parameterValues) {
        Object[] parameters = parameterValues.stream().map(Value::getValue).toArray(Object[]::new);
        Object object = null;
        try {
            object = constructor.newInstance(parameters);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
            // if everything done correctly, should be impossible
        }
        return Value.of(object);
    }

    @Override
    default @NotNull ClassValue<Class<V>> toClass() {
        return (ClassValue<Class<V>>) (Object) of(Class.class);
    }

    @Override
    default @NotNull Class<?> toJavaClass() {
        return getValue();
    }

    /**
     * Converts the {@link #getValue()} to its respective wrapper type.
     *
     * @return the wrapper value
     */
    default @NotNull Class<V> getWrapperValue() {
        return (Class<V>) ReflectionUtils.getWrapperClass(getValue());
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
        if (clazz.isArray()) return (ClassValue<V>) ArrayClassValue.of(of(clazz.getComponentType()));
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
