package it.fulminazzo.mojito.parser.node.statements;

import it.fulminazzo.mojito.parser.node.Node;
import it.fulminazzo.mojito.parser.node.container.CodeBlock;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the code block associated with {@link it.fulminazzo.mojito.tokenizer.TokenType#CASE}.
 */
public class CaseStatement extends Statement {
    private final @NotNull CodeBlock block;

    /**
     * Instantiates a new Case statement.
     *
     * @param expression the expression
     * @param block      the body
     */
    public CaseStatement(final @NotNull Node expression, final @NotNull CodeBlock block) {
        super(expression);
        this.block = block;
    }

}
