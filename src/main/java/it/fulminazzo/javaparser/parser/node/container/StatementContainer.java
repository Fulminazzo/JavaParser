package it.fulminazzo.javaparser.parser.node.container;

import it.fulminazzo.javaparser.parser.node.NodeImpl;
import it.fulminazzo.javaparser.parser.node.statements.Statement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

/**
 * Represents a general class that contains one or more statements.
 */
abstract class StatementContainer extends NodeImpl {
    protected final @NotNull LinkedList<Statement> statements;

    /**
     * Instantiates a new Statement container.
     *
     * @param statements the statements
     */
    public StatementContainer(final @NotNull LinkedList<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public @NotNull String toString() {
        return parseSingleListClassPrint();
    }

}
