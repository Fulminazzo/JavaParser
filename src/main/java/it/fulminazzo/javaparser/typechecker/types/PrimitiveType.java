package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.OperationUtils;
import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a primitive {@link Type} name.
 */
public enum PrimitiveType implements ClassType {
    //TODO: byte should be compatible only with 'c', not char c;
    //TODO: byte should be compatible only with 1, not int i;
    /**
     * <code>byte</code>
     */
    BYTE(ValueType.BYTE, ValueType.CHAR, ValueType.NUMBER,
            ObjectType.BYTE),
    //TODO: short should be compatible only with 'c', not char c;
    //TODO: short should be compatible only with 1, not int i;
    /**
     * <code>short</code>
     */
    SHORT(ValueType.BYTE, ValueType.SHORT, ValueType.CHAR, ValueType.NUMBER,
            ObjectType.BYTE, ObjectType.SHORT),
    /**
     * <code>char</code>
     */
    CHAR(ValueType.CHAR, ValueType.NUMBER,
            ObjectType.CHARACTER),
    /**
     * <code>int</code>
     */
    INT(ValueType.BYTE, ValueType.SHORT, ValueType.CHAR, ValueType.NUMBER,
            ObjectType.BYTE, ObjectType.SHORT, ObjectType.CHARACTER, ObjectType.INTEGER),
    /**
     * <code>long</code>
     */
    LONG(ValueType.BYTE, ValueType.SHORT, ValueType.CHAR, ValueType.NUMBER, ValueType.LONG,
            ObjectType.BYTE, ObjectType.SHORT, ObjectType.CHARACTER, ObjectType.INTEGER, ObjectType.LONG),
    /**
     * <code>float</code>
     */
    FLOAT(ValueType.BYTE, ValueType.SHORT, ValueType.CHAR, ValueType.NUMBER, ValueType.LONG,
            ValueType.FLOAT,
            ObjectType.BYTE, ObjectType.SHORT, ObjectType.CHARACTER, ObjectType.INTEGER, ObjectType.LONG,
            ObjectType.FLOAT),
    /**
     * <code>double</code>
     */
    DOUBLE(ValueType.BYTE, ValueType.SHORT, ValueType.CHAR, ValueType.NUMBER, ValueType.LONG,
            ValueType.FLOAT, ValueType.DOUBLE,
            ObjectType.BYTE, ObjectType.SHORT, ObjectType.CHARACTER, ObjectType.INTEGER, ObjectType.LONG,
            ObjectType.FLOAT, ObjectType.DOUBLE),
    /**
     * <code>boolean</code>
     */
    BOOLEAN(ValueType.BOOLEAN,
            ObjectType.BOOLEAN),
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
        if (this != BOOLEAN && type.is(OperationUtils.getDecimalTypes()))
            return toType();
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
        return ClassType.print(name().toLowerCase());
    }

}
