package it.fulminazzo.javaparser.typechecker.types;

/**
 * Represents all the values read by the parser.
 */
public enum TypeValue implements Type {
    NUMBER_TYPE,
    LONG_TYPE,
    DOUBLE_TYPE,
    FLOAT_TYPE,
    BOOLEAN_TYPE,
    CHARACTER_TYPE,
    STRING_TYPE
    ;
}
