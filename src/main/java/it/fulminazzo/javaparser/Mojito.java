package it.fulminazzo.javaparser;

import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.parser.node.values.StringValueLiteral;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Starting point of the program.
 */
public final class Mojito {
    private static final String[] HELP_OPTIONS = new String[]{"-h", "--help", "/?"};

    public static void main(final String @NotNull [] args) {
        try {
            if (args.length == 0) throw new ArgumentsException();
            String argument = args[0];

            for (String o : HELP_OPTIONS)
                if (argument.equalsIgnoreCase(o))
                    throw new ArgumentsException();

            Runner runner = newRunner();

            if (argument.equalsIgnoreCase("--code")) ; //TODO:
            else ; //TODO:
        } catch (ArgumentsException e) {
            System.out.println("Usage:");
            System.out.println("java -jar mojito.jar <filename> <var1:val1> <var2:val2>...");
            System.out.println("java -jar mojito.jar --code \"code to run\" <var1:val1> <var2:val2>...");
        }
    }

    /**
     * Uses {@link Runner} to read the given array expecting a <code>key:value</code>
     * pair for each element.
     *
     * @param arguments the array
     * @param start     the index to start from
     * @return a map with the parsed variables
     */
    static @NotNull Map<String, Object> parseVariables(final String @NotNull [] arguments, int start) {
        Map<String, Object> variables = new HashMap<>();

        for (int i = start; i < arguments.length; i++) {
            String argument = arguments[i];
            try {
                if (argument.contains(":")) {
                    String[] parts = argument.split(":");
                    String name = new StringValueLiteral(String.format("\"%s\"", parts[0])).getRawValue();
                    String value = String.join(":", Arrays.copyOfRange(parts, 1, parts.length));

                    //TODO: value logic
                } else throw new ArgumentsException("Expected 'key:value' pair");
            } catch (ArgumentsException | NodeException e) {
                throw new RunnerException("Error for variable '%s' at index %s: %s", argument, i, e.getMessage());
            }
        }

        return variables;
    }

    /**
     * Creates a new {@link Runner} with {@link Object} as executor.
     *
     * @return the runner
     */
    public static @NotNull Runner newRunner() {
        return newRunner(new Object());
    }

    /**
     * Creates a new {@link Runner} with the given object as executor.
     *
     * @param executor the executing object
     * @return the runner
     */
    public static @NotNull Runner newRunner(final @NotNull Object executor) {
        return new MojitoRunner(executor);
    }

    /**
     * A helper exception for many functions of this class.
     */
    private static class ArgumentsException extends Exception {

        /**
         * Instantiates a new Arguments exception.
         */
        public ArgumentsException() {
        }

        /**
         * Instantiates a new Arguments exception.
         *
         * @param message the message
         * @param args    the arguments to add in the message format
         */
        public ArgumentsException(final @NotNull String message, final Object @NotNull ... args) {
            super(String.format(message, args));
        }

    }

}
