package it.fulminazzo.javaparser.typechecker.types.values;

import it.fulminazzo.javaparser.typechecker.types.EnumType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Represents a literal value accepted by the language.
 */
@NoArgsConstructor(access = AccessLevel.PACKAGE)
abstract class ValueType extends EnumType {

}
