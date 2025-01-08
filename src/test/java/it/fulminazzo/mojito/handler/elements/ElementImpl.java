package it.fulminazzo.mojito.handler.elements;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.mojito.handler.HandlerException;
import it.fulminazzo.mojito.visitors.visitorobjects.VisitorObject;
import it.fulminazzo.mojito.wrappers.ObjectWrapper;
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
        if (!isNull() && ReflectionUtils.isPrimitiveOrWrapper(getElementClass()) && !isPrimitive())
                return Element.of(ReflectionUtils.getPrimitiveClass(getElementClass()).cast(getElement()));
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
        else return ClassElement.of(getElementClass());
    }

    @Override
    public String toString() {
        Object element = getElement();
        String elementClass = element == null ? null : element.getClass().getCanonicalName();
        return String.format("%s(%s: %s)", getClass().getCanonicalName(), elementClass, element);
    }

}
