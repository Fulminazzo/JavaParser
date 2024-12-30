package it.fulminazzo.javaparser.typechecker.types;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Represents an absence of {@link Type}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NoType {
    public static final NoType NO_TYPE = new NoType();

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
