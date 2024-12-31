package it.fulminazzo.javaparser.typechecker.types;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Types {
    public static final NoType NO_TYPE = new NoType();
}
