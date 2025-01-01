package it.fulminazzo.javaparser.parser.node.statements;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CaseBlock;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a switch statement.
 */
public class SwitchStatement extends Statement {
    private final @NotNull List<CaseBlock> cases;
    private final @NotNull CodeBlock defaultBlock;

    /**
     * Instantiates a new Switch statement.
     *
     * @param expression   the expression
     * @param cases        the cases
     * @param defaultBlock the default block
     */
    public SwitchStatement(final @NotNull Node expression,
                           final @NotNull List<CaseBlock> cases,
                           final @NotNull CodeBlock defaultBlock) {
        super(expression);
        this.cases = cases;
        this.defaultBlock = defaultBlock;
    }

}
