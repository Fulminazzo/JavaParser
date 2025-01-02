package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.types.objects.ObjectClassType;
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType;
import org.jetbrains.annotations.NotNull;

/**
 * Contains all the values accepted by Java.
 */
public enum PrimitiveType implements Type {
    /**
     * Type of {@link it.fulminazzo.javaparser.tokenizer.TokenType#CHAR_VALUE}.
     */
    CHAR,
    /**
     * Type of {@link it.fulminazzo.javaparser.tokenizer.TokenType#NUMBER_VALUE}.
     */
    NUMBER,
    /**
     * Type of {@link it.fulminazzo.javaparser.tokenizer.TokenType#LONG_VALUE}.
     */
    LONG,
    /**
     * Type of {@link it.fulminazzo.javaparser.tokenizer.TokenType#DOUBLE_VALUE}.
     */
    DOUBLE,
    /**
     * Type of {@link it.fulminazzo.javaparser.tokenizer.TokenType#FLOAT_VALUE}.
     */
    FLOAT,
    /**
     * Type of {@link it.fulminazzo.javaparser.tokenizer.TokenType#BOOLEAN_VALUE}.
     */
    BOOLEAN,
    /**
     * Type of {@link it.fulminazzo.javaparser.tokenizer.TokenType#STRING_VALUE}.
     */
    STRING,

    /*
        The following types do not have an associated regex.
        They can only be identified after declaring a variable.
     */
    BYTE,
    SHORT,
    ;

    @Override
    public @NotNull ObjectType toWrapper() {
        switch (this) {
            case BYTE: return ObjectType.BYTE;
            case SHORT: return ObjectType.SHORT;
            case CHAR: return ObjectType.CHARACTER;
            case NUMBER: return ObjectType.INTEGER;
            case LONG: return ObjectType.LONG;
            case FLOAT: return ObjectType.FLOAT;
            case DOUBLE: return ObjectType.DOUBLE;
            case BOOLEAN: return ObjectType.BOOLEAN;
            default: return ObjectType.STRING;
        }
    }

    @Override
    public @NotNull ClassType toClassType() {
        switch (this) {
            case CHAR: return PrimitiveClassType.CHAR;
            case LONG: return PrimitiveClassType.LONG;
            case BYTE: return PrimitiveClassType.BYTE;
            case SHORT: return PrimitiveClassType.SHORT;
            case NUMBER: return PrimitiveClassType.INT;
            case FLOAT: return PrimitiveClassType.FLOAT;
            case DOUBLE: return PrimitiveClassType.DOUBLE;
            case BOOLEAN: return PrimitiveClassType.BOOLEAN;
            default: return ObjectClassType.STRING;
        }
    }

    @Override
    public String toString() {
        return Type.print(name() + "_VALUE");
    }

}
