package it.fulminazzo.javaparser.parser.node;

import it.fulminazzo.javaparser.parser.node.literals.EmptyLiteral;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an assignment statement.
 */
public class Assignment extends NodeImpl {
    @Getter
    private final @NotNull Node type;
    @Getter
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

    /**
     * Checks whether the current assignment is initialized.
     * An uninitialized assignment is of type:
     * <code>LITERAL LITERAL;</code>
     *
     * @return true if it is
     */
    public boolean isInitialized() {
        return !this.value.is(EmptyLiteral.class);
    }

}
