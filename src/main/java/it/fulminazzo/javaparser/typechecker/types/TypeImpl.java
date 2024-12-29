package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.fulmicollection.utils.StringUtils;

/**
 * Represents a general implementation of {@link Type}.
 */
abstract class TypeImpl implements Type {

    @Override
    public boolean equals(Object o) {
        return o != null && getClass().equals(o.getClass());
    }

    @Override
    public int hashCode() {
        return getClass().getCanonicalName().hashCode();
    }

    @Override
    public String toString() {
        return StringUtils.capitalize(getClass().getSimpleName());
    }

}
