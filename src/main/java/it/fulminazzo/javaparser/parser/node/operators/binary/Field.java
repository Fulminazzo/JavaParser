package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the retrieval of a field pointer:
 * <code>%object%.%field%</code>
 */
public class Field extends BinaryOperation {

    /**
     * Instantiates a new Field operation.
     *
     * @param object  the object
     * @param fieldName the field name
     */
    public Field(@NotNull Node object, @NotNull Literal fieldName) {
        super(object, fieldName);
    }

}
