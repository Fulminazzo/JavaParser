package it.fulminazzo.javaparser;

import it.fulminazzo.javaparser.parser.ParserException;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Starting point of the program.
 */
public final class Mojito {
    private static final String[] HELP_OPTIONS = new String[] {"-h", "--help", "/?"};

    public static void main(final String @NotNull [] args) {
        String argument = (args.length == 0 ? HELP_OPTIONS : args)[0];

        for (String o : HELP_OPTIONS)
            if (argument.equalsIgnoreCase(o)) {
                System.out.println("Usage:");
                System.out.println("java -jar mojito.jar <filename>");
                System.out.println("java -jar mojito.jar \"code to run\"");
                return;
            }

        Runner runner = newRunner();
        try {
            runner.run(argument);
        } catch (ParserException e) {
            runner.run(new File(argument));
        }
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

}
