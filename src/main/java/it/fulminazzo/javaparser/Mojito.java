package it.fulminazzo.javaparser;

import it.fulminazzo.javaparser.parser.ParserException;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Starting point of the program.
 */
public final class Mojito {

    public static void main(final String @NotNull [] args) {
        String code = args.length == 0 ? "-h" : args[0];
        if (code.equals("-h") || code.equals("--help")) {
            System.out.println("Usage:");
            System.out.println("java -jar mojito.jar <filename>");
            System.out.println("java -jar mojito.jar \"code to run\"");
        } else {
            Runner runner = newRunner();
            try {
                runner.run(code);
            } catch (ParserException e) {
                runner.run(new File(code));
            }
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
