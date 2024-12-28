package it.fulminazzo.javaparser.parser.node;

import it.fulminazzo.javaparser.parser.node.types.Literal;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an assignment statement.
 */
public class Assignment extends Node {
    private final @NotNull Node type;
    private final @NotNull Literal name;
    private final @NotNull Node value;

    /**
     * Instantiates a new Assignment.
     *
     * @param type  the type
     * @param name  the name
     * @param value the value
     */
    public Assignment(final @NotNull Node type,
                      final @NotNull Literal name,
                      final @NotNull Node value) {
        this.type = type;
        this.name = name;
        this.value = value;
    }

}
