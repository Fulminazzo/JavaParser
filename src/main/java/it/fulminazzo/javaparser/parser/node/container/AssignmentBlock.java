package it.fulminazzo.javaparser.parser.node.container;

import it.fulminazzo.javaparser.parser.node.Assignment;
import it.fulminazzo.javaparser.parser.node.statements.Statement;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a collection of {@link Assignment}s separated by
 * {@link it.fulminazzo.javaparser.tokenizer.TokenType#SEMICOLON}.
 */
public class AssignmentBlock extends StatementContainer {

    /**
     * Instantiates a new Assignment block.
     *
     * @param assignments the assignments
     */
    public AssignmentBlock(final @NotNull List<Assignment> assignments) {
        super(assignments.stream()
                .map(Statement::new)
                .collect(Collectors.toCollection(LinkedList::new)));
    }

}
