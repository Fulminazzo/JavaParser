package it.fulminazzo.javaparser.parser.node.statements;

import it.fulminazzo.javaparser.parser.node.container.CodeBlock;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents the statement associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#CATCH}.
 */
public class CatchStatement extends Statement {
    private final @NotNull List<Literal> exceptions;
    private final @NotNull CodeBlock block;

    /**
     * Instantiates a new Catch statement.
     *
     * @param exceptions     the exceptions
     * @param exceptionsName the exceptions variable name
     * @param block          the block to execute
     */
    public CatchStatement(final @NotNull List<Literal> exceptions,
                          final @NotNull Literal exceptionsName,
                          final @NotNull CodeBlock block) {
        super(exceptionsName);
        this.exceptions = exceptions;
        this.block = block;
    }

}
