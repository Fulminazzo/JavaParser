package it.fulminazzo.javaparser.executor.values.arrays;

import org.jetbrains.annotations.NotNull;

/**
 * An utility class for {@link ArrayValue} objects.
 */
final class ArrayUtils {

    /**
     * Converts the given byte array to a wrapped byte array.
     *
     * @param array the array
     * @return the wrapped byte array
     */
    public static Byte @NotNull [] toWrapperArray(final byte @NotNull [] array) {
        Byte[] wrapperArray = new Byte[array.length];
        for (int i = 0; i < array.length; i++) wrapperArray[i] = array[i];
        return wrapperArray;
    }

    /**
     * Converts the given short array to a wrapped short array.
     *
     * @param array the array
     * @return the wrapped short array
     */
    public static Short @NotNull [] toWrapperArray(final short @NotNull [] array) {
        Short[] wrapperArray = new Short[array.length];
        for (int i = 0; i < array.length; i++) wrapperArray[i] = array[i];
        return wrapperArray;
    }

    /**
     * Converts the given char array to a wrapped character array.
     *
     * @param array the array
     * @return the character array
     */
    public static Character @NotNull [] toWrapperArray(final char @NotNull [] array) {
        Character[] wrapperArray = new Character[array.length];
        for (int i = 0; i < array.length; i++) wrapperArray[i] = array[i];
        return wrapperArray;
    }

    /**
     * Converts the given int array to a wrapped integer array.
     *
     * @param array the array
     * @return the integer array
     */
    public static Integer @NotNull [] toWrapperArray(final int @NotNull [] array) {
        Integer[] wrapperArray = new Integer[array.length];
        for (int i = 0; i < array.length; i++) wrapperArray[i] = array[i];
        return wrapperArray;
    }

    /**
     * Converts the given long array to a wrapped long array.
     *
     * @param array the array
     * @return the wrapped long array
     */
    public static Long @NotNull [] toWrapperArray(final long @NotNull [] array) {
        Long[] wrapperArray = new Long[array.length];
        for (int i = 0; i < array.length; i++) wrapperArray[i] = array[i];
        return wrapperArray;
    }

    /**
     * Converts the given float array to a wrapped float array.
     *
     * @param array the array
     * @return the wrapped float array
     */
    public static Float @NotNull [] toWrapperArray(final float @NotNull [] array) {
        Float[] wrapperArray = new Float[array.length];
        for (int i = 0; i < array.length; i++) wrapperArray[i] = array[i];
        return wrapperArray;
    }

    /**
     * Converts the given double array to a wrapped double array.
     *
     * @param array the array
     * @return the wrapped double array
     */
    public static Double @NotNull [] toWrapperArray(final double @NotNull [] array) {
        Double[] wrapperArray = new Double[array.length];
        for (int i = 0; i < array.length; i++) wrapperArray[i] = array[i];
        return wrapperArray;
    }

    /**
     * Converts the given boolean array to a wrapped boolean array.
     *
     * @param array the array
     * @return the wrapped boolean array
     */
    public static Boolean @NotNull [] toWrapperArray(final boolean @NotNull [] array) {
        Boolean[] wrapperArray = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) wrapperArray[i] = array[i];
        return wrapperArray;
    }

}
