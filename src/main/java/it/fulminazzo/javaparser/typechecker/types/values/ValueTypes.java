package it.fulminazzo.javaparser.typechecker.types.values;

import it.fulminazzo.javaparser.typechecker.types.Type;

/**
 * Contains all the {@link ValueType}s representing the values accepted by Java.
 */
public enum ValueTypes implements Type {
    CHAR_TYPE,
    NUMBER_TYPE,
    LONG_TYPE,
    DOUBLE_TYPE,
    FLOAT_TYPE,
    BOOLEAN_TYPE,
    STRING_TYPE
    ;

}
