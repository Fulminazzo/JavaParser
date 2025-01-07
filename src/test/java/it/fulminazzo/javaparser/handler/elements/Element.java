package it.fulminazzo.javaparser.handler.elements;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.handler.HandlerException;
import it.fulminazzo.javaparser.tokenizer.TokenType;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObjectException;
import it.fulminazzo.javaparser.visitors.visitorobjects.variables.FieldContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

@SuppressWarnings("unchecked")
public class Element implements VisitorObject<ClassElement, Element, ParameterElements> {
    protected final @Nullable Object object;

    public Element(final @Nullable Object object) {
        this.object = object;
    }

    @Override
    public boolean isPrimitive() {
        return this.object != null && ReflectionUtils.isPrimitive(this.object.getClass());
    }

    @Override
    public boolean isNull() {
        return this.object == null;
    }

    @Override
    public <T extends VisitorObject<ClassElement, Element, ParameterElements>> @NotNull T check(@NotNull Class<T> clazz) {
        if (clazz.isInstance(this)) return (T) this;
        else throw new HandlerException("%s is not an instance of %s", this, clazz.getCanonicalName());
    }

    @Override
    public @NotNull ClassElement checkClass() {
        if (this instanceof ClassElement) return (ClassElement) this;
        else throw new HandlerException("%s is not a %s", this, ClassElement.class.getSimpleName());
    }

    @Override
    public @NotNull FieldContainer<ClassElement, Element, ParameterElements> getField(@NotNull Field field) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull Element invokeMethod(@NotNull Method method, @NotNull ParameterElements parameters) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull Element toPrimitive() {
        if (this.object != null && ReflectionUtils.isPrimitiveOrWrapper(this.object.getClass()))
            if (!isPrimitive()) return new Element(ReflectionUtils.getPrimitiveClass(this.object.getClass()).cast(this.object));
        throw new HandlerException("%s is not a wrapper type", this);
    }

    @Override
    public @NotNull Element toWrapper() {
        if (isPrimitive()) {
            Object newObject = ReflectionUtils.getWrapperClass(this.object.getClass()).cast(this.object);
            return new Element(newObject);
        } else throw new HandlerException("%s is not a primitive type", this);
    }

    @Override
    public @NotNull ClassElement toClass() {
        if (isNull()) throw new HandlerException("%s is null", this);
        else return new ClassElement(this.object.getClass());
    }

    @Override
    public @NotNull VisitorObjectException fieldNotFound(@NotNull ClassElement classVisitorObject, @NotNull String field) {
        return new ElementException("Could not find field '%s' in type %s", field, classVisitorObject);
    }

    @Override
    public @NotNull VisitorObjectException methodNotFound(@NotNull ClassElement classVisitorObject, @NotNull String method,
                                                          @NotNull ParameterElements parameters) {
        return new ElementException("Could not find method %s(%s) in type %s", method, parameters, classVisitorObject);
    }

    @Override
    public @NotNull VisitorObjectException typesMismatch(@NotNull ClassElement classVisitorObject, @NotNull Executable method,
                                                         @NotNull ParameterElements parameters) {
        return new ElementException("Types mismatch: cannot apply parameters %s to method %s%s in type %s",
                parameters, method.getName(), Arrays.toString(method.getParameterTypes()), classVisitorObject);
    }

    @Override
    public @NotNull RuntimeException noClassType(@NotNull Class<?> type) {
        return new HandlerException("%s does not have a class", type.getCanonicalName());
    }

    @Override
    public @NotNull RuntimeException unsupportedOperation(@NotNull TokenType operator,
                                                          @NotNull VisitorObject<ClassElement, Element, ParameterElements> left,
                                                          @NotNull VisitorObject<ClassElement, Element, ParameterElements> right) {
        return new HandlerException("Operator '%s' cannot be applied to '%s', '%s'", operator, left, right);
    }

    @Override
    public @NotNull RuntimeException unsupportedOperation(@NotNull TokenType operator,
                                                          @NotNull VisitorObject<ClassElement, Element, ParameterElements> operand) {
        return new HandlerException("Operator '%s' cannot be applied to '%s'", operator, operand);
    }

}
