package it.fulminazzo.javaparser.parser.node.statements;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CaseBlock;
import it.fulminazzo.javaparser.parser.node.container.DefaultBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a switch statement.
 */
public class SwitchStatement extends Statement {
    private final @NotNull List<CaseBlock> cases;
    private final @NotNull DefaultBlock defaultBlock;

    /**
     * Instantiates a new Switch statement.
     *
     * @param expression   the expression
     * @param cases        the cases
     * @param defaultBlock the default block
     */
    public SwitchStatement(final @NotNull Node expression,
                           final @NotNull List<CaseBlock> cases,
                           final @NotNull DefaultBlock defaultBlock) {
        super(expression);
        this.cases = cases;
        this.defaultBlock = defaultBlock;
    }

}
