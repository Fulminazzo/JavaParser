package it.fulminazzo.javaparser.typechecker.types

import it.fulminazzo.javaparser.typechecker.types.objects.ClassObjectType
import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.ValueType.*

class ValueTypeTest extends Specification {

    def 'test toWrapper of #type should return #expected'() {
        when:
        def actual = type.toWrapper()

        then:
        actual == expected

        where:
        type    | expected
        BYTE    | ObjectType.BYTE
        SHORT   | ObjectType.SHORT
        CHAR    | ObjectType.CHARACTER
        NUMBER  | ObjectType.INTEGER
        LONG    | ObjectType.LONG
        FLOAT   | ObjectType.FLOAT
        DOUBLE  | ObjectType.DOUBLE
        BOOLEAN | ObjectType.BOOLEAN
        STRING  | ObjectType.STRING
    }

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