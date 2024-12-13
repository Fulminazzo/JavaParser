package it.fulminazzo.javaparser.parser.node;

import it.fulminazzo.fulmicollection.objects.Refl;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
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

    @Override
    public boolean equals(final @Nullable Object o) {
        if (o != null && getClass() == o.getClass()) {
            Refl<?> refl = new Refl<>(this);
            Refl<?> other = new Refl<>(o);
            for (final Field field : refl.getNonStaticFields())
                if (!Objects.equals(refl.getFieldObject(field), other.getFieldObject(field)))
                    return false;
            return true;
        }
        return false;
    }

}
