package it.fulminazzo.javaparser.handler.elements;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.handler.HandlerException;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Element implements VisitorObject<ClassElement, Element, ParameterElements>, IElement {
    protected final @Nullable Object object;

    public Element(final @Nullable Object object) {
        this.object = object;
    }

    @Override
    public boolean isPrimitive() {
        return !isNull() && ReflectionUtils.isPrimitive(getObjectClass());
    }

    @Override
    public boolean isNull() {
        return this.object == null;
    }

    @Override
    public @NotNull Element toPrimitive() {
        if (!isNull() && ReflectionUtils.isPrimitiveOrWrapper(getObjectClass()))
            if (!isPrimitive()) return new Element(ReflectionUtils.getPrimitiveClass(getObjectClass()).cast(this.object));
        return IElement.super.toPrimitive();
    }

    @Override
    public @NotNull Element toWrapper() {
        if (isPrimitive()) {
            Object newObject = ReflectionUtils.getWrapperClass(getObjectClass()).cast(this.object);
            return new Element(newObject);
        } else return IElement.super.toWrapper();
    }

    @Override
    public @NotNull ClassElement toClass() {
        if (isNull()) throw new HandlerException("%s is null", this);
        else return new ClassElement(getObjectClass());
    }

    protected @NotNull Class<?> getObjectClass() {
        return (Class<?>) toClass().object;
    }

}
