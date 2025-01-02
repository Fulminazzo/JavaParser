package it.fulminazzo.javaparser.executor.values;

import it.fulminazzo.javaparser.environment.Info;
import it.fulminazzo.javaparser.executor.values.classes.PrimitiveClassValue;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the class of a {@link Value}.
 *
 * @param <V> the type of the value
 */
@SuppressWarnings("unchecked")
public interface ClassValue<V> extends Value<Class<V>>, Info {

    /**
     * Verifies that the current class value is compatible with the provided value.
     *
     * @param type the other type
     * @return true if it is
     */
    boolean compatibleWith(final @NotNull Value<?> type);

    @Override
    default boolean compatibleWith(@NotNull Object object) {
        return object instanceof Value && compatibleWith((Value<?>) object);
    }

    @Override
    @NotNull Class<V> getValue();

    /**
     * Gets a new {@link ClassValue} from the given class.
     * Tries first to obtain from {@link PrimitiveClassValue}.
     * If it fails, uses the fields of {@link ObjectClassValue}.
     * Otherwise, a new value is created.
     *
     * @param className the class name
     * @return the class type
     * @throws ValueException the exception thrown in case the class is not found
     */
    static <V> @NotNull ClassValue<V> of(final @NotNull String className) throws ValueException {
        try {
            String lowerCase = className.toLowerCase();
            if (lowerCase.equals(className))
                return (ClassValue<V>) PrimitiveClassValue.valueOf(className.toUpperCase());
        } catch (IllegalArgumentException ignored) {}
        return ObjectClassValue.of(className);
    }

    /**
     * Gets a new {@link ClassValue} from the given class.
     * Tries first to obtain from {@link PrimitiveClassValue}.
     * If it fails, uses the fields of {@link ObjectClassValue}.
     * Otherwise, a new value is created.
     *
     * @param clazz the class
     * @return the class value
     */
    static <V> @NotNull ClassValue<V> of(final @NotNull Class<V> clazz) {
        for (PrimitiveClassValue<?> value : PrimitiveClassValue.values())
            if (value.getValue().equals(clazz)) return (ClassValue<V>) value;
        return ObjectClassValue.of(clazz);
    }

}
