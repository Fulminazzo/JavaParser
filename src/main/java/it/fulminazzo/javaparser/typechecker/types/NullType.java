package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the type associated with {@link it.fulminazzo.javaparser.tokenizer.TokenType#NULL}.
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public final class NullType implements Type {

    @Override
    public @NotNull ClassType toClassType() {
        throw TypeCheckerException.noClassType(getClass());
    }

    @Override
    public int hashCode() {
        return NullType.class.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof NullType;
    }

    @Override
    public String toString() {
        return "NULL_TYPE";
    }

}
