package it.fulminazzo.javaparser.parser.node;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the parameters invoked during a method invocation.
 */
public class MethodInvocation extends Node {
    private final List<Node> parameters;

    /**
     * Instantiates a new Method invocation.
     *
     * @param parameters the parameters
     */
    public MethodInvocation(final @NotNull List<Node> parameters) {
        this.parameters = parameters;
    }

    @Override
    public @NotNull String toString() {
        String output = super.toString();
        final String className = getClass().getSimpleName();
        output = output.substring(className.length() + 2);
        output = output.substring(0, output.length() - 2);
        return String.format("%s(%s)", className, output);
    }

}
