package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a general array {@link Value}.
 *
 * @param <V> the type of the array
 */
@Getter
@SuppressWarnings("unchecked")
public class ArrayValue<V> extends ObjectWrapper<List<Value<V>>> implements Value<V[]> {
    private final @NotNull ClassValue<V> componentsType;

    /**
     * Instantiates a static array value.
     *
     * @param componentsType the components type
     * @param size           the size of the array
     */
    ArrayValue(final @NotNull ClassValue<V> componentsType, final int size) {
        super(new LinkedList<>());
        this.componentsType = componentsType;
        for (int i = 0; i < size; i++) this.object.add(componentsType.toValue());
    }

    /**
     * Instantiates a dynamic array value.
     *
     * @param componentsType the components type
     * @param values         the values of the array
     */
    ArrayValue(final @NotNull ClassValue<V> componentsType, final @NotNull Collection<Value<V>> values) {
        super(new LinkedList<>());
        this.componentsType = componentsType;
        List<Value<V>> list = new LinkedList<>(values);
        for (int i = 0; i < values.size(); i++) this.object.add(list.get(i));
    }

    @Override
    public @NotNull ClassValue<V[]> toClassValue() {
        return new ArrayClassValue<>(this.componentsType);
    }

    @Override
    public V[] getValue() {
        Class<V> componentsType = this.componentsType.getWrapperValue();
        return this.object.stream().map(Value::getValue).toArray(a -> (V[])
                Array.newInstance(componentsType, this.object.size()));
    }

    @Override
    public String toString() {
        return String.format("%s(%s, %s)", getClass().getSimpleName(), this.componentsType, this.object);
    }

    /**
     * Instantiates a static array value.
     *
     * @param <V>            the type of the value
     * @param componentsType the components type
     * @param size           the size of the array
     * @return the array value
     */
    public static <V> @NotNull ArrayValue<V> of(final @NotNull ClassValue<V> componentsType,
                                                final int size) {
        return new ArrayValue<>(componentsType, size);
    }

    /**
     * Instantiates a dynamic array value.
     *
     * @param <V>            the type of the value
     * @param componentsType the components type
     * @param values         the values of the array
     * @return the array value
     */
    public static <V> @NotNull ArrayValue<V> of(final @NotNull ClassValue<V> componentsType,
                                                final @NotNull Collection<Value<V>> values) {
        return new ArrayValue<>(componentsType, values);
    }

    /**
     * Instantiates a new array value from the given array.
     *
     * @param <V>    the components type
     * @param <T>    the type of the object
     * @param object the object
     * @return the array value
     */
    public static <V, T> @NotNull ArrayValue<V> of(final @NotNull T object) {
        Class<V> componentType = (Class<V>) object.getClass().getComponentType();
        final Collection<Value<V>> values;
        Refl<?> arrayUtils = new Refl<>(ArrayUtils.class);
        if (componentType.isPrimitive()) values = arrayUtils.invokeMethod("toValueCollection", object);
        else values = arrayUtils.invokeMethod("toValueCollection", new Class[]{Object[].class}, object);
        return of(ClassValue.of(componentType), values);
    }

}
