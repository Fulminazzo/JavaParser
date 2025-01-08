package it.fulminazzo.javaparser;

import org.jetbrains.annotations.NotNull;

/**
 * Starting point of the program.
 */
public final class Mojito {
    private static final String[] HELP_OPTIONS = new String[] {"-h", "--help", "/?"};

    public static void main(final String @NotNull [] args) {
        try {
            if (args.length == 0) throw new NotEnoughArgumentException();
            String argument = args[0];

            for (String o : HELP_OPTIONS)
                if (argument.equalsIgnoreCase(o))
                    throw new NotEnoughArgumentException();

            Runner runner = newRunner();

            if (argument.equalsIgnoreCase("--code")); //TODO:
            else; //TODO:
        } catch (NotEnoughArgumentException e) {
            System.out.println("Usage:");
            System.out.println("java -jar mojito.jar <filename> <var1:val1> <var2:val2>...");
            System.out.println("java -jar mojito.jar --code \"code to run\" <var1:val1> <var2:val2>...");
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

    private static class NotEnoughArgumentException extends RuntimeException {}

}
