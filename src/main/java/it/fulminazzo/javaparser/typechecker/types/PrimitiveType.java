package it.fulminazzo.javaparser.typechecker.types;

import org.jetbrains.annotations.NotNull;

import static it.fulminazzo.javaparser.typechecker.types.ValueTypes.*;

public enum PrimitiveType implements ClassType {
    BYTE(CHAR_TYPE, NUMBER_TYPE),
    CHAR(CHAR_TYPE, NUMBER_TYPE),
    SHORT(CHAR_TYPE, NUMBER_TYPE),
    INT(CHAR_TYPE, NUMBER_TYPE),
    LONG(CHAR_TYPE, NUMBER_TYPE, LONG_TYPE),
    FLOAT(CHAR_TYPE, NUMBER_TYPE, LONG_TYPE, FLOAT_TYPE),
    DOUBLE(CHAR_TYPE, NUMBER_TYPE, LONG_TYPE, FLOAT_TYPE, DOUBLE_TYPE),
    BOOLEAN(BOOLEAN_TYPE),
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
