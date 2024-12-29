package it.fulminazzo.javaparser.typechecker.types.values;

import it.fulminazzo.javaparser.typechecker.types.EnumType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a literal value accepted by the language.
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
abstract class ValueType implements EnumType {

    @Override
    public @NotNull String name() {
        return name(ValueTypes.class);
    }

    @Override
    public String toString() {
        return name();
    }

}
