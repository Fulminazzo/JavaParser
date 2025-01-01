package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.environment.Info;
import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the class of a {@link Type}.
 */
public interface ClassType extends Type, Info {

    /**
     * Checks if the given type can be cast to the current class.
     *
     * @param type the type
     * @return the associated type of this class
     */
    @NotNull Type cast(final @NotNull Type type);

    /**
     * Gets the actual class represented by this type.
     *
     * @return the class
     */
    @NotNull Class<?> toJavaClass();

    /**
     * Checks whether the current class type extends the provided type.
     * If not, throws a {@link TypeCheckerException}.
     *
     * @param classType the class type
     */
    default void checkExtends(final @NotNull ClassType classType) {
        if (!classType.toJavaClass().isAssignableFrom(toJavaClass()))
            throw TypeCheckerException.invalidType(classType, this);
    }

    /**
     * Verifies that the current class type is compatible with the provided type.
     *
     * @param type the other type
     * @return true if it is
     */
    boolean compatibleWith(final @NotNull Type type);

    @Override
    default boolean compatibleWith(@NotNull Object object) {
        return object instanceof Type && compatibleWith((Type) object);
    }

    /**
     * Searches for a constructor matching the given {@link ParameterTypes}.
     * Then, returns {@link #toType()}.
     *e
     * @return the type
     * @throws TypeException thrown in case the constructor could not be found, could not be accessed
     * (only <code>public</code> modifier allowed) or the given types did not match the expected ones
     */
    default @NotNull Type newObject(final @NotNull ParameterTypes parameterTypes) throws TypeException {
        final String methodName = "<init>";
        final ClassType classType = this;
        try {
            Class<?> javaClass = classType.toJavaClass();
            @NotNull List<Constructor<?>> constructors = Arrays.stream(javaClass.getDeclaredConstructors())
                    .filter(c -> TypeUtils.verifyExecutable(parameterTypes, c))
                    .collect(Collectors.toList());
            if (constructors.isEmpty()) throw new IllegalArgumentException();

            Refl<?> refl = new Refl<>(ReflectionUtils.class);
            Class<?> @NotNull [] parametersTypes = parameterTypes.toJavaClassArray();
            for (Constructor<?> method : constructors) {
                // For each one, validate its parameters
                if (Boolean.TRUE.equals(refl.invokeMethod("validateParameters",
                        new Class[]{Class[].class, Executable.class},
                        parametersTypes, method))) {
                    if (!Modifier.isPublic(method.getModifiers()))
                        throw TypeException.cannotAccessMethod(classType, method);
                    else return toType();
                }
            }

            throw TypeException.typesMismatch(classType, constructors.get(0), parameterTypes);
        } catch (IllegalArgumentException e) {
            throw TypeException.methodNotFound(classType, methodName, parameterTypes);
        }
    }

    /**
     * Gets a new {@link ClassType} from the given class name.
     * Tries first to obtain from {@link PrimitiveType}.
     * If it fails, uses the fields of {@link ClassObjectType}.
     * Otherwise, a new type is created.
     *
     * @param className the class name
     * @return the class type
     * @throws TypeException the exception thrown in case the class is not found
     */
    static @NotNull ClassType of(final @NotNull String className) throws TypeException {
        try {
            String lowerCase = className.toLowerCase();
            if (lowerCase.equals(className)) return PrimitiveType.valueOf(className.toUpperCase());
        } catch (IllegalArgumentException ignored) {}
        return ClassObjectType.of(className);
    }

    /**
     * Gets a new {@link ClassType} from the given class.
     * Tries first to obtain from {@link PrimitiveType}.
     * If it fails, uses the fields of {@link ClassObjectType}.
     * Otherwise, a new type is created.
     *
     * @param clazz the class
     * @return the class type
     */
    static @NotNull ClassType of(final @NotNull Class<?> clazz) {
        for (PrimitiveType type : PrimitiveType.values())
            if (type.toJavaClass().equals(clazz)) return type;
        return ClassObjectType.of(clazz);
    }

    /**
     * Converts the current {@link ClassType} to the matching {@link Type}.
     *
     * @return the type
     */
    @NotNull Type toType();

    @Override
    default @NotNull ClassType toClassType() {
        return ClassType.of(Class.class);
    }

    /**
     * Prints the given string to the format of a class.
     *
     * @param output the output
     * @return the new output
     */
    static @NotNull String print(final @NotNull String output) {
        return output + ".class";
    }

}
