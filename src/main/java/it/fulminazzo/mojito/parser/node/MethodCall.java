package it.fulminazzo.mojito.parser.node;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a method call invocation.
 */
public class MethodCall extends NodeImpl {
    private final @NotNull Node executor;
    private final @NotNull String methodName;
    private final @NotNull MethodInvocation invocation;

    /**
     * Instantiates a new Method call.
     *
     * @param executor   the executor
     * @param methodName the method name
     * @param invocation the invocation
     */
    public MethodCall(final @NotNull Node executor,
                      final @NotNull String methodName,
                      final @NotNull MethodInvocation invocation) {
        this.executor = executor;
        this.methodName = methodName;
        this.invocation = invocation;
    }

}
