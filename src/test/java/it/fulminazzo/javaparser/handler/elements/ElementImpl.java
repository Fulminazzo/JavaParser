package it.fulminazzo.javaparser.handler.elements;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.handler.HandlerException;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
class ElementImpl implements VisitorObject<ClassElement, Element, ParameterElements>, Element {
    protected final @Nullable Object element;

    public ElementImpl(final @Nullable Object element) {
        this.element = element;
    }

    @Override
    public boolean isPrimitive() {
        return !isNull() && ReflectionUtils.isPrimitive(getElementClass());
    }

    @Override
    public boolean isNull() {
        return this.element == null;
    }

    @Override
    public @NotNull Element toPrimitive() {
        if (!isNull() && ReflectionUtils.isPrimitiveOrWrapper(getElementClass()))
            if (!isPrimitive()) return new ElementImpl(ReflectionUtils.getPrimitiveClass(getElementClass()).cast(this.element));
        return Element.super.toPrimitive();
    }

    @Override
    public @NotNull Element toWrapper() {
        if (isPrimitive()) {
            Object newObject = ReflectionUtils.getWrapperClass(getElementClass()).cast(this.element);
            return new ElementImpl(newObject);
        } else return Element.super.toWrapper();
    }

    @Override
    public @NotNull ClassElement toClass() {
        if (isNull()) throw new HandlerException("%s is null", this);
        else return new ClassElement(getElementClass());
    }

}
