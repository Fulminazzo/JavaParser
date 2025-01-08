package it.fulminazzo.javaparser;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.javaparser.executor.Executor;
import it.fulminazzo.javaparser.parser.JavaParser;
import it.fulminazzo.javaparser.parser.ParserException;
import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.NodeException;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import it.fulminazzo.javaparser.typechecker.TypeChecker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
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
            else {
                File file = new File(argument);
                @NotNull Map<String, Object> variables = parseVariables(args, 1);
                runner.run(file, variables);
            }
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
                    String name = Literal.of(parts[0]).getLiteral();
                    String value = String.join(":", Arrays.copyOfRange(parts, 1, parts.length));

                    try {
                        variables.put(name, parseExpression(value));
                    } catch (ParserException e) {
                        throw new ArgumentsException("No value was returned from key");
                    }
                } else throw new ArgumentsException("Expected 'key:value' pair");
            } catch (ArgumentsException | NodeException e) {
                throw new RunnerException("Error for variable '%s' at index %s: %s", argument, i, e.getMessage());
            }
        }

        return variables;
    }

    /**
     * Reads a simple Java expression and converts it to a value.
     *
     * @param expression the expression
     * @return the object returned
     */
    static @Nullable Object parseExpression(final @NotNull String expression) {
        final Object executingObject = new Object();

        JavaParser parser = new JavaParser();
        parser.setInput(expression);
        Node parsed = new Refl<>(parser).callMethod("nextSpaceless").invokeMethod("parseExpression");

        TypeChecker checker = new TypeChecker(executingObject);
        parsed.accept(checker);

        Executor executor = new Executor(executingObject);
        return parsed.accept(executor).getValue();
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
