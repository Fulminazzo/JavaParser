package it.fulminazzo.javaparser.parser.node.literals;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.NodeImpl;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an array literal, an expression of the type <code>.*\[\]</code>.
 */
public class ArrayLiteral extends NodeImpl implements Literal {
    private final @NotNull Node type;

    /**
     * Instantiates a new Array literal.
     *
     * @param type the type of the elements
     */
    public ArrayLiteral(final @NotNull Node type) {
        this.type = type;
    }

}
