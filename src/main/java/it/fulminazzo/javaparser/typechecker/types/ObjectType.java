package it.fulminazzo.javaparser.typechecker.types;

import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an {@link Object} value, declared from its associated class canonical name.
 */
@Getter(AccessLevel.PACKAGE)
public class ObjectType implements Type {
    private final @NotNull Class<?> innerClass;

    private ObjectType(final @NotNull Class<?> innerClass) {
        this.innerClass = innerClass;
    }

}
