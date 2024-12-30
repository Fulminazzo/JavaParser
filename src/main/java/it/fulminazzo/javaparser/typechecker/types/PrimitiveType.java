package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a primitive {@link Type} name.
 */
public enum PrimitiveType implements ClassType {
    /**
     * <code>byte</code>
     */
    BYTE(ValueType.CHAR, ValueType.NUMBER,
            ObjectType.BYTE),
    /**
     * <code>char</code>
     */
    CHAR(ValueType.CHAR, ValueType.NUMBER,
            ObjectType.CHARACTER),
    /**
     * <code>short</code>
     */
    SHORT(ValueType.CHAR, ValueType.NUMBER,
            ObjectType.BYTE, ObjectType.SHORT),
    /**
     * <code>int</code>
     */
    INT(ValueType.CHAR, ValueType.NUMBER, ObjectType.BYTE,
            ObjectType.CHARACTER, ObjectType.SHORT, ObjectType.INTEGER),
    /**
     * <code>long</code>
     */
    LONG(ValueType.CHAR, ValueType.NUMBER, ValueType.LONG, ObjectType.BYTE,
            ObjectType.CHARACTER, ObjectType.SHORT, ObjectType.INTEGER, ObjectType.LONG),
    /**
     * <code>float</code>
     */
    FLOAT(ValueType.CHAR, ValueType.NUMBER, ValueType.LONG, ValueType.FLOAT, ObjectType.BYTE,
            ObjectType.CHARACTER, ObjectType.SHORT, ObjectType.INTEGER, ObjectType.LONG, ObjectType.FLOAT),
    /**
     * <code>double</code>
     */
    DOUBLE(ValueType.CHAR, ValueType.NUMBER, ValueType.LONG, ValueType.FLOAT, ValueType.DOUBLE, ObjectType.BYTE,
            ObjectType.CHARACTER, ObjectType.SHORT, ObjectType.INTEGER, ObjectType.LONG, ObjectType.FLOAT, ObjectType.DOUBLE),
    /**
     * <code>boolean</code>
     */
    BOOLEAN(ValueType.BOOLEAN, ObjectType.BOOLEAN),
    ;

    private final Type @NotNull [] compatibleTypes;

    PrimitiveType(final Type @NotNull ... compatibleTypes) {
        this.compatibleTypes = compatibleTypes;
    }

    @Override
    public @NotNull Type toType() {
        switch (this) {
            case BYTE: return ValueType.BYTE;
            case SHORT: return ValueType.SHORT;
            case INT: return ValueType.NUMBER;
            case CHAR: return ValueType.CHAR;
            case LONG: return ValueType.LONG;
            case FLOAT: return ValueType.FLOAT;
            case DOUBLE: return ValueType.DOUBLE;
            default: return ValueType.BOOLEAN;
        }
    }

    @Override
    public @NotNull Type cast(@NotNull Type type) {
        for (Type compatibleType : this.compatibleTypes)
            if (compatibleType.is(type)) return toType();
        throw TypeCheckerException.invalidCast(this, type);
    }

    @Override
    public @NotNull Class<?> toJavaClass() {
        switch (this) {
            case BYTE: return byte.class;
            case CHAR: return char.class;
            case SHORT: return short.class;
            case INT: return int.class;
            case LONG: return long.class;
            case FLOAT: return float.class;
            case DOUBLE: return double.class;
            default: return boolean.class;
        }
    }

    @Override
    public boolean compatibleWith(@NotNull Type type) {
        for (Type compatibleType : this.compatibleTypes)
            if (compatibleType.is(type)) return true;
        return false;
    }

    @Override
    public String toString() {
        return name().toLowerCase() + ".class";
    }

}
