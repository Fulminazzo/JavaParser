package it.fulminazzo.javaparser.typechecker.types;

import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType;
import org.jetbrains.annotations.NotNull;

/**
 * Contains all the values accepted by Java.
 */
public enum ValueType implements Type {
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
    STRING
    ;

    @Override
    public @NotNull ClassType toClassType() {
        switch (this) {
            case CHAR: return PrimitiveType.CHAR;
            case LONG: return PrimitiveType.LONG;
            case NUMBER: return PrimitiveType.INT;
            case FLOAT: return PrimitiveType.FLOAT;
            case DOUBLE: return PrimitiveType.DOUBLE;
            case BOOLEAN: return PrimitiveType.BOOLEAN;
            default: return ClassObjectType.STRING;
        }
    }

}
