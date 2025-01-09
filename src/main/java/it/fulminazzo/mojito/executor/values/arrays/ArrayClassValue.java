package it.fulminazzo.mojito.executor.values.arrays;

import it.fulminazzo.mojito.executor.values.ClassValue;
import it.fulminazzo.mojito.executor.values.Value;
import it.fulminazzo.mojito.executor.values.Values;
import it.fulminazzo.mojito.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

/**
 * Represents the class value for an array.
 *
 * @param <A> the type of the components
 */
@SuppressWarnings("unchecked")
public class ArrayClassValue<A> extends ObjectWrapper<ClassValue<A>> implements ClassValue<A> {

    /**
     * Instantiates a new Array class value.
     *
     * @param classValue the {@link ClassValue} of the components
     */
    ArrayClassValue(final ClassValue<A> classValue) {
        super(classValue);
    }

    /**
     * Gets the components class value.
     *
     * @return the components type
     */
    public ClassValue<A> getComponentsType() {
        return this.object;
    }

    @Override
    public @NotNull Class<A> getWrapperValue() {
        return of(ClassValue.of(getComponentsType().getWrapperValue())).getValue();
    }

    @Override
    public boolean compatibleWith(@NotNull Value<?> value) {
        if (value.is(Values.NULL_VALUE) && !this.object.isPrimitive()) return true;
        if (value.is(ArrayValue.class)) {
            Class<A> objectClass = this.object.getValue();
            Class<?> componentsClass = ((ArrayValue<?>) value).getComponentsType().getValue();
            return objectClass.isAssignableFrom(componentsClass);
        } else return false;
    }

    @Override
    public @NotNull Class<A> getValue() {
        return (Class<A>) Array.newInstance(this.object.getValue(), 0).getClass();
    }

    @Override
    public @NotNull String toString() {
        return getComponentsType().toString().replace(".class", "[].class");
    }

    /**
     * Instantiates a new {@link ArrayClassValue} from the given {@link ClassValue}.
     *
     * @param <V>        the type of the components
     * @param classValue the class value of the components
     * @return the array class value
     */
    public static <V> @NotNull ArrayClassValue<V> of(final ClassValue<V> classValue) {
        return new ArrayClassValue<>(classValue);
    }

}
