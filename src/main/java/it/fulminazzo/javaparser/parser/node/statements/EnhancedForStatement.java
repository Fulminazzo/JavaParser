package it.fulminazzo.javaparser.parser.node.statements;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a for statement in an enhanced form.
 */
public class EnhancedForStatement extends Statement {
    private final @NotNull Node type;
    private final @NotNull Node variable;
    private final @NotNull CodeBlock code;

    /**
     * Instantiates a new Enhanced for statement.
     *
     * @param type       the type of the variable
     * @param variable   the variable name
     * @param expression the iterable expression
     * @param code       the code that will be executed
     */
    public EnhancedForStatement(@NotNull Node type, @NotNull Node variable,
                                @NotNull Node expression, @NotNull CodeBlock code) {
        super(expression);
        this.type = type;
        this.variable = variable;
        this.code = code;
    }

}
