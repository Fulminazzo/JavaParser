package it.fulminazzo.javaparser.parser.node.statements;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an if statement.
 */
public class IfStatement extends Statement {
    private final @NotNull CodeBlock then;
    private final @NotNull Node elseBranch;

    /**
     * Instantiates a new If statement.
     *
     * @param condition  the condition
     * @param then       the code that will be executed
     * @param elseBranch the alternative code
     */
    public IfStatement(final @NotNull Node condition,
                       final @NotNull CodeBlock then,
                       final @NotNull Node elseBranch) {
        super(condition);
        this.then = then;
        this.elseBranch = elseBranch;
    }

}
