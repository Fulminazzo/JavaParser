package it.fulminazzo.javaparser.parser.node.statements;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the code block associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#CASE}.
 */
@Getter
public class CaseStatement extends Statement {
    private final @NotNull CodeBlock block;

    /**
     * Instantiates a new Case statement.
     *
     * @param expression the expression
     * @param block       the body
     */
    public CaseStatement(final @NotNull Node expression, final @NotNull CodeBlock block) {
        super(expression);
        this.block = block;
    }

}
