package it.fulminazzo.javaparser.typechecker.types.objects;

import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.TypeException;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents an {@link Object} value, declared from its associated class canonical name.
 */
@Getter(AccessLevel.PACKAGE)
public final class ObjectType implements Type {
    public static final ObjectType BYTE = new ObjectType(Byte.class);
    public static final ObjectType CHARACTER = new ObjectType(Character.class);
    public static final ObjectType SHORT = new ObjectType(Short.class);
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
    };
    private final @NotNull Class<?> innerClass;

    private ObjectType(final @NotNull Class<?> innerClass) {
        this.innerClass = innerClass;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() + this.innerClass.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ObjectType && this.innerClass.equals(((ObjectType) o).innerClass);
    }

    @Override
    public String toString() {
        String className = this.innerClass.getCanonicalName();
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
        Class<?> clazz = getClass(className);
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
