package it.fulminazzo.javaparser;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * An object capable of reading, parsing and executing Java code.
 */
public interface Runner {

    /**
     * Gets the result of the latest computation.
     *
     * @return an optional that may contain the output of the computation
     */
    @NotNull Optional<?> latestResult();

    /**
     * Runs the given code.
     *
     * @param code the code
     * @return an optional that may contain the output of the program (if present)
     */
    default @NotNull Optional<?> run(final @NotNull String code) {
        return run(code, new HashMap<>());
    }

    /**
     * Runs the given code.
     *
     * @param code      the code
     * @param variables a map containing all the variables to inject before executing the code
     * @return an optional that may contain the output of the program (if present)
     */
    default @NotNull Optional<?> run(final @NotNull String code, final @NotNull Map<String, Object> variables) {
        return run(new ByteArrayInputStream(code.getBytes()), variables);
    }

    /**
     * Reads the contents of the given file and executes Java code from it.
     *
     * @param file the file containing the code
     * @return an optional that may contain the output of the program (if present)
     */
    default @NotNull Optional<?> run(final @NotNull File file) {
        return run(file, new HashMap<>());
    }

    /**
     * Reads the contents of the given file and executes Java code from it.
     *
     * @param file      the file containing the code
     * @param variables a map containing all the variables to inject before executing the code
     * @return an optional that may contain the output of the program (if present)
     */
    default @NotNull Optional<?> run(final @NotNull File file, final @NotNull Map<String, Object> variables) {
        try {
            return run(new FileInputStream(file), variables);
        } catch (FileNotFoundException e) {
            throw RunnerException.cannotFindFile(file.getPath());
        }
    }

    /**
     * Reads the contents of the given stream and executes Java code from it.
     *
     * @param input the stream containing the code
     * @return an optional that may contain the output of the program (if present)
     */
    default @NotNull Optional<?> run(final @NotNull InputStream input) {
        return run(input, new HashMap<>());
    }

    /**
     * Reads the contents of the given stream and executes Java code from it.
     *
     * @param input     the stream containing the code
     * @param variables a map containing all the variables to inject before executing the code
     * @return an optional that may contain the output of the program (if present)
     */
    @NotNull Optional<?> run(final @NotNull InputStream input, final @NotNull Map<String, Object> variables);

}
