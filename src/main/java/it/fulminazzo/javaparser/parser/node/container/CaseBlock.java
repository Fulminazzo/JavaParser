package it.fulminazzo.javaparser.parser.node.container;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.statements.Statement;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Represents the code block associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#CASE}.
 */
@Getter
public class CaseBlock extends StatementContainer {
    private final @NotNull Node expression;

    /**
     * Instantiates a new Case block.
     *
     * @param expression the expression
     * @param statements the statements
     */
    public CaseBlock(final @NotNull Node expression, final Statement @NotNull ... statements) {
        this(expression, new LinkedList<>(Arrays.asList(statements)));
    }

    /**
     * Instantiates a new Case block.
     *
     * @param expression the expression
     * @param statements the statements
     */
    public CaseBlock(final @NotNull Node expression, final @NotNull LinkedList<Statement> statements) {
        super(statements);
        this.expression = expression;
    }

}
