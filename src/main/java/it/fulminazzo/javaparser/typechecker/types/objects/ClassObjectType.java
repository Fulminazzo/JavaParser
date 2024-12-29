package it.fulminazzo.javaparser.typechecker.types.objects;

import it.fulminazzo.javaparser.typechecker.types.ClassType;
import it.fulminazzo.javaparser.typechecker.types.PrimitiveType;
import it.fulminazzo.javaparser.typechecker.types.Type;
import it.fulminazzo.javaparser.typechecker.types.ValueType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an {@link Object} class type.
 */
public enum ClassObjectType implements ClassType {
    /**
     * {@link java.lang.Byte}
     */
    BYTE(PrimitiveType.BYTE),
    /**
     * {@link java.lang.Character}
     */
    CHARACTER(PrimitiveType.CHAR),
    /**
     * {@link java.lang.Short}
     */
    SHORT(PrimitiveType.SHORT),
    /**
     * {@link java.lang.Integer}
     */
    INTEGER(PrimitiveType.INT),
    /**
     * {@link java.lang.Long}
     */
    LONG(PrimitiveType.LONG),
    /**
     * {@link java.lang.Float}
     */
    FLOAT(PrimitiveType.FLOAT),
    /**
     * {@link java.lang.Double}
     */
    DOUBLE(PrimitiveType.DOUBLE),
    /**
     * {@link java.lang.Boolean}
     */
    BOOLEAN(PrimitiveType.BOOLEAN),
    /**
     * {@link java.lang.String}
     */
    STRING,
    /**
     * {@link java.lang.Object}
     */
    OBJECT,
    ;

    private final @Nullable PrimitiveType associatedType;

    ClassObjectType() {
        this(null);
    }

    ClassObjectType(final @Nullable PrimitiveType associatedType) {
        this.associatedType = associatedType;
    }

    @Override
    public boolean compatibleWith(@NotNull Type type) {
        if (this.associatedType != null) return this.associatedType.compatibleWith(type);
        else {
            // Either STRING or OBJECT
            if (this == STRING)
                return ValueType.STRING.equals(type) || ObjectType.STRING.equals(type);
            else return true;
        }
    }

}

