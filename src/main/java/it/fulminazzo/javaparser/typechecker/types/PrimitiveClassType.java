package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.TypeCheckerException;
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Represents a primitive {@link Type} name.
 */
public enum PrimitiveClassType implements ClassType {
    /**
     * <code>byte</code>
     */
    BYTE(PrimitiveType.BYTE,
            ObjectType.BYTE),
    /**
     * <code>short</code>
     */
    SHORT(PrimitiveType.BYTE, PrimitiveType.SHORT,
            ObjectType.BYTE, ObjectType.SHORT),
    /**
     * <code>char</code>
     */
    CHAR(PrimitiveType.CHAR, PrimitiveType.INT,
            ObjectType.CHARACTER),
    /**
     * <code>int</code>
     */
    INT(PrimitiveType.BYTE, PrimitiveType.SHORT, PrimitiveType.CHAR, PrimitiveType.INT,
            ObjectType.BYTE, ObjectType.SHORT, ObjectType.CHARACTER, ObjectType.INTEGER),
    /**
     * <code>long</code>
     */
    LONG(PrimitiveType.BYTE, PrimitiveType.SHORT, PrimitiveType.CHAR, PrimitiveType.INT, PrimitiveType.LONG,
            ObjectType.BYTE, ObjectType.SHORT, ObjectType.CHARACTER, ObjectType.INTEGER, ObjectType.LONG),
    /**
     * <code>float</code>
     */
    FLOAT(PrimitiveType.BYTE, PrimitiveType.SHORT, PrimitiveType.CHAR, PrimitiveType.INT, PrimitiveType.LONG,
            PrimitiveType.FLOAT,
            ObjectType.BYTE, ObjectType.SHORT, ObjectType.CHARACTER, ObjectType.INTEGER, ObjectType.LONG,
            ObjectType.FLOAT),
    /**
     * <code>double</code>
     */
    DOUBLE(PrimitiveType.BYTE, PrimitiveType.SHORT, PrimitiveType.CHAR, PrimitiveType.INT, PrimitiveType.LONG,
            PrimitiveType.FLOAT, PrimitiveType.DOUBLE,
            ObjectType.BYTE, ObjectType.SHORT, ObjectType.CHARACTER, ObjectType.INTEGER, ObjectType.LONG,
            ObjectType.FLOAT, ObjectType.DOUBLE),
    /**
     * <code>boolean</code>
     */
    BOOLEAN(PrimitiveType.BOOLEAN,
            ObjectType.BOOLEAN),
    ;

    private final Type @NotNull [] compatibleTypes;

    PrimitiveClassType(final Type @NotNull ... compatibleTypes) {
        this.compatibleTypes = compatibleTypes;
    }

    @Override
    public @NotNull Type toType() {
        switch (this) {
            case BYTE:
                return PrimitiveType.BYTE;
            case SHORT:
                return PrimitiveType.SHORT;
            case INT:
                return PrimitiveType.INT;
            case CHAR:
                return PrimitiveType.CHAR;
            case LONG:
                return PrimitiveType.LONG;
            case FLOAT:
                return PrimitiveType.FLOAT;
            case DOUBLE:
                return PrimitiveType.DOUBLE;
            default:
                return PrimitiveType.BOOLEAN;
        }
    }

    @Override
    public @NotNull Type cast(@NotNull Type type) {
        if (this != BOOLEAN && type.is(Arrays.stream(OperationUtils.getDecimalTypes())
                .filter(Type::isPrimitive).toArray(Type[]::new)))
            return toType();
        for (Type compatibleType : this.compatibleTypes)
            if (compatibleType.is(type)) return toType();
        throw TypeCheckerException.invalidCast(this, type);
    }

    @Override
    public @NotNull Class<?> toJavaClass() {
        switch (this) {
            case BYTE:
                return byte.class;
            case CHAR:
                return char.class;
            case SHORT:
                return short.class;
            case INT:
                return int.class;
            case LONG:
                return long.class;
            case FLOAT:
                return float.class;
            case DOUBLE:
                return double.class;
            default:
                return boolean.class;
        }
    }

    @Override
    public boolean compatibleWith(@NotNull Type type) {
        for (Type compatibleType : this.compatibleTypes)
            if (compatibleType.is(type)) return true;
        return (equals(BYTE) || equals(SHORT)) && type.is(PrimitiveType.INT);
    }

    @Override
    public String toString() {
        return ClassType.print(name().toLowerCase());
    }

}
