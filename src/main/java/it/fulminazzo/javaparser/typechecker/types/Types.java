package it.fulminazzo.javaparser.typechecker.types;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * A collection of static immutable {@link Type}s.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Types {
    public static final @NotNull NoType NO_TYPE = new NoType();

}
