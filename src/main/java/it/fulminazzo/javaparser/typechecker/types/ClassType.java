package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.environment.Info;
import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the class of a {@link Type}.
 */
public interface ClassType extends Type, Info {

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
     * Gets a new {@link ClassType} from the given class name.
     * Tries first to obtain from {@link PrimitiveType}.
     * If it fails, uses the fields of {@link ClassObjectType}.
     * Otherwise, a new type is created.
     *
     * @param className the class name
     * @return the class type
     * @throws TypeException the exception thrown in case the class is not found
     */
    static ClassType of(final @NotNull String className) throws TypeException {
        try {
            return PrimitiveType.valueOf(className.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ClassObjectType.of(className);
        }
    }

}
