package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.fulmicollection.utils.ReflectionUtils;
import it.fulminazzo.javaparser.environment.Info;
import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectClassType;
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
     * Gets the actual class represented by this type.
     *
     * @return the class
     */
    @NotNull Class<?> toJavaClass();

    @Override
    default boolean isExtending(@NotNull ClassType classType) {
        return classType.toJavaClass().isAssignableFrom(toJavaClass());
    }

    /**
     * Checks whether the current class type extends the provided type.
     * If not, throws a {@link TypeCheckerException}.
     *
     * @param classType the class type
     */
    default void checkExtends(final @NotNull ClassType classType) {
        if (!isExtending(classType))
            throw TypeCheckerException.invalidType(classType, this);
    }

    @Override
    default @NotNull Type newObject(final @NotNull Constructor<?> constructor,
                                    final @NotNull ParameterTypes parameterTypes) throws TypeException {
        if (!Modifier.isPublic(constructor.getModifiers()))
            throw TypeException.cannotAccessMethod(this, constructor);
        else return toType();
    }

    /**
     * Gets a new {@link ClassType} from the given class name.
     * Tries first to obtain from {@link PrimitiveClassType}.
     * If it fails, uses the fields of {@link ObjectClassType}.
     * Otherwise, a new type is created.
     *
     * @param className the class name
     * @return the class type
     * @throws TypeException the exception thrown in case the class is not found
     */
    static @NotNull ClassType of(final @NotNull String className) throws TypeException {
        try {
            String lowerCase = className.toLowerCase();
            if (lowerCase.equals(className)) return PrimitiveClassType.valueOf(className.toUpperCase());
        } catch (IllegalArgumentException ignored) {
        }
        return ObjectClassType.of(className);
    }

    /**
     * Gets a new {@link ClassType} from the given class.
     * Tries first to obtain from {@link PrimitiveClassType}.
     * If it fails, uses the fields of {@link ObjectClassType}.
     * Otherwise, a new type is created.
     *
     * @param clazz the class
     * @return the class type
     */
    static @NotNull ClassType of(final @NotNull Class<?> clazz) {
        for (PrimitiveClassType type : PrimitiveClassType.values())
            if (type.toJavaClass().equals(clazz)) return type;
        return ObjectClassType.of(clazz);
    }

    /**
     * Converts the current {@link ClassType} to the matching {@link Type}.
     *
     * @return the type
     */
    @NotNull Type toType();

    @Override
    default @NotNull ClassType toClass() {
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
