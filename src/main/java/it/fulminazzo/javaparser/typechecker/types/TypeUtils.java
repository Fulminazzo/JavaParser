package it.fulminazzo.javaparser.typechecker.types;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Executable;

/**
 * A collection of utilities for the classes present in this package.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TypeUtils {

    /**
     * Checks whether the given {@link Executable} might be compatible with the {@link ParameterTypes}.
     * A {@link Executable} is said compatible when its parameters count is less than or equal
     * to the given ones. In case of less than, then it must access variable arguments and the
     * last parameter must be of type array.
     *
     * @param parameterTypes the parameter types
     * @param executable     the executable
     * @return true if it matches
     */
    public static boolean verifyExecutable(final @NotNull ParameterTypes parameterTypes,
                                           final @NotNull Executable executable) {
        int parameterCount = executable.getParameterCount();
        int parameterSize = parameterTypes.size();
        if (parameterCount == parameterSize) return true;
        // These checks are necessary for var args methods.
        if (parameterCount > parameterSize || parameterCount == 0) return false;
        return executable.isVarArgs() && executable.getParameterTypes()[parameterCount - 1].isArray();
    }

}
