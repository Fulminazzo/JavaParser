package it.fulminazzo.mojito.parser.node.statements;

import it.fulminazzo.mojito.parser.node.Node;
import it.fulminazzo.mojito.parser.node.container.CodeBlock;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an if statement.
 */
public class IfStatement extends Statement {
    private final @NotNull CodeBlock code;
    private final @Nullable Node thenBranch;

    /**
     * Instantiates a new If statement.
     *
     * @param condition  the condition
     * @param code       the code that will be executed
     * @param thenBranch the alternative code
     */
    public IfStatement(final @NotNull Node condition,
                       final @NotNull CodeBlock code,
                       final @Nullable Node thenBranch) {
        super(condition);
        this.code = code;
        this.thenBranch = thenBranch;
    }

}
