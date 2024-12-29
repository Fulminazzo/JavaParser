package it.fulminazzo.javaparser.typechecker.types.objects;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an {@link Object} class type.
 */
public enum ClassObjectType implements ClassType {
    ;

    private final Type @NotNull [] compatibleTypes;

    ClassObjectType(final Type @NotNull ... compatibleTypes) {
        this.compatibleTypes = compatibleTypes;
    }

    @Override
    public boolean compatibleWith(@NotNull Type type) {
        for (Type compatibleType : this.compatibleTypes)
            if (compatibleType.equals(type)) return true;
        return false;
    }

}

