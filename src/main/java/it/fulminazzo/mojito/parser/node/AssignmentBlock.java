package it.fulminazzo.mojito.parser.node;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Represents a collection of {@link Assignment}s separated by
 * {@link it.fulminazzo.mojito.tokenizer.TokenType#SEMICOLON}.
 */
public class AssignmentBlock extends NodeImpl {
    private final @NotNull List<Assignment> assignments;

    /**
     * Instantiates a new Assignment block.
     *
     * @param assignments the assignments
     */
    public AssignmentBlock(final @NotNull List<Assignment> assignments) {
        this.assignments = assignments;
    }

}
