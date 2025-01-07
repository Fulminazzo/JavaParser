package it.fulminazzo.javaparser.handler.elements;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.visitors.visitorobjects.ClassVisitorObject;
import it.fulminazzo.javaparser.visitors.visitorobjects.VisitorObjectException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;

public class ClassElement extends ElementImpl implements ClassVisitorObject<ClassElement, Element, ParameterElements> {

    public ClassElement(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public boolean isPrimitive() {
        return ReflectionUtils.isPrimitive(toJavaClass());
    }

    @Override
    public @NotNull Element cast(@NotNull Element object) {
        return new ElementImpl(toJavaClass().cast(object));
    }

    @Override
    public boolean compatibleWith(@NotNull Element object) {
        return toJavaClass().isAssignableFrom(object.toClass().toJavaClass());
    }

    @Override
    public @NotNull Element newObject(@NotNull Constructor<?> constructor, @NotNull ParameterElements parameters) throws VisitorObjectException {
        return null;
    }

    @Override
    public @NotNull Class<?> toJavaClass() {
        return (Class<?>) this.object;
    }

    @Override
    public @NotNull ElementImpl toObject() {
        if (isPrimitive()) {
            Class<?> clazz = toJavaClass();
            if (clazz.equals(byte.class)) return new ElementImpl((byte) 0);
            else if (clazz.equals(char.class)) return new ElementImpl((char) 0);
            else if (clazz.equals(short.class)) return new ElementImpl((short) 0);
            else if (clazz.equals(int.class)) return new ElementImpl(0);
            else if (clazz.equals(long.class)) return new ElementImpl(0L);
            else if (clazz.equals(float.class)) return new ElementImpl(0f);
            else if (clazz.equals(double.class)) return new ElementImpl(0d);
            else return new ElementImpl(false);
        } else return new ElementImpl(null);
    }

}
