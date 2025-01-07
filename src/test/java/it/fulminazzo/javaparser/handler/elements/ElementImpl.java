package it.fulminazzo.javaparser.handler.elements;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.handler.HandlerException;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObject;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
class ElementImpl extends ObjectWrapper<Object> implements VisitorObject<ClassElement, Element, ParameterElements>, Element {

    public ElementImpl(final @Nullable Object element) {
        super(element);
    }

    @Override
    public Object getElement() {
        return this.object;
    }

    @Override
    public boolean isPrimitive() {
        return !isNull() && ReflectionUtils.isPrimitive(getElementClass());
    }

    @Override
    public boolean isNull() {
        return this.object == null;
    }

    @Override
    public @NotNull Element toPrimitive() {
        if (!isNull() && ReflectionUtils.isPrimitiveOrWrapper(getElementClass()))
            if (!isPrimitive()) return Element.of(ReflectionUtils.getPrimitiveClass(getElementClass()).cast(getElement()));
        return Element.super.toPrimitive();
    }

    @Override
    public @NotNull Element toWrapper() {
        if (isPrimitive()) {
            Object newObject = ReflectionUtils.getWrapperClass(getElementClass()).cast(getElement());
            return Element.of(newObject);
        } else return Element.super.toWrapper();
    }

    @Override
    public @NotNull ClassElement toClass() {
        if (isNull()) throw new HandlerException("%s is null", this);
        else return new ClassElement(getElementClass());
    }

}
