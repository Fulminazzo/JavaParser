package it.fulminazzo.javaparser.parser.node.statements;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a do statement.
 */
public class DoStatement extends Statement {
    private final @NotNull CodeBlock code;

    /**
     * Instantiates a new Do statement.
     *
     * @param condition  the condition
     * @param code       the code that will be executed
     */
    public DoStatement(final @NotNull Node condition,
                       final @NotNull CodeBlock code) {
        super(condition);
        this.code = code;
    }

}
