package it.fulminazzo.javaparser.parser.node.container;

import it.fulminazzo.javaparser.parser.node.statements.Statement;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Represents a general code block contained in braces.
 */
public class CodeBlock extends StatementContainer {

    public CodeBlock(final Statement @NotNull ... statements) {
        super(new LinkedList<>(Arrays.asList(statements)));
    }

    /**
     * Instantiates a new Code block.
     *
     * @param statements the statements
     */
    public CodeBlock(final @NotNull LinkedList<Statement> statements) {
        super(statements);
    }

}
