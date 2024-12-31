package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an absence of {@link Type}.
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class NoType implements Type {

    @Override
    public @NotNull ClassType toClassType() {
        throw TypeCheckerException.noClassType(getClass());
    }

    @Override
    public int hashCode() {
        return NoType.class.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof NoType;
    }

    @Override
    public String toString() {
        return "NONE";
    }

}
