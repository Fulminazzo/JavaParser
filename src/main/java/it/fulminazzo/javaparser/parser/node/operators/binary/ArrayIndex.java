package it.fulminazzo.javaparser.parser.node.operators.binary;

import it.fulminazzo.javaparser.parser.node.Node;
import it.fulminazzo.javaparser.parser.node.literals.Literal;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the retrieval of an array element:
 * <code>%array%[%index%]</code>
 */
public class ArrayIndex extends BinaryOperation implements Literal {

    /**
     * Instantiates a new Array index.
     *
     * @param array the array
     * @param index the index
     */
    public ArrayIndex(@NotNull Node array, @NotNull Node index) {
        super(array, index);
    }

    @Override
    public @NotNull String getLiteral() {
        return String.format("%s[%s]", this.left, this.right);
    }

}
