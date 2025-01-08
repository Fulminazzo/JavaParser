package it.fulminazzo.mojito.parser.node.statements;

import it.fulminazzo.mojito.parser.node.Node;
import it.fulminazzo.mojito.parser.node.container.CodeBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a switch statement.
 */
public class SwitchStatement extends Statement {
    private final @NotNull List<CaseStatement> cases;
    private final @NotNull CodeBlock defaultBlock;

    /**
     * Instantiates a new Switch statement.
     *
     * @param expression   the expression
     * @param cases        the cases
     * @param defaultBlock the default block
     */
    public SwitchStatement(final @NotNull Node expression,
                           final @NotNull List<CaseStatement> cases,
                           final @NotNull CodeBlock defaultBlock) {
        super(expression);
        this.cases = cases;
        this.defaultBlock = defaultBlock;
    }

}
