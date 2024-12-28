package it.fulminazzo.javaparser.parser.node;

import it.fulminazzo.fulmicollection.objects.Refl;
import it.fulminazzo.javaparser.visitors.Visitor;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a general node of the parser.
 */
public interface Node {

    /**
     * Allows the visitor to visit this node.
     * It does so by looking for a <code>visit%NodeName%</code> method.
     *
     * @param visitor the visitor
     * @return the node converted
     * @param <T> the type returned by the visitor
     */
    default  <T> T accept(final @NotNull Visitor<T> visitor) {
        String methodName = "visit" + getClass().getSimpleName();
        Refl<?> node = new Refl<>(this);
        return new Refl<>(visitor).invokeMethod(methodName,
                node.getNonStaticFields().stream()
                        .map(node::getFieldObject)
                        .toArray(Object[]::new)
        );
    }

    /**
     * Checks whether the current node is of the specified type.
     *
     * @param nodeType the node type
     * @return true if it is
     */
    boolean is(final @NotNull Class<? extends Node> nodeType);

}
