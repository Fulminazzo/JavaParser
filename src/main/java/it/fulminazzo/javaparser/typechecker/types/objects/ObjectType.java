package it.fulminazzo.javaparser.typechecker.types.objects;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.TypeException;
import it.fulminazzo.javaparser.wrappers.ObjectWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents an {@link Object} value, declared from its associated class canonical name.
 */
public final class ObjectType extends ObjectWrapper<Class<?>> implements Type {
    public static final ObjectType OBJECT = new ObjectType(Object.class);

    private static final String[] IMPLIED_PACKAGES = new String[]{
            String.class.getPackage().getName(),
            Map.class.getPackage().getName(),
    };

    private ObjectType(final @NotNull Class<?> innerClass) {
        super(innerClass);
    }

    Class<?> getInnerClass() {
        return this.object;
    }

    @Override
    public @NotNull ClassType getClassType() {
        return ClassType.of(getInnerClass());
    }

    @Override
    public String toString() {
        String className = this.object.getCanonicalName();
        for (String impliedPackage : IMPLIED_PACKAGES) {
            impliedPackage += ".";
            if (className.startsWith(impliedPackage)) {
                className = className.substring(impliedPackage.length());
                break;
            }
        }
        return String.format("Type(%s)", className);
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
                } catch (IllegalArgumentException ignored) {}
            }
            throw new TypeException("Could not find class " + className);
        }
    }

}
