package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.fulmicollection.objects.EnumObject;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Represents a primitive {@link ClassValue}.
 *
 * @param <V> the type of the primitive
 */
@Getter
public final class PrimitiveClassValue<V> extends EnumObject implements ClassValue<V> {
    public static final ClassValue<Byte> BYTE = new PrimitiveClassValue<>(byte.class,
            Byte.class, Integer.class);
    public static final ClassValue<Short> SHORT = new PrimitiveClassValue<>(short.class,
            Byte.class, Short.class, Integer.class);
    public static final ClassValue<Character> CHAR = new PrimitiveClassValue<>(char.class,
            Integer.class, Character.class);
    public static final ClassValue<Integer> INT = new PrimitiveClassValue<>(int.class,
            Byte.class, Short.class, Character.class, Integer.class);
    public static final ClassValue<Long> LONG = new PrimitiveClassValue<>(long.class,
            Byte.class, Short.class, Character.class, Integer.class, Long.class);
    public static final ClassValue<Float> FLOAT = new PrimitiveClassValue<>(float.class,
            Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class);
    public static final ClassValue<Double> DOUBLE = new PrimitiveClassValue<>(double.class,
            Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class);
    public static final ClassValue<Boolean> BOOLEAN = new PrimitiveClassValue<>(boolean.class,
            Boolean.class);

    private final Class<V> value;
    private final Class<?> @NotNull [] compatibleValues;

    /**
     * Instantiates a new Primitive class value.
     *
     * @param clazz            the clazz
     * @param compatibleValues the compatible values
     */
    public PrimitiveClassValue(final @NotNull Class<V> clazz, Class<?> @NotNull ... compatibleValues) {
        this.value = clazz;
        this.compatibleValues = Arrays.stream(compatibleValues)
                .map(c -> new Class[]{c, ReflectionUtils.getPrimitiveClass(c)})
                .flatMap(Arrays::stream)
                .toArray(Class[]::new);
    }

    @Override
    public boolean compatibleWith(@NotNull Value<?> type) {
        Object actual = type.getValue();
        if (actual == null) return false;
        else if (getValue().isAssignableFrom(actual.getClass())) return true;
        else for (Class<?> compatibleValue : this.compatibleValues)
            if (compatibleValue.isAssignableFrom(actual.getClass())) return true;
        return false;
    }

    @Override
    public String toString() {
        return getValue().getSimpleName().toLowerCase();
    }

    /**
     * Gets the most appropriate {@link PrimitiveClassValue} from the given name.
     *
     * @param name the name
     * @return the primitive class value
     */
    public static @NotNull PrimitiveClassValue<?> valueOf(final @NotNull String name) {
        return valueOf(PrimitiveClassValue.class, name);
    }

    /**
     * Gets all the static values.
     *
     * @return the primitive class values
     */
    public static PrimitiveClassValue<?> @NotNull [] values() {
        return values(PrimitiveClassValue.class);
    }

}
