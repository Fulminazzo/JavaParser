package it.fulminazzo.mojito.environment.scopetypes;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the initial {@link ScopeType} of a scoped object.
 */
@NoArgsConstructor
final class MainScopeType implements ScopeType {

    @Override
    public @NotNull String name() {
        return "MAIN";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MainScopeType;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public @NotNull String toString() {
        return name();
    }

}
