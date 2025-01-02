package it.fulminazzo.javaparser.executor;

import it.fulminazzo.javaparser.environment.Environment;
import it.fulminazzo.javaparser.executor.values.Value;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link Visitor} that executes all the objects of the parsed code.
 */
public class Executor {
    private final Object executingObject;
    private final Environment<Value<?>> environment;

    /**
     * Instantiates a new Executor.
     *
     * @param executingObject the executing object
     */
    public Executor(final @NotNull Object executingObject) {
        this.executingObject = executingObject;
        this.environment = new Environment<>();
    }

}
