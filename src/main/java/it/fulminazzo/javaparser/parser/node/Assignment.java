package it.fulminazzo.javaparser.parser.node;

import it.fulminazzo.javaparser.parser.node.operators.binary.ReAssign;
import it.fulminazzo.javaparser.parser.node.types.Literal;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an assignment statement.
 */
public class Assignment extends Node {
    private final @NotNull Literal type;
    private final @NotNull Node assignment;

    /**
     * Instantiates a new Assignment.
     *
     * @param type       the type
     * @param assignment the assignment
     */
    public Assignment(final @NotNull Literal type,
                      final @NotNull Node assignment) {
        this.type = type;
        this.assignment = assignment;
    }

}
