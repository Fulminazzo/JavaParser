package it.fulminazzo.javaparser.parser.node;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a collection of {@link Assignment}s separated by
 * {@link it.fulminazzo.javaparser.tokenizer.TokenType#SEMICOLON}.
 */
public class AssignmentBlock extends NodeImpl {
    private final List<Assignment> assignments;

    /**
     * Instantiates a new Assignment block.
     *
     * @param assignments the assignments
     */
    public AssignmentBlock(final @NotNull List<Assignment> assignments) {
        this.assignments = assignments;
    }

}
