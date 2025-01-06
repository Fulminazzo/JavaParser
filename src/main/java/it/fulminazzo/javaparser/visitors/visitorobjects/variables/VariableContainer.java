package it.fulminazzo.javaparser.visitors.visitorobjects.variables;

import it.fulminazzo.javaparser.visitors.visitorobjects.ClassVisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.ParameterVisitorObjects;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObjectException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * A variable container is an abstract class that contains the information of a variable.
 * It also provides a {@link #set(VisitorObject)} method to update it.
 *
 * @param <C> the type of the {@link ClassVisitorObject}
 * @param <O> the type of the {@link VisitorObject}
 * @param <P> the type of the {@link ParameterVisitorObjects}
 */
@Getter
public abstract class VariableContainer<
        C extends ClassVisitorObject<C, O, P>,
        O extends VisitorObject<C, O, P>,
        P extends ParameterVisitorObjects<C, O, P>
        > implements VisitorObject<C, O, P> {
    protected final @NotNull C type;
    protected final @NotNull O variable;

    /**
     * Instantiates a new Variable container.
     *
     * @param type     the type
     * @param variable the value
     */
    public VariableContainer(final @NotNull C type,
                             final @NotNull O variable) {
        this.type = type;
        this.variable = variable;
    }

    /**
     * Updates the value of the variable.
     *
     * @param newValue the new value
     * @return the new value set
     */
    public abstract @NotNull O set(final @NotNull O newValue);

    @Override
    public boolean isPrimitive() {
        return this.variable.isPrimitive();
    }

    @Override
    public boolean isNull() {
        return this.variable.isNull();
    }

    @Override
    public boolean is(@NotNull Class<?> object) {
        return VisitorObject.super.is(object) || this.variable.is(object);
    }

    @Override
    public boolean is(O @NotNull ... objects) {
        return VisitorObject.super.is(objects) || this.variable.is(objects);
    }

    @Override
    public boolean isAssignableFrom(@NotNull ClassVisitorObject<C, O, P> classVisitorObject) {
        return VisitorObject.super.isAssignableFrom(classVisitorObject) || this.variable.isAssignableFrom(classVisitorObject);
    }

    @Override
    public <T extends VisitorObject<C, O, P>> @NotNull T check(@NotNull Class<T> clazz) {
        if (clazz.isAssignableFrom(getClass())) return clazz.cast(this);
        else return this.variable.check(clazz);
    }

    @Override
    public @NotNull C checkClass() {
        return this.variable.checkClass();
    }

    @Override
    public @NotNull FieldContainer<C, O, P> getField(@NotNull String fieldName) throws VisitorObjectException {
        return this.variable.getField(fieldName);
    }

    @Override
    public @NotNull O invokeMethod(@NotNull String methodName, @NotNull P parameters) throws VisitorObjectException {
        return this.variable.invokeMethod(methodName, parameters);
    }

    @Override
    public @NotNull O toPrimitive() {
        return this.variable.toPrimitive();
    }

    @Override
    public @NotNull O toWrapper() {
        return this.variable.toWrapper();
    }

    @Override
    public @NotNull C toClass() {
        return this.variable.toClass();
    }

    @Override
    public @NotNull O and(@NotNull O other) {
        return this.variable.and(other);
    }

    @Override
    public @NotNull O or(@NotNull O other) {
        return this.variable.or(other);
    }

    @Override
    public @NotNull O equal(@NotNull O other) {
        return this.variable.equal(other);
    }

    @Override
    public @NotNull O notEqual(@NotNull O other) {
        return this.variable.notEqual(other);
    }

    @Override
    public @NotNull O lessThan(@NotNull O other) {
        return this.variable.lessThan(other);
    }

    @Override
    public @NotNull O lessThanEqual(@NotNull O other) {
        return this.variable.lessThanEqual(other);
    }

    @Override
    public @NotNull O greaterThan(@NotNull O other) {
        return this.variable.greaterThan(other);
    }

    @Override
    public @NotNull O greaterThanEqual(@NotNull O other) {
        return this.variable.greaterThanEqual(other);
    }

    @Override
    public @NotNull O bitAnd(@NotNull O other) {
        return this.variable.bitAnd(other);
    }

    @Override
    public @NotNull O bitOr(@NotNull O other) {
        return this.variable.bitOr(other);
    }

    @Override
    public @NotNull O bitXor(@NotNull O other) {
        return this.variable.bitXor(other);
    }

    @Override
    public @NotNull O lshift(@NotNull O other) {
        return this.variable.lshift(other);
    }

    @Override
    public @NotNull O rshift(@NotNull O other) {
        return this.variable.rshift(other);
    }

    @Override
    public @NotNull O urshift(@NotNull O other) {
        return this.variable.urshift(other);
    }

    @Override
    public @NotNull O add(@NotNull O other) {
        return this.variable.add(other);
    }

    @Override
    public @NotNull O subtract(@NotNull O other) {
        return this.variable.subtract(other);
    }

    @Override
    public @NotNull O multiply(@NotNull O other) {
        return this.variable.multiply(other);
    }

    @Override
    public @NotNull O divide(@NotNull O other) {
        return this.variable.divide(other);
    }

    @Override
    public @NotNull O modulo(@NotNull O other) {
        return this.variable.modulo(other);
    }

    @Override
    public @NotNull O minus() {
        return this.variable.minus();
    }

    @Override
    public @NotNull O not() {
        return this.variable.not();
    }

    @Override
    public int hashCode() {
        return this.variable.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this.variable.equals(o instanceof VariableContainer ? ((VariableContainer<?, ?, ?>) o).variable : o);
    }

    @Override
    public String toString() {
        return this.variable.toString();
    }

}
