package it.fulminazzo.mojito.executor.values.arrays;

import it.fulminazzo.mojito.executor.values.Value;
import it.fulminazzo.mojito.executor.values.primitivevalue.PrimitiveValue;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A utility class for {@link ArrayValue} objects.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class ArrayUtils {

    /**
     * Converts the given byte array to a collection of {@link Value}s.
     *
     * @param array the array
     * @return the values collection
     */
    public static @NotNull Collection<Value<Byte>> toValueCollection(final byte @NotNull [] array) {
        List<Value<Byte>> list = new ArrayList<>();
        for (byte b : array) list.add(PrimitiveValue.of(b));
        return list;
    }

    /**
     * Converts the given short array to a collection of {@link Value}s.
     *
     * @param array the array
     * @return the values collection
     */
    public static @NotNull Collection<Value<Short>> toValueCollection(final short @NotNull [] array) {
        List<Value<Short>> list = new ArrayList<>();
        for (short value : array) list.add(PrimitiveValue.of(value));
        return list;
    }

    /**
     * Converts the given char array to a collection of {@link Value}s.
     *
     * @param array the array
     * @return the character array
     */
    public static @NotNull Collection<Value<Character>> toValueCollection(final char @NotNull [] array) {
        List<Value<Character>> list = new ArrayList<>();
        for (char c : array) list.add(PrimitiveValue.of(c));
        return list;
    }

    /**
     * Converts the given int array to a collection of {@link Value}s.
     *
     * @param array the array
     * @return the integer array
     */
    public static @NotNull Collection<Value<Integer>> toValueCollection(final int @NotNull [] array) {
        List<Value<Integer>> list = new ArrayList<>();
        for (int j : array) list.add(PrimitiveValue.of(j));
        return list;
    }

    /**
     * Converts the given long array to a collection of {@link Value}s.
     *
     * @param array the array
     * @return the values collection
     */
    public static @NotNull Collection<Value<Long>> toValueCollection(final long @NotNull [] array) {
        List<Value<Long>> list = new ArrayList<>();
        for (long l : array) list.add(PrimitiveValue.of(l));
        return list;
    }

    /**
     * Converts the given float array to a collection of {@link Value}s.
     *
     * @param array the array
     * @return the values collection
     */
    public static @NotNull Collection<Value<Float>> toValueCollection(final float @NotNull [] array) {
        List<Value<Float>> list = new ArrayList<>();
        for (float v : array) list.add(PrimitiveValue.of(v));
        return list;
    }

    /**
     * Converts the given double array to a collection of {@link Value}s.
     *
     * @param array the array
     * @return the values collection
     */
    public static @NotNull Collection<Value<Double>> toValueCollection(final double @NotNull [] array) {
        List<Value<Double>> list = new ArrayList<>();
        for (double v : array) list.add(PrimitiveValue.of(v));
        return list;
    }

    /**
     * Converts the given boolean array to a collection of {@link Value}s.
     *
     * @param array the array
     * @return the values collection
     */
    public static @NotNull Collection<Value<Boolean>> toValueCollection(final boolean @NotNull [] array) {
        List<Value<Boolean>> list = new ArrayList<>();
        for (boolean b : array) list.add(PrimitiveValue.of(b));
        return list;
    }

    /**
     * Converts the given object array to a collection of {@link Value}s.
     *
     * @param array the array
     * @return the values collection
     */
    public static @NotNull Collection<Value<Object>> toValueCollection(final Object @NotNull [] array) {
        List<Value<Object>> list = new ArrayList<>();
        for (Object object : array) list.add(Value.of(object));
        return list;
    }

}
