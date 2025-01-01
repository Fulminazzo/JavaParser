package it.fulminazzo.javaparser.parser.node.container;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.statements.Statement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

/**
 * Represents the code block associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#CASE}.
 */
public class CaseBlock extends StatementContainer {
    private final @NotNull Node expr;

    /**
     * Instantiates a new Case block.
     *
     * @param expr the expression
     * @param statements the statements
     */
    public CaseBlock(final @NotNull Node expr, final @NotNull LinkedList<Statement> statements) {
        super(statements);
        this.expr = expr;
    }

}
