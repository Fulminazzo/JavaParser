package it.fulminazzo.javaparser.executor.values.objects;

import it.fulminazzo.fulmicollection.objects.EnumObject;
import it.fulminazzo.fulmicollection.utils.StringUtils;
import it.fulminazzo.javaparser.executor.values.*;
import it.fulminazzo.javaparser.executor.values.ClassValue;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unchecked")
@Getter
public final class ObjectClassValue<V> extends EnumObject implements ClassValue<V> {
    /**
     * {@link java.lang.Byte}
     */
    public static final ObjectClassValue<Byte> BYTE = new ObjectClassValue<>(Byte.class, PrimitiveClassValue.BYTE);
    /**
     * {@link java.lang.Short}
     */
    public static final ObjectClassValue<Short> SHORT = new ObjectClassValue<>(Short.class, PrimitiveClassValue.SHORT);
    /**
     * {@link java.lang.Character}
     */
    public static final ObjectClassValue<Character> CHARACTER = new ObjectClassValue<>(Character.class, PrimitiveClassValue.CHAR);
    /**
     * {@link java.lang.Integer}
     */
    public static final ObjectClassValue<Integer> INTEGER = new ObjectClassValue<>(Integer.class, PrimitiveClassValue.INT);
    /**
     * {@link java.lang.Long}
     */
    public static final ObjectClassValue<Long> LONG = new ObjectClassValue<>(Long.class, PrimitiveClassValue.LONG);
    /**
     * {@link java.lang.Float}
     */
    public static final ObjectClassValue<Float> FLOAT = new ObjectClassValue<>(Float.class, PrimitiveClassValue.FLOAT);
    /**
     * {@link java.lang.Double}
     */
    public static final ObjectClassValue<Double> DOUBLE = new ObjectClassValue<>(Double.class, PrimitiveClassValue.DOUBLE);
    /**
     * {@link java.lang.Boolean}
     */
    public static final ObjectClassValue<Boolean> BOOLEAN = new ObjectClassValue<>(Boolean.class, PrimitiveClassValue.BOOLEAN);
    /**
     * {@link java.lang.String}
     */
    public static final ObjectClassValue<String> STRING = new ObjectClassValue<>(String.class);
    /**
     * {@link java.lang.Object}
     */
    public static final ObjectClassValue<Object> OBJECT = new ObjectClassValue<>(Object.class);
    
    private final @NotNull Class<V> value;
    @Getter(AccessLevel.NONE)
    private final @Nullable ClassValue<V> associatedValue;

    private ObjectClassValue(final @NotNull Class<V> value) {
        this(value, null);
    }

    private ObjectClassValue(final @NotNull Class<V> value,
                             final @Nullable ClassValue<V> associatedValue) {
        this.value = value;
        this.associatedValue = associatedValue;
    }

    @Override
    public boolean compatibleWith(@NotNull Value<?> value) {
        if (value.equals(Values.NULL_VALUE)) return true;
        else if (this.associatedValue != null) return this.associatedValue.compatibleWith(value);
        else {
            // Either STRING or OBJECT
            return !equals(STRING) || value.isString();
        }
    }

    @Override
    public String toString() {
        return ClassValue.print(StringUtils.capitalize(name()));
    }

    /**
     * Gets the most appropriate {@link ObjectClassValue} from the given name.
     *
     * @param name the name
     * @return the primitive class value
     */
    public static @NotNull ObjectClassValue<?> valueOf(final @NotNull String name) {
        return valueOf(ObjectClassValue.class, name);
    }

    /**
     * Gets all the static values.
     *
     * @return the primitive class values
     */
    public static ObjectClassValue<?> @NotNull [] values() {
        return values(ObjectClassValue.class);
    }

    /**
     * Gets a new {@link ClassValue} from the given class name.
     *
     * @param className the class name
     * @return the class value
     * @throws ValueException the exception thrown in case the class is not found
     */
    public static <V> @NotNull ClassValue<V> of(final @NotNull String className) throws ValueException {
        return of(ObjectValue.getClass(className));
    }

    /**
     * Gets a new {@link ClassValue} from the given class name.
     *
     * @param clazz the class
     * @return the respective class value
     */
    public static <V> @NotNull ClassValue<V> of(final @NotNull Class<V> clazz) {
        try {
            return (ClassValue<V>) ObjectClassValue.valueOf(clazz.getSimpleName().toUpperCase());
        } catch (IllegalArgumentException e) {
            return new CustomObjectClassValue<>(clazz);
        }
    }

}
