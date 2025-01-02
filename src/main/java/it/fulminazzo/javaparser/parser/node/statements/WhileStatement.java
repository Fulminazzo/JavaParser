package it.fulminazzo.javaparser.parser.node.statements;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a while statement.
 */
public class WhileStatement extends Statement {
    private final @NotNull CodeBlock code;

    /**
     * Instantiates a new While statement.
     *
     * @param condition the condition
     * @param code      the code that will be executed
     */
    public WhileStatement(final @NotNull Node condition,
                          final @NotNull CodeBlock code) {
        super(condition);
        this.code = code;
    }

}
