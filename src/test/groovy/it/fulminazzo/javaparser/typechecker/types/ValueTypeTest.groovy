package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.ValueType.*

class ValueTypeTest extends Specification {

    def 'test #type toClassType should return #expected'() {
        given:
        def actual = type.toClassType()

        expect:
        actual == expected

        where:
        type    | expected
        BYTE    | PrimitiveType.BYTE
        SHORT   | PrimitiveType.SHORT
        CHAR    | PrimitiveType.CHAR
        NUMBER  | PrimitiveType.INT
        LONG    | PrimitiveType.LONG
        FLOAT   | PrimitiveType.FLOAT
        DOUBLE  | PrimitiveType.DOUBLE
        BOOLEAN | PrimitiveType.BOOLEAN
        STRING  | ClassObjectType.STRING
    }
    
}