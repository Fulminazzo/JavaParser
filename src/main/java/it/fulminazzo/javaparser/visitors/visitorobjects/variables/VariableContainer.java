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
abstract class VariableContainer<
        C extends ClassVisitorObject<C, O, P>,
        O extends VisitorObject<C, O, P>,
        P extends ParameterVisitorObjects<C, O, P>
        > implements VisitorObject<C, O, P> {
    protected final @NotNull C type;
    protected final @NotNull O value;

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
     * @return the new value set
     */
    public abstract @NotNull O set(final @NotNull O newValue);

    @Override
    public boolean isPrimitive() {
        return this.value.isPrimitive();
    }

    @Override
    public boolean isNull() {
        return this.value.isNull();
    }

    @Override
    public boolean is(@NotNull Class<?> object) {
        return this.value.is(object);
    }

    @Override
    public boolean is(O @NotNull ... objects) {
        return this.value.is(objects);
    }

    @Override
    public boolean isAssignableFrom(@NotNull ClassVisitorObject<C, O, P> classVisitorObject) {
        return this.value.isAssignableFrom(classVisitorObject);
    }

    @Override
    public <T extends VisitorObject<C, O, P>> @NotNull T check(@NotNull Class<T> clazz) {
        return this.value.check(clazz);
    }

    @Override
    public @NotNull C checkClass() {
        return this.value.checkClass();
    }

    @Override
    public @NotNull FieldContainer<C, O, P> getField(@NotNull String fieldName) throws VisitorObjectException {
        return this.value.getField(fieldName);
    }

    @Override
    public @NotNull O invokeMethod(@NotNull String methodName, @NotNull P parameters) throws VisitorObjectException {
        return this.value.invokeMethod(methodName, parameters);
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
        return this.value.toClass();
    }

    @Override
    public @NotNull O and(@NotNull O other) {
        return this.value.and(other);
    }

    @Override
    public @NotNull O or(@NotNull O other) {
        return this.value.or(other);
    }

    @Override
    public @NotNull O equal(@NotNull O other) {
        return this.value.equal(other);
    }

    @Override
    public @NotNull O notEqual(@NotNull O other) {
        return this.value.notEqual(other);
    }

    @Override
    public @NotNull O lessThan(@NotNull O other) {
        return this.value.lessThan(other);
    }

    @Override
    public @NotNull O lessThanEqual(@NotNull O other) {
        return this.value.lessThanEqual(other);
    }

    @Override
    public @NotNull O greaterThan(@NotNull O other) {
        return this.value.greaterThan(other);
    }

    @Override
    public @NotNull O greaterThanEqual(@NotNull O other) {
        return this.value.greaterThanEqual(other);
    }

    @Override
    public @NotNull O bitAnd(@NotNull O other) {
        return this.value.bitAnd(other);
    }

    @Override
    public @NotNull O bitOr(@NotNull O other) {
        return this.value.bitOr(other);
    }

    @Override
    public @NotNull O bitXor(@NotNull O other) {
        return this.value.bitXor(other);
    }

    @Override
    public @NotNull O lshift(@NotNull O other) {
        return this.value.lshift(other);
    }

    @Override
    public @NotNull O rshift(@NotNull O other) {
        return this.value.rshift(other);
    }

    @Override
    public @NotNull O urshift(@NotNull O other) {
        return this.value.urshift(other);
    }

    @Override
    public @NotNull O add(@NotNull O other) {
        return this.value.add(other);
    }

    @Override
    public @NotNull O subtract(@NotNull O other) {
        return this.value.subtract(other);
    }

    @Override
    public @NotNull O multiply(@NotNull O other) {
        return this.value.multiply(other);
    }

    @Override
    public @NotNull O divide(@NotNull O other) {
        return this.value.divide(other);
    }

    @Override
    public @NotNull O modulo(@NotNull O other) {
        return this.value.modulo(other);
    }

    @Override
    public @NotNull O minus() {
        return this.value.minus();
    }

    @Override
    public @NotNull O not() {
        return this.value.not();
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this.value.equals(o);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), this.value);
    }

}
