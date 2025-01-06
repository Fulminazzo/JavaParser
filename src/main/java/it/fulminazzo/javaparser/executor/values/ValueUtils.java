package it.fulminazzo.javaparser.executor.values;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Executable;

/**
 * A collection of utilities for the classes present in this package.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValueUtils {

    /**
     * Checks whether the given {@link Executable} might be compatible with the {@link ParameterValues}.
     * A {@link Executable} is said compatible when its parameters count is less than or equal
     * to the given ones. In case of less than, then it must access variable arguments and the
     * last parameter must be of type array.
     *
     * @param parameterValues the parameter values
     * @param executable      the executable
     * @return true if it matches
     */
    public static boolean verifyExecutable(final @NotNull ParameterValues parameterValues,
                                           final @NotNull Executable executable) {
        int parameterCount = executable.getParameterCount();
        int parameterSize = parameterValues.size();
        if (parameterCount == parameterSize) return true;
        // These checks are necessary for var args methods.
        if (parameterCount > parameterSize || parameterCount == 0) return false;
        return executable.isVarArgs() && executable.getParameterTypes()[parameterCount - 1].isArray();
    }

}
