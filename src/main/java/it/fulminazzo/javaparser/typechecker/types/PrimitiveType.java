package it.fulminazzo.javaparser.typechecker.types;

import org.jetbrains.annotations.NotNull;

import static it.fulminazzo.javaparser.typechecker.types.ValueType.*;

/**
 * Represents a primitive {@link Type} name.
 */
public enum PrimitiveType implements ClassType {
    BYTE(ValueType.CHAR, NUMBER),
    CHAR(ValueType.CHAR, NUMBER),
    SHORT(ValueType.CHAR, NUMBER),
    INT(ValueType.CHAR, NUMBER),
    LONG(ValueType.CHAR, NUMBER, ValueType.LONG),
    FLOAT(ValueType.CHAR, NUMBER, ValueType.LONG, ValueType.FLOAT),
    DOUBLE(ValueType.CHAR, NUMBER, ValueType.LONG, ValueType.FLOAT, ValueType.DOUBLE),
    BOOLEAN(ValueType.BOOLEAN),
    ;

    private final Type @NotNull [] compatibleTypes;

    PrimitiveType(final Type @NotNull ... compatibleTypes) {
        this.compatibleTypes = compatibleTypes;
    }

    @Override
    public boolean compatibleWith(@NotNull Type type) {
        for (Type compatibleType : this.compatibleTypes)
            if (compatibleType.equals(type)) return true;
        return false;
    }

}
