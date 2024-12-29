package it.fulminazzo.javaparser.typechecker.types.arrays

import it.fulminazzo.javaparser.typechecker.types.PrimitiveType
import it.fulminazzo.javaparser.typechecker.types.ValueType
import spock.lang.Specification

class ArrayClassTypeTest extends Specification {

    def 'test compatibleWith'() {
        given:
        // int[][][]
        def classType = new ArrayClassType(new ArrayClassType(new ArrayClassType(PrimitiveType.INT)))
        def type = new ArrayType(new ArrayType(new ArrayType(ValueType.NUMBER)))

        expect:
        classType.compatibleWith(type)
    }

    def 'test not compatibleWith #type'() {
        given:
        def classType = new ArrayClassType(PrimitiveType.INT)

        expect:
        !classType.compatibleWith(type)

        where:
        type << [
                ValueType.values(),
                new ArrayType(ValueType.BOOLEAN)
        ].flatten()
    }

}
