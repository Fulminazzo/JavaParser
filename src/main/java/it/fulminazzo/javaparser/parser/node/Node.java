package it.fulminazzo.javaparser.parser.node;

import it.fulminazzo.fulmicollection.objects.Refl;

import java.util.Objects;

/**
 * Represents a general node of the parser.
 */
public abstract class Node {

    @Override
    public int hashCode() {
        Refl<?> refl = new Refl<>(this);
        return refl.getNonStaticFields().stream()
                .map(refl::getFieldObject)
                .filter(Objects::nonNull)
                .mapToInt(Object::hashCode)
                .sum();
    }

}
