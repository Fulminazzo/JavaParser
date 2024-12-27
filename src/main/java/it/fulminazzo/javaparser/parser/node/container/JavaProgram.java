package it.fulminazzo.javaparser.parser.node.container;

import it.fulminazzo.javaparser.parser.node.statements.Statement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

/**
 * Represents the starting point of a Java program.
 */
public class JavaProgram extends StatementContainer {

    /**
     * Instantiates a new Code block.
     *
     * @param statements the statements
     */
    public JavaProgram(final @NotNull LinkedList<Statement> statements) {
        super(statements);
    }

}
