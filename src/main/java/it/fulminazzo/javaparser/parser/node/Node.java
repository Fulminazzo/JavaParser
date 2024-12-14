package it.fulminazzo.javaparser.parser.node;

import it.fulminazzo.fulmicollection.objects.Refl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Collectors;

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

    @Override
    public @NotNull String toString() {
        Refl<?> refl = new Refl<>(this);
        return getClass().getSimpleName() + "(" + refl.getNonStaticFields().stream()
                .map(refl::getFieldObject)
                .map(o -> o == null ? "null" : o.toString())
                .map(Object::toString)
                .collect(Collectors.joining(", ")) + ")";
    }

    /**
     * Fixes the current object {@link #toString()} method
     * by removing brackets deriving from internal lists.
     *
     * @return the output
     */
    protected @NotNull String parseSingleListClassPrint() {
        String output = super.toString();
        final String className = getClass().getSimpleName();
        output = output.substring(className.length() + 2);
        output = output.substring(0, output.length() - 2);
        return String.format("%s(%s)", className, output);
    }

}
