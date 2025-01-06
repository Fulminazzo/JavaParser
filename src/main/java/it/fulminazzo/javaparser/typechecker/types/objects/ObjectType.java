package it.fulminazzo.javaparser.typechecker.types.objects;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.typechecker.types.*;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * Represents an {@link Object} type, declared from its associated class canonical name.
 * The fields in this class represent the wrapper types in Java (like {@link Integer}).
 * The distinction between these and {@link PrimitiveClassType}
 * is mandatory for correct functioning of casts, operations, null assignments and more.
 */
public final class ObjectType extends ObjectWrapper<Class<?>> implements Type {
    public static final ObjectType BYTE = new ObjectType(Byte.class);
    public static final ObjectType SHORT = new ObjectType(Short.class);
    public static final ObjectType CHARACTER = new ObjectType(Character.class);
    public static final ObjectType INTEGER = new ObjectType(Integer.class);
    public static final ObjectType LONG = new ObjectType(Long.class);
    public static final ObjectType FLOAT = new ObjectType(Float.class);
    public static final ObjectType DOUBLE = new ObjectType(Double.class);
    public static final ObjectType BOOLEAN = new ObjectType(Boolean.class);
    public static final ObjectType STRING = new ObjectType(String.class);
    public static final ObjectType OBJECT = new ObjectType(Object.class);

    private static final String[] IMPLIED_PACKAGES = new String[]{
            String.class.getPackage().getName(),
            Map.class.getPackage().getName(),
            IOException.class.getPackage().getName()
    };

    private ObjectType(final @NotNull Class<?> innerClass) {
        super(innerClass);
    }

    Class<?> getInnerClass() {
        return this.object;
    }

    @Override
    public @NotNull PrimitiveType toPrimitive() {
        if (equals(BYTE)) return PrimitiveType.BYTE;
        else if (equals(SHORT)) return PrimitiveType.SHORT;
        else if (equals(CHARACTER)) return PrimitiveType.CHAR;
        else if (equals(INTEGER)) return PrimitiveType.INT;
        else if (equals(LONG)) return PrimitiveType.LONG;
        else if (equals(FLOAT)) return PrimitiveType.FLOAT;
        else if (equals(DOUBLE)) return PrimitiveType.DOUBLE;
        else if (equals(BOOLEAN)) return PrimitiveType.BOOLEAN;
        else return Type.super.toPrimitive();
    }

    @Override
    public @NotNull ClassType toClass() {
        return ClassType.of(getInnerClass());
    }

    @Override
    public @NotNull String toString() {
        return Type.print(getClassName(getInnerClass()));
    }

    /**
     * Gets the class name from the given class.
     * If its package is present in {@link #IMPLIED_PACKAGES},
     * it will be stripped.
     *
     * @param clazz the class
     * @return the name
     */
    public static @NotNull String getClassName(final @NotNull Class<?> clazz) {
        String className = clazz.getCanonicalName();
        for (String impliedPackage : IMPLIED_PACKAGES) {
            impliedPackage += ".";
            if (className.startsWith(impliedPackage)) {
                className = className.substring(impliedPackage.length());
                break;
            }
        }
        return className;
    }

    /**
     * Returns all the {@link ObjectType}s fields in this class.
     *
     * @return the object types
     */
    public static ObjectType @NotNull [] values() {
        Refl<?> refl = new Refl<>(ObjectType.class);
        return refl.getFields(f -> Modifier.isStatic(f.getModifiers()) &&
                        ObjectType.class.isAssignableFrom(f.getType())).stream()
                .map(refl::getFieldObject)
                .map(o -> (ObjectType) o)
                .toArray(ObjectType[]::new);
    }

    /**
     * Obtains an instance of {@link ObjectType} from the given class name.
     *
     * @param className the class name
     * @return the object type
     * @throws TypeException the exception thrown in case the class is not found
     */
    public static @NotNull ObjectType of(final @NotNull String className) throws TypeException {
        return of(getClass(className));
    }

    /**
     * Obtains an instance of {@link ObjectType} from the given class name.
     *
     * @param clazz the clazz
     * @return the object type
     */
    public static @NotNull ObjectType of(final @NotNull Class<?> clazz) {
        return new ObjectType(clazz);
    }

    /**
     * Searches for a class matching the given class name.
     * If no match is found, it tries to append the packages in
     * {@link #IMPLIED_PACKAGES}.
     * If still nothing is found, a {@link TypeException} is thrown.
     *
     * @param className the class name
     * @return the class
     * @throws TypeException the exception thrown in case the class is not found
     */
    static @NotNull Class<?> getClass(final @NotNull String className) throws TypeException {
        try {
            return ReflectionUtils.getClass(className);
        } catch (IllegalArgumentException e) {
            for (String impliedPackage : IMPLIED_PACKAGES) {
                String name = impliedPackage + "." + className;
                try {
                    return ReflectionUtils.getClass(name);
                } catch (IllegalArgumentException ignored) {
                }
            }
            throw TypeException.classNotFound(className);
        }
    }

}
