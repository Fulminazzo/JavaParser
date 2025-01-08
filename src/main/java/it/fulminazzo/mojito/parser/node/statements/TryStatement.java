package it.fulminazzo.mojito.parser.node.statements;

import it.fulminazzo.mojito.parser.node.AssignmentBlock;
import it.fulminazzo.mojito.parser.node.container.CodeBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the statement associated with {@link it.fulminazzo.mojito.tokenizer.TokenType#TRY}.
 */
public class TryStatement extends Statement {
    private final @NotNull CodeBlock block;
    private final @NotNull List<CatchStatement> catchBlocks;
    private final @NotNull CodeBlock finallyBlock;

    /**
     * Instantiates a new Try statement.
     *
     * @param assignmentBlock the assignment block
     * @param block           the block executed
     * @param catchBlocks     the catch blocks
     * @param finallyBlock    the finally block
     */
    public TryStatement(final @NotNull AssignmentBlock assignmentBlock,
                        final @NotNull CodeBlock block,
                        final @NotNull List<CatchStatement> catchBlocks,
                        final @NotNull CodeBlock finallyBlock) {
        super(assignmentBlock);
        this.block = block;
        this.catchBlocks = catchBlocks;
        this.finallyBlock = finallyBlock;
    }

}
