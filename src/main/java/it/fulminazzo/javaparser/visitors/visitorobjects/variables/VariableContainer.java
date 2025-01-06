package it.fulminazzo.javaparser.visitors.visitorobjects.variables;

import it.fulminazzo.javaparser.visitors.visitorobjects.ClassVisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.ParameterVisitorObjects;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObjectException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * A variable container is an abstract class that contains the information of a variable.
 * It also provides a {@link #set(VisitorObject)} method to update it.
 *
 * @param <C> the type of the {@link ClassVisitorObject}
 * @param <O> the type of the {@link VisitorObject}
 * @param <P> the type of the {@link ParameterVisitorObjects}
 */
@Getter
abstract class VariableContainer<
        C extends ClassVisitorObject<C, O, P>,
        O extends VisitorObject<C, O, P>,
        P extends ParameterVisitorObjects<C, O, P>
        > implements VisitorObject<C, O, P> {
    private final @NotNull C type;
    private final @NotNull O value;

    /**
     * Instantiates a new Variable container.
     *
     * @param type  the type
     * @param value the value
     */
    public VariableContainer(final @NotNull C type,
                             final @NotNull O value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Updates the value of the variable.
     *
     * @param newValue the new value
     */
    public abstract void set(final @NotNull O newValue);

    @Override
    public boolean isPrimitive() {
        return this.value.isPrimitive();
    }

    @Override
    public boolean isNull() {
        return this.value.isNull();
    }

    @Override
    public <T extends VisitorObject<C, O, P>> @NotNull T check(@NotNull Class<T> clazz) {
        return this.value.check(clazz);
    }

    @Override
    public @NotNull FieldContainer<C, O, P> getField(@NotNull Field field) throws VisitorObjectException {
        return this.value.getField(field);
    }

    @Override
    public @NotNull O invokeMethod(@NotNull Method method, @NotNull P parameters) throws VisitorObjectException {
        return this.value.invokeMethod(method, parameters);
    }

    @Override
    public @NotNull O toPrimitive() {
        return this.value.toPrimitive();
    }

    @Override
    public @NotNull O toWrapper() {
        return this.value.toWrapper();
    }

    @Override
    public @NotNull C toClass() {
        return this.type;
    }

}
