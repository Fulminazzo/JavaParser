package it.fulminazzo.javaparser.typechecker.types;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Represents an {@link Object} value, declared from its associated class canonical name.
 */
@Getter(AccessLevel.PACKAGE)
public class ObjectType implements Type {
    private static final String[] IMPLIED_PACKAGES = new String[]{
            String.class.getPackage().getName(),
            Map.class.getPackage().getName(),
    };
    private final @NotNull Class<?> innerClass;

    private ObjectType(final @NotNull Class<?> innerClass) {
        this.innerClass = innerClass;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode() + this.innerClass.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ObjectType && this.innerClass.equals(((ObjectType) o).innerClass);
    }

    @Override
    public String toString() {
        String className = this.innerClass.getCanonicalName();
        return String.format("Type(%s)", className);
    }

}
