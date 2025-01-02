package it.fulminazzo.javaparser.typechecker.types


import it.fulminazzo.javaparser.typechecker.types.objects.ObjectType
import spock.lang.Specification

import static it.fulminazzo.javaparser.typechecker.types.PrimitiveType.*

class PrimitiveTypeTest extends Specification {

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
    }

    def 'test #type toClassType should return #expected'() {
        given:
        def actual = type.toClassType()

        expect:
        actual == expected

        where:
        type    | expected
        BYTE    | PrimitiveClassType.BYTE
        SHORT   | PrimitiveClassType.SHORT
        CHAR    | PrimitiveClassType.CHAR
        NUMBER  | PrimitiveClassType.INT
        LONG    | PrimitiveClassType.LONG
        FLOAT   | PrimitiveClassType.FLOAT
        DOUBLE  | PrimitiveClassType.DOUBLE
        BOOLEAN | PrimitiveClassType.BOOLEAN
    }

}