package it.fulminazzo.mojito.typechecker.types;

import it.fulminazzo.mojito.typechecker.types.objects.ObjectType;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a primitive {@link Type} in Java.
 */
public enum PrimitiveType implements Type {
    /**
     * Type of {@link it.fulminazzo.mojito.tokenizer.TokenType#CHAR_VALUE}.
     */
    CHAR,
    /**
     * Type of {@link it.fulminazzo.mojito.tokenizer.TokenType#NUMBER_VALUE}.
     */
    INT,
    /**
     * Type of {@link it.fulminazzo.mojito.tokenizer.TokenType#LONG_VALUE}.
     */
    LONG,
    /**
     * Type of {@link it.fulminazzo.mojito.tokenizer.TokenType#FLOAT_VALUE}.
     */
    FLOAT,
    /**
     * Type of {@link it.fulminazzo.mojito.tokenizer.TokenType#DOUBLE_VALUE}.
     */
    DOUBLE,
    /**
     * Type of {@link it.fulminazzo.mojito.tokenizer.TokenType#BOOLEAN_VALUE}.
     */
    BOOLEAN,

    /*
        The following types do not have an associated regex.
        They can only be identified after declaring a variable.
     */
    BYTE,
    SHORT,
    ;

    @Override
    public @NotNull Type toWrapper() {
        switch (this) {
            case BYTE:
                return ObjectType.BYTE;
            case SHORT:
                return ObjectType.SHORT;
            case CHAR:
                return ObjectType.CHARACTER;
            case INT:
                return ObjectType.INTEGER;
            case LONG:
                return ObjectType.LONG;
            case FLOAT:
                return ObjectType.FLOAT;
            case DOUBLE:
                return ObjectType.DOUBLE;
            default:
                return ObjectType.BOOLEAN;
        }
    }

    @Override
    public @NotNull ClassType toClass() {
        switch (this) {
            case CHAR:
                return PrimitiveClassType.CHAR;
            case LONG:
                return PrimitiveClassType.LONG;
            case BYTE:
                return PrimitiveClassType.BYTE;
            case SHORT:
                return PrimitiveClassType.SHORT;
            case INT:
                return PrimitiveClassType.INT;
            case FLOAT:
                return PrimitiveClassType.FLOAT;
            case DOUBLE:
                return PrimitiveClassType.DOUBLE;
            default:
                return PrimitiveClassType.BOOLEAN;
        }
    }

    @Override
    public @NotNull String toString() {
        return Type.print(name() + "_VALUE");
    }

}
