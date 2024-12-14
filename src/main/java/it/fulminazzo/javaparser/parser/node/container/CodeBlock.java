package it.fulminazzo.javaparser.parser.node.container;

import it.fulminazzo.javaparser.parser.node.statements.Statement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

/**
 * Represents a general code block contained in braces.
 */
public class CodeBlock extends StatementContainer {

    /**
     * Instantiates a new Code block.
     *
     * @param statements the statements
     */
    public CodeBlock(final @NotNull LinkedList<Statement> statements) {
        super(statements);
    }

}
