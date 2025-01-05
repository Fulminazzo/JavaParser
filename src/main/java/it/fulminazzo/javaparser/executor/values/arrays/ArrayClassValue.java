package it.fulminazzo.javaparser.executor.values.arrays;

import it.fulminazzo.javaparser.executor.values.ClassValue;
import it.fulminazzo.javaparser.executor.values.Value;
import it.fulminazzo.javaparser.executor.values.Values;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

/**
 * Represents the class value for an array.
 *
 * @param <V> the type of the components
 */
@SuppressWarnings("unchecked")
public class ArrayClassValue<V> extends ObjectWrapper<ClassValue<V>> implements ClassValue<V[]> {

    /**
     * Instantiates a new Array class value.
     *
     * @param classValue the {@link ClassValue} of the components
     */
    ArrayClassValue(final ClassValue<V> classValue) {
        super(classValue);
    }

    @Override
    public @NotNull Class<V[]> getWrapperValue() {
        return of(ClassValue.of(getComponentsType().getWrapperValue())).getValue();
    }

    @Override
    public @NotNull Value<V[]> cast(@NotNull Value<?> value) {
        Object[] internal = (Object[]) value.getValue();
        Value<V[]> returnedValue = ArrayValue.of(getComponentsType(), internal.length);
        for (int i = 0; i < internal.length; i++)
            returnedValue.getValue()[i] = getComponentsType().cast(Value.of(internal[i])).getValue();
        return returnedValue;
    }

    @Override
    public boolean compatibleWith(@NotNull Value<?> value) {
        if (value.is(Values.NULL_VALUE) && !this.object.isPrimitive()) return true;
        if (value.is(ArrayValue.class)) {
            Class<V> objectClass = this.object.getValue();
            Class<?> componentsClass = ((ArrayValue<?>) value).getComponentsType().getValue();
            return objectClass.isAssignableFrom(componentsClass);
        } else return false;
    }

    @Override
    public @NotNull Class<V[]> getValue() {
        return (Class<V[]>) Array.newInstance(this.object.getValue(), 0).getClass();
    }

    /**
     * Gets the components type.
     *
     * @return the components type
     */
    public ClassValue<V> getComponentsType() {
        return this.object;
    }

    @Override
    public @NotNull String toString() {
        return getComponentsType().toString().replace(".class", "[].class");
    }

    /**
     * Instantiates a new {@link ArrayClassValue} from the given {@link ClassValue}.
     *
     * @param <V>        the type of the value
     * @param classValue the class value
     * @return the array class value
     */
    public static <V> @NotNull ArrayClassValue<V> of(final ClassValue<V> classValue) {
        return new ArrayClassValue<>(classValue);
    }

}
