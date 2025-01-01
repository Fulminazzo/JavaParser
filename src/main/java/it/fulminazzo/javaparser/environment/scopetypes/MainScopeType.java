package it.fulminazzo.javaparser.environment.scopetypes;

import lombok.NoArgsConstructor;

/**
 * Represents the initial {@link ScopeType} of a scoped object.
 */
@NoArgsConstructor
final class MainScopeType implements ScopeType {

    @Override
    public String name() {
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
    public String toString() {
        return name();
    }

}
