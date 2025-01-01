package it.fulminazzo.javaparser.parser.node.container;

import it.fulminazzo.javaparser.parser.node.statements.Statement;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Represents the code block associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#DEFAULT}.
 */
public class DefaultBlock extends StatementContainer {

    /**
     * Instantiates a new Default block.
     *
     * @param statements the statements
     */
    public DefaultBlock(final Statement @NotNull ... statements) {
        this(new LinkedList<>(Arrays.asList(statements)));
    }

    /**
     * Instantiates a new Default block.
     *
     * @param statements the statements
     */
    public DefaultBlock(final @NotNull LinkedList<Statement> statements) {
        super(statements);
    }

}
