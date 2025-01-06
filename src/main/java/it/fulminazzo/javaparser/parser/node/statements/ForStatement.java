package it.fulminazzo.javaparser.parser.node.statements;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a for statement.
 */
public class ForStatement extends Statement {
    private final @NotNull Node assignment;
    private final @NotNull Node increment;
    private final @NotNull CodeBlock code;

    /**
     * Instantiates a new For statement.
     *
     * @param assignment the assignment
     * @param condition  the condition
     * @param increment  the increment
     * @param code       the code that will be executed
     */
    public ForStatement(final @NotNull Node assignment, final @NotNull Node condition,
                        final @NotNull Node increment, final @NotNull CodeBlock code) {
        super(condition);
        this.assignment = assignment;
        this.increment = increment;
        this.code = code;
    }

}
