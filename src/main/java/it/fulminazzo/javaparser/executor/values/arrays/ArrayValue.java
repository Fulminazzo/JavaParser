package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a general array {@link Value}.
 *
 * @param <V> the type of the array
 */
@Getter
@SuppressWarnings("unchecked")
public class ArrayValue<V> extends ObjectWrapper<List<V>> implements Value<V[]> {
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
        for (int i = 0; i < size; i++) this.object.add(componentsType.toValue().getValue());
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
        for (int i = 0; i < values.size(); i++) this.object.add(list.get(i).getValue());
    }

    @Override
    public @NotNull ClassValue<V[]> toClassValue() {
        return new ArrayClassValue<>(this.componentsType);
    }

    @Override
    public V[] getValue() {
        Class<V> componentsType = this.componentsType.getWrapperValue();
        V[] array = (V[]) Array.newInstance(componentsType, this.object.size());
        return this.object.toArray(array);
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
        final V[] array;
        Class<V> componentType = (Class<V>) object.getClass().getComponentType();
        if (componentType.isPrimitive())
            array = new Refl<>(ArrayUtils.class).invokeMethod("toWrapperArray", object);
        else array = (V[]) object;
        return of(ClassValue.of(componentType), Arrays.stream(array).map(Value::of).collect(Collectors.toList()));
    }

}
