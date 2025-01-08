package it.fulminazzo.mojito.executor;

import it.fulminazzo.mojito.executor.values.ClassValue;
import it.fulminazzo.mojito.executor.values.variables.ValueLiteralVariableContainer;
import it.fulminazzo.mojito.parser.node.Node;
import it.fulminazzo.mojito.parser.node.container.CodeBlock;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A utility class for {@link Executor#visitTryStatement(CodeBlock, List, CodeBlock, Node)}.
 */
@Getter
final class ExceptionTuple {
    private final @NotNull ClassValue<?> exceptionType;
    private final @NotNull ValueLiteralVariableContainer<?> exceptionName;

    /**
     * Instantiates a new Exception tuple.
     *
     * @param exceptionType the exception type
     * @param exceptionName the exception name
     */
    public ExceptionTuple(final @NotNull ClassValue<?> exceptionType,
                          final @NotNull ValueLiteralVariableContainer<?> exceptionName) {
        this.exceptionType = exceptionType;
        this.exceptionName = exceptionName;
    }

    @Override
    public int hashCode() {
        return this.exceptionType.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this.exceptionType.equals(o);
    }

}
