package it.fulminazzo.javaparser.typechecker.types.objects;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.ValueType;
import org.jetbrains.annotations.NotNull;

import static it.fulminazzo.javaparser.typechecker.types.ValueType.NUMBER;

/**
 * Represents an {@link Object} class type.
 */
public enum ClassObjectType implements ClassType {
    /**
     * {@link java.lang.Byte}
     */
    BYTE(ValueType.CHAR, NUMBER),
    /**
     * {@link java.lang.Character}
     */
    CHARACTER(ValueType.CHAR, NUMBER),
    /**
     * {@link java.lang.Short}
     */
    SHORT(ValueType.CHAR, NUMBER),
    /**
     * {@link java.lang.Integer}
     */
    INTEGER(ValueType.CHAR, NUMBER),
    /**
     * {@link java.lang.Long}
     */
    LONG(ValueType.CHAR, NUMBER, ValueType.LONG),
    /**
     * {@link java.lang.Float}
     */
    FLOAT(ValueType.CHAR, NUMBER, ValueType.LONG, ValueType.FLOAT),
    /**
     * {@link java.lang.Double}
     */
    DOUBLE(ValueType.CHAR, NUMBER, ValueType.LONG, ValueType.FLOAT, ValueType.DOUBLE),
    /**
     * {@link java.lang.Boolean}
     */
    BOOLEAN(ValueType.BOOLEAN),
    /**
     * {@link java.lang.String}
     */
    STRING(ValueType.STRING),
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

