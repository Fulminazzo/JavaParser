package it.fulminazzo.javaparser.parser.node.statements;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.types.EmptyLiteral;
import it.fulminazzo.javaparser.parser.node.types.Literal;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a general statement.
 */
public class Statement extends Node {
    private final @NotNull Node expr;

    /**
     * Instantiates a new Statement with expression {@link EmptyLiteral}.
     */
    public Statement() {
        this(new EmptyLiteral());
    }

    /**
     * Instantiates a new Statement.
     *
     * @param expr the expression
     */
    public Statement(final @NotNull Node expr) {
        this.expr = expr;
    }

}
